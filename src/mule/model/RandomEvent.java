package mule.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Random event class that keeps track of different
 * events and updates stats according to what the
 * event is.
 *
 * @author ModernMango
 *
 */
final class RandomEvent {

    private final List<String> playerEventList, globalEventList;
    private int randInt;

    /**
     * Constructs RandomEvent object which has
     * a list of bad and good events
     */
    public RandomEvent() {
        playerEventList = new ArrayList<>();
        playerEventList.add("YOU RECEIVE A SUBSTANTIAL PACKAGE FROM GT ALUMNI: 3 FOOD, 2 ENERGY UNITS, AND $");
        playerEventList.add("YOU RECEIVE A MODERATE PACKAGE FROM GT ALUMNI: 2 FOOD AND 2 ENERGY UNITS.");
        playerEventList.add("YOU RECEIVE A MODEST PACKAGE FROM GT ALUMNI: 1 FOOD AND 1 ENERGY UNIT.");
        playerEventList.add("A WANDERING TECH STUDENT REPAID YOUR HOSPITALITY BY LEAVING TWO BARS OF ORE.");
        playerEventList.add("THE MUSEUM BOUGHT YOUR ANTIQUE PERSONAL COMPUTER FOR $");
        playerEventList.add("YOU FOUND A DEAD MOOSE RAT AND SOLD THE HIDE FOR $");
        playerEventList.add("FLYING CAT-BUGS ATE THE ROOF OFF YOUR HOUSE. REPAIRS COST $");
        playerEventList.add("MISCHIEVOUS UGA STUDENTS BROKE INTO YOUR STORAGE SHED AND STOLE HALF YOUR FOOD.");
        playerEventList.add("YOUR SPACE GYPSY IN-LAWS MADE A MESS OF THE TOWN. CLEANING IT UP COST YOU $");
        playerEventList.add("THE INTERNET GOES OUT. IN YOUR PANIC, YOU KNOCK FOOD ONTO THE GROUND AND RUIN IT.");
        playerEventList.add("WOMP-RATS CHEW THROUGH THE WIRING IN ONE OF YOUR MULES. REPAIRS COST $");
        playerEventList.add("AN EARTHQUAKE HITS ONE YOUR PROPERTIES. YOU LOSE FOOD AND ENERGY UNITS.");
        playerEventList.add("YOU RECEIVE AN INHERITANCE FROM A MYSTERIOUS DEAD UNCLE of $");
        playerEventList.add("SOMEONE CONDUCTS A BIZARRE RITUAL ON ONE OF YOUR PROPERTIES. THE CLEANERS COST $");
        playerEventList.add("IT'S WABBIT SEASON. GAIN FOOD AND SELL THE HIDE FOR $");
        playerEventList.add("IT'S DUCK SEASON. GAIN FOOD AND SELL THE HIDE FOR $");
        playerEventList.add("YOU FIND A ORE DEPOSIT ON YOUR LAND.");
        playerEventList.add("ONE OF YOUR DEPOSITS GOES TOO DEEP AND AWAKENS AN ANCIENT EVIL. YOU LOSE ORE.");
        playerEventList.add("YOUR UNCLE DIED AND LEFT YOU ORE. UNFORTUNATELY, HE ALSO LEFT YOU HIS DEBTS OF $");
        playerEventList.add("YOU CONTRACT THE PLAGUE AND, AS YOU KNOW, HOSPITAL BILLS AREN'T CHEAP.");

        globalEventList = new ArrayList<>();
        globalEventList.add("IT'S TAX TIME. PAY 20% OF YOUR CURRENT WEALTH.");
        globalEventList.add("A NEW RULER HAS TAKEN OVER AND HE DEMANDS TRIBUTE IN THE FORM OF SHINY, SHINY ORE.");
        globalEventList.add("DROUGHT STRIKES. LOSE 5 FOOD.");
        globalEventList.add("THE LAND IS FERTILE. GAIN 5 FOOD.");
        globalEventList.add("SCIENTISTS DEVELOP NEW FORM OF ENERGY PRODUCTION. IT GOES WELL. YOU GAIN 3 ENERGY.");
        globalEventList.add("SCIENTISTS DEVELOP NEW FORM OF ENERGY PRODUCTION. IT GOES POORLY. YOU LOSE 3 ENERGY.");
        globalEventList.add("THE RULER OF YOUR LAND GIVES BACK. EVERYONE GETS ORE, FOOD, AND ENERGY.");
        globalEventList.add("THE RULER OF YOUR LAND TAKES. EVERYONE GIVES ORE, FOOD, AND ENERGY");
    }

    private static final int RAND_MULTIPLIER = 4;

