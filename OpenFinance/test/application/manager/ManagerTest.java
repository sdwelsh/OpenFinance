/**
 * 
 */
package application.manager;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Stephen Welsh
 *
 */
public class ManagerTest {

	/**
	 * Test method for {@link application.manager.Manager#login(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testLogin() {
		Manager manager = Manager.getInstance();
		
		assertTrue(manager.login("sdwelsh", "smokey"));
		
	}

	/**
	 * Test method for {@link application.manager.Manager#getCurrentUser()}.
	 */
	@Test
	public void testGetCurrentUser() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link application.manager.Manager#setCurrentUser(application.users.User)}.
	 */
	@Test
	public void testSetCurrentUser() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link application.manager.Manager#createNewUser(application.users.User)}.
	 */
	@Test
	public void testCreateNewUser() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link application.manager.Manager#readUsers()}.
	 */
	@Test
	public void testReadUsers() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link application.manager.Manager#writeUsers()}.
	 */
	@Test
	public void testWriteUsers() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link application.manager.Manager#logout()}.
	 */
	@Test
	public void testLogout() {
		fail("Not yet implemented");
	}

}
