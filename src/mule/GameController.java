package mule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	private Pane pane;

	private String phase;

	public static Game game;

	private Player currentPlayer;

    //numOfPropBuyInRound = number of properties bought this round, if 0 then land selection phase ends
	private int turnNumber, numOfPropBuyInRound, turnTime, maxTurnTime, defaultTurnTime;

	private boolean selectionPhase;

	public static ArrayList<Player> playerList;

    private ArrayList<Pane> propertyOwnedList;

	@FXML
	private void initialize() {
		playerList = ConfigureController.playerList;
		currentPlayer = ConfigureController.player1;
		turn.setText(currentPlayer.getName());
		turnNumber = 1;
		numOfPropBuyInRound = 0;
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
        defaultTurnTime = 50;
        updateTurnTime();
        propertyOwnedList = new ArrayList<>();
	}

    //Calls the Game.fxml file and actually constructs the GUI.
	@FXML
	private void handleTown(MouseEvent event) throws IOException {
		Parent townScreen = FXMLLoader.load(getClass().getResource("Town.fxml"));
		Scene townScene = new Scene(townScreen);
		Stage townStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		townStage.setScene(townScene);
		townStage.show();
	}

    //Performs a variety of things when the End Turn button is clicked
	@FXML
	private void handleEndTurn(MouseEvent event) throws IOException {
        //refreshes screen
		game.update();

		round.setText(String.valueOf(game.getRound()));
		turnNumber++;

        //if the number of turns exceeds the number of players, the round ends
		if (turnNumber > ConfigureController.maxPlayers) {
            //Calculates all player scores and rearranges playerList for a new order for the next turn
			getTurnOrder();

            //Refreshes scores on GUI
            refreshScores();

            //Resets the turn to 1
			turnNumber = 1;


			if (numOfPropBuyInRound == 0) {
				selectionPhase = false;
			}
			else {
				numOfPropBuyInRound = 0;
			}
		}

        currentPlayer = playerList.get(turnNumber - 1);
        updateTurnTime();

		turn.setText(currentPlayer.getName());
		food.setText(String.valueOf(currentPlayer.getFood()));
		money.setText(String.valueOf(currentPlayer.getMoney()));
		energy.setText(String.valueOf(currentPlayer.getEnergy()));
		ore.setText(String.valueOf(currentPlayer.getOre()));
	}

    private void handleEndTurn() {
        //refreshes screen
        game.update();

        round.setText(String.valueOf(game.getRound()));
        turnNumber++;

        //if the number of turns exceeds the number of players, the round ends
        if (turnNumber > ConfigureController.maxPlayers) {
            //Calculates all player scores and rearranges playerList for a new order for the next turn
            getTurnOrder();

            //Refreshes scores on GUI
            refreshScores();

            //Resets the turn to 1
            turnNumber = 1;


            if (numOfPropBuyInRound == 0) {
                selectionPhase = false;
            }
            else {
                numOfPropBuyInRound = 0;
            }
        }

        currentPlayer = playerList.get(turnNumber - 1);
        updateTurnTime();

        turn.setText(currentPlayer.getName());
        food.setText(String.valueOf(currentPlayer.getFood()));
        money.setText(String.valueOf(currentPlayer.getMoney()));
        energy.setText(String.valueOf(currentPlayer.getEnergy()));
        ore.setText(String.valueOf(currentPlayer.getOre()));
    }

	@FXML
	private void handleProperty(MouseEvent event) throws IOException {
		if (selectionPhase) {
			if (!game.selectedProp()) {
				Color playerColor = game.getCurrPlayer().getColor();

                //Gets the actual property object that was clicked so that things can be done to it
				Pane property = (Pane) event.getSource();

				if (!propertyOwnedList.contains(property)) {
					currentPlayer.incrementPropertyOwned();

					if (currentPlayer.getNumOfFreeProperties() == 0) {
                        //Automatically updates player's money and on the GUI
						currentPlayer.setMoney(currentPlayer.getMoney() - 300);
                        money.setText(String.valueOf(currentPlayer.getMoney()));
					}
					else {
						currentPlayer.decrementFreeProperty();
					}

					numOfPropBuyInRound++;
                    currentPlayer.addToPropertyList(property);

					Region reg = (Region) event.getSource();
					reg.setBorder(new Border(new BorderStroke(playerColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4.0))));

                    currentPlayer.calculateScore();
                    refreshScores();
                }
			}
		}
	}

	private void getTurnOrder() {
        Player minScore = null;
        ArrayList<Player> tempList = new ArrayList<Player>();

        for (int i = 0; i < ConfigureController.maxPlayers; i++) {
            for (int j = 0; j < ConfigureController.maxPlayers; j++) {
                if (minScore == null) {
                    minScore = playerList.get(i);
                }
                else if (playerList.get(i) != null && playerList.get(i).getScore() < minScore.getScore()) {
                    minScore = playerList.get(i);
                    playerList.remove(i);
                }
            }
            tempList.add(minScore);
        }

        playerList = tempList;
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
        if (game.getRound() == 1) {
            turnTime = 50;
        }
        else {
            if (game.getRound() < 5) {
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
            else if (game.getRound() < 9) {
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

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                      @Override
                      public void run() {
                          if (turnTime >= 0) {
                              timeLeft.setText(String.valueOf(turnTime--));
                          }
                          else {
                              handleEndTurn();
                          }
                      }
                  });
            }
        }, 1000, 1000);
    }
}
