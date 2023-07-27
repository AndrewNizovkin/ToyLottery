package toyLottery.presenter;

import java.util.List;

public interface ToyTypeModel {

    /**
     * Gets toy type name
     * @return String
     */
    String getNameToyType();

    /**
     * Adds toy to toy collection
     * @param toy ToyModel instance
     */
    void addToy(ToyModel toy);

    /**
     * Removes toy from toy collection
     * @return removed toy
     * @param name toy name
     */
    ToyModel removeToy();

    /**
     * Gets toy weight in lottery
     * @return int number
     */
    int getWeight();

    /**
     * Sets toy weight for toys with name
     * @param weight int weight
     */
    void setWeight(int weight);

    /**
     * Countes toys of this type
     * @return int
     */
    int countToys();

    /**
     * Gets toy list
     * @return List
     */
    List<ToyModel> getToys();

    /**
     * Gets typeToy ID
     * @return int
     */
    int getIdType();

}
