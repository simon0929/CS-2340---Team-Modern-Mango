package mule;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TownController {

	@FXML
    private Button returnToMap;

	public Stage gameStage;

	public Scene gameScene;

	@FXML
	private void handleReturnToMap(ActionEvent event) throws IOException {
		gameScene = ConfigureController.gameScene;
		gameStage = ConfigureController.gameStage;
		gameStage.setScene(gameScene);
	}

	@FXML
	private void handleEndTurn(MouseEvent event) throws IOException {
		Game game = ConfigureController.game;
		game.update();
	}
}
