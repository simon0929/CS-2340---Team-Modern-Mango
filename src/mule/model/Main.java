package mule.model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.WindowEvent;

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
    private static AudioClip audio;

	@Override
	public void start(Stage primaryStage) {
		try {

			AnchorPane root = FXMLLoader.load(getClass().getResource("/mule/view/TitleScreen.fxml"));
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			scene.getStylesheets().add(getClass().getResource("/mule/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
            audio = new AudioClip(getClass().getResource("/mule/view/resources/UnusedMysteriousHornTheme.mp3").toString());
            audio.play(.25);

		} catch(Exception e) {
			Logger logger = Logger.getLogger(Main.class.getName());
			logger.log(Level.SEVERE, e.toString(), e);
		}

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
	}

	/**
	 * Entry point for application
	 * @param args Used to access command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

    public static AudioClip getAudioClip() {
        return audio;
    }
}
