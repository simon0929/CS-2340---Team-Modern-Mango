package mule.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player {

    private String name, race, diff;
    private Color color;
    //numOfFreeProperties = number of free land grants left (only nonzero for first two land choices)
    private int food, money, energy, ore, score, numOfProperties, numOfFreeProperties;
    private ArrayList<Pane> propertyList;

    public Player(String name, String race, Color color, String diff) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.diff = diff;
        numOfProperties = 0;
        numOfFreeProperties = 2;
        propertyList = new ArrayList<>();

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
        if(diff == "beginner") {
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

    public ArrayList<Pane> getPropertyList() {
        return propertyList;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addToPropertyList(Pane pane) {
        propertyList.add(pane);
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

    public void buyResource(String resource) {

        int price;
        Store store = ConfigureController.game.getStore();
        boolean canPurchase = store.canPurchase(resource);

        switch(resource) {
            case "food":
                price = 30;
                if(price <= money && canPurchase) {
                    money -= price;
                    food++;
                    store.setFood(store.getFood() - 1);
                }
                break;
            case "energy":
                price = 25;
                if(price <= money && canPurchase) {
                    money -= price;
                    energy++;
                    store.setEnergy(store.getEnergy() - 1);
                }
                break;
            case "ore":
                price = 50;
                if(price <= money && canPurchase) {
                    money -= price;
                    ore++;
                    store.setOre(store.getOre() - 1);
                }
                break;
            case "foodMule":
            	price = 125;
            	if (price <= money && canPurchase) {
            		money -= price;
            		store.setMule(store.getMule() - 1);
            		System.out.println("foodMule bought");
            	}
            	break;
            case "energyMule":
            	price = 150;
            	if (price <= money && canPurchase) {
            		money -= price;
            		store.setMule(store.getMule() - 1);
            	}
            	break;
            case "oreMule":
            	price = 175;
            	if (price <= money && canPurchase) {
            		money -= price;
            		store.setMule(store.getMule() - 1);
            	}
            	break;
            default:
                break;
        }
    }

    public void sellResource(String resource) {

        int price;
        Store store = ConfigureController.game.getStore();

        switch(resource) {
            case "food":
                price = 30;
                if(food >= 1) {
                    money += price;
                    food--;
                    store.setFood(store.getFood() + 1);
                }
                break;
            case "energy":
                price = 25;
                if(energy >= 1) {
                    money += price;
                    energy--;
                    store.setEnergy(store.getEnergy() + 1);
                }
                break;
            case "ore":
                price = 50;
                if(ore >= 1) {
                    money += price;
                    ore--;
                    store.setOre(store.getOre() + 1);
                }
                break;

            default:
                break;
        }
    }
}
