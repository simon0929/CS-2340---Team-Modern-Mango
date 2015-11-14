package mule.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Game class that keeps track
 * of players, player's stats,
 * and game stats (Turn #, Round #,
 * current player, etc)
 *
 * @author ModernMango
 *
 */
public final class Game implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int round, turn;
	private List<Player> playerArr;
	private boolean selectedProp;
	private final Store store;
	private int randomFactor;
    private Player currentPlayer;
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

	/**
	 * Constructs a new Game with 2-4 players and a difficulty
	 *
	 * @param p1 Player one of the game
	 * @param p2 Player two of the game
	 * @param p3 Player three of the game (may be null)
	 * @param p4 Player four of the game (may also be null)
	 * @param diff Difficulty of the game
	 */
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

	/**
	 * Gets the current player
	 * @return Current player
	 */
	public Player getCurrPlayer() {
		currentPlayer = playerArr.get(turn - 1);
		return currentPlayer;
	}

	/**
	 * Returns whether the player has selected a property this turn
	 *
	 * @return True if player selected property
	 * 		   False if player hasn't selected
	 */
	public boolean selectedProp() {
		return selectedProp;
	}

	/**
	 * Set whether player has selected property to the boolean passed in
	 * @param ans Boolean to set whether player has chosen a property
	 */
	public void setSelectedProp(boolean ans) {
		selectedProp = ans;
	}

	/**
	 * Updates round number, turn number, random factor,
	 * and whether player selected property
	 */
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

	/**
	 * Gets the round of the game
	 * @return Round of game
	 */
	public int getRound() {
		return round;
	}

	/**
	 * Sets round number to the value passed in
	 * @param roundNumber New round number
	 */
	public void setRound(int roundNumber) {
		round = roundNumber;
	}

	/**
	 * Gets the turn of the game
	 * @return Number value of the turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Sets turn to the value passed in
	 * @param turnNumber Number to set turn number to
	 */
	public void setTurn(int turnNumber) {
		turn = turnNumber;
	}

	/**
	 * Gets store of the game
	 * @return Store of game
	 */
	public Store getStore() { return store; }

	/**
	 * Gets the list of players in the game
	 * @return List of players
	 */
	public List<Player> getPlayerArr() {
		return playerArr;
	}

	/**
	 * Sets the list of players the list passed in
	 * @param playerList List to set the player list to
	 */
	public void setPlayerList(List<Player> playerList) {
		playerArr = playerList;
	}

	/**
	 * Gets the random factor used for random events
	 * @return Random factor
	 */
	public int getRandomFactor() {
		return randomFactor;
	}

	/**
	 * Set random factor to the the value passed in
	 * @param randFact Value to set the random factor to
	 */
	public void setRandomFactor(int randFact) {
		randomFactor = randFact;
	}

	/**
	 * Gets the current player of the game
	 * @return Current player
	 */
	public Player getCurrentPlayer() { return currentPlayer; }
}
