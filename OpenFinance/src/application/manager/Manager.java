/**
 * 
 */
package application.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.users.User;
import data.io.UserIO;

/**
 * Creates a list of Users that will be managed by the login system.
 * @author Stephen Welsh
 *
 */
public class Manager {
	
	/** List of Users */
	private ArrayList<User> users;
	
	/** Creates a single instance of the manager */
	private static Manager instance;
	
	/** Creates a current user of the software*/
	private User currentUser;
	
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Constructs a single instance of the manager class on initialization of the program
	 */
	private Manager() {
		users = readUsers();
		currentUser = getCurrentUser();
	}
	
	/**
	 * Returns the current instance of the Manager allowing for effective singleton pattern
	 * @return Current instance of Manager
	 */
	public static Manager getInstance() {
		if(instance==null) {
			instance = new Manager();
		}
		return instance;
	}
	
	/**
	 * Creates a hashed password using the sha 1 hashing algorithm for password hashing.
	 * @param pw is the password the user wishes to hash
	 * @return the hashed password
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch(NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	
	/**
	 * Attempts to log a user into the system checking if the account is in the system. Once the account is found the
	 * password is used to check if the user signing in is the intended user.
	 * @param id user id as stored in the system
	 * @param password the password the user is attempting to use to enter the system.
	 * @return true		if the user is in the system and the password match
	 * 		   false	if the user doesnt exist of the passwords dont match
	 */
	public boolean login(String id, String password) {
		if(currentUser ==  null) {
			for(int i = 0; i < users.size(); i++) {
				if(users.get(i).getId().equals(id)) {
					String hash = hashPW(password);
					writePW(hash);
					String pw = readPW();
					writePW(users.get(i).getPassword());
					String userPW = readPW();
					if(pw.equals(userPW)) {
						currentUser = users.get(i);
						return true;
					}
				} 
			}
		}
		return false;
	}
	

	/** Returns the current user */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Sets the current user to a user passed as parameter
	 * @param user The user being set to current user
	 */
	public void setCurrentUser(User user) {
		currentUser = user;
	}
	
	/**
	 * Creates a new user account and adds it to the current user list. User password is also hashed here 
	 * for secure password storage.
	 * @param user A user object that will be stored in the system
	 */
	public void createNewUser(User user) {
		User hashedUser = new User(user.getFirstName(), user.getLastName(), user.getId(), hashPW(user.getPassword()));
		
		users.add(hashedUser);
		setCurrentUser(hashedUser);
		writeUsers();
	}
	
	/**
	 * Reads the current user list upon starting up the program.
	 * @return an array list of user objects
	 */
	public ArrayList<User> readUsers() {
		try {
			return UserIO.readUsersFromFile();
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Writes Users to the user file when a new user is added to the system
	 */
	public void writeUsers() {
		UserIO.writeUsersToFile(users);
	}
	
	/**
	 * Private helper method for writing passwords and reading them
	 * @param pw the password being written
	 */
	private void writePW(String pw) {
		try {
			PrintStream p = new PrintStream(new File("test-files/password.txt"));
			p.print(pw);
			p.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Private helper method that reads passwords from the written file and deletes the file
	 * @return the password sting read from the file
	 */
	private String readPW() {
		try {
			File password = new File("test-files/password.txt");
			Scanner scan = new Scanner(password);
			String pw = scan.next();
			scan.close();
			password.delete();
			return pw;
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Logs the current user out of the system
	 */
	public void logout() {
		currentUser = null;
	}
}
