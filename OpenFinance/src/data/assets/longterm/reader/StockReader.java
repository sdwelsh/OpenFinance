/**
 * 
 */
package data.assets.longterm.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import data.assets.longterm.LongTermAsset;
import data.assets.longterm.LongTermAssetsList;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * @author Stephen Welsh
 *
 */
public class StockReader {

	public static void getSockData(ArrayList<LongTermAsset> stockList) throws IOException {
		String[] stocks;
		
		stocks = new String[stockList.size()];
		
		for(int i = 0; i < stockList.size(); i++) {
			stocks[i] = stockList.get(i).getTicker();
		}
		
		Map<String, Stock> yahooStocks = YahooFinance.get(stocks); // single request
		
		
		for(int i = 0; i < yahooStocks.size(); i++) {
			stockList.get(i).setPrice(yahooStocks.get(stockList.get(i).getTicker()).getQuote().getPrice().doubleValue());
		}
		
		
	}
}