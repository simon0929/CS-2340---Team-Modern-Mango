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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.Button;

public final class StoreController {

    @FXML
    private Label round, turn, timeLeft, food, money, energy, ore, player1score, player2score, player3score,
            player4score, storeOre, storeEnergy, storeFood, storeMule, storeMessage, name1, name2, name3, name4,
            foodPrice, energyPrice, orePrice;

    @FXML
    private Button buyOre, buyEnergy, buyFood, buyMule, sellOre, sellEnergy, sellFood;
    
    @FXML
    private ChoiceBox<String> muleType;

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

        food.setText(String.valueOf(GameController.currentPlayer.getFood()));
        money.setText(String.valueOf(GameController.currentPlayer.getMoney()));
        energy.setText(String.valueOf(GameController.currentPlayer.getEnergy()));
        ore.setText(String.valueOf(GameController.currentPlayer.getOre()));

        foodPrice.setText(String.valueOf(Store.getResourceList().get("food")));
        energyPrice.setText(String.valueOf(Store.getResourceList().get("energy")));
        orePrice.setText(String.valueOf(Store.getResourceList().get("ore")));

        storeOre.setText(String.valueOf(ConfigureController.getGame().getStore().getOre()));
        storeFood.setText(String.valueOf(ConfigureController.getGame().getStore().getFood()));
        storeEnergy.setText(String.valueOf(ConfigureController.getGame().getStore().getEnergy()));
        storeMule.setText(String.valueOf(ConfigureController.getGame().getStore().getMule()));
        storeMessage.setText("");

        round.setText(String.valueOf(GameController.roundNumber));
        turn.setText(GameController.currentPlayer.getName());

        turnTime = GameController.turnTime;

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
        
        final ObservableList<String> type = FXCollections.observableArrayList();
		type.add("food (+25)");
		type.add("energy (+50)");
        type.add("ore (+75)");

        muleType.setItems(type);
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
    			GameController.setTypeOfMule("food");
    		}
    		else if (muleType.getSelectionModel().getSelectedItem().compareTo("energy (+50)") == 0) {
                message = GameController.currentPlayer.buyResource("energyMule");
    			GameController.setTypeOfMule("energy");
    		}
    		else if (muleType.getSelectionModel().getSelectedItem().compareTo("ore (+75)") == 0) {
                message = GameController.currentPlayer.buyResource("oreMule");
    			GameController.setTypeOfMule("ore");
    		}
    	}

        storeMessage.setText(message);
        updateValues();
    }

    private void updateValues() {
        food.setText(String.valueOf(GameController.currentPlayer.getFood()));
        money.setText(String.valueOf(GameController.currentPlayer.getMoney()));
        energy.setText(String.valueOf(GameController.currentPlayer.getEnergy()));
        ore.setText(String.valueOf(GameController.currentPlayer.getOre()));
        storeOre.setText(String.valueOf(ConfigureController.getGame().getStore().getOre()));
        storeFood.setText(String.valueOf(ConfigureController.getGame().getStore().getFood()));
        storeEnergy.setText(String.valueOf(ConfigureController.getGame().getStore().getEnergy()));
        storeMule.setText(String.valueOf(ConfigureController.getGame().getStore().getMule()));
    }
    
    @FXML
    private void changeScreen(ActionEvent event) {
    	GameController.placingMule = true;
    	gameScene = ConfigureController.getGameScene();
        gameStage = ConfigureController.getGameStage();
        gameStage.setScene(gameScene);
    }

}
