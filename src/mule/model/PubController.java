package mule.model;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Button;

public class PubController {
    @FXML
    private Label round, turn, timeLeft, food, money, energy, ore, player1score, player2score, player3score,
            player4score, name1, name2, name3, name4;

    @FXML
    private Button gamble;

    @FXML
    private Rectangle p1Color, p2Color, p3Color, p4Color;

    private int turnTime;

    private Stage gameStage;

    private Scene gameScene;

    @FXML
    private void initialize() {
        List<Player> playerArr = ConfigureController.getGame().getPlayerArr();

        ArrayList<Label> scoreView = new ArrayList<>(ConfigureController.getMaxNumPlayers());
        scoreView.add(player1score);
        scoreView.add(player2score);
        scoreView.add(player3score);
        scoreView.add(player4score);

        for (int i = 0; i < ConfigureController.getMaxNumPlayers(); i++) {
            String s = (playerArr.size() >= i + 1 && playerArr.get(i) != null) ? String.valueOf(playerArr.get(i).getScore()) : "";
            scoreView.get(i).setText(s);
        }

        ArrayList<Rectangle> colorView = new ArrayList<>(ConfigureController.getMaxNumPlayers());
        colorView.add(p1Color);
        colorView.add(p2Color);
        colorView.add(p3Color);
        colorView.add(p4Color);

        for (int i = 0; i < ConfigureController.getMaxNumPlayers(); i++) {
            Color c = (playerArr.size() >= i + 1 && playerArr.get(i) != null) ? playerArr.get(i).getColor() : Color.TRANSPARENT;
            colorView.get(i).setFill(c);
        }

        ArrayList<Label> nameView = new ArrayList<>(ConfigureController.getMaxNumPlayers());
        nameView.add(name1);
        nameView.add(name2);
        nameView.add(name3);
        nameView.add(name4);

        for (int i = 0; i < ConfigureController.getMaxNumPlayers(); i++) {
            String n = (playerArr.size() >= i + 1 && playerArr.get(i) != null) ? playerArr.get(i).getName() + ":" : "";
            nameView.get(i).setText(n);
        }

        food.setText(String.valueOf(GameController.getCurrentPlayer().getFood()));
        money.setText(String.valueOf(GameController.getCurrentPlayer().getMoney()));
        energy.setText(String.valueOf(GameController.getCurrentPlayer().getEnergy()));
        ore.setText(String.valueOf(GameController.getCurrentPlayer().getOre()));

        round.setText(String.valueOf(GameController.getRoundNumber()));
        turn.setText(GameController.getCurrentPlayer().getName());

        turnTime = GameController.getTurnTime();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (turnTime >= 1) {
                        timeLeft.setText(String.valueOf(turnTime--));
                    } else {
                        handleEndTurn();
                    }
                });
            }
        }, 0, 1000);
    }

    @FXML
    private void handleReturnToMap(ActionEvent event) {
        gameScene = ConfigureController.getGameScene();
        gameStage = ConfigureController.getGameStage();
        gameStage.setScene(gameScene);
    }

    @FXML
    private void handleReturnToMap() {
        gameScene = ConfigureController.getGameScene();
        gameStage = ConfigureController.getGameStage();
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

            int roundNumber = GameController.getRoundNumber();
            int turnTime = GameController.getTurnTime();

            if (roundNumber < 4) {
                roundBonus = 50;
            } else if (roundNumber < 8) {
                roundBonus = 100;
            } else if (roundNumber < 12) {
                roundBonus = 150;
            } else {
                roundBonus = 200;
            }

            if (turnTime < 13) {
                timeBonus = 50;
            } else if (turnTime < 26) {
                timeBonus = 100;
            } else if (turnTime < 38) {
                timeBonus = 150;
            } else {
                timeBonus = 200;
            }

            moneyBonus = roundBonus * (rand.nextInt(timeBonus) + 1);

            if (moneyBonus > 250) {
                moneyBonus = 250;
            }

            GameController.getCurrentPlayer().setMoney(GameController.getCurrentPlayer().getMoney() + moneyBonus);
            GameController.getCurrentPlayer().setScore(GameController.getCurrentPlayer().getScore() + moneyBonus);
            GameController.setTurnTime(0);


            handleEndTurn();
            gameScene = ConfigureController.getGameScene();
            gameStage = ConfigureController.getGameStage();
            gameStage.setScene(gameScene);

            handleReturnToMap();

        }
    }
}
