package application;
	



import application.manager.Manager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;



public class Main extends Application{
	
	/** Creates a static root to change views*/
	private static BorderPane root;
	
	
	private static Stage primaryStage;
	
	private static Scene scene;
	
	
	 /**
	  * Starts the login screen allowing the user to enter their username and password.
	  * This also allows for the user to create an account with the system.
	  */
	public void start(Stage stage) {
		try {
			
			primaryStage = new Stage();
			primaryStage.getIcons().add(new Image(getClass().getResource("/logo16.png").toExternalForm()));
			primaryStage.getIcons().add(new Image(getClass().getResource("/logo64.png").toExternalForm()));
			primaryStage.getIcons().add(new Image(getClass().getResource("/logo32.png").toExternalForm()));
			primaryStage.getIcons().add(new Image(getClass().getResource("/logo128.png").toExternalForm()));
			primaryStage.getIcons().add(new Image(getClass().getResource("/logo256.png").toExternalForm()));
			primaryStage.getIcons().add(new Image(getClass().getResource("/logo512.png").toExternalForm()));
			primaryStage.getIcons().add(new Image(getClass().getResource("/logo1024.png").toExternalForm()));
			primaryStage.setIconified(true);
			primaryStage.setTitle("OpenFinance");
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
		boolean bool = primaryStage.isFullScreen();
		primaryStage.getScene().setRoot(view);
		primaryStage.setFullScreen(bool);
		primaryStage.show();
		
	}
	
	public static void setMain(BorderPane view) {
		primaryStage.setScene(new Scene(view));
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
