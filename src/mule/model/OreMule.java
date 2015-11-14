package mule.model;

/**
 * Ore mule class that produces ore
 * based on what property it's on
 *
 * @author Modern Mango
 *
 */
public class OreMule extends Mule implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
    private static final int PLAIN_VALUE = 1, RIVER_VALUE = 0, M1_VALUE = 2, M2_VALUE = 3, M3_VALUE = 4;

    static {

        PRODUCTION_VALUES.put("River", RIVER_VALUE);
        PRODUCTION_VALUES.put("Plain", PLAIN_VALUE);
        PRODUCTION_VALUES.put("M1", M1_VALUE);
        PRODUCTION_VALUES.put("M2", M2_VALUE);
        PRODUCTION_VALUES.put("M3", M3_VALUE);
    }

    /**
     * Constructs OreMule with a specific property type
     *
     * @param pType Type of property
     */
    public OreMule(String pType) {
        setPropertyType(pType);
        setCurrentPlayer(GameController.getCurrentPlayer());
        setMuleType("ore");
    }

    /**
     * Calculates production rate based on type of mule
     * and type of land
     */
    public final void calculateResourceChanges() {
        if(!getPropertyType().equals("River")) {
            Player player = getCurrentPlayer();
            player.setEnergy(player.getEnergy() - 1);
            player.setOre(player.getEnergy() + PRODUCTION_VALUES.get(getPropertyType()));
        }
    }

}
