package mule.Model;

import javafx.scene.layout.Pane;

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
