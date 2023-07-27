package toyLottery;

import toyLottery.presenter.ToyLottery;
import toyLottery.view.FileReadWriter;

public class Program {
    public static void main(String[] args) {
        ToyLottery.getInstance(new FileReadWriter()).start();
    }
}
