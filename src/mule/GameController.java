package mule;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameController {
	
	@FXML
    private Label townLabel;

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

	}
}
