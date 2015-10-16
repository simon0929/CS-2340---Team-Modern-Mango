package mule;

import java.util.ArrayList;

/**
 * Created by Hunter on 9/18/2015.
 */
public class Game {
	private int round, turn;
	private ArrayList<Player> playerArr;
	private boolean selectedProp;
	private Store store;
	private int m;

	public Game(Player p1, Player p2, Player p3, Player p4, String diff) {

		playerArr = new ArrayList<Player>();
		playerArr.add(p1);
		playerArr.add(p2);

		if (p3 != null) {
			playerArr.add(p3);
		}
		if (p4 != null) {
			playerArr.add(p4);
		}
		turn = 1;
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
		} else {
			turn++;
		}

		if (turn <= 3) {
			m = 25;
		} else {

			if (turn >= 4 && turn <= 7) {
				m = 50;
			} else {
				if (turn >= 8 && turn <= 11) {
				m = 75;
				} else {
					m = 100;
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


	public ArrayList<Player> getPlayerArr() {
		return playerArr;
		}

	public int getM() {
		return m;
		}
	}
