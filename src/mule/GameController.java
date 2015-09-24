package mule;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class GameController {
	
	@FXML
    private Label townLabel;

	@FXML
	private Pane pane;

	public static int round, turn = 0;

	public static String phase;

	public static Player currentPlayer = ConfigureController.currentPlayer;

	@FXML
	private void handleTown(MouseEvent event) throws IOException {
		Parent townScreen = FXMLLoader.load(getClass().getResource("Town.fxml"));
		Scene townScene = new Scene(townScreen);
		Stage townStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		townStage.setScene(townScene);
		townStage.show();
	}

	@FXML
	private void handleEndTurn(MouseEvent event) throws IOException {

	}

	@FXML
	private void handleProperty(MouseEvent event) throws IOException {
		Color playerColor = currentPlayer.getColor();
	}
}
