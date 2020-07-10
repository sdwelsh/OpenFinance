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
	 * @param ticker the Stock Ticker
	 * @param price the total price of the mutual fund
	 * @param quantity the total amount of stocks owned
	 * @param years the years till maturity
	 * @param bank bank the stock is at
	 * @param type Cap Type of the stock
	 */
	public MutualFunds(String ticker, double quantity, int years, Bank bank, AccountType type, CountryType country, CapType cap) {
		super(ticker, quantity, years, bank, type);
		this.cap = cap;
		this.country = country;
	}

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
			AccountType type, CountryType country, CapType cap) {
		super(ticker, initPrice, quantity, bank, type);
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
	private String getCountryString() {
		if(country == CountryType.DOMESTIC) {
			return "Domestic";
		} else {
			return "Foreign";
		}
	}

	/**
	 * Returns the capacity String of the stocks cap type
	 * @return the cap type string
	 */
	private String getCapString() {
		if(cap == CapType.LARGE_CAP) {
			return "Large Cap Growth";
		} else if(cap == CapType.MID_CAP) {
			return "Mid Cap";
		} else if (cap == CapType.SMALL_CAP) {
			return "Small Cap";
		} else {
			return null;
		}
	}

	@Override
	public String getAssetType() {
		return "Mutual Fund";
	}
	
	
	

}
