package application;
	


import com.gluonhq.charm.glisten.control.TextField;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application{
	
	 @FXML private TextField login;
	
	 /**
	  * Starts the login screen allowing the user to enter their username and password.
	  * This also allows for the user to create an account with the system.
	  */
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			
			Scene scene = new Scene(root,850,350);
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getText() {
        return textProperty().get();
    }
	
	public StringProperty textProperty() {
        return login.textProperty();
    }
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
    protected void login(ActionEvent event) {
		if(event.toString().equals("#login")) {
			System.out.println("The login was clicked!");
		}
		else {
			System.out.println("The create User was clicked!");
		}
    }
}
