package mule.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public final class Player {

    private final String name, race, diff;
    private final Color color;
    //numOfFreeProperties = number of free land grants left (only nonzero for first two land choices)
    private int food, money, energy, ore, score, numOfProperties, numOfFreeProperties;
    private final List<Pane> propertyList;
    private final List<Mule> muleList;
    private static final int INIT_MONEY_F = 1600, INIT_MONEY_H = 600, INIT_MONEY_OTHER = 1000, INIT_FOOD_B = 8,
            INIT_ENERGY_B = 4, INIT_ORE_B = 0, INIT_FOOD_OTHER = 4, INIT_ENERGY_OTHER = 2, INIT_ORE_OTHER = 0,
            SCORE_CALC_MULTIPLIER = 500;

    public Player(String n, String r, Color c, String d) {
        name = n;
        race = r;
        color = c;
        diff = d;
        this.score = 0;
        numOfProperties = 0;
        numOfFreeProperties = 2;
        propertyList = new ArrayList<>();
        muleList = new ArrayList<>();

        //Different races get different amounts of money
        switch (race) {
            case "Flapper":
                money = INIT_MONEY_F;
                break;
            case "Human":
                money = INIT_MONEY_H;
                break;
            default:
                money = INIT_MONEY_OTHER;
                break;
        }

        //Different difficulties get different resources
        if(diff.equals("beginner")) {
            food = INIT_FOOD_B;
            energy = INIT_ENERGY_B;
            ore = INIT_ORE_B;
        } else {
            food = INIT_FOOD_OTHER;
            energy = INIT_ENERGY_OTHER;
            ore = INIT_ORE_OTHER;
        }
    }

    public String getName() {
        return name;
    }

    public String getRace() {
        return race;
    }

    public String getDiff() {
        return diff;
    }

    public Color getColor() {
        return color;
    }

    public int getFood() {
        return food;
    }

    public int getMoney() {
        return money;
    }

    public int getEnergy() {
        return energy;
    }

    public int getOre() {
        return ore;
    }

    public int getScore() {
        return score;
    }


    public int getNumOfProperties() { return numOfProperties; }

    public int getNumOfFreeProperties() { return numOfFreeProperties; }

    public List<Pane> getPropertyList() {
        return propertyList;
    }

    public List<Mule> getMuleList() {
    	return muleList;
    }

    public void setFood(int newFood) {
        food = newFood;
    }

    public void setMoney(int newMoney) {
        money = newMoney;
    }

    public void setEnergy(int newEnergy) {
        energy = newEnergy;
    }

    public void setOre(int newOre) {
        ore = newOre;
    }

    public void setScore(int newScore) {
        score = newScore;
    }

    public void addToPropertyList(Pane pane) {
        propertyList.add(pane);
    }

    public void addToMuleList(Mule mule) {
    	muleList.add(mule);
    }

    public void removeFromPropertyList(Pane pane) {
        propertyList.remove(pane);
    }

    public boolean hasProperty(Pane pane) {
        return propertyList.contains(pane);
    }

    public void incrementPropertyOwned() {
        numOfProperties++;
    }

    public void decrementPropertyOwned() {
        numOfProperties--;
    }

    public void decrementFreeProperty() {
        numOfFreeProperties--;
    }

    public void calculateScore() {
        score = money + (numOfProperties * SCORE_CALC_MULTIPLIER) + food + energy + ore;
    }

    public String buyResource(String resource) {

        Store store = ConfigureController.getGame().getStore();
        String message = "";

        if(!store.canPurchase(resource)) {
            message = "Not enough inventory in the store to make that purchase";
        } else {
            int price = Store.getResourceList().get(resource);
            if (price <= money) {
                purchase(resource, price, store);
            } else {
                message = "You don't have enough money to make that purchase";
            }
        }
        return message;
    }

    private void purchase(String resource,int price, Store store) {
        switch (resource) {
            case "food":
                money -= price;
                food++;
                store.setFood(store.getFood() - 1);
                break;
            case "energy":
                money -= price;
                energy++;
                store.setEnergy(store.getEnergy() - 1);
                break;
            case "ore":
                money -= price;
                ore++;
                store.setOre(store.getOre() - 1);
                break;
            case "foodMule":
                money -= price;
                store.setMule(store.getMule() - 1);
                break;
            case "energyMule":
                money -= price;
                store.setMule(store.getMule() - 1);
                break;
            case "oreMule":
                money -= price;
                store.setMule(store.getMule() - 1);
            default:
                break;
        }

    }

    public String sellResource(String resource) {

        Store store = ConfigureController.getGame().getStore();
        String message = "";
        int price = Store.getResourceList().get(resource);
        boolean sold = false;

        switch(resource) {
            case "food":
                if(food >= 1) {
                    sold = true;
                    money += price;
                    food--;
                    store.setFood(store.getFood() + 1);
                }
                break;
            case "energy":
                if(energy >= 1) {
                    sold = true;
                    money += price;
                    energy--;
                    store.setEnergy(store.getEnergy() + 1);
                }
                break;
            case "ore":
                if(ore >= 1) {
                    sold = true;
                    money += price;
                    ore--;
                    store.setOre(store.getOre() + 1);
                }
                break;

            default:
                break;
        }
        if(!sold) {
            message = "You don't have enough inventory to sell that item";
        }
        return message;
    }
}
