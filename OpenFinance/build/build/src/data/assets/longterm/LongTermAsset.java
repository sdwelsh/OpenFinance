/**
 * 
 */
package data.assets.longterm;

import java.io.IOException;
import java.text.DecimalFormat;

import data.assets.longterm.reader.StockReader;
import javafx.beans.property.SimpleStringProperty;

/**
 * Parent class to ETF's, Mutual Funds, Bond Stocks, and Preferred Stocks
 * @author Stephen Welsh
 *
 */
public class LongTermAsset {
	
	/** The bank Associated with the stock */
	public enum Bank { SCHWAB, MERRIL_LYNCH, VANGUARD, TD, FIDELITY, BANK, ROBINHOOD, ETRADE};
	
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
	
	/** This is the stocks bank */
	private Bank bank;
	
	/** This is the stocks Account type */
	private AccountType type;
	
	SimpleStringProperty tickerString;
	SimpleStringProperty totalPriceString;
	SimpleStringProperty quantityString;
	SimpleStringProperty pricePerShareString;
	SimpleStringProperty bankString;
	SimpleStringProperty accountString;

	/**
	 * Constructs the stock class using all the fields needed to make a stock
	 * @param ticker the ticker of the stock 
	 * @param price the current price of the stock
	 * @param quantity the quantity of stocks 
	 * @param years the years till maturity
	 * @param bank the bank the stock is at
	 * @param type the type of account the stock is in
	 */
	public LongTermAsset(String ticker, double quantity, Bank bank, AccountType type) {
		this.ticker = ticker;
		this.initPrice = 0;
		this.price = refreshPrice();
		this.quantity = quantity;
		this.bank = bank;
		this.type = type;
		
		tickerString = new SimpleStringProperty(ticker);
		totalPriceString = new SimpleStringProperty("$" + (price * quantity));
		quantityString = new SimpleStringProperty(""+ quantity);
		pricePerShareString = new SimpleStringProperty("$" + price);
		bankString = new SimpleStringProperty(getBankName());
		accountString = new SimpleStringProperty(getAccountName());
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
	public LongTermAsset(String ticker, double initPrice, double quantity, Bank bank, AccountType type) {
		this.ticker = ticker;
		this.initPrice = initPrice;
		this.price = refreshPrice();
		this.quantity = quantity;
		this.bank = bank;
		this.type = type;
		
		tickerString = new SimpleStringProperty(ticker);
		totalPriceString = new SimpleStringProperty("$" + (price * quantity));
		quantityString = new SimpleStringProperty(""+ quantity);
		pricePerShareString = new SimpleStringProperty("$" + price);
		bankString = new SimpleStringProperty(getBankName());
		accountString = new SimpleStringProperty(getAccountName());
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
	
	public double refreshPrice() {
		try {
			return price = StockReader.getStockPrice(this.ticker);
		} catch (Exception e) {
			return 0;
		}
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
		asset[4] = "$" + getTotalPrice();
		asset[5] = getBankName();
		asset[6] = getAccountName();
		return asset;
	}

	public double getTotalPrice() {
		return price * quantity;
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
			return "Roth_401K";
		} else if(type == AccountType.ROTH_IRA) {
			return "Roth_IRA";
		} else if(type == AccountType.TAXABLE_ACCOUNT) {
			return "Taxable_Account";
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
			return "Merril_Lynch";
		} else if(bank == Bank.ROBINHOOD) {
			return "Robinhood";
		} else if(bank == Bank.TD) {
			return "TD_Ameritrade";
		} else if(bank == Bank.VANGUARD) {
			return "Vanguard";
		} else if(bank == Bank.ETRADE) {
			return "E-Trade";
		} else if(bank == Bank.SCHWAB) {
			return "Schwab";
		}
		return null;
	}
	
	/**
	 * @return the tickerString
	 */
	public String getTickerString() {
		return tickerString.get();
	}

	/**
	 * @return the totalPriceString
	 */
	public String getTotalPriceString() {
		 DecimalFormat decimalFormat = new DecimalFormat("#.##");
	        decimalFormat.setGroupingUsed(true);
	        decimalFormat.setGroupingSize(3);
		return "$" + (decimalFormat.format(getTotalPrice()));
	}

	/**
	 * @return the quantityString
	 */
	public String getQuantityString() {
		return quantityString.get();
	}

	/**
	 * @return the pricePerShareString
	 */
	public String getPricePerShareString() {
		 DecimalFormat decimalFormat = new DecimalFormat("#.##");
	        decimalFormat.setGroupingUsed(true);
	        decimalFormat.setGroupingSize(3);
		return "$" + (decimalFormat.format(getPrice()));
	}

	/**
	 * @return the bankString
	 */
	public String getBankString() {
		return bankString.get();
	}

	/**
	 * @return the accountString
	 */
	public String getAccountString() {
		return accountString.get();
	}

	/**
	 * @param tickerString the tickerString to set
	 */
	public void setTickerString(SimpleStringProperty tickerString) {
		this.tickerString = tickerString;
	}

	/**
	 * @param totalPriceString the totalPriceString to set
	 */
	public void setTotalPriceString(SimpleStringProperty totalPriceString) {
		this.totalPriceString = totalPriceString;
	}

	/**
	 * @param quantityString the quantityString to set
	 */
	public void setQuantityString(SimpleStringProperty quantityString) {
		this.quantityString = quantityString;
	}

	/**
	 * @param pricePerShareString the pricePerShareString to set
	 */
	public void setPricePerShareString(SimpleStringProperty pricePerShareString) {
		this.pricePerShareString = pricePerShareString;
	}

	/**
	 * @param bankString the bankString to set
	 */
	public void setBankString(SimpleStringProperty bankString) {
		this.bankString = bankString;
	}

	/**
	 * @param accountString the accountString to set
	 */
	public void setAccountString(SimpleStringProperty accountString) {
		this.accountString = accountString;
	}
	
	public String getAssetType() {
		return "Stock";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bank == null) ? 0 : bank.hashCode());
		long temp;
		temp = Double.doubleToLongBits(initPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(quantity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((ticker == null) ? 0 : ticker.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		LongTermAsset other = (LongTermAsset) obj;
		if (bank != other.bank)
			return false;
		if (Double.doubleToLongBits(initPrice) != Double.doubleToLongBits(other.initPrice))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (Double.doubleToLongBits(quantity) != Double.doubleToLongBits(other.quantity))
			return false;
		if (ticker == null) {
			if (other.ticker != null)
				return false;
		} else if (!ticker.equals(other.ticker))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
	
}
