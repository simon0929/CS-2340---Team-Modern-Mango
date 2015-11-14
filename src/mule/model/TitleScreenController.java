package mule.model;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;

public class TitleScreenController {

    @FXML
    private Rectangle rectangle;

    @FXML
    private Button startButton1, startButton2;


    @FXML
    private void initialize() {
        rectangle.setStyle("-fx-stroke-width: 5; -fx-stroke: #FFF9E2; -fx-stroke-dash-array: 10 10 10 10;");
        startButton1.setStyle("-fx-background-color: #FFF9E2");
        startButton2.setStyle("-fx-background-color: TRANSPARENT");
    }

    @FXML
    private void handleStartGame(ActionEvent event) throws IOException {
        Parent gameScreenParent = FXMLLoader.load(getClass().getResource("/mule/view/GameConfigure.fxml"));
        Scene gameScene = new Scene(gameScreenParent);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();
    }

}
