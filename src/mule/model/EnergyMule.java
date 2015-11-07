package mule.model;

import java.util.HashMap;

public class EnergyMule extends Mule implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    public EnergyMule(String pType) {
        propertyType = pType;
        currentPlayer = GameController.currentPlayer;
		muleType = "energy";
    }

        static {
	        plainValue = 3;
	        riverValue = 2;
	        m1Value = 1;
	        m2Value = 1;
	        m3Value = 1;
	
	        productionValues = new HashMap<>(NUM_TYPES_OF_PROPERTY);
	        productionValues.put("River", riverValue);
	        productionValues.put("Plain", plainValue);
	        productionValues.put("M1", m1Value);
	        productionValues.put("M2", m2Value);
	        productionValues.put("M3", m3Value);
        }
        

    public final void calculateResourceChanges() {
        currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
        currentPlayer.setEnergy(currentPlayer.getEnergy() + productionValues.get(propertyType));
    }

}
