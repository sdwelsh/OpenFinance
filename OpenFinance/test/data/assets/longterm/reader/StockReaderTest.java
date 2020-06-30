package data.assets.longterm.reader;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import data.assets.longterm.LongTermAsset;
import data.assets.longterm.LongTermAssetsList;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import data.assets.longterm.LongTermAsset.AccountType;
import data.assets.longterm.LongTermAsset.Bank;

public class StockReaderTest {

	@Test
	public void test() {
		LongTermAssetsList stocks = new LongTermAssetsList();
		
		LongTermAsset stock = new LongTermAsset("AMD", 20.2, 10, 5, Bank.SCHWAB, AccountType.BROKERAGE);
		
		stocks.addAsset(stock);
		
		ArrayList<LongTermAsset> newStocks = stocks.returnStocks();
		
		assertTrue(newStocks.size()==1);
		assertEquals(newStocks.get(0).getTicker(), "AMD");
		assertTrue(newStocks.get(0).getPrice()==20.2);
		
		try {
			Stock intel = YahooFinance.get("INTC");
			System.out.println(intel.getName());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		newStocks = stocks.returnStocks();
		
		assertTrue(newStocks.size()==1);
		assertEquals(newStocks.get(0).getTicker(), "AMD");
		System.out.println(newStocks.get(0).getPrice());
		
		
	}

}
