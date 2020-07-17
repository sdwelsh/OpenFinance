/**
 * 
 */
package data.assets.longterm;

import data.assets.longterm.ETF.CapType;
import data.assets.longterm.ETF.CountryType;
import data.assets.longterm.ETF.InvestmentType;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Stephen Welsh
 *
 */
public class MutualFunds extends LongTermAsset {
	
	/** The type of country market of the ETF*/
	private CountryType country;
	
	/** The cap of the ETF */
	private CapType cap;
	
	private InvestmentType invType;
	
	SimpleStringProperty countryString;
	SimpleStringProperty capString;
	SimpleStringProperty investmentTypeString;

	/**
	 * Constructor of mutual find with initial price
	 * @param ticker the Stock Ticker
	 * @param price the total price of the mutual fund
	 * @param intiPrice the initial price of the stock
	 * @param quantity the total amount of stocks owned
	 * @param years the years till maturity
	 * @param bank bank the stock is at
	 * @param type Cap Type of the stock
	 */
	public MutualFunds(String ticker, double initPrice, double quantity, Bank bank,
			AccountType type, CountryType country, CapType cap, InvestmentType invType) {
		super(ticker, initPrice, quantity, bank, type);
		this.cap = cap;
		this.country = country;
		this.invType = invType;
		
		countryString = new SimpleStringProperty(getCountryString());
		capString = new SimpleStringProperty(getCapString());
		investmentTypeString = new SimpleStringProperty(getInvestmentTypeString());
	}

	/**
	 * @return the country
	 */
	public CountryType getCountry() {
		return country;
	}

	/**
	 * @return the cap
	 */
	public CapType getCap() {
		return cap;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(CountryType country) {
		this.country = country;
	}

	/**
	 * @param cap the cap to set
	 */
	public void setCap(CapType cap) {
		this.cap = cap;
	}

	/**
	 * Overrides the stringArray of the Long Term Assets
	 */
	@Override
	public String[] stringArray() {
		String[] array = new String[10];
		String[] array2 = super.stringArray();
		
		for(int i = 0; i < array2.length; i++) {
			array[i] = array2[i];
		}
		
		array[7] = getCountryString();
		array[8] = getCapString();
		array[9] = "mutual funds";
		
		return array;
	}

	/**
	 * Returns the string of the country type of stock
	 * @return the country type 
	 */
	public String getCountryString() {
		if(country == CountryType.DOMESTIC) {
			return "Domestic";
		} else {
			return "Foreign";
		}
	}

	/**
	 * Returns the cap string
	 * @return Cap Type in string form
	 */
	public String getCapString() {
		if(cap == CapType.LARGE_CAP) {
			return "Large_Cap";
		} else if(cap == CapType.MID_CAP) {
			return "Mid_Cap";
		} else if (cap == CapType.SMALL_CAP) {
			return "Small_Cap";
		} else {
			return null;
		}
	}
	
	public String getInvestmentTypeString(){
		if(invType == InvestmentType.NA) {
			return "NA";
		} else if(invType == InvestmentType.GROWTH) {
			return "Growth";
		} else if (invType == InvestmentType.VALUE) {
			return "Value";
		} else {
			return null;
		}
	}

	@Override
	public String getAssetType() {
		return "Mutual Fund";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cap == null) ? 0 : cap.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((invType == null) ? 0 : invType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MutualFunds other = (MutualFunds) obj;
		if (cap != other.cap)
			return false;
		if (country != other.country)
			return false;
		if (invType != other.invType)
			return false;
		return true;
	}
	
	
	
	
	

}
