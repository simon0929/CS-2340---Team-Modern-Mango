package mule.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hunter on 9/18/2015.
 */
public final class Game implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int round, turn;
	private List<Player> playerArr;
	private boolean selectedProp;
	private final Store store;
	private int randomFactor;
    public Player currentPlayer;
	private static final int RAND_FACT_1 = 25, RAND_FACT_2 = 50, RAND_FACT_3 = 75, RAND_FACT_4 = 100;
	private static final Map<Integer, Integer> RAND_FACTS = new HashMap<>();
	static {
		RAND_FACTS.put(1, RAND_FACT_1);
		RAND_FACTS.put(2, RAND_FACT_1);
		RAND_FACTS.put(3, RAND_FACT_1);
		RAND_FACTS.put(4, RAND_FACT_2);
		RAND_FACTS.put(5, RAND_FACT_2);
		RAND_FACTS.put(6, RAND_FACT_2);
		RAND_FACTS.put(7, RAND_FACT_2);
		RAND_FACTS.put(8, RAND_FACT_3);
		RAND_FACTS.put(9, RAND_FACT_3);
		RAND_FACTS.put(10, RAND_FACT_3);
		RAND_FACTS.put(11, RAND_FACT_3);
		RAND_FACTS.put(12, RAND_FACT_4);
	}

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
		randomFactor = RAND_FACTS.get(turn);
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


	public List<Player> getPlayerArr() {
		return playerArr;
	}
	
	public void setPlayerList(List<Player> playerList) {
		playerArr = playerList;
	}

	public int getRandomFactor() {
		return randomFactor;
	}
	
	public void setRandomFactor(int randomFactor) {
		this.randomFactor = randomFactor;
	}
}
