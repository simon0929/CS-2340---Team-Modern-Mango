package mule.model;

/**
 * Food mule class that produces food
 * based on what property it's on
 *
 * @author Modern Mango
 *
 */
public class FoodMule extends Mule implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
    private static final int PLAIN_VALUE = 2, RIVER_VALUE = 4, M1_VALUE = 1, M2_VALUE = 1, M3_VALUE = 1;

    /**
     * Construct FoodMule with a specific property type
     *
     * @param pType Type of property
     */
    public FoodMule(String pType) {
        setPropertyType(pType);
        setCurrentPlayer(GameController.getCurrentPlayer());
        setMuleType("food");
    }
    static {

        PRODUCTION_VALUES.put("River", RIVER_VALUE);
        PRODUCTION_VALUES.put("Plain", PLAIN_VALUE);
        PRODUCTION_VALUES.put("M1", M1_VALUE);
        PRODUCTION_VALUES.put("M2", M2_VALUE);
        PRODUCTION_VALUES.put("M3", M3_VALUE);
    }

    /**
     * Calculates production rate based on type of mule
     * and type of land
     */
    public final void calculateResourceChanges() {
        Player player = getCurrentPlayer();
        player.setEnergy(player.getEnergy() - 1);
        player.setFood(player.getEnergy() + PRODUCTION_VALUES.get(getPropertyType()));
    }

}
