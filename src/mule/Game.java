package mule;

import java.util.ArrayList;

/**
 * Created by Hunter on 9/18/2015.
 */
public class Game {
	private int round, turn;
	private ArrayList<Player> playerArr;
	private boolean selectedProp;

	public Game(Player p1, Player p2, Player p3, Player p4) {
		this.playerArr = new ArrayList<Player>();
		this.playerArr.add(p1);
		this.playerArr.add(p2);

		if (p3 != null) {
			this.playerArr.add(p3);
		}
		if (p4 != null) {
			this.playerArr.add(p4);
		}
		this.turn = 1;
		this.selectedProp = false;
		this.round = 1;
	}

	public Player getCurrPlayer() {
		return this.playerArr.get(this.turn - 1);
	}

	public boolean selectedProp() {
		return this.selectedProp;
	}

	public void setSelectedProp(boolean ans) {
		this.selectedProp = ans;
	}

	public void update() {
		if (this.playerArr.size() == this.turn) {
			this.turn = 1;
			this.round++;
		} else {
			this.turn++;
		}

		this.selectedProp = false;
	}

	public int getRound() {
		return this.round;
	}

	public int getTurn() {
		return this.turn;
	}

}
