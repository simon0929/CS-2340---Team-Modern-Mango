package mule.model;

import java.util.HashMap;
import java.util.Map;

abstract class Mule {

    private String propertyType;
    private static String muleType;
    private Player currentPlayer;
    static final  Map<String, Integer> PRODUCTION_VALUES = new HashMap<>(5);


    public abstract void calculateResourceChanges();

    public String getPropertyType() {return propertyType;}

    public String getMuleType() { return muleType; }

    public void setPropertyType(String pType) { propertyType = pType; }

    public void setMuleType(String mType) { muleType = mType; }

    public void setCurrentPlayer(Player cp) { currentPlayer = cp; }

    public Player getCurrentPlayer() { return currentPlayer; }

}
