/**
 * 
 */
package data.assets.shortTerm;

import java.text.DecimalFormat;

/**
 * @author Stephen Welsh
 *
 */
public class ShortTermAsset {
	
	public enum AccountType {Cash, Checking, Savings, Money_Market, High_Yield_Savings, Cds};
	
	private String bank;
	
	private String accountName;
	
	private AccountType accountType;
	
	private double totalAmount;

	public ShortTermAsset(String bank, String accountName, AccountType accountType, double totalAmount) {
		this.bank = bank;
		this.accountName = accountName;
		this.accountType = accountType;
		this.totalAmount = totalAmount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getTotalAmount() {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        return "$" + (decimalFormat.format(totalAmount));
	}
	
	public double getAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getAccountTypeString() {
		if(accountType == AccountType.Cash) {
			return "cash";
		} else if(accountType == AccountType.Cds) {
			return "cds";
		} else if(accountType == AccountType.Checking) {
			return "checking";
		} else if(accountType == AccountType.High_Yield_Savings) {
			return "high";
		} else if(accountType == AccountType.Money_Market) {
			return "money";
		} else if(accountType == AccountType.Savings) {
			return "savings";
		} else {
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + ((bank == null) ? 0 : bank.hashCode());
		long temp;
		temp = Double.doubleToLongBits(totalAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ShortTermAsset other = (ShortTermAsset) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (accountType != other.accountType)
			return false;
		if (bank == null) {
			if (other.bank != null)
				return false;
		} else if (!bank.equals(other.bank))
			return false;
		if (Double.doubleToLongBits(totalAmount) != Double.doubleToLongBits(other.totalAmount))
			return false;
		return true;
	}

	
	
	
	

	
}
