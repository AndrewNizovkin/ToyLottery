package toyLottery.model;

import toyLottery.presenter.ToyModel;

public class Toy implements ToyModel {
    private String name;
    private int id;

    public Toy(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getID() {
        return this.id;
    }

}
