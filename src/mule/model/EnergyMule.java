package mule.model;

/**
 * Energy mule class that produces energy
 * based on what property it's on
 *
 * @author Modern Mango
 *
 */
public class EnergyMule extends Mule implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private static final int PLAIN_VALUE = 3, RIVER_VALUE = 2, M1_VALUE = 1, M2_VALUE = 1, M3_VALUE = 1;

	/**
     * Construct EnergyMule with a specific property type
     *
     * @param pType Type of property
     */
	public EnergyMule(String pType) {
        setPropertyType(pType);
        setCurrentPlayer(GameController.getCurrentPlayer());
        setMuleType("energy");
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
        player.setEnergy(player.getEnergy() + PRODUCTION_VALUES.get(getPropertyType()));
    }

}
