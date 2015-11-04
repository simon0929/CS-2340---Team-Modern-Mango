package mule.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentSkipListMap;

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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    //Everything labeled @FXML relates directly to the .fxml files

    @FXML
    private Label townLabel;

	@FXML
	private Label round, turn, timeLeft, food, money, energy, ore, player1score, player2score, player3score,
            player4score, randomEvent, name1, name2, name3, name4;

//	@FXML
//	private Button
//    label00, label10, label20, label30, label40, label50, label60, label70, label80,
//	label01, label11, label21, label31, label41, label51, label61, label71, label81,
//	label02, label12, label22, label32, label42, label52, label62, label72, label82,
//	label03, label13, label23, label33, label43, label53, label63, label73, label83,
//	label04, label14, label24, label34, label44, label54, label64, label74, label84;

//	@FXML
//	private Pane pane;

    @FXML
    private Rectangle p1Color, p2Color, p3Color, p4Color;

	public static Player currentPlayer;

    //numOfPropBuyInRound = number of properties bought this round, if 0 then land selection phase ends
	private int turnNumber, numOfPropBoughtInTurn, numOfPropBoughtInRound;

    public static int turnTime, roundNumber;

    private static final int propertyPrice = 300, timeLimit = 50;

	private boolean selectionPhase;

	private static ArrayList<Player> playerList, basePlayerList;

    private ArrayList<Pane> propertyOwnedList;

    private ArrayList<Label> scoreView;

    public Stage gameStage;

    public Scene gameScene;

    public static boolean placingMule = false;

    private static String typeOfMule;

	@FXML
	private void initialize() {
        ArrayList<Player> playerArr = ConfigureController.getGame().getPlayerArr();
        playerList = ConfigureController.getPlayerList();

        basePlayerList = playerList;
		currentPlayer = ConfigureController.getGame().getPlayerArr().get(0); //player1
		turn.setText(currentPlayer.getName());
		turnNumber = 1;
        roundNumber = 1;
        numOfPropBoughtInTurn = 0;
        numOfPropBoughtInRound = 0;
		selectionPhase = true;

        food.setText(String.valueOf(currentPlayer.getFood()));
		money.setText(String.valueOf(currentPlayer.getMoney()));
		energy.setText(String.valueOf(currentPlayer.getEnergy()));
		ore.setText(String.valueOf(currentPlayer.getOre()));

        scoreView = new ArrayList<>(ConfigureController.getMaxNumPlayers());
        scoreView.add(player1score);
        scoreView.add(player2score);
        scoreView.add(player3score);
        scoreView.add(player4score);

        refreshScores();

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

        turnTime = timeLimit;
        startTurnTimer();
        propertyOwnedList = new ArrayList<>();
	}

    //Calls the Town.fxml file and constructs the Town GUI.
	@FXML
	private void handleTown(MouseEvent event) throws IOException {
		Parent townScreen = FXMLLoader.load(getClass().getResource("/mule/view/Town.fxml"));
		Scene townScene = new Scene(townScreen);
		Stage townStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		townStage.setScene(townScene);
		townStage.show();
	}


    //Performs a variety of things when the End Turn button is clicked
	@FXML
	private void handleEndTurn(MouseEvent event) {
        endTurn();
	}

    private void handleEndTurn() {
        endTurn();
    }

    private void endTurn() {
        //refreshes screen
        ConfigureController.getGame().update();
        this.randomEvent.setText("");
        turnNumber++;

        numOfPropBoughtInRound += numOfPropBoughtInTurn;

        //if the number of turns exceeds the number of players, the round ends
        if (turnNumber > ConfigureController.maxPlayers) {
            //Calculates all player scores and rearranges playerList for a new order for the next turn
            getTurnOrder();

            //Refreshes scores on GUI
            refreshScores();

            //Resets the turn to 1
            turnNumber = 1;

            roundNumber++;
            round.setText(String.valueOf(roundNumber));

            if (numOfPropBoughtInRound == 0) {
                selectionPhase = false;
            }
            else {
                numOfPropBoughtInRound = 0;
            }


        }

        numOfPropBoughtInTurn = 0;

        currentPlayer = playerList.get(turnNumber - 1);
        turn.setText(currentPlayer.getName());
        if (Math.random() < .27) {
        	RandomEvent randEvent = new RandomEvent();
        	this.randomEvent.setText(randEvent.random(ConfigureController.getGame(), currentPlayer));
        }
        updateTurnTime();

        if (currentPlayer.getMuleList().size() > 0) {
            for(Mule mule : currentPlayer.getMuleList()) {
                System.out.println(currentPlayer.getMuleList().size());

                if (currentPlayer.getEnergy() >= 1) {
                    mule.calculateResourceChanges();
                }
            }
        }

        food.setText(String.valueOf(currentPlayer.getFood()));
        money.setText(String.valueOf(currentPlayer.getMoney()));
        energy.setText(String.valueOf(currentPlayer.getEnergy()));
        ore.setText(String.valueOf(currentPlayer.getOre()));
    }

	@FXML
	private void handleProperty(MouseEvent event) {
        if (numOfPropBoughtInTurn == 0) {
            if (selectionPhase) {
                if (!ConfigureController.getGame().selectedProp()) {
                    Color playerColor = ConfigureController.getGame().getCurrPlayer().getColor();

                    //Gets the actual property object that was clicked so that things can be done to it
                    Pane property = (Pane) event.getSource();

                    if (!propertyOwnedList.contains(property) && currentPlayer.getMoney() >= propertyPrice) {
                        currentPlayer.incrementPropertyOwned();
                        propertyOwnedList.add(property);

                        if (currentPlayer.getNumOfFreeProperties() == 0) {
                            //Automatically updates player's money and on the GUI
                            currentPlayer.setMoney(currentPlayer.getMoney() - propertyPrice);
                            money.setText(String.valueOf(currentPlayer.getMoney()));
                        }
                        else {
                            currentPlayer.decrementFreeProperty();
                        }

                        numOfPropBoughtInTurn++;
                        currentPlayer.addToPropertyList(property);

                        Region reg = (Region) event.getSource();
                        reg.setBorder(new Border(new BorderStroke(playerColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4.0))));

                        currentPlayer.calculateScore();
                        refreshScores();
                    }
                }
            }
        }
	}

    private void getTurnOrder() {
        int minScore;
        ArrayList<Player> tempList = playerList;
        playerList = new ArrayList<>();
        int j;

        for (int i = 0; i < ConfigureController.maxPlayers; i++) {
            j = 0;
            minScore = 0;

            while (j < tempList.size()) {
                if (tempList.get(j).getScore() < tempList.get(minScore).getScore()) {
                    minScore = j;
                }

                j++;
            }

            playerList.add(tempList.get(minScore));
            tempList.remove(minScore);
        }
    }

    private void refreshScores() {
        ArrayList<Player> playerArr = ConfigureController.getGame().getPlayerArr();

        for (int i = 0; i < ConfigureController.getMaxNumPlayers(); i++) {
            String s = (playerArr.size() >= i + 1 && playerArr.get(i) != null) ? String.valueOf(playerArr.get(i).getScore()) : "";
            scoreView.get(i).setText(s);
        }
    }

    private void updateTurnTime() {
        if (roundNumber == 1) {
            turnTime = 50;
        }
        else {
            if (roundNumber < 5) {
                if (currentPlayer.getFood() >= 3) {
                    turnTime = 50;
                }
                else if (currentPlayer.getFood() > 0 && currentPlayer.getFood() < 3) {
                    turnTime = 30;
                }
                else {
                    turnTime = 5;
                }
            }
            else if (roundNumber < 9) {
                if (currentPlayer.getFood() >= 4) {
                    turnTime = 50;
                }
                else if (currentPlayer.getFood() > 0 && currentPlayer.getFood() < 4) {
                    turnTime = 30;
                }
                else {
                    turnTime = 5;
                }
            }
            else {
                if (currentPlayer.getFood() >= 5) {
                    turnTime = 50;
                }
                else if (currentPlayer.getFood() > 0 && currentPlayer.getFood() < 5) {
                    turnTime = 30;
                }
                else {
                    turnTime = 5;
                }
            }
        }

        timeLeft.setText(String.valueOf(turnTime));
    }

    private void startTurnTimer() {
        //turnTime = 50;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (turnTime >= 0) {
                            timeLeft.setText(String.valueOf(turnTime--));
                        } else {
                            handleEndTurn();
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    private int calTimeBonus() {
        if (turnTime < 50 && turnTime >= 37) {
            return 200;
        }
        if (turnTime < 37 && turnTime >= 25) {
            return 150;
        }
        if (turnTime < 25 && turnTime >= 12) {
            return 100;
        }
        if (turnTime < 13 && turnTime >= 0) {
            return 50;
        }

        return 50;
    }

    private int calRoundBonus() {

        if (roundNumber < 4) {
            return 50;
        }

        if (roundNumber < 8) {
            return 100;
        }

        if (roundNumber < 12) {
            return 150;
        }
        return 200;
    }

    @FXML
    private void placeMule(ActionEvent event) {
    	food.setText(String.valueOf(currentPlayer.getFood()));
        money.setText(String.valueOf(currentPlayer.getMoney()));
        energy.setText(String.valueOf(currentPlayer.getEnergy()));
        ore.setText(String.valueOf(currentPlayer.getOre()));
    	if (placingMule) {
    		Object source = event.getSource();
    		Button clickedButton = (Button) source;
            Label label = (Label) clickedButton.getParent().getChildrenUnmodifiable().get(0);
            String propertyType = label.getText();
            Mule mule = null;

            if (typeOfMule.compareTo("food") == 0 && clickedButton.getText().compareTo("Mule") == 0 &&
                    propertyOwnedList.contains(clickedButton.getParent()) && currentPlayer.getPropertyList().contains(clickedButton.getParent())) {
                clickedButton.setText("food");
                clickedButton.getParent();
                mule = new FoodMule(propertyType);
            } else if (typeOfMule.compareTo("energy") == 0 && clickedButton.getText().compareTo("Mule") == 0 &&
                    propertyOwnedList.contains(clickedButton.getParent()) && currentPlayer.getPropertyList().contains(clickedButton.getParent())) {
                clickedButton.setText("energy");
                mule = new EnergyMule(propertyType);
            } else if (typeOfMule.compareTo("ore") == 0 && clickedButton.getText().compareTo("Mule") == 0 &&
                    propertyOwnedList.contains(clickedButton.getParent()) && currentPlayer.getPropertyList().contains(clickedButton.getParent())) {
                clickedButton.setText("ore");
                mule = new OreMule(propertyType);
            }
			placingMule = false;
			currentPlayer.addToMuleList(mule);
    	}
    }

    public static void setTypeOfMule(String type) { typeOfMule = type; }
}
