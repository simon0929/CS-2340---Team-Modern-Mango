package mule.model;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class TitleScreenController {

    @FXML
    private void handleStartGame(ActionEvent event) throws IOException {
        Parent gameScreenParent = FXMLLoader.load(getClass().getResource("/mule/view/GameConfigure.fxml"));
        Scene gameScene = new Scene(gameScreenParent);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();
    }

}
