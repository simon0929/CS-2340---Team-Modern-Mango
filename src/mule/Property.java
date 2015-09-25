package mule;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * Created by Hunter on 9/18/2015.
 */
public class Property extends Pane {

    Player owner;
    int price;

    public Property() {
        price = 300;
    }


    public void setOwner(Player player) {
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }
}
