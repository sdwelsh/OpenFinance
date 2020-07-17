package application;
	


import java.util.ArrayList;
import java.util.List;

import application.manager.Manager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
	
	private static Scene scene;
	
	
	 /**
	  * Starts the login screen allowing the user to enter their username and password.
	  * This also allows for the user to create an account with the system.
	  */
	public void start(Stage stage) {
		try {
			
			primaryStage = new Stage();
			primaryStage.getIcons().add(new Image("/application/resources/favicon.png"));
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			
			root = new LoginController();
			scene = new Scene(root);
			
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
		
		
		primaryStage.getScene().setRoot(view);
		primaryStage.setFullScreen(true);
		primaryStage.show();
		
	}
	
	public static void login(BorderPane view) {
		primaryStage.setScene(new Scene(view));
		primaryStage.show();
	}
	
	public static Scene getScene() {
		return scene;
	}
	
	@FXML
	public void exitApplication(ActionEvent event) {
	   Platform.exit();
	}
	
	@Override
	public void stop(){
	    if(Manager.getInstance().getCurrentUser() != null) {
	    	Manager.getInstance().logout();
	    }
	}
}
