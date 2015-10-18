package mule.model;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TownController {

	@FXML
    private Button returnToMap;

	@FXML
	private Label round, turn, timeLeft, food, money, energy, ore, player1score, player2score, player3score, player4score;

	private int turnTime;

	public Stage gameStage;

	public Scene gameScene;

	@FXML
	private void initialize() {
		player1score.setText(String.valueOf(ConfigureController.player1.getScore()));
		player2score.setText(String.valueOf(ConfigureController.player2.getScore()));
		if (ConfigureController.player3 != null) {
			player3score.setText(String.valueOf(ConfigureController.player3.getScore()));
		}
		else {
			player3score.setText("0");
		}
		if (ConfigureController.player4 != null) {
			player4score.setText(String.valueOf(ConfigureController.player4.getScore()));
		}
		else {
			player4score.setText("0");
		}

		food.setText(String.valueOf(GameController.currentPlayer.getFood()));
		money.setText(String.valueOf(GameController.currentPlayer.getMoney()));
		energy.setText(String.valueOf(GameController.currentPlayer.getEnergy()));
		ore.setText(String.valueOf(GameController.currentPlayer.getOre()));

		round.setText(String.valueOf(GameController.roundNumber));
		turn.setText(GameController.currentPlayer.getName());

		turnTime = GameController.turnTime;

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (turnTime >= 1) {
							timeLeft.setText(String.valueOf(turnTime--));
						} else {
							handleEndTurn();
						}
					}
				});
			}
		}, 0, 1000);
	}

	@FXML
	private void handleReturnToMap(ActionEvent event) throws IOException {
		gameScene = ConfigureController.gameScene;
		gameStage = ConfigureController.gameStage;
		gameStage.setScene(gameScene);
	}

	@FXML
	private void handleReturnToMap() {
		gameScene = ConfigureController.gameScene;
		gameStage = ConfigureController.gameStage;
		gameStage.setScene(gameScene);
	}

	@FXML
	private void handleEndTurn() {
		Game game = ConfigureController.game;
		game.update();
	}

	@FXML
	 private void handlePub(MouseEvent event) throws IOException {
		Parent pubScreen = FXMLLoader.load(getClass().getResource("/mule/view/Pub.fxml"));
		Scene pubScene = new Scene(pubScreen);
		Stage pubStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		pubStage.setScene(pubScene);
		pubStage.show();
	}

	@FXML
	private void handleStore(MouseEvent event) throws IOException {
		Parent storeScreen = FXMLLoader.load(getClass().getResource("/mule/view/Store.fxml"));
		Scene storeScene = new Scene(storeScreen);
		Stage storeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		storeStage.setScene(storeScene);
		storeStage.show();
	}

}
