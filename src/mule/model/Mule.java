package mule.model;

import java.util.Map;

abstract class Mule {

    String propertyType;
    Player currentPlayer;
    static Map<String, Integer> productionValues;
    static int plainValue, riverValue, m1Value, m2Value, m3Value;
    static final int NUM_TYPES_OF_PROPERTY = 5;

    public abstract void calculateResourceChanges();

    public String getPropertyType() {return propertyType;}


}
