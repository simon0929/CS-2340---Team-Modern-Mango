package mule.model;

public class FoodMule extends Mule implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
    private static final int PLAIN_VALUE = 2, RIVER_VALUE = 4, M1_VALUE = 1, M2_VALUE = 1, M3_VALUE = 1;

    public FoodMule(String pType) {
        propertyType = pType;
        currentPlayer = GameController.currentPlayer;
        muleType = "food";
    }
    static {

        PRODUCTION_VALUES.put("River", RIVER_VALUE);
        PRODUCTION_VALUES.put("Plain", PLAIN_VALUE);
        PRODUCTION_VALUES.put("M1", M1_VALUE);
        PRODUCTION_VALUES.put("M2", M2_VALUE);
        PRODUCTION_VALUES.put("M3", M3_VALUE);
    }

    public final void calculateResourceChanges() {
        currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
        currentPlayer.setFood(currentPlayer.getEnergy() + PRODUCTION_VALUES.get(propertyType));
    }

}
