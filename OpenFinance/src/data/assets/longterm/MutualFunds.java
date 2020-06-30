/**
 * 
 */
package data.assets.longterm;

import data.assets.longterm.ETF.CapType;
import data.assets.longterm.ETF.CountryType;

/**
 * @author Stephen Welsh
 *
 */
public class MutualFunds extends LongTermAsset {
	
	/** The type of country market of the ETF*/
	private CountryType country;
	
	/** The cap of the ETF */
	private CapType cap;

	/**
	 * Constructor of mutualFunds excluding the initial price of the fund
	 * @param ticker
	 * @param price
	 * @param quantity
	 * @param years
	 * @param bank
	 * @param type
	 */
	public MutualFunds(String ticker, double price, double quantity, int years, Bank bank, AccountType type, CountryType country, CapType cap) {
		super(ticker, price, quantity, years, bank, type);
		this.cap = cap;
		this.country = country;
	}

	/**
	 * Constructor of mutual find with initial price
	 * @param ticker
	 * @param price
	 * @param initPrice
	 * @param quantity
	 * @param years
	 * @param bank
	 * @param type
	 */
	public MutualFunds(String ticker, double price, double initPrice, double quantity, int years, Bank bank,
			AccountType type, CountryType country, CapType cap) {
		super(ticker, price, initPrice, quantity, years, bank, type);
		this.cap = cap;
		this.country = country;
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

	private String getCountryString() {
		if(country == CountryType.DOMESTIC) {
			return "Domestic";
		} else {
			return "Foreign";
		}
	}

	private String getCapString() {
		if(cap == CapType.LARGE_CAP_GROWTH) {
			return "Large Cap Growth";
		} else if(cap == CapType.LARGE_CAP_VALUE) {
			return "Large Cap Value";
		} else if(cap == CapType.MID_CAP) {
			return "Mid Cap";
		} else if (cap == CapType.SMALL_CAP) {
			return "Small Cap";
		} else {
			return null;
		}
	}
	
	

}
