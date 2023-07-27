package toyLottery.presenter;

import toyLottery.model.Toy;
import toyLottery.model.ToyType;

import java.util.*;

public class ToyLottery {
    private final Random rnd;
    private final String SEPARATOR = "-".repeat(55);
    private  static ToyLottery instance;
    private final Scanner myScanner;
    private final List<ToyTypeModel> toyTypes;
    private final List<ToyModel> prizes;
    private final FileReadWriteModel readWriter;

    private ToyLottery(FileReadWriteModel readWriter){
        rnd = new Random();
        myScanner = new Scanner(System.in);
        toyTypes = new LinkedList<>();
        prizes = new LinkedList<>();
        this.readWriter = readWriter;

    }

    /**
     * Gets instance this singleton
     * @return ToyLottery instance
     */
    public static ToyLottery getInstance(FileReadWriteModel readWriter){
        if (instance == null) {
            instance = new ToyLottery(readWriter);
        }
        return instance;
    }

    /**
     * Starts the display
     */
    public void start() {
        int choise = mainMenu();
        while (choise !=0) {
            switch (choise) {
                case 1 -> this.readToysFromFile();
                case 2 -> this.writeToysToFile();
                case 3 -> this.displayPrizes();
                case 4 -> this.loadDemo();
                case 5 -> this.menuAddToy();
                case 6 -> this.menuChangeWeight();
                case 7 -> this.menuLottery();
            }
            choise = mainMenu();
        }
        System.out.println("The end!");

    }

    /**
     * Provides a choice to the user from main menu
     * @return int choice
     */
    private int mainMenu() {
        System.out.println();
        if (toyTypes.size() > 0) {
            System.out.println(SEPARATOR);
            toyTypes.forEach(System.out::println);
        } else {
            System.out.println("Магазин пуст");
        }
        System.out.println(SEPARATOR);
        return getIntFromConsole("""
                [0] Выход           [1] Чтение из файла  [2] Запись в файл
                [3] Показать призы  [4] Загрузить демо   [5] Добавить
                [6] Изменить вес    [7] Розыгрыш приза""");

    }

    /**
     * Provides the user with the ability to add toys to the collection
     */
    private void menuAddToy() {
        System.out.println("Название игрушки:");
        String name = myScanner.nextLine();
        int number = getIntFromConsole("Количество игрушек");
        if (number <= 0) menuAddToy();
        this.addToys(name, number);
    }

    /**
     * Provides the user with the ability to change id toyType in the lottery
     */
    private void menuChangeWeight() {
        if (toyTypes.size() > 0) {
            int id = this.getIntFromConsole("Введите id игрушки:");
            int index = this.getIndexOf(id);
            if (index == -1) {
                System.out.println("Нет игрушки с таким id.");
                return;
            }
            int weight = this.getIntFromConsole("Введите вес:");
            if (weight < 0) {
                System.out.println("Вес не может быть отрицательным числом.");
                return;
            }
            toyTypes.get(index).setWeight(weight);
        }
    }

    /**
     * Displays lottery result
     */
    private void menuLottery() {
        if (toyTypes.size() > 0) {
            String prize = lotteryRun();
            prizes.add(extractPrize(prize));
            removeEmptyToyType();
            writePrizesToFile();
            System.out.printf("Ваш приз:\n-%s-\nСписок призов сохранён в файле ./prizes.csv", prize);
        }
    }

    /**
     * Checks and removes empty ToyType
     */
    private void removeEmptyToyType() {
        int index = -1;
        for (int i = 0; i < toyTypes.size(); i++) {
            if (toyTypes.get(i).getToys().size() == 0) {
                index = i;
            }
        }
        if (index >= 0) toyTypes.remove(index);
    }

    /**
     * Loads demo to this.toyTypes
     */
    private void loadDemo() {
        toyTypes.clear();
        this.addToys("Самолёт", 3);
        this.addToys("Грузовик", 2);
        this.addToys("Монстр-трак", 3);
        this.addToys("Велосипед", 1);
        this.addToys("Пистолет", 3);
        this.addToys("Робот", 2);
    }

    /**
     * Display prizes
     */
    private void displayPrizes() {
        if (prizes.size() == 0) {
            System.out.println("Список призов пуст.");
        } else {
            System.out.println("-Список призов-");
            prizes.forEach(x -> System.out.printf("name: %-20s  id: %-2d\n", x.getName(), x.getID()));
        }
    }

