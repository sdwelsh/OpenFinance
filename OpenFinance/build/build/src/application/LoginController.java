/**
 * 
 */
package application;

import java.io.File;
import java.io.IOException;

import application.manager.Manager;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
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
	
	@FXML private TextField username;
	
	@FXML private PasswordField password;
	
	@FXML private Label error;
	
	@FXML private ImageView imageView;
	
	@FXML private ImageView image;
	
	

    public LoginController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        Manager.getInstance();

        try {
            fxmlLoader.load();
            
            Image thumb = new Image("/application/resources/logo.png");
            
            image.setImage(thumb);
            
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    protected void login(ActionEvent event) {
    	Manager manager = Manager.getInstance();
    	
    	if(manager.login(username.getText(), password.getText())) {
    		BorderPane view = new MainController();
    	    Main.setView(view);
    	} else {
    		error.setText("Username or Password doesn't match");
    	}
    
    }
    
    @FXML
    protected void openNewAccount(ActionEvent event) {
        Main.login(new AccountCreatorController());
    }
}
