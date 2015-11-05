package mule.model;

import java.util.HashMap;
import java.util.Map;

public final class Store {

    private int food, energy, ore, mule;
    private static final int INIT_FOOD_B = 16, INIT_ENERGY_B = 16, INIT_ORE_B = 0, INIT_MULE_B = 25, INIT_FOOD_S = 8,
            INIT_ENERGY_S = 8, INIT_ORE_S = 8, INIT_MULE_S = 14;
    private static final Map<String, Integer> RESOURCE_LIST = new HashMap<>();
    {
        RESOURCE_LIST.put("food", 30);
        RESOURCE_LIST.put("energy", 25);
        RESOURCE_LIST.put("ore", 50);
        RESOURCE_LIST.put("energyMule", 150);
        RESOURCE_LIST.put("foodMule", 125);
        RESOURCE_LIST.put("oreMule", 175);
    }

    public Store() {
        this("beginner");
    }
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

    public int getFood() { return food; }

    public int getEnergy() { return energy; }

    public int getOre() {return ore; }

    public int getMule() { return mule; }

    public void setFood(int f) { food = f; }

    public void setEnergy(int e) { energy = e; }

    public void setOre(int o) { ore = o;}

    public void setMule(int m) { mule = m; }

    public static  Map<String, Integer> getResourceList() {return RESOURCE_LIST;}

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
