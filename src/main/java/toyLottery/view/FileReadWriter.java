package toyLottery.view;

import toyLottery.presenter.FileReadWriteModel;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileReadWriter implements FileReadWriteModel {
    private final String pathProject;

    public FileReadWriter() {
        pathProject = System.getProperty("user.dir");
    }
    /**
     * Writes to fileName toy list
     *
     * @param fileName String file name
     * @param listToys  toy list
     */
    @Override
    public void writeFile(String fileName, List<String> listToys) {
        String pathFile = pathProject.concat(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, false))){
            while (listToys.size() > 0) {
                writer.write(listToys.remove(0));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Что-то пошло не так" + e.getMessage());
        }

    }

    /**
     * Reads from fileName
     * @param fileName String fileName
     * @return toy list
     */
    @Override
    public List<String> readFile(String fileName) {
        String pathFile = pathProject.concat(fileName);
        List<String> listToys = new LinkedList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))){
//            BufferedReader reader = new BufferedReader(new FileReader(pathFile));
            while ((line = reader.readLine()) != null) {
                listToys.add(line);
            }
        } catch (IOException e) {
            System.out.println("Что-то пошло не так." + e.getMessage());
        }
        return listToys;
    }
}
