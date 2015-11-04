package mule.model;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public final class Main extends Application {

	private final int width = 600, height = 500;

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("/mule/view/GameConfigure.fxml"));
			Scene scene = new Scene(root, width, height);
			scene.getStylesheets().add(getClass().getResource("/mule/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
