package mule.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
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
	private Button
    label00, label01, label02, label03, label04, label05, label06, label07, label08,
	label10, label11, label12, label13, label14, label15, label16, label17, label18,
	label20, label21, label22, label23, label25, label26, label27, label28,
	label30, label31, label32, label33, label34, label35, label36, label37, label38,
	label40, label41, label42, label43, label44, label45, label46, label47, label48;

    @FXML
    private Rectangle p1Color, p2Color, p3Color, p4Color;
    
    @FXML
    private MenuItem save, load;
    
    @FXML
    private GridPane grid;

	private Game game;

	public static Player currentPlayer;

    //numOfPropBuyInRound = number of properties bought this round, if 0 then land selection phase ends
	public int turnNumber, numOfPropBoughtInTurn, numOfPropBoughtInRound;

    public static int turnTime, roundNumber;

    private static final int PROPERTY_PRICE = 300, TIME_LIMIT = 50, MAP_SIZE = 45;

	private static boolean selectionPhase;

	private static boolean buyingMode = false;
	
	private static boolean sellMode = false;

	private List<Player> playerList;

	private final ArrayList<Player> constantPlayerList = new ArrayList<>();

    private ArrayList<String> propertyOwnedList;

    private ArrayList<Label> scoreView;

    public static Stage gameStage;

    public static Scene gameScene;

    public static boolean placingMule = false;

    private static String typeOfMule;

    private static Button[] buttonArr;


    @FXML
	private void initialize() {
        List<Player> playerArr = ConfigureController.getGame().getPlayerArr();
        playerList = ConfigureController.getPlayerList();
        this.game = ConfigureController.getGame();
        //basePlayerList = playerList;
        constantPlayerList.addAll(playerList.stream().collect(Collectors.toList()));
		currentPlayer = game.getCurrPlayer();

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

        scoreView = new ArrayList<>(ConfigureController.getMaxNumPlayers());
        scoreView.add(player1score);
        scoreView.add(player2score);
        scoreView.add(player3score);
        scoreView.add(player4score);

        scoreView = new ArrayList<>(ConfigureController.getMaxNumPlayers());
        scoreView.add(player1score);
        scoreView.add(player2score);
        scoreView.add(player3score);
        scoreView.add(player4score);

        refreshScores();
        if (ConfigureController.getLoaded()) {
        	//createButtonList();
        	reDrawProperty();
        }

        ArrayList<Rectangle> colorView = new ArrayList<>(ConfigureController.getMaxNumPlayers());
        colorView.add(p1Color);
        colorView.add(p2Color);
        colorView.add(p3Color);
        colorView.add(p4Color);

        for (int i = 0; i < ConfigureController.getMaxNumPlayers(); i++) {
            Color c = (constantPlayerList.size() >= i + 1 && constantPlayerList.get(i) != null) ? constantPlayerList.get(i).getColor() : Color.TRANSPARENT;
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

        turnTime = TIME_LIMIT;
        startTurnTimer();
        propertyOwnedList = new ArrayList<>();

        buttonArr = new Button[]
                {label00, label01, label02, label03, label04, label05, label06, label07, label08,
                        label10, label11, label12, label13, label14, label15, label16, label17, label18,
                        label20, label21, label22, label23, label25, label26, label27, label28,
                        label30, label31, label32, label33, label34, label35, label36, label37, label38,
                        label40, label41, label42, label43, label44, label45, label46, label47, label48 };

        enableButtons(false);
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
        	this.randomEvent.setText(randEvent.random(ConfigureController.getGame(), currentPlayer));
        }
        updateTurnTime();
        if (currentPlayer.getMuleList().size() > 0) {
            currentPlayer.getMuleList().stream().filter(mule -> mule != null
                    && currentPlayer.getEnergy() >= 1).forEach(mule -> mule.calculateResourceChanges());
        }

        food.setText(String.valueOf(currentPlayer.getFood()));
        money.setText(String.valueOf(currentPlayer.getMoney()));
        energy.setText(String.valueOf(currentPlayer.getEnergy()));
        ore.setText(String.valueOf(currentPlayer.getOre()));
        refreshScores();
    }

	@FXML
	private void handleProperty(MouseEvent event) throws IOException {
		if (!sellMode) {
	        if (numOfPropBoughtInTurn == 0) {
	            if (selectionPhase) {
	                if (!ConfigureController.getGame().selectedProp()) {
	                    Color playerColor = ConfigureController.getGame().getCurrPlayer().getColor();
	
	                    //Gets the actual property object that was clicked so that things can be done to it
	                    Pane mapElement = (Pane) event.getSource();
	                    String property = "pane" + mapElement.getId();
	                    if (!propertyOwnedList.contains(property) && (currentPlayer.getNumOfFreeProperties() != 0
	                            || currentPlayer.getMoney() >= PROPERTY_PRICE)) {
	                        currentPlayer.incrementPropertyOwned();
	                        propertyOwnedList.add(property);
	
	                        if (currentPlayer.getNumOfFreeProperties() == 0) {
	                            //Automatically updates player's money and on the GUI
	                            currentPlayer.setMoney(currentPlayer.getMoney() - PROPERTY_PRICE);
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
		} else {
				Pane mapElement = (Pane) event.getSource();
				String property = "pane" + mapElement.getId();
                if (propertyOwnedList.contains(property) && (currentPlayer.getPropertyList().contains(property))) {
                	currentPlayer.decrementPropertyOwned();
                    currentPlayer.removeFromPropertyList(property);
                    currentPlayer.setMoney(currentPlayer.getMoney() + PROPERTY_PRICE);
                    money.setText(String.valueOf(currentPlayer.getMoney()));
                    propertyOwnedList.remove(property);
                    currentPlayer.calculateScore();
                    refreshScores();
                    Region reg = (Region) event.getSource();
                    reg.setBorder(null);
                }
                Parent landOfficeScreen = FXMLLoader.load(getClass().getResource("/mule/view/LandOffice.fxml"));
        		Scene landOfficeScene = new Scene(landOfficeScreen);
        		Stage landOfficeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        		landOfficeStage.setScene(landOfficeScene);
        		landOfficeStage.show();
		}
        if (buyingMode) {
        	Parent landOfficeScreen = FXMLLoader.load(getClass().getResource("/mule/view/LandOffice.fxml"));
    		Scene landOfficeScene = new Scene(landOfficeScreen);
    		Stage landOfficeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		landOfficeStage.setScene(landOfficeScene);
    		landOfficeStage.show();
    		buyingMode =  false;
        }
	}
	
	private void reDrawProperty() {
        for (Player player : constantPlayerList) {
            Color playerColor = player.getColor();
            for (int i = 0; i < MAP_SIZE; i++) {
                if (player.getPropertyList().contains(grid.getChildren().get(i).getId())) {
                    Region reg = (Region) grid.getChildren().get(i);
                    reg.setBorder(new Border(new BorderStroke(playerColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4.0))));
                    /*if (constantPlayerList.get(j).getMuleList().get(0) != null) {
	                	String buttonName = "label" + grid.getChildren().get(i).getId().charAt(4) + grid.getChildren().get(i).getId().charAt(5);
	                	for (int m = 0; m < 43; m++) {
	                		System.out.println(buttonList.get(0));
		                	if (buttonList.get(m).getId().compareTo(buttonName) == 0) {
		                		buttonList.get(m).setText(constantPlayerList.get(j).getMuleList().get(0).getMuleType());
		                	}
	                	}
	                }*/
                }
            }
        }
	}

    private void getTurnOrder() {
        int minScore;
        int size = playerList.size();
        List<Player> tempList = playerList;
        playerList = new ArrayList<>();
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
        game.setPlayerList(playerList);
    }

    private void refreshScores() {
        ArrayList<Player> playerArr = constantPlayerList;

        for (int i = 0; i < ConfigureController.getMaxNumPlayers(); i++) {
            String s = (playerArr.size() >= i + 1 && playerArr.get(i) != null) ? String.valueOf(playerArr.get(i).getScore()) : "";
            scoreView.get(i).setText(s);
        }
    }

    public void updateTurnTime() {
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

        //timeLeft.setText(String.valueOf(turnTime));
    }

    private void startTurnTimer() {
        //turnTime = 50;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (turnTime >= 0) {
                        timeLeft.setText(String.valueOf(turnTime--));
                    } else {
                        handleEndTurn();
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
            String id = "pane" + clickedButton.getParent().getId();

            if (typeOfMule.compareTo("food") == 0 && clickedButton.getText().compareTo("Mule") == 0 &&
                    propertyOwnedList.contains(id) && currentPlayer.getPropertyList().contains(id)) {
                clickedButton.setText("food");
                mule = new FoodMule(propertyType);
            } else if (typeOfMule.compareTo("energy") == 0 && clickedButton.getText().compareTo("Mule") == 0 &&
                    propertyOwnedList.contains(id) && currentPlayer.getPropertyList().contains(id)) {
                clickedButton.setText("energy");
                mule = new EnergyMule(propertyType);
            } else if (typeOfMule.compareTo("ore") == 0 && clickedButton.getText().compareTo("Mule") == 0 &&
                    propertyOwnedList.contains(id) && currentPlayer.getPropertyList().contains(id)) {
                clickedButton.setText("ore");
                mule = new OreMule(propertyType);
            }
			placingMule = false;

            if (mule != null) {
                currentPlayer.addToMuleList(mule);
            }

            enableButtons(false);
    	}
    }
    
    @FXML
    private void handleSave (ActionEvent event) {
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
    		out3.writeInt(game.getRandomFactor());
    		out3.close();
    		fileOut3.close();
    	} catch (IOException i) {
    		i.printStackTrace();
    	}
    }

    public static void setTypeOfMule(String type) { typeOfMule = type; }

    public Game getGame(){ return game; }

    public List<Player> getPlayerList() { return playerList; }

    public static void enableButtons(Boolean bool) {
        for (Button button : buttonArr) {
            if (button.getText().equals("Mule")) {
                button.setVisible(bool);
            }
        }
    }
    
    public static void buyProperty() {
    	gameScene = ConfigureController.getGameScene();
        gameStage = ConfigureController.getGameStage();
        gameStage.setScene(gameScene);
        buyingMode = true;
        if (!selectionPhase) {
        	selectionPhase = true;
        }
        
	}
    
    public static void sellProperty() {
    	gameScene = ConfigureController.getGameScene();
        gameStage = ConfigureController.getGameStage();
        gameStage.setScene(gameScene);
        sellMode = true;
    }

}