    /**
     * Writes prizes to file
     */
    private void writePrizesToFile() {
        List<String> listPrizes = new LinkedList<>();
        prizes.forEach(x -> listPrizes.add(String.format("%s;%d", x.getName(), x.getID())));
        readWriter.writeFile("/prizes.csv", listPrizes);
    }

    /**
     * Extracts toy from toyTypes
     * @param name String toy name
     * @return Toy instance
     */
    private ToyModel extractPrize(String name) {
        return toyTypes.get(getIndexOf(name)).removeToy();
    }

    /**
     * Writes all toys from stock to file
     */
    private void writeToysToFile() {
        List<String> listToys = new LinkedList<>();
        for (ToyTypeModel toyType: toyTypes) {
            toyType.getToys().forEach(x -> listToys.add(String.format("%s;%d",x.getName(), x.getID())));
        }
        readWriter.writeFile("/toys.csv", listToys);
        System.out.println("Список игрушек записан в файл ./toys.csv");
    }

    /**
     * Reads toys from file
     */
    private void readToysFromFile() {
        List<String> listToys = readWriter.readFile("/toys.csv");
        toyTypes.clear();
        int index;
        String[] array;
        Toy toy;
        ToyType toyType;
        for (String line: listToys) {
            array = line.split(";");
            index = getIndexOf(array[0]);
            if (index == -1) {
                toyType = new ToyType(array[0], getIdToyType());
                toy = new Toy(array[0], Integer.parseInt(array[1]));
                toyType.addToy(toy);
                toyTypes.add(toyType);
            } else {
                toy = new Toy(array[0], Integer.parseInt(array[1]));
                toyTypes.get(index).addToy(toy);
            }
        }
    }

    /**
     * Lottery run
     */
    private String lotteryRun() {
            List<String> lots = new LinkedList<>();
            for (ToyTypeModel toyTypeModel : toyTypes) {
                for (int i = 0; i < toyTypeModel.getWeight(); i++) {
                    lots.add(toyTypeModel.getNameToyType());
                }
            }
            Collections.shuffle(lots, rnd);
            return lots.get(rnd.nextInt(lots.size()));
    }

    /**
     * Adds toys to toyTypes collection
     */
    private void addToys(String name, int number) {
        Toy toy;
        int index;
        for (int i = 0; i < number; i++) {
            index = getIndexOf(name);
            if (index == -1) {
                ToyType toyType = new ToyType(name, getIdToyType());
                    toy = new Toy(name, getIdToy());
                    toyType.addToy(toy);
                this.toyTypes.add(toyType);
            } else {
                    toy = new Toy(name, getIdToy());
                    this.toyTypes.get(index).addToy(toy);
            }
        }
    }

    /**
     * Gets index with name equal name toyType
     * @param name String toy name
     * @return int
     */
    private int getIndexOf(String name) {
        int index = -1;
        if (toyTypes.size() > 0) {
            for (int i = 0; i < toyTypes.size(); i++) {
                if (toyTypes.get(i).getNameToyType().equals(name)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    /**
     * Gets index with id equal id toyType
     * @param id id toyType
     * @return int
     */
    private int getIndexOf(int id) {
        int index = -1;
        if (toyTypes.size() > 0) {
            for (int i = 0; i < toyTypes.size(); i++) {
                if (toyTypes.get(i).getIdType() == id) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    /**
     * Gets int number from console
     * @return int
     */
    private int getIntFromConsole(String msg) {
        System.out.println(msg);
        int input = -1;
                try {
            input = Integer.parseInt(myScanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод " + e.getMessage());
        }
        return input;
    }

    /**
     * Gets unique identifier
     * @return int
     */
    private int getIdToy() {
        int index = 1;
        List<Integer> listID = new LinkedList<>();
        if (toyTypes.size() > 0) {
            for (ToyTypeModel toyType: toyTypes) {
                toyType.getToys().forEach(x -> listID.add(x.getID()));
            }
            index = Collections.max(listID) + 1;
        }
        return index;
    }

    private int getIdToyType() {
        int index = 1;
        if (toyTypes.size() > 0) {
            List<Integer> listIdToyTypes = new LinkedList<>();
            toyTypes.forEach(x -> listIdToyTypes.add(x.getIdType()));
            index = Collections.max(listIdToyTypes) + 1;
        }
        return index;
    }
}

