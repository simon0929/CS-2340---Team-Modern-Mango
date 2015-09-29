package mule.Controller;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import mule.Model.Game;
import mule.Model.Player;

public class ConfigureController {

	@FXML
	private RadioButton standMap, randomMap, beginDiff, stndDiff, tourDiff;

	@FXML
	private ToggleGroup mapType, difficultyType;

	@FXML
	private ChoiceBox<Integer> numOfPlayers;

	@FXML
	private TextField p1Name, p2Name, p3Name, p4Name;

	@FXML
	private ColorPicker p1Color, p2Color, p3Color, p4Color;

	@FXML
	private ChoiceBox<String> p1Race, p2Race, p3Race, p4Race;

	@FXML
	private Button startGameButton;

	private String diff;

	public static Game game;

	public static Player currentPlayer, player1, player2, player3, player4;

	public static ArrayList<Player> playerList;

	public static Stage gameStage;

	public static Scene gameScene;

	public static int maxPlayers;

	public ConfigureController() {
	}

	@FXML
	private void initialize() {
		final ObservableList<Integer> numPlay = FXCollections.observableArrayList();
		numPlay.add(2);
		numPlay.add(3);
		numPlay.add(4);
		numOfPlayers.setItems(numPlay);
		final ObservableList<String> race = FXCollections.observableArrayList();
		race.add("Human");
		race.add("Flapper");
		race.add("Bonzoid");
		race.add("Ugaite");
		race.add("Buzzite");
		p1Race.setItems(race);
		p2Race.setItems(race);
		p3Race.setItems(race);
		p4Race.setItems(race);
		//GameController.round = 1;
	}

	@FXML
	private void handleNumSelect() {
		if (numOfPlayers.getValue() != null) {
			if (numOfPlayers.getSelectionModel().getSelectedItem().intValue() == 2) {
				p1Name.setDisable(false);
				p1Color.setDisable(false);
				p1Race.setDisable(false);
				p2Name.setDisable(false);
				p2Color.setDisable(false);
				p2Race.setDisable(false);
			} else if (numOfPlayers.getSelectionModel().getSelectedItem().intValue() == 3) {
				p1Name.setDisable(false);
				p1Color.setDisable(false);
				p1Race.setDisable(false);
				p2Name.setDisable(false);
				p2Color.setDisable(false);
				p2Race.setDisable(false);
				p3Name.setDisable(false);
				p3Color.setDisable(false);
				p3Race.setDisable(false);
			} else if (numOfPlayers.getSelectionModel().getSelectedItem().intValue() == 4) {
				p1Name.setDisable(false);
				p1Color.setDisable(false);
				p1Race.setDisable(false);
				p2Name.setDisable(false);
				p2Color.setDisable(false);
				p2Race.setDisable(false);
				p3Name.setDisable(false);
				p3Color.setDisable(false);
				p3Race.setDisable(false);
				p4Name.setDisable(false);
				p4Color.setDisable(false);
				p4Race.setDisable(false);
			}
		}
	}

	@FXML
	private void handleMapDiff() {
		if (beginDiff.isSelected() && standMap.isSelected()) {
			startGameButton.setDisable(false);
		} else {
			startGameButton.setDisable(true);
		}
	}

	@FXML
	private void handleStartGame(ActionEvent event) throws IOException {
		maxPlayers = numOfPlayers.getSelectionModel().getSelectedItem().intValue();

		if (beginDiff.isSelected()) {
			diff = "beginner";
		} else if (stndDiff.isSelected()) {
			diff = "standard";
		} else {
			diff = "tournament";
		}

		if (maxPlayers == 4) {
			player1 = new Player(p1Name.getText(), p1Race.getValue(), p1Color.getValue(), diff);
			playerList.add(player1);
			player2 = new Player(p2Name.getText(), p2Race.getValue(), p2Color.getValue(), diff);
			playerList.add(player2);
			player3 = new Player(p3Name.getText(), p3Race.getValue(), p3Color.getValue(), diff);
			playerList.add(player3);
			player4 = new Player(p4Name.getText(), p3Race.getValue(), p3Color.getValue(), diff);
			playerList.add(player4);
			game = new Game(player1, player2, player3, player4);
		}
		else {
			if (maxPlayers == 3) {
				player1 = new Player(p1Name.getText(), p1Race.getValue(), p1Color.getValue(), diff);
				playerList.add(player1);
				player2 = new Player(p2Name.getText(), p2Race.getValue(), p2Color.getValue(), diff);
				playerList.add(player2);
				player3 = new Player(p3Name.getText(), p3Race.getValue(), p3Color.getValue(), diff);
				playerList.add(player3);
				game = new Game(player1, player2, player3, null);
			}
			else {

				if (maxPlayers == 2) {
					player1 = new Player(p1Name.getText(), p1Race.getValue(), p1Color.getValue(), diff);
					playerList.add(player1);
					player2 = new Player(p2Name.getText(), p2Race.getValue(), p2Color.getValue(), diff);
					playerList.add(player2);
					game = new Game(player1, player2, null, null);
				}
			}
		}

		Parent gameScreenParent = FXMLLoader.load(getClass().getResource("/mule/View/Game.fxml"));
		this.gameScene = new Scene(gameScreenParent);
		this.gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.gameStage.setScene(gameScene);
		this.gameStage.show();
	}
}
