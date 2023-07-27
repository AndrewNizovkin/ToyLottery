package toyLottery.model;

import toyLottery.presenter.ToyTypeModel;
import toyLottery.presenter.ToyModel;

import java.util.*;

public class ToyType implements ToyTypeModel {
    private final int idToyType;
    private final List<ToyModel> toys;
    private int weight;
    private final String nameType;

    /**
     * Constructor
     */
    public ToyType(String nameType, int idToyType) {
        this.nameType = nameType;
        this.toys = new LinkedList<>();
        this.weight = 1;
        this.idToyType = idToyType;
    }

    /**
     * Gets toy type name
     *
     * @return String
     */
    @Override
    public String getNameToyType() {
        return this.nameType;
    }

    /**
     * Adds toy to toy collection
     * @param toy ToyModel instance
     */
    @Override
    public void addToy(ToyModel toy) {
        toys.add(toy);
    }

    /**
     * Removes toy from toy collection
     *
     * @return removed toy
     */
    @Override
    public ToyModel removeToy() {

        return toys.remove(0);
    }

    /**
     * Gets toy weight in lottery
     * @return int number
     */
    @Override
    public int getWeight() {
        return this.weight;
    }

    /**
     * Sets toy weight for toys with name
     *
     * @param weight int weight
     */
    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Countes toys of this type
     *
     * @return int
     */
    @Override
    public int countToys() {
        return toys.size();
    }

    /**
     * Gets toy list
     *
     * @return List
     */
    @Override
    public List<ToyModel> getToys() {
        return this.toys;
    }

    /**
     * Gets typeToy ID
     *
     * @return int
     */
    @Override
    public int getIdType() {
        return this.idToyType;
    }


    @Override
    public String toString() {
        return String.format("id: %-2d  name: %-20s  weight: %-3d  count: %-2d ",
                this.idToyType,
                this.nameType,
                getWeight(),
                this.countToys());
    }
}
