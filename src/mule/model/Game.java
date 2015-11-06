package mule.model;

import java.util.ArrayList;

/**
 * Created by Hunter on 9/18/2015.
 */
public final class Game implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int round, turn;
	private ArrayList<Player> playerArr;
	private boolean selectedProp;
	private Store store;
	private int m;
	public Player currentPlayer;

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
		round = 1;
		selectedProp = false;
		store = new Store(diff);
		currentPlayer = playerArr.get(turn - 1);
	}

	public Player getCurrPlayer() {
		currentPlayer = playerArr.get(turn - 1);
		return currentPlayer;
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
	
	public void setRound(int round) {
		this.round = round;
	}

	public int getTurn() {
		return turn;
	}
	
	public void setTurn(int turn) {
		this.turn = turn;
	}

	public Store getStore() { return store; }


	public ArrayList<Player> getPlayerArr() {
		return playerArr;
	}
	
	public void setPlayerList(ArrayList<Player> newList) {
		playerArr = newList;
	}

	public int getM() {
		return m;
	}
	
	public void setM(int m) {
		this.m = m;
	}
}
