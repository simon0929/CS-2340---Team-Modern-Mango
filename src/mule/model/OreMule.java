package mule.model;

import java.util.HashMap;

public class OreMule extends Mule implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    static {

        plainValue = 1;
        riverValue = 0;
        m1Value = 2;
        m2Value = 3;
        m3Value = 4;

        productionValues = new HashMap<>(NUM_TYPES_OF_PROPERTY);
        productionValues.put("River", riverValue);
        productionValues.put("Plain", plainValue);
        productionValues.put("M1", m1Value);
        productionValues.put("M2", m2Value);
        productionValues.put("M3", m3Value);
    }

    public OreMule(String pType) {
        propertyType = pType;
        currentPlayer = GameController.currentPlayer;
        muleType = "ore";
    }

    public final void calculateResourceChanges() {
        if(!propertyType.equals("River")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setOre(currentPlayer.getEnergy() + productionValues.get(propertyType));
        }
    }

}
