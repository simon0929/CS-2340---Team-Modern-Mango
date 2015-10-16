package mule.model;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Button;

/**
 * Created by madijuby on 10/5/15.
 *
 */
public class PubController {
    @FXML
    private Label round, turn, timeLeft, food, money, energy, ore, player1score, player2score, player3score, player4score;

    @FXML
    private Button gamble;

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
    private void handleGamble() {
        if (gamble.isPressed()) {
            int moneyBonus, timeBonus, roundBonus;
            Random rand = new Random();

            if (GameController.roundNumber < 4) {
                roundBonus = 50;
            } else if (GameController.roundNumber < 8) {
                roundBonus = 100;
            } else if (GameController.roundNumber < 12) {
                roundBonus = 150;
            } else {
                roundBonus = 200;
            }

            if (GameController.turnTime < 13) {
                timeBonus = 50;
            } else if (GameController.turnTime < 26) {
                timeBonus = 100;
            } else if (GameController.turnTime < 38) {
                timeBonus = 150;
            } else {
                timeBonus = 200;
            }

            moneyBonus = roundBonus * (rand.nextInt(timeBonus) + 1);

            if (moneyBonus > 250) {
                moneyBonus = 250;
            }

            GameController.currentPlayer.setMoney(GameController.currentPlayer.getMoney() + moneyBonus);
            GameController.turnTime = 0;
            handleReturnToMap();
        }
    }
}
