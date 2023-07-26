package toyLottery.presenter;

import java.util.List;

public interface FileReadWriteModel {

    /**
     * Writes to fileName toy list
     * @param fileName String file name
     * @param listToy toy list
     */
    void writeFile(String fileName, List<String> listToy);

    /**
     * Reads from fileName
     * @param fileName String fileName
     * @return toy list
     */
    List<String> readFile(String fileName);
}
