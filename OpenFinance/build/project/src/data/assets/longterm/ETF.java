/**
 * 
 */
package data.assets.longterm;

import data.assets.longterm.ETF.CapType;
import data.assets.longterm.ETF.CountryType;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Stephen Welsh
 *
 */
public class ETF extends LongTermAsset{
	
	/** The type of country that the ETF is for */
	public enum CountryType {DOMESTIC, FOREIGN};
	
	/** The capacity of the ETF */
	public enum CapType {SMALL_CAP, MID_CAP, LARGE_CAP };
	
	public enum InvestmentType {NA, GROWTH, VALUE};
	
	/** The type of country market of the ETF*/
	private CountryType country;
	
	/** The cap of the ETF */
	private CapType cap;
	
	private InvestmentType investmentType;
	
	SimpleStringProperty countryString;
	SimpleStringProperty capString;
	SimpleStringProperty investmentTypeString;

	/**
	 * Constructs the ETF class passing superclass variables to the LongTermAsset super class
	 * @param ticker
	 * @param price
	 * @param initPrice
	 * @param quantity
	 * @param years
	 * @param bank
	 * @param type
	 */
	public ETF(String ticker, double initPrice, double quantity, 
			Bank bank, AccountType type, CountryType country, CapType cap, InvestmentType investmentType) {
		super(ticker, initPrice, quantity, bank, type);
		this.country = country;
		this.cap = cap;
		this.investmentType = investmentType;
		
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

	public InvestmentType getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(InvestmentType investmentType) {
		this.investmentType = investmentType;
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
	 * Overrides the LongTerm Assets string array adding the country and capacity 
	 * to the string array being passed
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
		array[9] = "etf";
		
		return array;
	}

	/**
	 * Returns the country string to the string array
	 * @return string of Country Type
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
		if(investmentType == InvestmentType.NA) {
			return "NA";
		} else if(investmentType == InvestmentType.GROWTH) {
			return "Growth";
		} else if (investmentType == InvestmentType.VALUE) {
			return "Value";
		} else {
			return null;
		}
	}
	
	@Override
	public String getAssetType() {
		return "ETF";
	}

	public void setCountryString(SimpleStringProperty countryString) {
		this.countryString = countryString;
	}

	public void setCapString(SimpleStringProperty capString) {
		this.capString = capString;
	}

	public void setInvestmentTypeString(SimpleStringProperty investmentTypeString) {
		this.investmentTypeString = investmentTypeString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cap == null) ? 0 : cap.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((investmentType == null) ? 0 : investmentType.hashCode());
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
		ETF other = (ETF) obj;
		if (cap != other.cap)
			return false;
		if (country != other.country)
			return false;
		if (investmentType != other.investmentType)
			return false;
		return true;
	}
	
	
	
	
	
	
}
