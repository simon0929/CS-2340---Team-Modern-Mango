package mule.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Store class that keeps track of resource
 * and mule inventory.
 *
 * @author ModernMango
 *
 */
public final class Store implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int food, energy, ore, mule;
    private static final int INIT_FOOD_B = 16, INIT_ENERGY_B = 16, INIT_ORE_B = 0, INIT_MULE_B = 25, INIT_FOOD_S = 8,
            INIT_ENERGY_S = 8, INIT_ORE_S = 8, INIT_MULE_S = 14, FOOD_PRICE = 30, ENERGY_PRICE = 25, ORE_PRICE = 50,
            E_MULE_PRICE = 150,F_MULE_PRICE = 125, O_MULE_PRICE = 175;
    private static final Map<String, Integer> RESOURCE_LIST = new HashMap<>();

    static {
        RESOURCE_LIST.put("food", FOOD_PRICE);
        RESOURCE_LIST.put("energy", ENERGY_PRICE);
        RESOURCE_LIST.put("ore", ORE_PRICE);
        RESOURCE_LIST.put("energyMule", E_MULE_PRICE);
        RESOURCE_LIST.put("foodMule", F_MULE_PRICE);
        RESOURCE_LIST.put("oreMule", O_MULE_PRICE);
    }

    public Store() {
        this("beginner");
    }

    /**
     * Constructs a store that sells resources and mules
     * and the inventory is determined by the difficulty
     * of the game passed in
     *
     * @param difficulty Difficulty of the game
     */
    public Store(String difficulty) {

        if (difficulty.equals("beginner")) {
            food = INIT_FOOD_B;
            energy = INIT_ENERGY_B;
            ore = INIT_ORE_B;
            mule = INIT_MULE_B;
        } else if (difficulty.equals("standard") || difficulty.equals("tournament")) {
            food = INIT_FOOD_S;
            energy = INIT_ENERGY_S;
            ore = INIT_ORE_S;
            mule = INIT_MULE_S;
        }
    }

    /**
     * Gets the amount of food in stock
     * @return Amount of food
     */
    public int getFood() { return food; }

    /**
     * Gets the amount of energy in stock
     * @return Amount of energy
     */
    public int getEnergy() { return energy; }

    /**
     * Gets the amount of ore in stock
     * @return Amount of ore
     */
    public int getOre() {return ore; }

    /**
     * Gets the amount of mules in stock
     * @return Amount of mules
     */
    public int getMule() { return mule; }

    /**
     * Sets the amount of food to the value passed in
     * @param f New value to set the amount food there is
     */
    public void setFood(int f) { food = f; }

    /**
     * Sets the amount of energy to the value passed in
     * @param e New value to set the amount energy there is
     */
    public void setEnergy(int e) { energy = e; }

    /**
     * Sets the amount of ore to the value passed in
     * @param o New value to set the amount ore there is
     */
    public void setOre(int o) { ore = o;}

    /**
     * Sets the number of mules to the value passed in
     * @param m New value to set the number of mules there are
     */
    public void setMule(int m) { mule = m; }

    /**
     * Gets the list of resources available
     * @return List of resources
     */
    public static  Map<String, Integer> getResourceList() {return RESOURCE_LIST;}

    /**
     * Returns whether a resource can be purchased or not
     *
     * @param item Resource that's trying to be purchased
     *
     * @return True if resource can be purchased
     *         False if the item is out of stock
     */
    public boolean canPurchase(String item) {
        switch(item) {
            case "food": return food > 0;
            case "ore": return ore > 0;
            case "energy": return energy > 0;
            case "foodMule": return mule > 0;
            case "energyMule": return mule > 0;
            case "oreMule": return mule > 0;

            default: return false;
        }
    }

}
