package mule.model;

import java.io.*;
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
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
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

public class GameController implements java.io.Serializable {

    //Everything labeled @FXML relates directly to the .fxml files

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@FXML
    private Label townLabel;

	@FXML
	private Label round, turn, timeLeft, food, money, energy, ore, player1score, player2score, player3score,
            player4score, randomEvent, name1, name2, name3, name4;

	@FXML
	private Button label00, label10, label20, label30, label40, label50, label60, label70, label80,
	label01, label11, label21, label31, label41, label51, label61, label71, label81,
	label02, label12, label22, label32, label42, label52, label62, label72, label82,
	label03, label13, label23, label33, label43, label53, label63, label73, label83,
	label04, label14, label24, label34, label44, label54, label64, label74, label84;

	@FXML
	private Pane pane;

    @FXML
    private Rectangle p1Color, p2Color, p3Color, p4Color;
    
    @FXML
    private MenuItem save, load;

	public Game game;

	public static Player currentPlayer;

    //numOfPropBuyInRound = number of properties bought this round, if 0 then land selection phase ends
	private int turnNumber, numOfPropBoughtInTurn, numOfPropBoughtInRound;

    public static int turnTime, roundNumber;

	private boolean selectionPhase;

	public static ArrayList<Player> playerList, basePlayerList;
	
	private ArrayList<Player> constantPlayerList = new ArrayList<>();

    private ArrayList<Pane> propertyOwnedList;

    public Stage gameStage;

    public Scene gameScene;

    public static boolean placingMule = false;

    public static String typeOfMule;

