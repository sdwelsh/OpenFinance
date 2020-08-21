package data.assets.longterm;

import java.io.IOException;
import java.util.ArrayList;

import data.assets.longterm.reader.StockReader;
/**
 * List class that stores a list of the long term assets per user
 * @author Stephen Welsh
 *
 */
public class LongTermAssetsList{
	
	/** The array list of long term assets */
	private ArrayList<LongTermAsset> stocks;
	
	private ArrayList<Asset> assets;
	
	/**
	 * Constructs the Long Term Assets list.
	 */
	public LongTermAssetsList() {
		stocks = new ArrayList<LongTermAsset>();
		assets = new ArrayList<Asset>();
	}
	
	/**
	 * Adds an asset to the long term assets list.
	 * @param stock the long term asset being added
	 */
	public void addAsset(LongTermAsset stock) {
		stocks.add(stock);
	}
	
	public void addAsset(Asset asset) {
		assets.add(asset);
	}
	
	/**
	 * Removes a long term asset from the long term assets list
	 * @param stock The stock the user wishs's to remove from their assets
	 */
	public void removeStocks(LongTermAsset stock) {
		for(int i = 0; i < stocks.size(); i++) {
			if(stock.equals(stocks.get(i))) {
				stocks.remove(i);
			}
		}
	}

	
	/**
	 * Returns the particular stock from the list that has the requested ticker
	 * @param ticker the ticker of the stock
	 * @return the stock from the list
	 */
	public LongTermAsset getStock(String ticker) {
		for(int i = 0; i < stocks.size(); i++) {
			if(ticker.toUpperCase().equals(stocks.get(i).getTicker().toUpperCase())) {
				return stocks.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns a list of LongTerm Assets Stocks
	 * @return ArrayList of LongTermAssets
	 */
	public ArrayList<LongTermAsset> returnStocks(){
		ArrayList<LongTermAsset> stocksList = new ArrayList<LongTermAsset>();
		
		for(int i = 0; i < stocks.size(); i++) {
			LongTermAsset stock = stocks.get(i);
			if(stock.getAssetType().equals("Stock")) {
				stocksList.add(stock);
			}
		}
		
		return stocksList;
	}
	
	/**
	 * Returns a list of LongTerm ETFs
	 * @return ArrayList of LongTermAssets
	 */
	public ArrayList<LongTermAsset> returnETFs(){
		ArrayList<LongTermAsset> stocksList = new ArrayList<LongTermAsset>();
		
		for(int i = 0; i < stocks.size(); i++) {
			LongTermAsset stock = stocks.get(i);
			if(stock.getAssetType().equals("ETF")) {
				stocksList.add(stock);
			}
		}
		
		return stocksList;
	}
	
	/**
	 * Returns a list of LongTerm Assets MutualFunds
	 * @return ArrayList of LongTermAssets
	 */
	public ArrayList<LongTermAsset> returnMutualFunds(){
		ArrayList<LongTermAsset> stocksList = new ArrayList<LongTermAsset>();
		
		for(int i = 0; i < stocks.size(); i++) {
			LongTermAsset stock = stocks.get(i);
			if(stock.getAssetType().equals("Mutual Fund")) {
				stocksList.add(stock);
			}
		}
		
		return stocksList;
	}
	
	public ArrayList<LongTermAsset> returnLongTermAssets(){
		return stocks;
	}
	
	public ArrayList<Asset> returnAssets(){
		return assets;
	}
	
	/**
	 * Updates the price of the stocks by getting stock data from the Yahoo API
	 */
	public void update(){
		try {
			StockReader.getSockData(stocks);
		} catch (IOException e) {
			System.out.println("No Stock Files");
		}
	}

	public double getLongTermAssetsTotal() {
		double total = 0;
		for(int i = 0; i < stocks.size(); i++) {
			total += stocks.get(i).getTotalPrice();
		}
		for(Asset asset : assets) {
			total += asset.getValue();
		}
		
		return total;
	}
	
	public double getStocksTotal() {
		double total = 0;
		for(LongTermAsset asset : stocks) {
			if(asset.getAssetType().equals("Stock")) {
				total += asset.getTotalPrice();
			}
			
		}
		
		return total;
	}
	
	public double getETFTotal() {
		double total = 0;
		for(LongTermAsset asset : stocks) {
			if(asset.getAssetType().equals("ETF")) {
				total += asset.getTotalPrice();
			}
			
		}
		
		return total;
	}
	
	public double getMutualTotal() {
		double total = 0;
		for(LongTermAsset asset : stocks) {
			if(asset.getAssetType().equals("Mutual Fund")) {
				total += asset.getTotalPrice();
			}
			
		}
		
		return total;
	}
	
	public double getAssetTotal() {
		double total = 0;
		for(Asset asset : assets) {
			total += asset.getValue();
		}
		return total;
	}
	
	public double getStockDayChange() {
		double total = 0;
		for(LongTermAsset asset : stocks) {
			if(asset.getAssetType().contentEquals("Stock")) {
				total += asset.getMovement() * asset.getQuantity();
			}
		}
		return total;
	}
	
	public double getETFDayChange() {
		double total = 0;
		for(LongTermAsset asset : stocks) {
			if(asset.getAssetType().contentEquals("ETF")) {
				total += asset.getMovement() * asset.getQuantity();
			}
		}
		return total;
	}
	
	public double getMutualFundDayChange() {
		double total = 0;
		for(LongTermAsset asset : stocks) {
			if(asset.getAssetType().contentEquals("Mutual Fund")) {
				total += asset.getMovement() * asset.getQuantity();
			}
		}
		return total;
	}
}
