package application.popup;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import application.Main;
import application.MainController;
import application.manager.Manager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class ProgressBarController extends BorderPane {
	
	@FXML private ProgressIndicator progressIndicator;
	
	public ProgressBarController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loading.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            
            new Thread() {

                // runnable for that thread
                public void run() {
                    progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                    try {
                    	Manager.getInstance().readCurrentUser();
                    	
                    	
                        URL url = new URL("http://www.google.com");
                        URLConnection connection = url.openConnection();
                        connection.connect();
                    	
                    	Platform.runLater(new Runnable() {

                            public void run() {
                            	Main.loaded();
                        		Main.setMain(new MainController());
                            }
                        });
                    } catch(Exception e) {
                    	Main.exitApplication();
                    }
                	
                       
                        
                    
                }
            }.start();
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        } 
		
		
		 
	}
}
