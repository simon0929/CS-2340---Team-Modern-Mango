package mule;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player {

    private String name, race, diff;
    private Color color;
    //numOfFreeProperties = number of free land grants left (only nonzero for first two land choices)
    private int food, money, energy, ore, score, numOfProperties, numOfFreeProperties;
    //is current player, not sure if needed
    private boolean isCurrent;
    private ArrayList<Pane> propertyList;

    public Player(String name, String race, Color color, String diff) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.diff = diff;
        isCurrent = false;
        numOfProperties = 0;
        numOfFreeProperties = 2;
        propertyList = new ArrayList<>();

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

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
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
}
