package mule.model;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
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
    private Label round, turn, timeLeft, food, money, energy, ore, player1score, player2score, player3score,
            player4score, name1, name2, name3, name4;

    @FXML
    private Button gamble;

    @FXML
    private Rectangle p1Color, p2Color, p3Color, p4Color;

    private int turnTime;

    public Stage gameStage;

    public Scene gameScene;

    @FXML
    private void initialize() {
        ArrayList<Player> playerArr = ConfigureController.getGame().getPlayerArr();

        ArrayList<Label> scoreView = new ArrayList<>(ConfigureController.maxNumPlayers);
        scoreView.add(player1score);
        scoreView.add(player2score);
        scoreView.add(player3score);
        scoreView.add(player4score);

        for (int i = 0; i < ConfigureController.maxNumPlayers; i++) {
            String s = (playerArr.size() >= i + 1 && playerArr.get(i) != null) ? String.valueOf(playerArr.get(i).getScore()) : "";
            scoreView.get(i).setText(s);
        }

        ArrayList<Rectangle> colorView = new ArrayList<>(ConfigureController.maxNumPlayers);
        colorView.add(p1Color);
        colorView.add(p2Color);
        colorView.add(p3Color);
        colorView.add(p4Color);

        for (int i = 0; i < ConfigureController.maxNumPlayers; i++) {
            Color c = (playerArr.size() >= i + 1 && playerArr.get(i) != null) ? playerArr.get(i).getColor() : Color.TRANSPARENT;
            colorView.get(i).setFill(c);
        }

        ArrayList<Label> nameView = new ArrayList<>(ConfigureController.maxNumPlayers);
        nameView.add(name1);
        nameView.add(name2);
        nameView.add(name3);
        nameView.add(name4);

        for (int i = 0; i < ConfigureController.maxNumPlayers; i++) {
            String n = (playerArr.size() >= i + 1 && playerArr.get(i) != null) ? playerArr.get(i).getName() + ":" : "";
            nameView.get(i).setText(n);
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
        Game game = ConfigureController.getGame();
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
            GameController.currentPlayer.setScore(GameController.currentPlayer.getScore() + moneyBonus);
            GameController.turnTime = 0;


            handleEndTurn();
            gameScene = ConfigureController.gameScene;
            gameStage = ConfigureController.gameStage;
            gameStage.setScene(gameScene);

            handleReturnToMap();

        }
    }
}
