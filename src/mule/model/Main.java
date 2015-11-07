package mule.model;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

import java.util.logging.Logger;
import java.util.logging.Level;

public final class Main extends Application {

	private static final int WIDTH = 600, HEIGHT = 500;

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("/mule/view/GameConfigure.fxml"));
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			scene.getStylesheets().add(getClass().getResource("/mule/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			Logger logger = Logger.getLogger(Main.class.getName());
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
