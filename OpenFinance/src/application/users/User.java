/**
 * 
 */
package application.users;

import java.util.ArrayList;

import data.assets.longterm.LongTermAsset;
import data.assets.longterm.LongTermAssetsList;
import data.assets.shortTerm.ShortTermAsset;
import data.assets.shortTerm.ShortTermAssetsList;
import data.liabilities.LiabilitiesList;
import data.liabilities.Liability;

/**
 * User class Creates a new User that is able to create a list of assets and liabilities for themselves to be viewed by them 
 * on opening of the app.
 * @author Stephen Welsh
 *
 */
public class User {
	
	/** The Users firstName*/
	private String firstName;
	
	/** The Users last name */
	private String lastName;
	
	/** The Users Unique id*/
	private String id;
	
	/** The users password*/
	private String password;
	
	/** List of User Longterm Assets*/
	private LongTermAssetsList longTermAssets;
	
	private ShortTermAssetsList shortTermAssets;
	
	private LiabilitiesList liabilities;

	/**
	 * Constructs the User class giving the user a name, unique id, and password.
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @param password
	 */
	public User(String firstName, String lastName, String id, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setPassword(password);
		longTermAssets = new LongTermAssetsList();
		shortTermAssets = new ShortTermAssetsList();
		liabilities = new LiabilitiesList();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName==null || firstName.trim().equals("")) {
			throw new IllegalArgumentException();
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName==null || lastName.trim().equals("")) {
			throw new IllegalArgumentException();
		}
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if(id==null || id.trim().equals("")) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(password==null || password.trim().equals("")) {
			throw new IllegalArgumentException();
		}
		this.password = password;
	}
	
	public void addLongTermAsset(LongTermAsset asset) {
		longTermAssets.addAsset(asset);
	}
	
	public LongTermAssetsList getLongTermAssets() {
		return longTermAssets;
	}
	
	public double getLongTermAssetsTotal() {
		return longTermAssets.getLongTermAssetsTotal();
	}

	@Override
	public String toString() {
		return  firstName + " | " + lastName + " | " + id + " | " + password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	public void reset() {
		longTermAssets = new LongTermAssetsList(); 
		shortTermAssets = new ShortTermAssetsList();
		liabilities = new LiabilitiesList();
	}

	public void deleteLongTermAsset(LongTermAsset asset) {
		longTermAssets.removeStocks(asset);
	}
	
	public ShortTermAssetsList returnShortTermAssets() {
		return shortTermAssets;
	}
	
	public LiabilitiesList returnLiabilities() {
		return liabilities;
	}

	public double getShortTermAssetsTotal() {
		ArrayList<ShortTermAsset> assets = shortTermAssets.returnShortTermAssets();
		
		double total = 0;
		
		for(ShortTermAsset asset : assets) {
			total += asset.getAmount();
		}
		
		return total;
	}

	public double getTotalLongTermLiabilities() {
		ArrayList<Liability> liability = liabilities.getLiabilities();
		
		double total = 0;
		
		for(Liability l : liability) {
			if(l.getYearsToMaturity() > 1) {
				total += l.getTotalAmount();
			}
		}
		
		return total;
	}
	
	
	
}
