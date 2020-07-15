/**
 * 
 */
package data.liabilities;

import java.util.ArrayList;

/**
 * @author Stephen Welsh
 *
 */
public class LiabilitiesList {
	
	ArrayList<Liability> liabilities;
	
	public LiabilitiesList() {
		liabilities = new ArrayList<Liability>();
	}
	
	public ArrayList<Liability> getLiabilities() {
		return liabilities;
	}
	
	public void addLiability(Liability liability) {
		liabilities.add(liability);
	}
	
	public void deleteLiability(Liability liability) {
		for(int i = 0; i < liabilities.size(); i++) {
			Liability l = liabilities.get(i);
			if(l.equals(liability)) {
				liabilities.remove(i);
			}
		}
	}
	
}