	@FXML
	private void initialize() {
        ArrayList<Player> playerArr = ConfigureController.game.getPlayerArr();
        playerList = ConfigureController.playerList;
        this.game = ConfigureController.game;
        basePlayerList = playerList;
        for (int i = 0; i < playerList.size(); i++) {
        	constantPlayerList.add(playerList.get(i));
        }
        System.out.println("PlayerArr:");
        System.out.println(game.getPlayerArr());
		currentPlayer = this.game.getCurrPlayer();
		turn.setText(currentPlayer.getName());
		turnNumber = game.getTurn();
        roundNumber = game.getRound();
        numOfPropBoughtInTurn = 0;
        numOfPropBoughtInRound = 0;
		selectionPhase = true;
		

        food.setText(String.valueOf(currentPlayer.getFood()));
		money.setText(String.valueOf(currentPlayer.getMoney()));
		energy.setText(String.valueOf(currentPlayer.getEnergy()));
		ore.setText(String.valueOf(currentPlayer.getOre()));
		round.setText(String.valueOf(roundNumber));
		turn.setText(currentPlayer.getName());

        refreshScores();

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

        game = ConfigureController.game;
        turnTime = 50;
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
	private void handleEndTurn(MouseEvent event) throws IOException {
        endTurn();
	}

    private void handleEndTurn() {
        endTurn();
    }

    private void endTurn() {
    	System.out.println("PlayerArr: Beginning of end turn:");
        System.out.println(playerList);
        System.out.println(turnNumber);
        System.out.println();
        //refreshes screen
        game.update();
        this.randomEvent.setText("");
        turnNumber++;

        numOfPropBoughtInRound += numOfPropBoughtInTurn;

        //if the number of turns exceeds the number of players, the round ends
        if (turnNumber > playerList.size()) {
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
        	this.randomEvent.setText(randEvent.random(game, currentPlayer));
        }
        updateTurnTime();
        if (currentPlayer.getMuleList().size() > 0) {
            for(Object muleButton:currentPlayer.getMuleList()) {
            	Button mule = (Button) muleButton;
                if (currentPlayer.getEnergy() >= 1) {
                	Label label = (Label)mule.getParent().getChildrenUnmodifiable().get(0);
	                String propertyType = label.getText();
	
	                    if (mule.getText().equals( "food")) {
	                    if (propertyType.equals("Plain")) {
	                        currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
	                        currentPlayer.setFood(currentPlayer.getFood() + 2);
	                    } else if (propertyType.equals("River")) {
	                        currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
	                        currentPlayer.setFood(currentPlayer.getFood() + 4);
	                        } else if (propertyType.equals("M1") || propertyType.equals("M2") || propertyType.equals("M3")) {
	                            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
	                            currentPlayer.setFood(currentPlayer.getFood() + 1);
	                        }
	                } else if (mule.getText().equals("energy")) {
	                    if (propertyType.equals("Plain")) {
	                        currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
	                        currentPlayer.setEnergy(currentPlayer.getEnergy() + 3);
	                    } else if (propertyType.equals("River")) {
	                        currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
	                        currentPlayer.setEnergy(currentPlayer.getEnergy() + 2);
	                    } else if (propertyType.equals("M1") || propertyType.equals("M2") || propertyType.equals("M3")) {
	                            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
	                            currentPlayer.setEnergy(currentPlayer.getEnergy() + 1);
	                        }
	                } else if (mule.getText().equals( "ore")) {
	                    if (propertyType.equals("Plain")) {
	                        currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
	                        currentPlayer.setOre(currentPlayer.getOre() + 1);
	                        } else if (propertyType.equals("M1")) {
	                            currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
	                            currentPlayer.setOre(currentPlayer.getOre() + 2);
	                            } else if (propertyType.equals("M2")) {
	                                currentPlayer.setEnergy(currentPlayer.getEnergy() - 1);
	                                currentPlayer.setOre(currentPlayer.getOre() + 3);
	                            } else if (propertyType.equals("M3")) {
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
        refreshScores();
    }

	@FXML
	private void handleProperty(MouseEvent event) throws IOException {
        if (numOfPropBoughtInTurn == 0) {
            if (selectionPhase) {
                if (!game.selectedProp()) {
                    Color playerColor = game.getCurrPlayer().getColor();

                    //Gets the actual property object that was clicked so that things can be done to it
                    Pane property = (Pane) event.getSource();

                    if (!propertyOwnedList.contains(property) && currentPlayer.getMoney() >= 300) {
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
        int size = playerList.size();
        ArrayList<Player> tempList = playerList;
        playerList = new ArrayList<>();
        System.out.println(tempList);
        int j;
        for (int i = 0; i < size; i++) {
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
        String s1 = (constantPlayerList.size() >= 1 && constantPlayerList.get(0) != null) ? String.valueOf(constantPlayerList.get(0).getScore()) : "";
        String s2 = (constantPlayerList.size() >= 2 && constantPlayerList.get(1) != null) ? String.valueOf(constantPlayerList.get(1).getScore()) : "";
        String s3 = (constantPlayerList.size() >= 3 && constantPlayerList.get(2) != null) ? String.valueOf(constantPlayerList.get(2).getScore()) : "";
        String s4 = (constantPlayerList.size() == 4 && constantPlayerList.get(3) != null) ? String.valueOf(constantPlayerList.get(3).getScore()) : "";
        player1score.setText(s1);
        player2score.setText(s2);
        player3score.setText(s3);
        player4score.setText(s4);
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
    
    @FXML
    private void handleSave (ActionEvent event) throws IOException {
    	try {
    		FileOutputStream fileOut = new FileOutputStream("/tmp/game.ser");
    		ObjectOutputStream out = new ObjectOutputStream(fileOut);
    		out.writeObject(game);
    		out.close();
    		fileOut.close();
    		
    		FileOutputStream fileOut1 = new FileOutputStream("/tmp/round.ser");
    		ObjectOutputStream out1 = new ObjectOutputStream(fileOut1);
    		out1.writeInt(game.getRound());
    		out1.close();
    		fileOut1.close();
    		
    		FileOutputStream fileOut2 = new FileOutputStream("/tmp/turn.ser");
    		ObjectOutputStream out2 = new ObjectOutputStream(fileOut2);
    		out2.writeInt(game.getTurn());
    		out2.close();
    		fileOut2.close();
    		
    		FileOutputStream fileOut3 = new FileOutputStream("/tmp/m.ser");
    		ObjectOutputStream out3 = new ObjectOutputStream(fileOut3);
    		out3.writeInt(game.getM());
    		out3.close();
    		fileOut3.close();
    		System.out.println("save went through");
    	} catch (IOException i) {
    		i.printStackTrace();
    	}
    }
}
