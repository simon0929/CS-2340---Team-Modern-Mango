package mule.model;

public class OreMule implements Mule, java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String propertyType;
    Player currentPlayer;
    public String type;

    public OreMule(String propertyType) {
        this.propertyType = propertyType;
        currentPlayer = GameController.currentPlayer;
    }

    @Override
    public void calculateResourceChanges() {
        if (propertyType.equals("Plain")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setOre(currentPlayer.getOre() + 1);
        } else if (propertyType.equals("M1")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setOre(currentPlayer.getOre() + 2);
        } else if (propertyType.equals("M2")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setOre(currentPlayer.getOre() + 3);
        } else if (propertyType.equals("M3")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setOre(currentPlayer.getOre() + 4);
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
		return type = new String("ore");
	}
}