    /**
     * Gets a random event and changes a player's
     * based on what the event is
     *
     * @param game Game to get random factor from
     *
     * @param player Player to have random event happen to
     *
     * @return Random event that happened
     */
    public String random(Game game, Player player) {
        int m = game.getRandomFactor();
        int randInt = calcPlayerRandInt(game);
        String eventModifier = "";

        switch(randInt) {
            case 0:
                player.setFood(player.getFood() + 3);
                player.setEnergy(player.getEnergy() + 2);
                player.setMoney(player.getMoney() + (2 * m));
                eventModifier = Integer.toString(2 * m) + ".";
                break;
            case 1:
                player.setFood(player.getFood() + 2);
                player.setEnergy(player.getEnergy() + 2);
                break;
            case 2:
                player.setFood(player.getFood() + 1);
                player.setEnergy(player.getEnergy() + 1);
                break;
            case 3:
                player.setOre(player.getOre() + 2);
                break;
            case 4:
                player.setMoney(player.getMoney() + (2 * m));
                eventModifier = Integer.toString(2 * m);
                break;
            case 5:
                player.setMoney(player.getMoney() + m);
                eventModifier = Integer.toString(m);
                break;
            case 6:
                int money6 = player.getMoney() - (3 * m);
                player.setMoney(money6 >= 0 ? money6 : 0);
                eventModifier = Integer.toString(3 * m);
                break;
            case 7:
                player.setFood(player.getFood() / 2);
                break;
            case 8:
                int money8 = player.getMoney() - (4 * m);
                player.setMoney(money8 >= 0 ? money8 : 0);
                eventModifier = Integer.toString(4 * m);
                break;
            case 9:
                int food9 = player.getFood() - 2;
                player.setFood(food9 >= 0 ? food9 : 0);
                break;
            case 10:
                player.setMoney(player.getMoney() + m);
                eventModifier = Integer.toString(m);
                break;
            case 11:
                int food11 = player.getFood() - 4;
                player.setFood(food11 >= 0 ? food11 : 0);
                int energy11 = player.getEnergy() - 2;
                player.setEnergy(energy11 >= 0 ? energy11 : 0);
                break;
            case 12:
                player.setMoney(player.getMoney() + (4 * m));
                eventModifier = Integer.toString(4 * m);
                break;
            case 13:
                player.setMoney(player.getMoney() + (2 * m));
                eventModifier = Integer.toString(2 * m);
                break;
            case 14:
                player.setFood(player.getFood() + 1);
                player.setMoney(player.getMoney() + m);
                break;
            case 15:
                player.setFood(player.getFood() + 1);
                player.setMoney(player.getMoney() + m);
                break;
            case 16:
                player.setOre(player.getOre() + 4);
                break;
            case 17:
                int ore17 = player.getOre() - 4;
                player.setOre(ore17 >= 0 ? ore17 : 0);
                break;
            case 18:
                player.setOre(player.getOre() + 15);
                int money18 = player.getMoney() - (9 * m);
                player.setMoney(money18 >= 0 ? money18 : 0);
                eventModifier = Integer.toString(9 * m);
                break;
            case 19:
                int money19 = player.getMoney() - (7 * m);
                player.setMoney(money19 >= 0 ? money19 : 0);
                eventModifier = Integer.toString(7 * m);
                break;
        }
        return playerEventList.get(randInt) + eventModifier;
    }

    public int getGlobalRandomEventInt(Game game) {
        return calcGlobalRandInt(game);
    }

    public String globalRandomEvent(Game game, Player player, int randInt) {
        int m = game.getRandomFactor();
        String eventModifier = "";

        switch(randInt) {
            case 0:
                int money0 = (int) (player.getMoney() - (player.getMoney() * .25));
                player.setMoney(money0 >= 0 ? money0 : 0);
                break;
            case 1:
                int ore1 = player.getOre() - 2;
                player.setOre(ore1 >= 0 ? ore1 : 0);
                break;
            case 2:
                int food2 = player.getFood() - 5;
                player.setFood(food2 >= 0 ? food2 : 0);
                break;
            case 3:
                player.setFood(player.getFood() + 5);
                break;
            case 4:
                player.setEnergy(player.getEnergy() + 3);
                break;
            case 5:
                int energy5 = player.getEnergy() - 3;
                player.setEnergy(energy5 >= 0 ? energy5 : 0);
                break;
            case 6:
                player.setFood(player.getFood() + 2);
                player.setEnergy(player.getEnergy() + 2);
                player.setOre(player.getOre() + 2);
                break;
            case 7:
                int food7 = player.getFood() - 2;
                player.setFood(food7 >= 0 ? food7 : 0);
                int energy7 = player.getEnergy() - 2;
                player.setEnergy(energy7 >= 0 ? energy7 : 0);
                int ore7 = player.getOre() - 2;
                player.setOre(ore7 >= 0 ? ore7 : 0);
                break;
        }
        return globalEventList.get(randInt) + eventModifier;
    }

    /**
     * Calculate random integer to use as the index to
     * get random event from list
     *
     * @param game Game to calculate random integer for
     *
     * @return Random integer to get random event
     */
    private int calcPlayerRandInt(Game game) {
        int randInt;
        if (game.getTurn() == 1 && game.getRound() > 1) {
            randInt = (int) (Math.random() * RAND_MULTIPLIER);
        } else {
            randInt = (int) (Math.random() * playerEventList.size());
        }
        return randInt;
    }

    private int calcGlobalRandInt(Game game) {
        int randInt;
        if (game.getTurn() == 1 && game.getRound() > 1) {
            randInt = (int) (Math.random() * RAND_MULTIPLIER);
        } else {
            randInt = (int) (Math.random() * globalEventList.size());
        }
        return randInt;
    }
}


