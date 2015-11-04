package mule.model;

public class EnergyMule implements Mule {

    private final String propertyType;
    private final Player currentPlayer;

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

}
