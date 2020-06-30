package application;
	


import java.util.ArrayList;
import java.util.List;

import com.gluonhq.charm.glisten.control.TextField;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;



public class Main extends Application{
	
	/** Creates a static root to change views*/
	private static BorderPane root;
	
	/** the name of the view we are currently in */
	private String view;
	
	/** list of screens */
	private static List<VBox> screens = new ArrayList<VBox>();
	
	private static Stage primaryStage;
	
	
	 /**
	  * Starts the login screen allowing the user to enter their username and password.
	  * This also allows for the user to create an account with the system.
	  */
	public void start(Stage stage) {
		try {
			
			primaryStage = new Stage();
			
			root = new LoginController();
			Scene scene = new Scene(root);
			
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void setView(BorderPane view) {
		primaryStage.setScene(new Scene(view, primaryStage.getWidth(), primaryStage.getHeight()));
		primaryStage.show();
	}
	
	public static void login(BorderPane view) {
		primaryStage.setScene(new Scene(view));
		primaryStage.show();
	}
}
