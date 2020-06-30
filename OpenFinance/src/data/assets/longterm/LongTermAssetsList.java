package data.assets.longterm;

import java.io.IOException;
import java.util.ArrayList;

import data.assets.longterm.reader.StockReader;

public class LongTermAssetsList{
	
	private ArrayList<LongTermAsset> stocks;
	
	public LongTermAssetsList() {
		stocks = new ArrayList<LongTermAsset>();
	}
	
	public void addAsset(LongTermAsset stock) {
		stocks.add(stock);
	}
	
	public void removeStocks(LongTermAsset stock) {
		for(int i = 0; i < stocks.size(); i++) {
			if(stock.getTicker().toUpperCase().equals(stocks.get(i).getTicker().toUpperCase())) {
				stocks.remove(i);
			}
		}
	}
	
	public LongTermAsset getStock(String ticker) {
		for(int i = 0; i < stocks.size(); i++) {
			if(ticker.toUpperCase().equals(stocks.get(i).getTicker().toUpperCase())) {
				return stocks.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<LongTermAsset> returnStocks(){
		return stocks;
	}
	
	public void update(){
		try {
			StockReader.getSockData(stocks);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
}
