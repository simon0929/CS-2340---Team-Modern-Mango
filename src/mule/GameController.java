package mule;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
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

	@FXML
    private Label townLabel;

	@FXML
	private Label round, turn, timeLeft;


	@FXML
	private Pane pane;

	public static String phase;

	public static Game game;

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
		this.game.update();
		this.round.setText(String.valueOf(this.game.getRound()));
	}

	@FXML
	private void handleProperty(MouseEvent event) throws IOException {
		this.game = ConfigureController.game;
		if (!this.game.selectedProp()) {
		Color playerColor = this.game.getCurrPlayer().getColor();
		Region reg = (Region) event.getSource();
		reg.setBorder(new Border(new BorderStroke(playerColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(8.0))));
		this.game.setSelectedProp(true);
		}
	}


}
