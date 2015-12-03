package mule.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract mule class defining
 * the methods and characteristics
 * that all types of mules have
 *
 * @author ModernMango
 *
 */
abstract class Mule {

    private String propertyType;
    private static String muleType;
    private Player currentPlayer;
    static final  Map<String, Integer> PRODUCTION_VALUES = new HashMap<>(6);


    /**
     * Calculates production rate based on type of mule
     * and type of land
     */
    public abstract void calculateResourceChanges();

    /**
     * Gets the type of property that the mule is on
     * @return Type of property mule is on
     */
    public String getPropertyType() {return propertyType;}

    /**
     * Gets the type of mule
     * @return Type of mule
     */
    public String getMuleType() { return muleType; }

    /**
     * Sets the property type to the type that's passed in
     * @param pType Type to set the property type to
     */
    public void setPropertyType(String pType) { propertyType = pType; }

    /**
     * Sets the mule type to the type that's passed in
     * @param mType Type to set the mule type to
     */
    public void setMuleType(String mType) { muleType = mType; }

    /**
     * Sets current player to the player passed in
     * @param cp Player to set the current player to
     */
    public void setCurrentPlayer(Player cp) { currentPlayer = cp; }

    /**
     * Gets the current player
     * @return Current player
     */
    public Player getCurrentPlayer() { return currentPlayer; }

}
