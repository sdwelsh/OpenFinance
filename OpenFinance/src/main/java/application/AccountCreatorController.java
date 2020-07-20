package application;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

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
	@FXML
	private TextField emailText;
	@FXML
	private TextField phoneText;
	
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
		String email = getEmail();
		String phone = getPhone();
		
		
		
		if(firstName==null || lastName == null || username==null || password==null || email == null || phone == null) {
			//Do Nothing
		} else {
			try {
				Calendar calander = Calendar.getInstance();
				User user = new User(firstName, lastName, username, password, 0, 0, email, phone, calander);
				Manager manager = Manager.getInstance();
				manager.createNewUser(user);
				manager.setCurrentUser(user);
				Main.setMain(new MainController());
			} catch(IllegalArgumentException e) {
				error.setText(e.getMessage());
			}
			
		}
	}

	private String getPhone() {
		if(phoneText.getText().contains(" ") || phoneText.getText().length() > 12 || phoneText.getText().length() < 12) {
			try {
				Integer.parseInt(phoneText.getText().substring(0, 3));
			} catch(IllegalArgumentException e) {
				error.setText("Enter a Valid Phone Number");
				return null;
			}
			try {
				Integer.parseInt(phoneText.getText().substring(4, 7));
			} catch(IllegalArgumentException e) {
				error.setText("Enter a Valid Phone Number");
				return null;
			}
			try {
				Integer.parseInt(phoneText.getText().substring(8));
			} catch(IllegalArgumentException e) {
				error.setText("Enter a Valid Phone Number");
				return null;
			}
			error.setText("Enter a Valid Phone Number");
			return null;
		}
		return phoneText.getText();
	}

	private String getEmail() {
		if(emailText.getText().trim().equals("") || !emailText.getText().contains("@") || !emailText.getText().contains(".")) {
			error.setText("Enter a Valid Email");
			return null;
		} 
		return emailText.getText();
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
		}else if(username.contains(" ")) {
			error.setText("Username cannot have any spaces");
			return null;
		} else if(username.contains("_") || username.contains(".") || username.contains("-")) {
			error.setText("Invalid Character Used: _  .  -");
			return null;
		} else if(username.length() > 32) {
			error.setText("Username must be less than 32 characters");
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
		lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
		
		return lastName;
	}

	private String getFirstName() {
		String firstName = this.firstName.getText();
		if(firstName.trim().equals("")) {
			error.setText("Enter a valid First Name");
			return null;
		}
		firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
		return firstName;
	}
}
