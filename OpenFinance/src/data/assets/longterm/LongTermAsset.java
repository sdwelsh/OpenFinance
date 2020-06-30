/**
 * 
 */
package data.assets.longterm;

/**
 * Parent class to ETF's, Mutual Funds, Bond Stocks, and Preferred Stocks
 * @author Stephen Welsh
 *
 */
public class LongTermAsset {
	
	/** The bank Associated with the stock */
	public enum Bank { SCHWAB, MERRIL_LYNCH, VANGUARD, TD, FIDELITY, BANK, ROBINHOOD};
	
	/** The account types */
	public enum AccountType {BROKERAGE, ROTH401K, _401K, ROTH_IRA, IRA, TAXABLE_ACCOUNT};
	
	/**  The Ticker symbol of the stock */
	private String ticker;
	
	/** The price per share of the stock */
	private double price;
	
	/** The Initial Price of the stock */
	private double initPrice;
	
	/** The Quantity of the Stock owned */
	private double quantity;
	
	/**  The Years to matrutiry for the stock */
	private int years;
	
	/** This is the stocks bank */
	private Bank bank;
	
	/** This is the stocks Account type */
	private AccountType type;

	/**
	 * Constructs the stock class using all the fields needed to make a stock
	 * @param ticker the ticker of the stock 
	 * @param price the current price of the stock
	 * @param quantity the quantity of stocks 
	 * @param years the years till maturity
	 * @param bank the bank the stock is at
	 * @param type the type of account the stock is in
	 */
	public LongTermAsset(String ticker, double price, double quantity, int years, Bank bank, AccountType type) {
		this.ticker = ticker;
		this.price = price;
		this.initPrice = 0;
		this.quantity = quantity;
		this.years = years;
		this.bank = bank;
		this.type = type;
	}
	
	/**
	 * Overrides the original constructor adding initial price of the stock.
	 * Constructs the stock class using all the fields needed to make a stock
	 * @param ticker the ticker of the stock 
	 * @param price the current price of the stock
	 * @param quantity the quantity of stocks 
	 * @param years the years till maturity
	 * @param bank the bank the stock is at
	 * @param type the type of account the stock is in
	 */
	public LongTermAsset(String ticker, double price, double initPrice, double quantity, int years, Bank bank, AccountType type) {
		this.ticker = ticker;
		this.price = price;
		this.initPrice = initPrice;
		this.quantity = quantity;
		this.years = years;
		this.bank = bank;
		this.type = type;
	}

	/**
	 * @return the ticker
	 */
	public String getTicker() {
		return ticker;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return the initPrice
	 */
	public double getInitPrice() {
		return initPrice;
	}

	/**
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @return the years
	 */
	public int getYears() {
		return years;
	}

	/**
	 * @return the bank
	 */
	public Bank getBank() {
		return bank;
	}

	/**
	 * @return the type
	 */
	public AccountType getType() {
		return type;
	}

	/**
	 * @param ticker the ticker to set
	 */
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @param initPrice the initPrice to set
	 */
	public void setInitPrice(double initPrice) {
		this.initPrice = initPrice;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @param years the years to set
	 */
	public void setYears(int years) {
		this.years = years;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(AccountType type) {
		this.type = type;
	}

	/**
	 * Returns a string array of the Long Term asset class
	 * @return string array of assets values
	 */
	public String[] stringArray() {
		String[] asset = new String[10];
		asset[0] = ticker;
		asset[1] = "$" + price;
		if(initPrice == 0) {
			asset[2] = "-";
		} else {
			asset[2] = "$" + initPrice;
		}
		asset[3] = quantity + ""; 
		asset[4] = years + "";
		asset[5] = getBankName();
		asset[6] = getAccountName();
		return asset;
	}

	/**
	 * Returns a string name of the account names
	 * @return account name string
	 */
	private String getAccountName() {
		if(type == AccountType._401K) {
			return "401K";
		} else if(type == AccountType.BROKERAGE){
			return "Brokerage";
		} else if(type == AccountType.IRA) {
			return "IRA";
		} else if(type == AccountType.ROTH401K) {
			return "Roth 401k";
		} else if(type == AccountType.ROTH_IRA) {
			return "Roth IRA";
		} else if(type == AccountType.TAXABLE_ACCOUNT) {
			return "Taxable Account";
		}
		return null;
	}

	/**
	 * Returns the string name of the bank 
	 * @return the bank name
	 */
	private String getBankName() {
		if(bank == Bank.BANK) {
			return "Bank";
		} else if(bank == Bank.FIDELITY){
			return "Fidelity";
		} else if(bank == Bank.MERRIL_LYNCH) {
			return "Merril Lynch";
		} else if(bank == Bank.ROBINHOOD) {
			return "Robinhood";
		} else if(bank == Bank.TD) {
			return "TD Ameritrade";
		} else if(bank == Bank.VANGUARD) {
			return "Vanguard";
		}
		return null;
	}
	
}
