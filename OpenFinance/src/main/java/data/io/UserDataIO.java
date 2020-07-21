package data.io;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import application.users.User;
import data.assets.longterm.Asset;
import data.assets.longterm.ETF;
import data.assets.longterm.ETF.CapType;
import data.assets.longterm.ETF.CountryType;
import data.assets.longterm.ETF.InvestmentType;
import data.assets.longterm.LongTermAsset;
import data.assets.longterm.LongTermAssetsList;
import data.assets.longterm.MutualFunds;
import data.assets.shortTerm.ShortTermAsset;
import data.liabilities.Liability;
import data.assets.longterm.LongTermAsset.AccountType;
import data.assets.longterm.LongTermAsset.Bank;

public class UserDataIO {
	
	
	private static final String FILENAME = System.getProperty("user.home") + "/OpenFinance/ProgramFiles/Users/";
	
	public static void readUserData(User user, String key, String transformation) {
		File file = new File(FILENAME + user.getId() + ".enc");
		System.out.println(file.getAbsolutePath());
		
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		FileEncrypterDecrypter decrypt = new FileEncrypterDecrypter(key, transformation);
		
		Scanner s = new Scanner(decrypt.decrypt(FILENAME + user.getId() + ".enc"));
		
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
					String accountName = line.next();
					double dividends = Double.parseDouble(line.next());
					boolean reinvest = line.nextBoolean();
					
					LongTermAsset asset = new LongTermAsset(ticker, Double.parseDouble(initPrice), 
							Double.parseDouble(quantity), getBank(bank), getAccount(account), accountName, dividends, reinvest);
					
					user.addLongTermAsset(asset);
				} else if(type.equals("ETF")) {
					String ticker = line.next();
					String quantity = line.next();
					String initPrice = line.next();
					String bank = line.next();
					String account = line.next();
					String accountName = line.next();
					double dividends = Double.parseDouble(line.next());
					boolean reinvest = line.nextBoolean();
					String cap = line.next();
					String invType = line.next();
					String country = line.next();
					
					
					
					LongTermAsset asset = new ETF(ticker, Double.parseDouble(initPrice), Double.parseDouble(quantity), 
							getBank(bank), getAccount(account), accountName, dividends, reinvest, getCountry(country), getCap(cap), getInvestmentType(invType));
					
					user.addLongTermAsset(asset);
				} else if(type.equals("Mutual Fund")){
					String ticker = line.next();
					String quantity = line.next();
					String initPrice = line.next();
					String bank = line.next();
					String account = line.next();
					String accountName = line.next();
					double dividends = Double.parseDouble(line.next());
					boolean reinvest = line.nextBoolean();
					String cap = line.next();
					String invType = line.next();
					String country = line.next();
					
					
					
					LongTermAsset asset = new MutualFunds(ticker, Double.parseDouble(initPrice), Double.parseDouble(quantity), 
							getBank(bank), getAccount(account), accountName, dividends, reinvest, getCountry(country), getCap(cap), getInvestmentType(invType));
					
					user.addLongTermAsset(asset);
				} else if(type.equals("short")) {
					String bank = line.next();
					String accountName = line.next();
					String totalAmount = line.next();
					String accountType = line.next();
					
					ShortTermAsset asset = new ShortTermAsset(bank, accountName, getAccountType(accountType), Double.parseDouble(totalAmount));
					user.returnShortTermAssets().addShortTermAsset(asset);
				} else {
					String name = line.next();
					double value = Double.parseDouble(line.next());
					
					Asset asset = new Asset(name, value);
					user.getLongTermAssets().addAsset(asset);
				}
				
			} else if(lineStart.equals("-")){
				String name = line.next();
				double totalAmount = Double.parseDouble(line.next());
				int yearsToMaturity = Integer.parseInt(line.next());
				int creationYear = Integer.parseInt(line.next());
				
				Liability liability = new Liability(name, totalAmount, yearsToMaturity, creationYear);
				user.returnLiabilities().addLiability(liability);
			}
			
			line.close();
			
		}
		s.close();
		
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
					asset.getAccountString() + "," +
					asset.getAccountNameString() + "," +
					asset.getDividends() + "," +
					asset.isReinvestDividends() 
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
					asset.getAccountNameString() + "," +
					asset.getDividends() + "," +
					asset.isReinvestDividends() + "," +
					asset.getCapString() + "," +
					asset.getInvestmentTypeString() + "," +
					asset.getCountryString()
					+ " | ";		
		}
		
		ArrayList<LongTermAsset> mutualFunds = list.returnMutualFunds();
		
		for(int i = 0; i < mutualFunds.size(); i++) {
			MutualFunds asset = (MutualFunds) mutualFunds.get(i);
			
			content += "+," +
					asset.getAssetType() + "," +
					asset.getTicker() + "," +
					asset.getQuantity() + "," +
					asset.getInitPrice() + "," +
					asset.getBankString() + "," +
					asset.getAccountString() + "," +
					asset.getAccountNameString() + "," +
					asset.getDividends() + "," +
					asset.isReinvestDividends() + "," +
					asset.getCapString() + "," +
					asset.getInvestmentTypeString() + "," +
					asset.getCountryString()
					+ " | ";		
		}
		
		ArrayList<Asset> assets = list.returnAssets();
		
		for(Asset asset : assets) {
			content += "+," + "asset," +
				asset.getName() + "," +
				asset.getValue() +
				" | ";
		}
		
		ArrayList<ShortTermAsset> shortTermAssets = user.returnShortTermAssets().returnShortTermAssets();
		
		for(int i = 0; i < shortTermAssets.size(); i++) {
			ShortTermAsset asset = shortTermAssets.get(i);
			
			content += "+," + "short," +
					asset.getBank() + "," +
					asset.getAccountName() + "," +
					asset.getAmount() + "," +
					asset.getAccountTypeString() + "," +
					" | ";		
		}
		
		ArrayList<Liability> liabilities = user.returnLiabilities().getLiabilities();
		
		for(Liability l : liabilities) {
			content += "-," +
					l.getName() + "," +
					l.getTotalAmount() + "," +
					l.getYearsToMaturity() + "," +
					l.getCreationYear() + "," +
					" | ";
		}
		
		File file = new File(FILENAME + user.getId() + ".enc");
		
		if(!file.exists()) {
			file.mkdir();
		}
		
		fileEncrypt.encrypt(content, FILENAME + user.getId() + ".enc");
		
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
		}  else if(cap.equals("NA")) {
			return CapType.NA;
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
	
	private static data.assets.shortTerm.ShortTermAsset.AccountType getAccountType(String accountType) {
		if(accountType.equals("cash")) {
			return data.assets.shortTerm.ShortTermAsset.AccountType.Cash;
		} else if(accountType.equals("cds")) {
			return data.assets.shortTerm.ShortTermAsset.AccountType.Cds;
		} else if(accountType.equals("checking")) {
			return data.assets.shortTerm.ShortTermAsset.AccountType.Checking;
		} else if(accountType.equals("high")) {
			return data.assets.shortTerm.ShortTermAsset.AccountType.High_Yield_Savings;
		} else if(accountType.equals("money")) {
			return data.assets.shortTerm.ShortTermAsset.AccountType.Money_Market;
		} else if(accountType.equals("savings")) {
			return data.assets.shortTerm.ShortTermAsset.AccountType.Savings;
		} else {
			return null;
		}
	}
}
