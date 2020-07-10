/**
 * 
 */
package data.io;

import static org.junit.Assert.*;

import org.junit.Test;

import application.manager.Manager;

/**
 * @author Stephen Welsh
 *
 */
public class UserDataIOTest {

	/**
	 * Test method for {@link data.io.UserDataIO#readUserData(application.users.User)}.
	 */
	@Test
	public void testReadUserData() {
		Manager manager = Manager.getInstance();
		
		assertTrue(manager.login("swelsh", "smokey"));
		
		manager.logout();
	}

	/**
	 * Test method for {@link data.io.UserDataIO#writeUserData(application.users.User)}.
	 */
	@Test
	public void testWriteUserData() {
		fail("Not yet implemented");
	}

}
