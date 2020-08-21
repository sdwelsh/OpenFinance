package data.assets.longterm.reader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

public class IntraDayReader {
	
	private  TimeSeriesResponse response;
	private boolean success;
	
	public IntraDayReader(String ticker) {
		AlphaVantage.api()
	    .timeSeries()
	    .intraday()
	    .forSymbol(ticker)
	    .interval(Interval.SIXTY_MIN)
	    .outputSize(OutputSize.FULL)
	    .onSuccess(e->handleSuccess((TimeSeriesResponse) e))
	    .onFailure(e->handleFailure(e))
	    .fetch();
		success = false;
		
	}

	public ArrayList<Double> getIntraDayData() {
		ArrayList<Double> daily = new ArrayList<Double>();
		for(StockUnit data : response.getStockUnits()) {
			String date[] = data.getDate().split(" ");
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        Date today = Calendar.getInstance().getTime();
			String calendarString = df.format(today);
			
			System.out.println(data.getDate());
			System.out.println(calendarString);
			
			if(date[0].equals(calendarString)) {
				daily.add(data.getClose());
			}
			
		}
		return daily;
	}
	
	public void handleSuccess(TimeSeriesResponse response) {
	    this.response = response;
	    success = true;
	    System.out.println("Success");
	}
	public void handleFailure(AlphaVantageException error) {
	   throw new IllegalArgumentException();
	}
	
	public boolean isSuccess() {
		return success;
	}

	
}
