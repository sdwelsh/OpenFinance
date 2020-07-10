package data.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.crypto.SecretKey;

import application.users.User;
import data.assets.longterm.ETF;
import data.assets.longterm.ETF.CapType;
import data.assets.longterm.ETF.CountryType;
import data.assets.longterm.ETF.InvestmentType;
import data.assets.longterm.LongTermAsset;
import data.assets.longterm.LongTermAssetsList;
import data.assets.longterm.MutualFunds;
import data.assets.longterm.LongTermAsset.AccountType;
import data.assets.longterm.LongTermAsset.Bank;

public class UserDataIO {
	
	
	public static void readUserData(User user, String key, String transformation) {
		FileEncrypterDecrypter decrypt = new FileEncrypterDecrypter(key, transformation);
		
		Scanner s = new Scanner(decrypt.decrypt("test-files/" + user.getId() + ".enc"));
		
		s.useDelimiter(" \\| ");
		
		while(s.hasNext()) {
			Scanner line = new Scanner(s.next());
			
			line.useDelimiter(",");
			
			String lineStart = line.next();
			
			if(lineStart.equals("+")) {
				String type = line.next();
				if(type.equals("Stock")) {
					String ticker = line.next();
					String quantity = line.next();
					String initPrice = line.next();
					String bank = line.next();
					String account = line.next();
					
					LongTermAsset asset = new LongTermAsset(ticker, Double.parseDouble(initPrice), 
							Double.parseDouble(quantity), getBank(bank), getAccount(account));
					
					user.addLongTermAsset(asset);
				} else if(type.equals("ETF")) {
					String ticker = line.next();
					String quantity = line.next();
					String initPrice = line.next();
					String bank = line.next();
					String account = line.next();
					String cap = line.next();
					String invType = line.next();
					String country = line.next();
					
					
					
					LongTermAsset asset = new ETF(ticker, Double.parseDouble(initPrice), Double.parseDouble(quantity), 
							getBank(bank), getAccount(account), getCountry(country), getCap(cap), getInvestmentType(invType));
					
					user.addLongTermAsset(asset);
				} else {
					String ticker = line.next();
					String quantity = line.next();
					String initPrice = line.next();
					String bank = line.next();
					String account = line.next();
					String country = line.next();
					String cap = line.next();
					
					LongTermAsset asset = new MutualFunds(ticker, Double.parseDouble(initPrice), Double.parseDouble(quantity), 
							getBank(bank), getAccount(account), getCountry(country), getCap(cap));
					
					user.addLongTermAsset(asset);
				}
				
			}
			
			line.close();
			
		}
		s.close();
		
		File file = new File("test-files/" + user.getId() + ".enc");
		file.delete();
		
	}

	public static void writeUserData(User user, String key, String transformation) throws FileNotFoundException {
		
		String content = "";
		
		FileEncrypterDecrypter fileEncrypt = new FileEncrypterDecrypter(key, transformation);
		
		LongTermAssetsList list = user.getLongTermAssets();
		
		ArrayList<LongTermAsset> stocks = list.returnStocks();
		
		for(int i = 0; i < stocks.size(); i++) {
			LongTermAsset asset = stocks.get(i);
			
			content += "+," +
					asset.getAssetType() + "," +
					asset.getTicker() + "," +
					asset.getQuantity() + "," +
					asset.getInitPrice() + "," +
					asset.getBankString() + "," +
					asset.getAccountString()
					+ " | ";		
		}
		
		ArrayList<LongTermAsset> etfs = list.returnETFs();
		
		for(int i = 0; i < etfs.size(); i++) {
			ETF asset = (ETF) etfs.get(i);
			
			content += "+," +
					asset.getAssetType() + "," +
					asset.getTicker() + "," +
					asset.getQuantity() + "," +
					asset.getInitPrice() + "," +
					asset.getBankString() + "," +
					asset.getAccountString() + "," +
					asset.getCapString() + "," +
					asset.getInvestmentTypeString() + "," +
					asset.getCountryString()
					+ " | ";		
		}
		
		fileEncrypt.encrypt(content, "test-files/" + user.getId() + ".enc");
		
	}
	
	private static AccountType getAccount(String accountType) {
		if(accountType.equals("Brokerage")) {
			return AccountType.BROKERAGE;
		} else if(accountType.equals("Roth_401K")) {
			return AccountType.ROTH401K;
		} else if(accountType.equals("Roth_IRA")) {
			return AccountType.ROTH_IRA;
		} else if(accountType.equals("IRA")) {
			return AccountType.IRA;
		} else if(accountType.equals("401K")) {
			return AccountType._401K;
		}else if(accountType.equals("Taxable_Account")) {
			return AccountType.TAXABLE_ACCOUNT;
		} else {
			return null;
		}
	}

	private static Bank getBank(String investmentBank) {
		if(investmentBank.equals("Schwab")) {
			return Bank.SCHWAB;
		} else if(investmentBank.equals("Merril_Lynch")) {
			return Bank.MERRIL_LYNCH;
		} else if(investmentBank.equals("Vanguard")) {
			return Bank.VANGUARD;
		} else if(investmentBank.equals("TD_Ameritrade")) {
			return Bank.TD;
		} else if(investmentBank.equals("Fidelity")) {
			return Bank.FIDELITY;
		} else if(investmentBank.equals("Bank")) {
			return Bank.BANK;
		} else if(investmentBank.equals("Robinhood")) {
			return Bank.ROBINHOOD;
		} else if(investmentBank.equals("E-Trade")) {
			return Bank.ETRADE;
		}else {
			return null;
		}
	}
	
	private static CountryType getCountry(String country) {
		if(country.equals("Domestic")) {
			return CountryType.DOMESTIC;
		} else if(country.equals("Foreign")){
			return CountryType.FOREIGN;
		} else {
			return null;
		}
	}
	
	private static CapType getCap(String cap) {
		if(cap.equals("Small_Cap")) {
			return CapType.SMALL_CAP;
		} else if(cap.equals("Mid_Cap")) {
			return CapType.MID_CAP;
		}  else if(cap.equals("Large_Cap")) {
			return CapType.LARGE_CAP;
		}  else {
			return null;
		}
	}
	
	private static InvestmentType getInvestmentType(String invType) {
		if(invType.equals("NA")) {
			return InvestmentType.NA;
		} else if(invType.equals("Growth")) {
			return InvestmentType.GROWTH;
		}  else if(invType.equals("Value")) {
			return InvestmentType.GROWTH;
		}  else {
			return null;
		}
	}
}
