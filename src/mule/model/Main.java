package mule.model;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Class that handles the launch of
 * the application
 *
 * @author ModernMango
 *
 */
public final class Main extends Application {

	private static final int WIDTH = 900, HEIGHT = 675;

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("/mule/view/TitleScreen.fxml"));
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			scene.getStylesheets().add(getClass().getResource("/mule/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			Logger logger = Logger.getLogger(Main.class.getName());
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}

	/**
	 * Entry point for application
	 * @param args Used to access command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
