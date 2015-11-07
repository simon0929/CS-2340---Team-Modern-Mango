package mule.model;

public class EnergyMule implements Mule, java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String propertyType;
    Player currentPlayer;
    public String type;


    public EnergyMule(String propertyType) {
        this.propertyType = propertyType;
        currentPlayer = GameController.currentPlayer;
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

	@Override
	public String getMuleType() {
		return type = new String("energy");
	}
}
