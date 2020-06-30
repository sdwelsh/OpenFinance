package data.assets.longterm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import data.assets.longterm.LongTermAsset.AccountType;
import data.assets.longterm.LongTermAsset.Bank;

public class StocksTest {

	@Test
	public void test() {
		Stocks stocks = new Stocks();
		
		LongTermAsset stock = new LongTermAsset("AMD", 20.2, 10, 5, Bank.SCHWAB, AccountType.BROKERAGE);
		
		stocks.addStock(stock);
		
		ArrayList<LongTermAsset> newStocks = stocks.returnStocks();
		
		assertTrue(newStocks.size()==1);
		assertEquals(newStocks.get(0).getTicker(), "AMD");
	}

}
