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

	private final List<String> eventList;

	/**
	 * Constructs RandomEvent object which has
	 * a list of bad and good events
	 */
	public RandomEvent() {
		eventList = new ArrayList<>();
		eventList.add("YOU JUST RECEIVED A PACKAGE FROM THE GT ALUMNI CONTAINING 3 FOOD AND 2 ENERGY UNITS.");
		eventList.add("A WANDERING TECH STUDENT REPAID YOUR HOSPITALITY BY LEAVING TWO BARS OF ORE.");
		eventList.add("THE MUSEUM BOUGHT YOUR ANTIQUE PERSONAL COMPUTER FOR $");
		eventList.add("YOU FOUND A DEAD MOOSE RAT AND SOLD THE HIDE FOR $");
		eventList.add("FLYING CAT-BUGS ATE THE ROOF OFF YOUR HOUSE. REPAIRS COST $");
		eventList.add("MISCHIEVOUS UGA STUDENTS BROKE INTO YOUR STORAGE SHED AND STOLE HALF YOUR FOOD.");
		eventList.add("YOUR SPACE GYPSY IN-LAWS MADE A MESS OF THE TOWN. CLEANING IT UP COST YOU $");
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
		int randInt = calcRandInt(game);
		String eventModifier = "";

		switch(randInt) {
			case 0:
				player.setFood(player.getFood() + 3);
				player.setEnergy(player.getEnergy() + 2);
				break;
			case 1:
				player.setOre(player.getOre() + 2);
				break;
			case 2:
				player.setMoney(player.getMoney() + (8 * m));
				eventModifier = Integer.toString(8 * m);
				break;
			case 3:
				player.setMoney(player.getMoney() + (2 * m));
				eventModifier = Integer.toString(2 * m);
				break;
			case 4:
				int money4 = player.getMoney() - (4 * m);
				player.setMoney(money4 >= 0 ? money4 : 0);
				eventModifier = Integer.toString(4 * m);
				break;
			case 5:
				player.setFood(player.getFood() / 2);
				break;
			case 6:
				int money5 = player.getMoney() - (6 * m);
				player.setMoney(money5 >= 0 ? money5 : 0);
				eventModifier = Integer.toString(6 * m);
				break;
		}
		return eventList.get(randInt) + eventModifier;
	}

	/**
	 * Calculate random integer to use as the index to
	 * get random event from list
	 *
	 * @param game Game to calculate random integer for
	 *
	 * @return Random integer to get random event
	 */
	private int calcRandInt(Game game) {
		int randInt;
		if (game.getTurn() == 1 && game.getRound() > 1) {
			randInt = (int) (Math.random() * RAND_MULTIPLIER);
		} else {
			randInt = (int) (Math.random() * eventList.size());
		}
		return randInt;
	}

}


