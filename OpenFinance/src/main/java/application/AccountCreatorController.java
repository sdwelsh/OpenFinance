package application;

import java.io.IOException;

import application.manager.Manager;
import application.users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AccountCreatorController extends BorderPane{
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML 
	private PasswordField rePassword;
	@FXML
	private Label error;
	
	public AccountCreatorController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	@FXML 
	public void createAccount() {
		String firstName = getFirstName();
		String lastName = getLastName();
		String username = getUserName();
		String password = getPassword();
		
		System.out.println(password);
		
		
		
		if(firstName==null || lastName == null || username==null || password==null) {
			//Do Nothing
		} else {
			User user = new User(firstName, lastName, username, password, 0, 0);
			Manager manager = Manager.getInstance();
			manager.createNewUser(user);
			manager.setCurrentUser(user);
			Main.login(new MainController());
		}
	}

	private String getPassword() {
		String password = this.password.getText();
		String passwordRe =  this.rePassword.getText();
		if(password.equals("")) {
			error.setText("Enter a valid Password");
			return null;
		} else if(!password.equals(passwordRe)) {
			error.setText("Passowrds dont match!");
			return null;
		}
		return password;
	}

	private String getUserName() {
		String username = this.username.getText();
		if(username.trim().equals("")) {
			error.setText("Enter a valid username");
			return null;
		}
		return username;
	}

	private String getLastName() {
		String lastName = this.lastName.getText();
		if(lastName.trim().equals("")) {
			error.setText("Enter a valid Last Name");
			return null;
		}
		return lastName;
	}

	private String getFirstName() {
		String firstName = this.firstName.getText();
		if(firstName.trim().equals("")) {
			error.setText("Enter a valid First Name");
			return null;
		}
		return firstName;
	}
}
