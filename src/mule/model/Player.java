package mule.model;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;


import java.util.ArrayList;

public final class Player implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name, race, diff;
    private transient javafx.scene.paint.Color color;
    public  java.awt.Color newColor;
    //numOfFreeProperties = number of free land grants left (only nonzero for first two land choices)
    private int food, money, energy, ore, score, numOfProperties, numOfFreeProperties;
    private ArrayList<Object> propertyList;
    private ArrayList<Object> muleList;

    public Player(String n, String r, javafx.scene.paint.Color c, String d) {
        name = n;
        race = r;
        color = c;
        diff = d;
        this.score = 0;
        numOfProperties = 0;
        numOfFreeProperties = 2;
        propertyList = new ArrayList<>();
        muleList = new ArrayList<>();
        
        newColor = new java.awt.Color((int) c.getRed(), (int) c.getGreen(), (int) c.getBlue());
        
        //Different races get different amounts of money
        if (race.equals("Flapper")) {
            money = 1600;
        }
        else if (race.equals("Human")) {
            money = 600;
        }
        else {
            money = 1000;
        }

        //Different difficulties get different resources
        if(diff.equals("beginner")) {
            food = 8;
            energy = 4;
            ore = 0;
        } else {
            food = 4;
            energy = 2;
            ore = 0;
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

    public javafx.scene.paint.Color getColor() {
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

    public ArrayList<Object> getPropertyList() {
        return propertyList;
    }

    public ArrayList<Object> getMuleList() {
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
    
    public void setColor(java.awt.Color savedColor) {
    	this.color = new javafx.scene.paint.Color(savedColor.getRed(), savedColor.getGreen(), savedColor.getBlue(), 1);
    }

    public void addToPropertyList(Pane pane) {
        propertyList.add(pane);
    }

    public void addToMuleList(Button mule) {
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
        score = money + (numOfProperties * 500) + food + energy + ore;
    }

    public String buyResource(String resource) {

        int price;
        Store store = ConfigureController.game.getStore();
        boolean bought = false;
        String message = "";

        if(!store.canPurchase(resource)) {
            message = "Not enough inventory in the store to make that purchase";
        } else {
            switch (resource) {
                case "food":
                    price = 30;
                    if (price <= money) {
                        bought = true;
                        money -= price;
                        food++;
                        store.setFood(store.getFood() - 1);
                    }
                    break;
                case "energy":
                    price = 25;
                    if (price <= money) {
                        bought = true;
                        money -= price;
                        energy++;
                        store.setEnergy(store.getEnergy() - 1);
                    }
                    break;
                case "ore":
                    price = 50;
                    if (price <= money) {
                        bought = true;
                        money -= price;
                        ore++;
                        store.setOre(store.getOre() - 1);
                    }
                    break;
                case "foodMule":
                    price = 125;
                    if (price <= money) {
                        bought = true;
                        money -= price;
                        store.setMule(store.getMule() - 1);
                    }
                    break;
                case "energyMule":
                    price = 150;
                    if (price <= money) {
                        bought = true;
                        money -= price;
                        store.setMule(store.getMule() - 1);
                    }
                    break;
                case "oreMule":
                    price = 175;
                    if (price <= money) {
                        bought = true;
                        money -= price;
                        store.setMule(store.getMule() - 1);
                    }
                    break;
                default:
                    break;
            }
            if(!bought) {
                message = "You don't have enough money to make that purchase";
            }
        }
        return message;
    }

    public String sellResource(String resource) {

        int price;
        Store store = ConfigureController.game.getStore();
        String message = "";
        Boolean sold = false;

        switch(resource) {
            case "food":
                price = 30;
                if(food >= 1) {
                    sold = true;
                    money += price;
                    food--;
                    store.setFood(store.getFood() + 1);
                }
                break;
            case "energy":
                price = 25;
                if(energy >= 1) {
                    sold = true;
                    money += price;
                    energy--;
                    store.setEnergy(store.getEnergy() + 1);
                }
                break;
            case "ore":
                price = 50;
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
