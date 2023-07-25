package toyLottery.presenter;

import toyLottery.model.Toy;
import toyLottery.view.ToyType;

import java.util.*;

public class ToyLottery {
    private  static ToyLottery instance;
    private Scanner myScanner;
    private List<ToyTypeModel> toyTypes;

    private ToyLottery(){
        myScanner = new Scanner(System.in);
        toyTypes = new LinkedList<>();

    }

    /**
     * Gets instance this singleton
     * @return ToyLottery instance
     */
    public static ToyLottery getInstance(){
        if (instance == null) {
            instance = new ToyLottery();
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
                case 1 -> {
                    System.out.println("Чтение из файла");
                }
                case 2 -> {
                    System.out.println("Запись в файл");
                }
                case 3 -> {
                    System.out.println("Показать ассортимент");
                }
                case 4 -> {
                    this.loadDemo();
                }
                case 5 -> {
                    System.out.println("Добавить");
                }
                case 6 -> {
                    System.out.println("Изменить вес");
                }
                case 7 -> {
                    System.out.println("Розыгрыш приза");
                }
            }
            choise = mainMenu();
        }
        System.out.println("The end!");

    }

//    /**
//     * Manages the lottery
//     */
//    private void lottery() {
//        int lotteryChoice = lotteryMenu();
//        while (lotteryChoice != 0) {
//            switch (lotteryChoice) {
//                case 1 -> {
//                    System.out.println("Изменить вес");
//                }
//                case 2 -> {
//                    System.out.println("Запуск лотереи");
//                }
//            }
//            lotteryChoice = lotteryMenu();
//        }
//    }

    /**
     * Provides a choice to the user from main menu
     * @return int choice
     */
    private int mainMenu() {
        if (toyTypes.size() > 0) {
            toyTypes.forEach(System.out::println);
        } else {
            System.out.println("Магазин пуст");
        }
        System.out.println("-".repeat(55));
        return getIntFromConsole("[0] Выход         [1] Чтение из файла  [2] Запись в файл\n[3] Показать      " +
                "[4] Загрузить демо   [5] Добавить\n[6] Изменить вес  [7] Розыгрыш приза");

    }

//    /**
//     * Provides a choice to the user from lottery menu
//     * @return int choice
//     */
//    private int lotteryMenu() {
//        return getIntFromConsole("[0] Главное меню  [1] Изменить вес  [2] Запуск лотереи");
//    }

    /**
     * Loads this.toyTypes
     */
    private void loadDemo() {
        this.addToys("Самолёт", 3);
        this.addToys("Грузовик", 2);
        this.addToys("Монстр-трак", 3);
        this.addToys("Велосипед", 1);
        this.addToys("Пистолет", 3);
        this.addToys("Грузовик", 2);
    }

    /**
     * Adds toys to toyTypes collection
     */
    private void addToys(String name, int number) {
//        System.out.println("Название игрушки:");
//        String name = myScanner.nextLine();
//        int number = getIntFromConsole("Количество игрушек");

        Toy toy;
        int index = getIndexOf(name);
        if (index == -1) {
            ToyType toyType = new ToyType(name, getIdToyType());
            for (int i = 0; i < number; i++) {
                toy = new Toy(name, getIdToy());
                toyType.addToy(toy);
            }
            this.toyTypes.add(toyType);
        } else {
            for (int i = 0; i < number; i++) {
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
            toyTypes.forEach(x -> listIdToyTypes.add(x.getID()));
            index = Collections.max(listIdToyTypes) + 1;
        }
        return index;
    }



}

