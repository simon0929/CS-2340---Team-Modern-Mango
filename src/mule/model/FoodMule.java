package mule.model;

public class FoodMule implements Mule {

    private final String propertyType;
    private final Player currentPlayer;

    public FoodMule(String type) {
        this.propertyType = type;
        currentPlayer = GameController.currentPlayer;
    }

    @Override
    public final void calculateResourceChanges() {
        switch (propertyType) {
            case "Plain":
                currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
                currentPlayer.setFood(currentPlayer.getFood() + 2);
                break;
            case "River":
                currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
                currentPlayer.setFood(currentPlayer.getFood() + 4);
                break;
            case "M1":
            case "M2":
            case "M3":
                currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
                currentPlayer.setFood(currentPlayer.getFood() + 1);
                break;
        }
    }

    @Override
    public final String getPropertyType() {
        return propertyType;
    }

}
