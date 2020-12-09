/**
 * 
 */
package application;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import application.manager.Manager;
import application.popup.ProgressBarController;
import data.assets.longterm.LongTermAsset;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

/**
 * Creates the controller for the login screen
 * @author Stephen Welsh
 *
 */
public class LoginController extends BorderPane {
	
	@FXML public TextField username;
	
	@FXML public PasswordField password;
	
	@FXML public Label error;
	
	@FXML public ImageView imageView;
	
	@FXML public ImageView image;
	
	

    public LoginController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        Manager.getInstance();

        try {
            fxmlLoader.load();
            
            Image thumb = new Image("/logo.png");
            
            image.setImage(thumb);
            
            password.setOnKeyReleased(new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent ke)
                {
                    if (ke.getCode().equals(KeyCode.ENTER))
                    {
                        login();
                    }
                }
            });
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void login() {
    	Manager manager = Manager.getInstance();
    	
    	
    	
    	if(manager.login(username.getText(), password.getText())) {
    		
    		BorderPane view = new ProgressBarController();
    	    Main.login(view);
    	   
    	    
//    	    Task<Void> task = new Task<Void>() {
//                @Override
//                public Void call() throws Exception {
//                	
//                    return null;
//                }
//            };
//
//            task.setOnSucceeded(event -> {
//            	BorderPane view2 = new MainController();
//        	    Main.setView(view2);
//            });
//    	    
//    	    new Thread(task).run();
    	} else {
    		error.setText("Username or Password doesn't match");
    	}

    
    }
    
    @FXML
    public void openNewAccount() {
        Main.login(new AccountCreatorController());
    }
}
