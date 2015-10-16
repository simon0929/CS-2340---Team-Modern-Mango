package mule.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
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
            player4score, storeOre, storeEnergy, storeFood, storeMule;

    @FXML
    private Button buyOre, buyEnergy, buyFood, buyMule, sellOre, sellEnergy, sellFood, placeMule;
    
    @FXML
    private ChoiceBox<String> muleType;

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
        storeOre.setText(String.valueOf(GameController.game.getStore().getOre()));
        storeFood.setText(String.valueOf(GameController.game.getStore().getFood()));
        storeEnergy.setText(String.valueOf(GameController.game.getStore().getEnergy()));
        storeMule.setText(String.valueOf(GameController.game.getStore().getMule()));


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
		type.add("food");
		type.add("ore");
		type.add("energy");
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

        if(buyOre.isPressed()) {
            GameController.currentPlayer.buyResource("ore");
        } else if (buyEnergy.isPressed()) {
            GameController.currentPlayer.buyResource("energy");
        } else if (buyFood.isPressed()) {
            GameController.currentPlayer.buyResource("food");
        }
        updateValues();
    }

    @FXML
    private void handleSellResource() {

        if(sellOre.isPressed()) {
            GameController.currentPlayer.sellResource("ore");
        } else if (sellEnergy.isPressed()) {
            GameController.currentPlayer.sellResource("energy");
        } else if (sellFood.isPressed()) {
            GameController.currentPlayer.sellResource("food");
        }
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
    private void handleMuleBuy() {
    	if(muleType.getValue() != null && buyMule.isPressed()) {
    		if (muleType.getSelectionModel().getSelectedItem().compareTo("food") == 0) {
    			GameController.currentPlayer.buyResource("foodMule");
    			GameController.typeOfMule = "food";
    		}
    		else if (muleType.getSelectionModel().getSelectedItem().compareTo("energy") == 0) {
    			GameController.currentPlayer.buyResource("energyMule");
    			GameController.typeOfMule = "energy";
    		}
    		else if (muleType.getSelectionModel().getSelectedItem().compareTo("ore") == 0) {
    			GameController.currentPlayer.buyResource("oreMule");
    			GameController.typeOfMule = "ore";
    		}
    	}
    	updateValues();
    }
    
    @FXML
    private void changeScreen(ActionEvent event) throws IOException {
    	GameController.placingMule = true;
    	gameScene = ConfigureController.gameScene;
        gameStage = ConfigureController.gameStage;
        gameStage.setScene(gameScene);
    }

}