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
        switch (propertyType) {
            case "Plain":
                currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
                currentPlayer.setEnergy(currentPlayer.getEnergy() + 3);
                break;
            case "River":
                currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
                currentPlayer.setEnergy(currentPlayer.getEnergy() + 2);
                break;
            case "M1":
            case "M2":
            case "M3":
                currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
                currentPlayer.setEnergy(currentPlayer.getEnergy() + 1);
                break;
        }
    }

    @Override
    public String getPropertyType() {
        return propertyType;
    }

}
