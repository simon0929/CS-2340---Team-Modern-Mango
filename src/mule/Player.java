package mule;

import javafx.scene.paint.Color;

/**
 * Created by Hunter on 9/18/2015.
 */
public class Player {

    private String name, race, diff;
    private Color color;
    //numOfFreeProperties = number of free land grants left (only nonzero for first two land choices)
    private int food, money, energy, ore, score, numOfProperties, numOfFreeProperties;
    //is current player, not sure if needed
    private boolean isCurrent;

    public Player(String name, String race, Color color, String diff) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.diff = diff;
        isCurrent = false;
        numOfProperties = 0;
        numOfFreeProperties = 2;

        //Different races get different amounts of resources
        if (race.equals("Flapper")) {
            money = 1600;
        }
        else if (race.equals("Human")) {
            money = 600;
        }
        else {
            money = 1000;
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

    public boolean getIsCurrent() {
        return isCurrent;
    }

    public int getNumOfProperties() { return numOfProperties; }

    public int getNumOfFreeProperties() { return numOfFreeProperties; }

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

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
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
}
