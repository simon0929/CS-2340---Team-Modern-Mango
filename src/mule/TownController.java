package mule;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TownController {
	
	@FXML
    private Button returnToMap;

	@FXML
	private void handleReturnToMap(ActionEvent event) throws IOException {
		Parent gameScreenParent = FXMLLoader.load(getClass().getResource("Game.fxml"));
		Scene gameScene = new Scene(gameScreenParent);
		Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		gameStage.setScene(gameScene);
		gameStage.show();
	}

	@FXML
	private void handleEndTurn(MouseEvent event) throws IOException {

	}
}
