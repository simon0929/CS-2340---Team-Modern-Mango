package mule;

import javafx.scene.paint.Color;

/**
 * Created by Hunter on 9/18/2015.
 */
public class Player {

    private String name, race, diff;
    private Color color;
    private int food, money, energy, ore, score;

    public Player(String name, String race, Color color, String diff) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.diff = diff;
    }
}
