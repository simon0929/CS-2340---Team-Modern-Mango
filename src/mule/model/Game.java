package mule.model;

import java.util.ArrayList;
import java.util.List;

public final class Game {
	private int round, turn;
	private final List<Player> playerArr;
	private boolean selectedProp;
	private final Store store;
	private int randomFactor;
	private static final int RAND_FACT_1 = 25, RAND_FACT_2 = 50, RAND_FACT_3 = 75, RAND_FACT_4 = 100;

	public Game(Player p1, Player p2, Player p3, Player p4, String diff) {

		playerArr = new ArrayList<>();
		playerArr.add(p1);
		playerArr.add(p2);

		if (p3 != null) {
			playerArr.add(p3);
		}
		if (p4 != null) {
			playerArr.add(p4);
		}
		turn = 1;
		round = 1;
		selectedProp = false;
		store = new Store(diff);
	}

	public Player getCurrPlayer() {
		return playerArr.get(turn - 1);
	}

	public boolean selectedProp() {
		return selectedProp;
	}

	public void setSelectedProp(boolean ans) {
		selectedProp = ans;
	}

	public void update() {
		if (playerArr.size() == turn) {
			turn = 1;
			round++;
		} else {
			turn++;
		}

		if (turn <= 3) {
			randomFactor = RAND_FACT_1;
		} else {

			if (turn >= 4 && turn <= 7) {
				randomFactor = RAND_FACT_2;
			} else {
				if (turn >= 8 && turn <= 11) {
				randomFactor = RAND_FACT_3;
				} else {
					randomFactor = RAND_FACT_4;
				}
			}
		}
		selectedProp = false;
	}

	public int getRound() {
		return round;
	}

	public int getTurn() {
		return turn;
	}

	public Store getStore() { return store; }


	public List<Player> getPlayerArr() {
		return playerArr;
		}

	public int getRandomFactor() {
		return randomFactor;
		}
	}
