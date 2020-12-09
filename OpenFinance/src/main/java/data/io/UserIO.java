/**
 * 
 */
package data.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import application.users.User;

/**
 * Creates the IO for reading and writing the user data to the current system file
 * @author Stephen Welsh
 *
 */
public class UserIO {
	
	private static final String FILENAME = System.getProperty("user.home") + "\\OpenFinance\\ProgramFiles\\users.txt";
		
	/**
	 * Reads user data from the user file and creates an ArrayList of user
	 * objects to be passed to the manager on start up.
	 * @return an array list of user objects form previous users
	 * @throws IOException if the file is unreadable
	 */
	public static ArrayList<User> readUsersFromFile(String key, String transformation) throws IOException{
		
		System.out.println(FILENAME);
		
		File file = new File(FILENAME);
		
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		
		FileEncrypterDecrypter security = new FileEncrypterDecrypter(key, transformation);
		
		Scanner scan = new Scanner(security.decrypt(FILENAME));
		
		ArrayList<User> users = new ArrayList<User>();
		
		String firstName = "";
		String lastName = "";
		String username = "";
		String password = "";
		int age = 0;
		int retirementAge = 0;
		String email = "";
		String phone = "";
		String date = "";
		
		scan.useDelimiter(" , ");
		
		while(scan.hasNext()) {
			String line = scan.next();
			String[] split = line.split(" \\| ");
			
			firstName = split[0];
			lastName = split[1];
			username = split[2];
			password = split[3];
			age = Integer.parseInt(split[4]);
			retirementAge = Integer.parseInt(split[5]);
			email = split[6];
			phone = split[7];
			date = split[8];
			int month = Integer.parseInt(date.substring(0, 2));
			int day = Integer.parseInt(date.substring(3, 5));
			int year = Integer.parseInt(date.substring(6, 10));
			int hourOfDay = Integer.parseInt(date.substring(11, 13));
			int minute = Integer.parseInt(date.substring(14, 16));
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day, hourOfDay, minute);
			
			
			users.add(new User(firstName, lastName, username, password, age, retirementAge, email, phone, calendar));
		}
		
		
		
		scan.close();
		
		return users;
	}
	
	/**
	 * Writes the Users to the current user file.
	 * @param users an Array List of user objects passed to the user
	 */
	public static void writeUsersToFile(ArrayList<User> users, String key, String transformation){
		try {
			FileEncrypterDecrypter security = new FileEncrypterDecrypter(key, transformation);
			
			String content = "";
			
			for(User user : users) {
				content += user.toString() + " , ";
			}
			
			security.encrypt(content, FILENAME);
		} catch(Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Unable to save users.");
		}
	}
}
