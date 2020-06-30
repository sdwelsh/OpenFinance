package data.assets.longterm;

import java.util.ArrayList;

public class Stocks{
	
	private ArrayList<LongTermAsset> stocks;
	
	public Stocks() {
		stocks = new ArrayList<LongTermAsset>();
	}
	
	public void addStock(LongTermAsset stock) {
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
}
