package mule;

import javafx.scene.paint.Color;

/**
 * Created by Hunter on 9/18/2015.
 */
public class Player {

    private String name, race, diff;
    private Color color;
    private int food, money, energy, ore, score;
    private boolean isCurrent;

    public Player(String name, String race, Color color, String diff) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.diff = diff;
        isCurrent = false;
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
}
