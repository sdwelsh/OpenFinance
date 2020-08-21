/**
 * 
 */
package data.assets.longterm.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import data.assets.longterm.LongTermAsset;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

/**
 * Reads Stock data from the Yahoo API
 * @author Stephen Welsh
 *
 */
public class StockReader {

	/**
	 * Static method takes in a list of stocks. The method then creates a list of stock tickers and pings the 
	 * Yahoo API for the stocks current prices.
	 * @param stockList the list of stocks being updated
	 * @throws IOException if the API is unable to read the stock data
	 */
	public static void getSockData(ArrayList<LongTermAsset> stockList) throws IOException {
		String[] stocks;
		
		stocks = new String[stockList.size()];
		
		for(int i = 0; i < stockList.size(); i++) {
			stocks[i] = stockList.get(i).getTicker();
		}
		
		Map<String, Stock> yahooStocks = YahooFinance.get(stocks, true); // single request
		
		
		for(int i = 0; i < stocks.length; i++) {
			LongTermAsset stock = stockList.get(i);
			Stock yahooStock = yahooStocks.get(stock.getTicker());
			stock.setPrice(yahooStock.getQuote().getPrice().doubleValue());
			if(!stock.getAssetType().equals("Mutual Fund")) {
				stock.setOpen(yahooStock.getQuote().getPreviousClose().doubleValue());
			} else {
				stock.setOpen(yahooStock.getQuote().getPreviousClose().doubleValue());
			}
			stock.setHistoricalData(yahooStock.getHistory(Interval.MONTHLY));
			stock.setName(yahooStock.getName());
			
		}
		
		
	}
	
	public static boolean checkStock(String ticker) {
		try {
			Stock check = YahooFinance.get(ticker);
			if(check==null) {
				return false;
			}
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public static double getStockPrice(String ticker) throws IOException {
		return YahooFinance.get(ticker).getQuote().getPrice().doubleValue();
	}
}