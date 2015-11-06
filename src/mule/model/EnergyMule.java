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

    @Override
    public void calculateResourceChanges() {
        if (propertyType.equals("Plain")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setEnergy(currentPlayer.getEnergy() + 3);
        } else if (propertyType.equals("River")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setEnergy(currentPlayer.getEnergy() + 2);
        } else if (propertyType.equals("M1") || propertyType.equals("M2") || propertyType.equals("M3")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setEnergy(currentPlayer.getEnergy() + 1);
        }
    }

    @Override
    public String getPropertyType() {
        return propertyType;
    }

    @Override
    public void setPropertyType() {

    }

	@Override
	public String getMuleType() {
		return type = new String("energy");
	}
}
