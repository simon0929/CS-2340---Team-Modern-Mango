package mule.model;


import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * Player class that keeps track of stats including food,
 * ore, energy, etc. Can also update stats
 * and purchase & sell resources
 *
 * @author ModernMango
 *
 */
public final class Player implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private final String name, race, diff;
    private transient javafx.scene.paint.Color color;
    private final Color newColor;
    //numOfFreeProperties = number of free land grants left (only nonzero for first two land choices)
    private int food, money, energy, ore, score, numOfProperties, numOfFreeProperties;
    private final List<String> propertyList;
    private final List<Mule> muleList;
    private static final int INIT_MONEY_F = 1600, INIT_MONEY_S = 1250, INIT_MONEY_H = 600, INIT_MONEY_D = 500, INIT_MONEY_OTHER = 1000, INIT_FOOD_B = 8,
            INIT_ENERGY_B = 4, INIT_ORE_B = 0, INIT_FOOD_OTHER = 4, INIT_ENERGY_OTHER = 2, INIT_ORE_OTHER = 0, INIT_FOOD_L = 3, INIT_ENERGY_L = 0, INIT_ORE_L = 0,
            INIT_FOOD_N = 12, INIT_ENERGY_N = 8, INIT_ORE_N = 2,
            SCORE_CALC_MULTIPLIER = 500;
    private static final double COLOR_CONSTANT = 255.0;


    /**
     * Constructs a player with a name and race. Player has
     * initial stats based on race and difficulty of game
     *
     * @param n Name of the player
     * @param r Race of the player
     * @param c Player's color
     * @param d Difficulty of game
     */
    public Player(String n, String r, javafx.scene.paint.Color c, String d) {
        name = n;
        race = r;
        color = c;
        diff = d;
        this.score = 0;
        numOfProperties = 0;
        numOfFreeProperties = 2;
        propertyList = new ArrayList<>();
        muleList = new ArrayList<>();

        newColor = new java.awt.Color((float) c.getRed(), (float)c.getGreen(), (float)c.getBlue());

        //Different races get different amounts of money
        switch (race) {
            case "Flapper":
                money = INIT_MONEY_F;
                break;
            case "Sozon":
            	money = INIT_MONEY_S;
            	break;
            case "Human":
                money = INIT_MONEY_H;
                break;
            case "Dracanoid":
            	money = INIT_MONEY_D;
            	break;
            default:
                money = INIT_MONEY_OTHER;
                break;
        }

        //Different difficulties get different resources
        if(diff.equals("noobie")) {
        	food = INIT_FOOD_N;
        	energy = INIT_ENERGY_N;
        	ore = INIT_ORE_N;
        } else if(diff.equals("beginner")) {
            food = INIT_FOOD_B;
            energy = INIT_ENERGY_B;
            ore = INIT_ORE_B;
        } else if(diff.equals("legendary")) {
        	food = INIT_FOOD_L;
        	energy = INIT_ENERGY_L;
        	ore = INIT_ORE_L;
        } else {
            food = INIT_FOOD_OTHER;
            energy = INIT_ENERGY_OTHER;
            ore = INIT_ORE_OTHER;
        }
    }

    /**
     * Gets name of player
     * @return Name of player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets race of player
     * @return Race of player
     */
    public String getRace() {
        return race;
    }

    /**
     * Gets difficulty of player
     * @return Difficulty of player
     */
    public String getDiff() {
        return diff;
    }

    /**
     * Gets color of player
     * @return Color of player
     */
    public javafx.scene.paint.Color getColor() {
        return color;
    }

    /**
     * Gets player's food
     * @return Player's food
     */
    public int getFood() {
        return food;
    }

    /**
     * Gets player's money
     * @return Player's money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Gets player's energy
     * @return Player's energy
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Gets player's ore
     * @return Player's ore
     */
    public int getOre() {
        return ore;
    }

    /**
     * Gets player's score
     * @return Player's score
     */
    public int getScore() {
        return score;
    }


    /**
     * Gets number of properties that player has
     * @return Number of properties player has
     */
    public int getNumOfProperties() { return numOfProperties; }

    /**
     * Gets number of properties that are free
     * @return Number of free properties
     */
    public int getNumOfFreeProperties() { return numOfFreeProperties; }

    /**
     * Gets the list of properties that player has
     * @return List of properties player has
     */
    public List<String> getPropertyList() {
        return propertyList;
    }

    /**
     * Gets list of mules that player has
     * @return List of mules player has
     */
    public List<Mule> getMuleList() {
    	return muleList;
    }

    /**
     * Sets amount of food to the new amount that is passed
     * @param newFood New amount of food
     */
    public void setFood(int newFood) {
        food = newFood;
    }

    /**
     * Sets amount of money to the new one that is passed
     * @param newMoney New amount of money
     */
    public void setMoney(int newMoney) {
        money = newMoney;
    }

    /**
     * Sets amount of energy to the new one that is passed
     * @param newEnergy New amount of energy
     */
    public void setEnergy(int newEnergy) {
        energy = newEnergy;
    }

    /**
     * Sets amount of ore to the new one that is passed
     * @param newOre New amount of ore
     */
    public void setOre(int newOre) {
        ore = newOre;
    }

    /**
     * Sets score to the new one that is passed
     * @param newScore New score
     */
    public void setScore(int newScore) {
        score = newScore;
    }

    /**
     * Sets the player's color to the color that's
     * passed in
     *
     * @param savedColor The new color to set the player's color to
     */
    public void setColor(java.awt.Color savedColor) {
    	this.color = new javafx.scene.paint.Color(savedColor.getRed() / COLOR_CONSTANT, savedColor.getGreen() / COLOR_CONSTANT,
                savedColor.getBlue() / COLOR_CONSTANT, 1);
    }

    /**
     * Adds the a property to the list of properties
     * that the player has
     *
     * @param pane Name of the property to add to the
     * player's property list
     */
    public void addToPropertyList(String pane) {
        propertyList.add(pane);
    }

    /**
     * Add the mule passed in to the player's
     * mule list
     *
     * @param mule New mule to add to player's mule list
     */
    public void addToMuleList(Mule mule) {
    	muleList.add(mule);
    }

    /**
     * Remove property passed in from the player's property list
     *
     * @param pane Name of property to remove player's
     * property list
     */
    public void removeFromPropertyList(String pane) {
        propertyList.remove(pane);
    }

    /**
     * Checks to see if the property name passed in
     * is already in the player's property list
     *
     * @param pane The name of the property to check
     * in the player's property list
     *
     * @return True if pane is in player's property list
     *         False if pane isn't in player's property list
     */
    public boolean hasProperty(String pane) {
        return propertyList.contains(pane);
    }

    /**
     * Increments the number of properties that
     * the player owns
     */
    public void incrementPropertyOwned() {
        numOfProperties++;
    }

    /**
     * Decrements the number of properties that
     * the player owns
     */
    public void decrementPropertyOwned() {
        numOfProperties--;
    }

    /**
     * Decrements the number of free properties
     * there are in the game
     */
    public void decrementFreeProperty() {
        numOfFreeProperties--;
    }

    /**
     * Calculates the score of the player
     */
    public void calculateScore() {
        score = money + (numOfProperties * SCORE_CALC_MULTIPLIER) + food + energy + ore;
    }

    /**
     * Purchases resources if player has enough money and
     * the store has it in stock otherwise a message will be
     * returned
     *
     * @param resource Resource that is trying to be bought
     *
     * @return Empty message if purchase was successful
     * otherwise a message depending on resource or
     * fund availability will be returned
     */
    public String buyResource(String resource) {

        Store store = ConfigureController.getGame().getStore();
        String message = "";

        if(!store.canPurchase(resource)) {
            message = "Not enough inventory in the store to make that purchase";
        } else {
            int price = Store.getResourceList().get(resource);
            if (price <= money) {
                purchase(resource, price, store);
            } else {
                message = "You don't have enough money to make that purchase";
            }
        }
        return message;
    }

    private void purchase(String resource,int price, Store store) {
        switch (resource) {
            case "food":
                money -= price;
                food++;
                store.setFood(store.getFood() - 1);
                break;
            case "energy":
                money -= price;
                energy++;
                store.setEnergy(store.getEnergy() - 1);
                break;
            case "ore":
                money -= price;
                ore++;
                store.setOre(store.getOre() - 1);
                break;
            case "foodMule":
                money -= price;
                store.setMule(store.getMule() - 1);
                break;
            case "energyMule":
                money -= price;
                store.setMule(store.getMule() - 1);
                break;
            case "oreMule":
                money -= price;
                store.setMule(store.getMule() - 1);
            default:
                break;
        }

    }

    /**
     * Sells resource if they player has any left to sell
     *
     * @param resource Resource to sell
     *
     * @return Empty message if item was sold
     * otherwise a message saying that there
     * isn't enough inventory will be returned
     */
    public String sellResource(String resource) {

        Store store = ConfigureController.getGame().getStore();
        String message = "";
        int price = Store.getResourceList().get(resource);
        boolean sold = false;

        switch(resource) {
            case "food":
                if(food >= 1) {
                    sold = true;
                    money += price;
                    food--;
                    store.setFood(store.getFood() + 1);
                }
                break;
            case "energy":
                if(energy >= 1) {
                    sold = true;
                    money += price;
                    energy--;
                    store.setEnergy(store.getEnergy() + 1);
                }
                break;
            case "ore":
                if(ore >= 1) {
                    sold = true;
                    money += price;
                    ore--;
                    store.setOre(store.getOre() + 1);
                }
                break;

            default:
                break;
        }
        if(!sold) {
            message = "You don't have enough inventory to sell that item";
        }
        return message;
    }

    /**
     * Gets the player's color
     *
     * @return Color of the player
     */
    public Color getNewColor() { return newColor; }
}
