/**
 * 
 */
package application;

import java.io.IOException;

import application.manager.Manager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void login() {
    	Manager manager = Manager.getInstance();
    	
    	Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
            	manager.readCurrentUser();
                return null;
            }
        };

        task.setOnSucceeded(event -> {
        	BorderPane view = new MainController();
    	    Main.setView(view);
        });
    	
    	if(manager.login(username.getText(), password.getText())) {
    		BorderPane view = new MainController();
    	    Main.setMain(view);
    	    new Thread(task).run();
    	} else {
    		error.setText("Username or Password doesn't match");
    	}

    
    }
    
    @FXML
    public void openNewAccount() {
        Main.login(new AccountCreatorController());
    }
}
