package mule.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Class that handles the configuration of the Mule game
 * @author Team Modern Mango
 *
 */
public final class ConfigureController implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	//Everything labeled @FXML relates directly to the .fxml files


	@FXML
	private RadioButton standMap, beginDiff, standDiff, tourDiff, noobDiff, legendDiff;


	@FXML
	private ChoiceBox<Integer> numOfPlayers;

	@FXML
	private TextField p1Name, p2Name, p3Name, p4Name;

	@FXML
	private ColorPicker p1Color, p2Color, p3Color, p4Color;

	@FXML
	private ChoiceBox<String> p1Race, p2Race, p3Race, p4Race;

	@FXML
	private Button startGameButton, startGameButton2;

    private static Game game;

	private static Scene gameScene;

	private static Stage gameStage;

    private static Parent gameScreenParent;

	private static List<Player> playerList;

	private static int numPlayers;

	private static final int MIN_NUM_PLAYERS = 2, MAX_NUM_PLAYERS = 4;

	private static boolean loaded = false;

	@FXML
	private void initialize() {

        final ObservableList<Integer> numPlay = FXCollections.observableArrayList();
        for (int i = MIN_NUM_PLAYERS; i <= MAX_NUM_PLAYERS; i++) {
            numPlay.add(i);
        }

        numOfPlayers.setItems(numPlay);
        final ObservableList<String> race = FXCollections.observableArrayList();
        race.add("Human");
        race.add("Flapper");
        race.add("Bonzoid");
        race.add("Ugaite");
        race.add("Buzzite");
        race.add("Sozon");
        race.add("Dracanoid");
        p1Race.setItems(race);
        p2Race.setItems(race);
        p3Race.setItems(race);
        p4Race.setItems(race);
        playerList = new ArrayList<>();

		startGameButton.setDisable(true);
		startGameButton2.setDisable(true);

	}

	//Selectively enables the correct number of Player entry fields based on max number of players
	//This happens when you select the number of players and hit "enter"
	@FXML
	private void handleNumSelect() {
		if (numOfPlayers.getValue() != null) {
			if (numOfPlayers.getSelectionModel().getSelectedItem() >= MIN_NUM_PLAYERS) {
				p1Name.setDisable(false);
				p1Color.setDisable(false);
				p1Race.setDisable(false);
				p2Name.setDisable(false);
				p2Color.setDisable(false);
				p2Race.setDisable(false);
				p1Race.getSelectionModel().selectFirst();
                p2Race.getSelectionModel().selectFirst();

				noobDiff.setSelected(true);
                standMap.setSelected(true);

				p3Name.setDisable(true);
				p3Color.setDisable(true);
				p3Race.setDisable(true);

				p4Name.setDisable(true);
				p4Color.setDisable(true);
				p4Race.setDisable(true);
            } if (numOfPlayers.getSelectionModel().getSelectedItem() >= MIN_NUM_PLAYERS + 1) {
				p3Name.setDisable(false);
				p3Color.setDisable(false);
				p3Race.setDisable(false);
                p3Race.getSelectionModel().selectFirst();
			} if (numOfPlayers.getSelectionModel().getSelectedItem() == MAX_NUM_PLAYERS) {
				p4Name.setDisable(false);
				p4Color.setDisable(false);
				p4Race.setDisable(false);
                p4Race.getSelectionModel().selectFirst();
			}
		}
		startGameButton.setDisable(false);
		startGameButton2.setDisable(false);
	}

    @FXML
	private void enableStartButton() {
    	if ((noobDiff.isSelected() || beginDiff.isSelected() || standDiff.isSelected() || tourDiff.isSelected()
    		    || legendDiff.isSelected()) && standMap.isSelected()
                && numOfPlayers.getSelectionModel().getSelectedItem() != null) {
			startGameButton.setDisable(false);
			startGameButton2.setDisable(false);

		} else {
			startGameButton.setDisable(true);
			startGameButton2.setDisable(true);

		}
	}

	//Runs when the start button is clicked. Sets up the main game screen and objects.
	@FXML
	private void handleStartGame(ActionEvent event) throws IOException {
			numPlayers = numOfPlayers.getSelectionModel().getSelectedItem();

			//Initializes string objects containing the difficulty to add to Player objects later.
			String diff;
			if (beginDiff.isSelected()) {
				diff = "beginner";
			} else if (standDiff.isSelected()) {
				diff = "standard";
			} else if (tourDiff.isSelected()) {
				diff = "tournament";
			} else if (noobDiff.isSelected()) {
				diff = "noobie";
			} else {
				diff = "legendary";
			}

			//Create new Game with correct number of players
			Player player1 = new Player(p1Name.getText(), p1Race.getValue(), p1Color.getValue(), diff);
			Player player2 = new Player(p2Name.getText(), p2Race.getValue(), p2Color.getValue(), diff);
			playerList.add(player1);
			playerList.add(player2);

			Player player3 = null;
			Player player4 = null;

			if (numPlayers >= MIN_NUM_PLAYERS + 1) {
				player3 = new Player(p3Name.getText(), p3Race.getValue(), p3Color.getValue(), diff);
				playerList.add(player3);

			}
			if (numPlayers == MAX_NUM_PLAYERS) {
				player4 = new Player(p4Name.getText(), p4Race.getValue(), p4Color.getValue(), diff);
				playerList.add(player4);

			}

			game = new Game(player1, player2, player3, player4, diff);


			//Calls the Game.fxml file and actually constructs the GUI.
			Parent gameScreenParent = FXMLLoader.load(getClass().getResource("/mule/view/Game.fxml"));
			gameScene = new Scene(gameScreenParent);
			gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			gameStage.setScene(gameScene);
			gameStage.show();
	}


	@FXML
	private void handleLoad(ActionEvent event) {
		loaded = true;
		try {
    		FileInputStream fileIn = new FileInputStream("/tmp/game.ser");
    		ObjectInputStream in = new ObjectInputStream(fileIn);
    		game = (Game) in.readObject();
    		playerList = game.getPlayerArr();
    		in.close();
    		fileIn.close();

    		FileInputStream fileIn1 = new FileInputStream("/tmp/round.ser");
    		ObjectInputStream in1 = new ObjectInputStream(fileIn1);
    		game.setRound(in1.readInt());
    		in1.close();
    		fileIn1.close();

    		FileInputStream fileIn2 = new FileInputStream("/tmp/turn.ser");
    		ObjectInputStream in2 = new ObjectInputStream(fileIn2);
    		game.setTurn(in2.readInt());
    		in2.close();
    		fileIn2.close();

    		FileInputStream fileIn3 = new FileInputStream("/tmp/m.ser");
    		ObjectInputStream in3 = new ObjectInputStream(fileIn3);
    		game.setRandomFactor(in3.readInt());
    		in3.close();
    		fileIn3.close();

    		for(Player player : playerList) {
                player.setColor(player.getNewColor());
            }

    		gameScreenParent = FXMLLoader.load(getClass().getResource("/mule/view/Game.fxml"));
    		gameScene = new Scene(gameScreenParent);
    		gameStage = (Stage) startGameButton.getScene().getWindow();
    		gameStage.setScene(gameScene);
    		gameStage.show();
            Main.getAudioClip().stop();
        } catch (IOException | ClassNotFoundException i) {
            Logger logger = Logger.getLogger(ConfigureController.class.getName());
            logger.log(Level.SEVERE, i.toString(), i);
    	}
    }

	/**
	 * Gets the Game
	 * @return Game created
	 */
	public static Game getGame() { return game; }

	/**
	 * Gets the players playing in the game
	 * @return List of players playing
	 */
	public static List<Player> getPlayerList() { return playerList;}

	/**
	 * Gets the maximum number of players allowed to play
	 * @return Maximum number of players allowed
	 */
	public static int getMaxNumPlayers() { return MAX_NUM_PLAYERS;}

	/**
	 * Gets the game stage
	 * @return Game stage
	 */
	public static Stage getGameStage() { return gameStage;}

	/**
	 * Gets the game scene
	 * @return Game scene
	 */
	public static Scene getGameScene() { return gameScene;}

    /**
     * Gets the object used to make the scene
     * @return Parent gameScreenParent
     */
    public static Parent getGameScreenParent() { return gameScreenParent; }

	/**
	 * Gets the number of players playing
	 * @return Number of players playing
	 */
	public static int getNumPlayers() { return numPlayers; }

	/**
	 * Gets whether a game was loaded
	 * @return True if a game was loaded
	 *         False if a game wasn't loaded
	 */
    public static boolean getLoaded() { return loaded; }

}
