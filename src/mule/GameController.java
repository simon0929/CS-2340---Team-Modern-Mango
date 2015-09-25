package mule;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class GameController {

	@FXML
    private Label townLabel;

	@FXML
	private Label round, turn, timeLeft, food, money, energy, ore, player1score, player2score, player3score, player4score;

	@FXML
	private Pane pane;

	private String phase;

	public static Game game;

	private Player currentPlayer;

	private int turnNumber, numOfPropBuyInRound;

	private boolean selectionPhase;

	@FXML
	private void initialize() {
		currentPlayer = ConfigureController.player1;
		turn.setText(currentPlayer.getName());
		turnNumber = 1;
		numOfPropBuyInRound = 0;
		selectionPhase = true;
		food.setText(String.valueOf(currentPlayer.getFood()));
		money.setText(String.valueOf(currentPlayer.getMoney()));
		energy.setText(String.valueOf(currentPlayer.getEnergy()));
		ore.setText(String.valueOf(currentPlayer.getOre()));
		player1score.setText("0");
		player2score.setText("0");
		player3score.setText("0");
		player4score.setText("0");
	}

	@FXML
	private void handleTown(MouseEvent event) throws IOException {
		Parent townScreen = FXMLLoader.load(getClass().getResource("Town.fxml"));
		Scene townScene = new Scene(townScreen);
		Stage townStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		townStage.setScene(townScene);
		townStage.show();
	}

	@FXML
	private void handleEndTurn(MouseEvent event) throws IOException {
		this.game.update();
		this.round.setText(String.valueOf(this.game.getRound()));
		turnNumber++;

		if (turnNumber > ConfigureController.maxPlayers) {
			calculateScores();

			turnNumber = 1;
			if (numOfPropBuyInRound == 0) {
				selectionPhase = false;
			}
			else {
				numOfPropBuyInRound = 0;
			}
		}

		currentPlayer = ConfigureController.playerList[turnNumber - 1];

		turn.setText(currentPlayer.getName());
		food.setText(String.valueOf(currentPlayer.getFood()));
		money.setText(String.valueOf(currentPlayer.getMoney()));
		energy.setText(String.valueOf(currentPlayer.getEnergy()));
		ore.setText(String.valueOf(currentPlayer.getOre()));
	}

	@FXML
	private void handleProperty(MouseEvent event) throws IOException {
		if (selectionPhase) {
			this.game = ConfigureController.game;
			if (!this.game.selectedProp()) {
				Color playerColor = this.game.getCurrPlayer().getColor();

				Property property = (Property) event.getSource();

				if (property.getOwner() == null || property.getOwner() == this.game.getCurrPlayer()) {
					currentPlayer.incrementPropertyOwned();

					if (currentPlayer.getNumOfFreeProperties() == 0) {
						currentPlayer.setMoney(currentPlayer.getMoney() - 300);
					}
					else {
						currentPlayer.decrementFreeProperty();
					}

					numOfPropBuyInRound++;
					property.setOwner(this.game.getCurrPlayer());

					Region reg = (Region) event.getSource();
					reg.setBorder(new Border(new BorderStroke(playerColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(8.0))));
					this.game.setSelectedProp(true);
				}
			}
		}
	}

	private void calculateScores() {
		player1score.setText("0");
		player2score.setText("0");
		player3score.setText("0");
		player4score.setText("0");
	}
}
