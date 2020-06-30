package data.assets.longterm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import data.assets.longterm.LongTermAsset.AccountType;
import data.assets.longterm.LongTermAsset.Bank;

public class LongTermAssetsListTest {

	@Test
	public void test() {
		LongTermAssetsList stocks = new LongTermAssetsList();
		
		LongTermAsset stock = new LongTermAsset("AMD", 20.2, 10, 5, Bank.SCHWAB, AccountType.BROKERAGE);
		
		stocks.addAsset(stock);
		
		ArrayList<LongTermAsset> newStocks = stocks.returnStocks();
		
		assertTrue(newStocks.size()==1);
		assertEquals(newStocks.get(0).getTicker(), "AMD");
		
		stocks.update();
		
		newStocks = stocks.returnStocks();
		
		System.out.println(stocks.getStock("AMD").getPrice());
		
		LongTermAsset apple = new LongTermAsset("AAPL", 20.2, 10, 5, Bank.SCHWAB, AccountType.BROKERAGE);
		LongTermAsset zoom = new LongTermAsset("ZM", 20.2, 10, 5, Bank.SCHWAB, AccountType.BROKERAGE);
		LongTermAsset space = new LongTermAsset("SPCE", 20.2, 10, 5, Bank.SCHWAB, AccountType.BROKERAGE);
		
		stocks.addAsset(apple);
		stocks.addAsset(zoom);
		stocks.addAsset(space);
		
		stocks.update();
		
		newStocks = stocks.returnStocks();
		
		for(int i = 0; i < newStocks.size(); i++) {
			System.out.println(newStocks.get(i).getPrice());
		}
		
	}

}
