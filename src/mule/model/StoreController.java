package mule.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.Button;

/**
 * Created by madijuby on 10/4/15.
 * Edited by Tyler Whitfield 10/5/15.
 */
public class StoreController {

    @FXML
    private Label round, turn, timeLeft, food, money, energy, ore, player1score, player2score, player3score,
            player4score, storeOre, storeEnergy, storeFood, storeMule, storeMessage, name1, name2, name3, name4;

    @FXML
    private Button buyOre, buyEnergy, buyFood, buyMule, sellOre, sellEnergy, sellFood, placeMule;
    
    @FXML
    private ChoiceBox<String> muleType;

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

        storeOre.setText(String.valueOf(GameController.game.getStore().getOre()));
        storeFood.setText(String.valueOf(GameController.game.getStore().getFood()));
        storeEnergy.setText(String.valueOf(GameController.game.getStore().getEnergy()));
        storeMule.setText(String.valueOf(GameController.game.getStore().getMule()));
        storeMessage.setText("");


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
        
        final ObservableList<String> type = FXCollections.observableArrayList();
		type.add("food (+25)");
		type.add("energy (+50)");
        type.add("ore (+75)");

        muleType.setItems(type);
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
    private void handleMakePurchase() {
        String message = "";
        storeMessage.setText(message);

        if(buyOre.isPressed()) {
            message = GameController.currentPlayer.buyResource("ore");
        } else if (buyEnergy.isPressed()) {
           message = GameController.currentPlayer.buyResource("energy");
        } else if (buyFood.isPressed()) {
            message = GameController.currentPlayer.buyResource("food");
        }

        storeMessage.setText(message);
        updateValues();
    }

    @FXML
    private void handleSellResource() {
        String message = "";
        storeMessage.setText(message);

        if(sellOre.isPressed()) {
            message = GameController.currentPlayer.sellResource("ore");
        } else if (sellEnergy.isPressed()) {
           message = GameController.currentPlayer.sellResource("energy");
        } else if (sellFood.isPressed()) {
            message = GameController.currentPlayer.sellResource("food");
        }

        storeMessage.setText(message);
        updateValues();
    }


    @FXML
    private void handleMuleBuy() {
        String message = "";
        storeMessage.setText(message);

    	if(muleType.getValue() != null && buyMule.isPressed()) {
    		if (muleType.getSelectionModel().getSelectedItem().compareTo("food (+25)") == 0) {
    			message = GameController.currentPlayer.buyResource("foodMule");
    			GameController.typeOfMule = "food";
    		}
    		else if (muleType.getSelectionModel().getSelectedItem().compareTo("energy (+50)") == 0) {
                message = GameController.currentPlayer.buyResource("energyMule");
    			GameController.typeOfMule = "energy";
    		}
    		else if (muleType.getSelectionModel().getSelectedItem().compareTo("ore (+75)") == 0) {
                message = GameController.currentPlayer.buyResource("oreMule");
    			GameController.typeOfMule = "ore";
    		}
    	}

        storeMessage.setText(message);
        updateValues();
    }

    public void updateValues() {
        food.setText(String.valueOf(GameController.currentPlayer.getFood()));
        money.setText(String.valueOf(GameController.currentPlayer.getMoney()));
        energy.setText(String.valueOf(GameController.currentPlayer.getEnergy()));
        ore.setText(String.valueOf(GameController.currentPlayer.getOre()));
        storeOre.setText(String.valueOf(GameController.game.getStore().getOre()));
        storeFood.setText(String.valueOf(GameController.game.getStore().getFood()));
        storeEnergy.setText(String.valueOf(GameController.game.getStore().getEnergy()));
        storeMule.setText(String.valueOf(GameController.game.getStore().getMule()));
    }
    
    @FXML
    private void changeScreen(ActionEvent event) throws IOException {
    	GameController.placingMule = true;
    	gameScene = ConfigureController.gameScene;
        gameStage = ConfigureController.gameStage;
        gameStage.setScene(gameScene);
    }

}
