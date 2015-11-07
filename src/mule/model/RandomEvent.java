package mule.model;

import java.util.ArrayList;
import java.util.List;

final class RandomEvent {

	private final List<String> eventList;

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


