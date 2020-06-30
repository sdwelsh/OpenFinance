/**
 * 
 */
package data.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import application.users.User;

/**
 * Creates the IO for reading and writing the user data to the current system file
 * @author Stephen Welsh
 *
 */
public class UserIO {
		
	/**
	 * Reads user data from the user file and creates an ArrayList of user
	 * objects to be passed to the manager on start up.
	 * @return an array list of user objects form previous users
	 * @throws IOException if the file is unreadable
	 */
	public static ArrayList<User> readUsersFromFile() throws IOException{
		try {
			BufferedReader scan = new BufferedReader(new FileReader("test-files/users.txt"));
			
			ArrayList<User> users = new ArrayList<User>();
			
			String firstName = "";
			String lastName = "";
			String username = "";
			String password = "";
			
			String line = "";
			
			while((line = scan.readLine()) != null) {
				
				String[] split = line.split(" \\| ");
				
				firstName = split[0];
				lastName = split[1];
				username = split[2];
				password = split[3];
				
				users.add(new User(firstName, lastName, username, password));
			}
			
			
			
			scan.close();
			
			return users;
			
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Writes the Users to the current user file.
	 * @param users an Array List of user objects passed to the user
	 */
	public static void writeUsersToFile(ArrayList<User> users){
		try {
			OutputStreamWriter p = new OutputStreamWriter(new FileOutputStream(new File("test-files/users.txt")));
			
			for(int i = 0; i < users.size(); i++) {
				p.write(users.get(i) + "\n");
			}
			
			p.close();
			
			
		} catch(Exception e) {
			throw new IllegalArgumentException("Unable to save users.");
		}
	}
}
