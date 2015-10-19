package mule.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
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

    //Everything labeled @FXML relates directly to the .fxml files

    @FXML
    private Label townLabel;

	@FXML
	private Label round, turn, timeLeft, food, money, energy, ore, player1score, player2score, player3score, player4score;

	@FXML
	private Button label00, label10, label20, label30, label40, label50, label60, label70, label80,
	label01, label11, label21, label31, label41, label51, label61, label71, label81,
	label02, label12, label22, label32, label42, label52, label62, label72, label82,
	label03, label13, label23, label33, label43, label53, label63, label73, label83,
	label04, label14, label24, label34, label44, label54, label64, label74, label84;

	@FXML
	private Pane pane;

	public static Game game;

	public static Player currentPlayer;

    //numOfPropBuyInRound = number of properties bought this round, if 0 then land selection phase ends
	private int turnNumber, numOfPropBoughtInTurn, numOfPropBoughtInRound;

    public static int turnTime, roundNumber;

	private boolean selectionPhase;

	public static ArrayList<Player> playerList, basePlayerList;

    private ArrayList<Pane> propertyOwnedList;

    public Stage gameStage;

    public Scene gameScene;

    public static boolean placingMule = false;

    public static String typeOfMule;

	@FXML
	private void initialize() {
		playerList = ConfigureController.playerList;
        basePlayerList = playerList;
		currentPlayer = ConfigureController.player1;
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
		player1score.setText("0");
		player2score.setText("0");
		player3score.setText("0");
		player4score.setText("0");
        game = ConfigureController.game;
        turnTime = 0;
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

    @FXML
    private void handleGamble(MouseEvent event) throws IOException {
        Random rand = new Random();

        int moneyBonus = calRoundBonus() * rand.nextInt(calTimeBonus());
        if (moneyBonus > 250) {
            currentPlayer.setMoney(currentPlayer.getMoney() + 250);
        } else {
            currentPlayer.setMoney(currentPlayer.getMoney() + moneyBonus);
        }

        gameScene = ConfigureController.gameScene;
        gameStage = ConfigureController.gameStage;
        gameStage.setScene(gameScene);

        this.handleEndTurn();

    }


    //Performs a variety of things when the End Turn button is clicked
	@FXML
	private void handleEndTurn(MouseEvent event) throws IOException {
        endTurn();
	}

    private void handleEndTurn() {
        endTurn();
    }

    private void endTurn() {
        //refreshes screen
        game.update();

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
        	System.out.println(randEvent.Random(game, currentPlayer));
        }
        updateTurnTime();

        if (currentPlayer.getMuleList().size() > 0) {
        for(Button mule:currentPlayer.getMuleList()) {
        	System.out.println("here");
        	System.out.println(currentPlayer.getMuleList().size());

        	if (currentPlayer.getEnergy() >= 1) {
        		//propertyInd =mule.getParent().getChildrenUnmodifiable().indexOf(new Label());
        		//System.out.println(propertyInd);
        			Label label = (Label)mule.getParent().getChildrenUnmodifiable().get(0);
        			String propertyType = label.getText();

        			if (mule.getText() == "food") {
        				if (propertyType == "Plain") {
        					currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
        					currentPlayer.setFood(currentPlayer.getFood() + 2);
        				} else if (propertyType == "River") {
        					currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
        					currentPlayer.setFood(currentPlayer.getFood() + 4);
        					} else if (propertyType == "M1" || propertyType == "M2" || propertyType == "M3") {
        						currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            					currentPlayer.setFood(currentPlayer.getFood() + 1);
        					}
        			} else if (mule.getText() == "energy") {
        				if (propertyType == "Plain") {
        					currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
        					currentPlayer.setEnergy(currentPlayer.getEnergy() + 3);
        				} else if (propertyType == "River") {
        					currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
        					currentPlayer.setEnergy(currentPlayer.getEnergy() + 2);
        					} else if (propertyType == "M1" || propertyType == "M2" || propertyType == "M3") {
        						currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
        						currentPlayer.setEnergy(currentPlayer.getEnergy() + 1);
        					}
        			} else if (mule.getText() == "ore") {
        				if (propertyType == "Plain") {
        					currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
        					currentPlayer.setOre(currentPlayer.getOre() + 1);
        					} else if (propertyType == "M1") {
        						currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
        						currentPlayer.setOre(currentPlayer.getOre() + 2);
        						} else if (propertyType == "M2") {
        							currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            						currentPlayer.setOre(currentPlayer.getOre() + 3);
        						} else if (propertyType == "M3") {
        							currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
            						currentPlayer.setOre(currentPlayer.getOre() + 4);
        						}
        			}
        	}
        }
        }

        food.setText(String.valueOf(currentPlayer.getFood()));
        money.setText(String.valueOf(currentPlayer.getMoney()));
        energy.setText(String.valueOf(currentPlayer.getEnergy()));
        ore.setText(String.valueOf(currentPlayer.getOre()));
    }

	@FXML
	private void handleProperty(MouseEvent event) throws IOException {
        if (numOfPropBoughtInTurn == 0) {
            if (selectionPhase) {
                if (!game.selectedProp()) {
                    Color playerColor = game.getCurrPlayer().getColor();

                    //Gets the actual property object that was clicked so that things can be done to it
                    Pane property = (Pane) event.getSource();

                    if (!propertyOwnedList.contains(property)) {
                        currentPlayer.incrementPropertyOwned();
                        propertyOwnedList.add(property);

                        if (currentPlayer.getNumOfFreeProperties() == 0) {
                            //Automatically updates player's money and on the GUI
                            currentPlayer.setMoney(currentPlayer.getMoney() - 300);
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
        player1score.setText(String.valueOf(ConfigureController.player1.getScore()));
        player2score.setText(String.valueOf(ConfigureController.player2.getScore()));
        if (ConfigureController.player3 != null) {
            player3score.setText(String.valueOf(ConfigureController.player3.getScore()));
        }
        if (ConfigureController.player4 != null) {
            player4score.setText(String.valueOf(ConfigureController.player4.getScore()));
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
    private void placeMule(ActionEvent event) throws IOException {
    	food.setText(String.valueOf(currentPlayer.getFood()));
        money.setText(String.valueOf(currentPlayer.getMoney()));
        energy.setText(String.valueOf(currentPlayer.getEnergy()));
        ore.setText(String.valueOf(currentPlayer.getOre()));
    	if (placingMule) {
    		Object source = event.getSource();
    		Button clickedbtn = (Button) source;
    		if (typeOfMule.compareTo("food") == 0 && clickedbtn.getText().compareTo("Mule") == 0 &&
    				propertyOwnedList.contains(clickedbtn.getParent()) && currentPlayer.getPropertyList().contains(clickedbtn.getParent())) {
    		    clickedbtn.setText("food");
    		    clickedbtn.getParent();
    		} else if (typeOfMule.compareTo("energy") == 0 && clickedbtn.getText().compareTo("Mule") == 0 &&
    				propertyOwnedList.contains(clickedbtn.getParent()) && currentPlayer.getPropertyList().contains(clickedbtn.getParent())) {
    			clickedbtn.setText("energy");
    		} else if (typeOfMule.compareTo("ore") == 0 && clickedbtn.getText().compareTo("Mule") == 0 &&
    				propertyOwnedList.contains(clickedbtn.getParent()) && currentPlayer.getPropertyList().contains(clickedbtn.getParent())) {
    			clickedbtn.setText("ore");
    		}
			placingMule = false;
			currentPlayer.addToMuleList(clickedbtn);
    	}
    }
}
