package mule.model;

import java.util.HashMap;
import java.util.Map;

abstract class Mule {

    String propertyType;
    static String muleType;
    Player currentPlayer;
    static final  Map<String, Integer> PRODUCTION_VALUES = new HashMap<>(5);


    public abstract void calculateResourceChanges();

    public String getPropertyType() {return propertyType;}

    //public String getMuleType() {return muleType;}


}
