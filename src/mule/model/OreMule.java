package mule.model;

public class OreMule implements Mule {

    private final String propertyType;
    private final Player currentPlayer;

    public OreMule(String propertyType) {
        this.propertyType = propertyType;
        currentPlayer = GameController.currentPlayer;
    }

    @Override
    public void calculateResourceChanges() {
        switch (propertyType) {
            case "Plain":
                currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
                currentPlayer.setOre(currentPlayer.getOre() + 1);
                break;
            case "M1":
                currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
                currentPlayer.setOre(currentPlayer.getOre() + 2);
                break;
            case "M2":
                currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
                currentPlayer.setOre(currentPlayer.getOre() + 3);
                break;
            case "M3":
                currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
                currentPlayer.setOre(currentPlayer.getOre() + 4);
                break;
        }
    }

    @Override
    public String getPropertyType() {
        return propertyType;
    }

}
