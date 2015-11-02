package mule.model;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TownController {

	@FXML
    private Button returnToMap;

	@FXML
	private Label round, turn, timeLeft, food, money, energy, ore, player1score, player2score, player3score,
			player4score, name1, name2, name3, name4;

	@FXML
	private Rectangle p1Color, p2Color, p3Color, p4Color;

	private int turnTime;

	public Stage gameStage;

	public Scene gameScene;

	@FXML
	private void initialize() {
		ArrayList<Player> playerArr = ConfigureController.game.getPlayerArr();

		String s1 = (playerArr.size() >= 1 && playerArr.get(0) != null) ? String.valueOf(playerArr.get(0).getScore()) : "";
		String s2 = (playerArr.size() >= 2 && playerArr.get(1) != null) ? String.valueOf(playerArr.get(1).getScore()) : "";
		String s3 = (playerArr.size() >= 3 && playerArr.get(2) != null) ? String.valueOf(playerArr.get(2).getScore()) : "";
		String s4 = (playerArr.size() == 4 && playerArr.get(3) != null) ? String.valueOf(playerArr.get(3).getScore()) : "";
		player1score.setText(s1);
		player2score.setText(s2);
		player3score.setText(s3);
		player4score.setText(s4);

		Color c1 = (playerArr.size() >= 1 && playerArr.get(0) != null) ? playerArr.get(0).getColor() : Color.TRANSPARENT;
		Color c2 = (playerArr.size() >= 2 && playerArr.get(1) != null) ? playerArr.get(1).getColor() : Color.TRANSPARENT;
		Color c3 = (playerArr.size() >= 3 && playerArr.get(2) != null) ? playerArr.get(2).getColor() : Color.TRANSPARENT;
		Color c4 = (playerArr.size() == 4 && playerArr.get(3) != null) ? playerArr.get(3).getColor() : Color.TRANSPARENT;
		p1Color.setFill(c1);
		p2Color.setFill(c2);
		p3Color.setFill(c3);
		p4Color.setFill(c4);

		String n1 = (playerArr.size() >= 1 && playerArr.get(0) != null) ? playerArr.get(0).getName() + ":" : "";
		String n2 = (playerArr.size() >= 2 && playerArr.get(1) != null) ? playerArr.get(1).getName() + ":" : "";
		String n3 = (playerArr.size() >= 3 && playerArr.get(2) != null) ? playerArr.get(2).getName() + ":" : "";
		String n4 = (playerArr.size() == 4 && playerArr.get(3) != null) ? playerArr.get(3).getName() + ":" : "";
		name1.setText(n1);
		name2.setText(n2);
		name3.setText(n3);
		name4.setText(n4);

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
