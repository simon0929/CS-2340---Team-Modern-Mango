package mule.model;

public class FoodMule implements Mule {

    private final String propertyType;
    private final Player currentPlayer;

    public FoodMule(String propertyType) {
        this.propertyType = propertyType;
        currentPlayer = GameController.currentPlayer;
    }

    @Override
    public void calculateResourceChanges() {
        if (propertyType.equals("Plain")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setFood(currentPlayer.getFood() + 2);
        } else if (propertyType.equals("River")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setFood(currentPlayer.getFood() + 4);
        } else if (propertyType.equals("M1") || propertyType.equals("M2") || propertyType.equals("M3")) {
            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            currentPlayer.setFood(currentPlayer.getFood() + 1);
        }
    }

    @Override
    public String getPropertyType() {
        return propertyType;
    }

}
