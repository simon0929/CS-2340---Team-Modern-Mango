package mule.model;

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

    public OreMule(String pType) {
        propertyType = pType;
        currentPlayer = GameController.currentPlayer;
        muleType = "ore";
    }

    public final void calculateResourceChanges() {
        if(!propertyType.equals("River")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setOre(currentPlayer.getEnergy() + PRODUCTION_VALUES.get(propertyType));
        }
    }

}
