package application.stock;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import application.Main;
import application.MainController;
import application.manager.Manager;
import application.users.User;
import data.assets.longterm.ETF;
import data.assets.longterm.LongTermAsset;
import data.assets.longterm.MutualFunds;
import data.assets.longterm.reader.IntraDayReader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockQuote;
import yahoofinance.quotes.stock.StockStats;

public class StockViewController extends BorderPane {
	
	
	private Stage primaryStage;
	
	@FXML private Label title;
	@FXML private Label bank;

	@FXML private Label currentPrice;
	@FXML private Label open;
	@FXML private Label high;
	@FXML private Label low;
	@FXML private Label yearHigh;
	@FXML private Label yearLow;
	@FXML private Label movingAverage50;
	@FXML private Label movingAverage200;
	@FXML private Label pe;
	@FXML private Label capType;
	@FXML private Label countryType;
	
	@FXML private Button ticker;
	
	@FXML private AreaChart<String, Number> chart;
	private Series<String, Number> aSeries;
	@FXML private CategoryAxis categoryAxis;
	@FXML private NumberAxis numberAxis;
	
	private LongTermAsset asset;
	private Stock thisStockData;
	private double currentPriceDouble;

	public StockViewController(LongTermAsset asset) {
		primaryStage = new Stage();
		primaryStage.setTitle(asset.getTicker());
		
		this.asset = asset;
		aSeries = new Series<String, Number>();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Stock.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
       

        try {
            BorderPane pane = fxmlLoader.load();
            Scene scene = new Scene(pane);
    		
    		primaryStage.setScene(scene);
    		primaryStage.initModality(Modality.WINDOW_MODAL);
    		primaryStage.show();
    		
    		chart.setLegendVisible(false);
    		chart.setAnimated(false);
    		chart.setHorizontalZeroLineVisible(false);
    		chart.setVerticalZeroLineVisible(false);
    		chart.setAlternativeColumnFillVisible(false);
    		categoryAxis.getCategories().clear();
    		
    		
    		title.setText(asset.getName() + " (" + asset.getTicker() + ") - " + asset.getTotalPriceString());
    		Label titleLength = new Label(title.getText());
    		title.setPrefWidth(titleLength.getPrefWidth());
    		bank.setText(asset.getBankString() + " - " + asset.getAccountNameString());
    		currentPrice.setText("Current Price - " + asset.getPricePerShareString() + " (" + asset.getMovementString() + ")");
    		ticker.setText(asset.getTicker());   
    		if(!asset.getAssetType().equals("Stock")) {
    			if(asset.getAssetType().equals("Mutual Fund")) {
    				MutualFunds fund = (MutualFunds)asset;
    				capType.setText("Cap Type - " + fund.getCapString());
    				countryType.setText("Country Type - " + fund.getCountryString());
    			} else {
    				ETF fund = (ETF)asset;
    				capType.setText("Cap Type - " + fund.getCapString());
    				countryType.setText("Country Type - " + fund.getCountryString());
    			}
    		}
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        onRefresh();
        chart.getData().addAll(aSeries);
        
	}
	
//	@FXML
//	public void oneDay() {
//		
//		ObservableList<String> list = FXCollections.observableArrayList();
//		
//		list.addAll("9", "10", "11", "12", "1", "2", "3", "4");
//		
//		categoryAxis.setCategories(list);
//		categoryAxis.setAutoRanging(false);
//		numberAxis.setAutoRanging(false);
//		numberAxis.setUpperBound(thisStockData.getQuote().getDayHigh().doubleValue());
//		numberAxis.setLowerBound(thisStockData.getQuote().getDayLow().doubleValue());
//		
//		
//		
//		chart.setTitle("Long Term Assets Performance");
//		
//		IntraDayReader reader = new IntraDayReader(asset.getTicker());
//		
//		while(!reader.isSuccess()) {
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		ArrayList<Double> numbers = reader.getIntraDayData();
//		
//		Series<String, Number> aSeries = new Series<String, Number>();
//		aSeries.setName("Daily");
//		
//		for(int i = 0; i < list.size(); i++) {
//			aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(i), numbers.get(i)));
//		}
//		
//		
//		
//		chart.getData().addAll(aSeries);
//		
//		
//		
//	}
	
	@FXML
	public void oneWeek() {
		aSeries.getData().clear();
		LocalDate today = LocalDate.now();
		
		int day0 = today.getDayOfMonth();
		int day1 = today.minusDays(1).getDayOfMonth();
		int day2 = today.minusDays(2).getDayOfMonth();
		int day3 = today.minusDays(3).getDayOfMonth();
		int day4 = today.minusDays(4).getDayOfMonth();
		int day5 = today.minusDays(5).getDayOfMonth();
		int day6 = today.minusDays(6).getDayOfMonth();
		
		ObservableList<String> list = FXCollections.observableArrayList();
		
		list.addAll(day6 + "", day5 + "", day4 + "", day3 + "", day2 + "", day1 + "", day0 + "");
		
		categoryAxis.getCategories().clear();
		categoryAxis.setCategories(list);
		categoryAxis.setAutoRanging(false);
		numberAxis.setAutoRanging(true);
		numberAxis.setUpperBound(thisStockData.getQuote().getYearHigh().doubleValue());
		numberAxis.setLowerBound(thisStockData.getQuote().getYearLow().doubleValue());
		
		List<Double> quotes = new ArrayList<Double>();
		
		Calendar calendar = new GregorianCalendar(LocalDate.now().minusWeeks(1).minusDays(1).getYear(), LocalDate.now().minusWeeks(1).minusDays(1).getMonthValue()-1, LocalDate.now().minusWeeks(1).minusDays(1).getDayOfMonth());
		
		try {
			List<HistoricalQuote> quotesList = thisStockData.getHistory(calendar, Interval.DAILY);
			for(HistoricalQuote quote : quotesList) {
				if(quote.getClose() != null) {
					quotes.add(quote.getClose().doubleValue());
				}
			}
			
			chart.setTitle("One Week");
			
			 
			
			for(int i = 0; i < list.size() - 1; i++) {
				aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(i), quotes.get(i)));
				aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(6), currentPriceDouble));
			} 
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void oneMonth() {
		//chart.getData().clear();
		aSeries.getData().clear();
		LocalDate today = LocalDate.now();
		
		int day0 = today.getDayOfMonth();
		int day1 = today.minusWeeks(1).getDayOfMonth();
		int day2 = today.minusWeeks(2).getDayOfMonth();
		int day3 = today.minusWeeks(3).getDayOfMonth();
		int day4 = today.minusWeeks(4).getDayOfMonth();
		
		
		
		ObservableList<String> list = FXCollections.observableArrayList();
		
		list.addAll( day4 + "", day3 + "", day2 + "", day1 + "", day0 + "");
		
		categoryAxis.getCategories().clear();
		categoryAxis.setCategories(list);
		categoryAxis.setAutoRanging(false);
		numberAxis.setAutoRanging(true);
		numberAxis.setUpperBound(thisStockData.getQuote().getYearHigh().doubleValue());
		numberAxis.setLowerBound(thisStockData.getQuote().getYearLow().doubleValue());
		
		List<Double> quotes = new ArrayList<Double>();
		
		Calendar calendar = new GregorianCalendar(LocalDate.now().minusMonths(1).getYear(), LocalDate.now().minusMonths(1).getMonthValue()-1, LocalDate.now().minusMonths(1).getDayOfMonth());
		
		try {
			List<HistoricalQuote> quotesList = thisStockData.getHistory(calendar, Interval.WEEKLY);
			for(HistoricalQuote quote : quotesList) {
				quotes.add(quote.getClose().doubleValue());
			}
			
			chart.setTitle("One Month");
			
			 
			
			for(int i = 0; i < list.size() - 1; i++) {
				aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(i), quotes.get(i)));
				aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(4), currentPriceDouble));
			}  
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void threeMonths() {
		//chart.getData().clear();
				aSeries.getData().clear();
				LocalDate today = LocalDate.now();
				
				String day0 = "" + today.getMonth() + " " + today.getDayOfMonth();
				String day1 = "" + today.minusWeeks(2).getMonth()+ " " +today.minusWeeks(2).getDayOfMonth();
				String day2 = "" + today.minusWeeks(4).getMonth()+ " " +today.minusWeeks(4).getDayOfMonth();
				String day3 = "" + today.minusWeeks(6).getMonth()+ " " +today.minusWeeks(6).getDayOfMonth();
				String day4 = "" + today.minusWeeks(8).getMonth()+ " " +today.minusWeeks(8).getDayOfMonth();
				String day5 = "" + today.minusWeeks(10).getMonth()+ " " +today.minusWeeks(10).getDayOfMonth();
				String day6 = "" + today.minusWeeks(12).getMonth()+ " " +today.minusWeeks(12).getDayOfMonth();
				
				
				
				
				ObservableList<String> list = FXCollections.observableArrayList();
				
				list.addAll( day6, day5, day4 , day3 , day2, day1, day0);
				
				categoryAxis.getCategories().clear();
				categoryAxis.setCategories(list);
				categoryAxis.setAutoRanging(false);
				numberAxis.setAutoRanging(true);
				numberAxis.setUpperBound(thisStockData.getQuote().getYearHigh().doubleValue());
				numberAxis.setLowerBound(thisStockData.getQuote().getYearLow().doubleValue());
				
				List<Double> quotes = new ArrayList<Double>();
				
				Calendar calendar = new GregorianCalendar(LocalDate.now().minusMonths(3).getYear(), LocalDate.now().minusMonths(3).getMonthValue()-1, LocalDate.now().minusMonths(3).getDayOfMonth());
				
				try {
					List<HistoricalQuote> quotesList = thisStockData.getHistory(calendar, Interval.WEEKLY);
					for(int i = 0 ; i < list.size(); i ++) {
						HistoricalQuote quote = quotesList.get(i * 2);
						quotes.add(quote.getClose().doubleValue());
					}
					
					chart.setTitle("Three Month");
					
					 
					
					for(int i = 0; i < list.size() - 1; i++) {
						aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(i), quotes.get(i)));
						aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(6), currentPriceDouble));
					}  
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	@FXML
	public void sixMonths() {
		aSeries.getData().clear();
		LocalDate today = LocalDate.now();
		
		String day0 = "" + today.getMonth() + " " + today.getDayOfMonth();
		String day1 = "" + today.minusWeeks(4).getMonth()+ " " +today.minusWeeks(4).getDayOfMonth();
		String day2 = "" + today.minusWeeks(8).getMonth()+ " " +today.minusWeeks(8).getDayOfMonth();
		String day3 = "" + today.minusWeeks(12).getMonth()+ " " +today.minusWeeks(12).getDayOfMonth();
		String day4 = "" + today.minusWeeks(16).getMonth()+ " " +today.minusWeeks(16).getDayOfMonth();
		String day5 = "" + today.minusWeeks(20).getMonth()+ " " +today.minusWeeks(20).getDayOfMonth();
		String day6 = "" + today.minusWeeks(24).getMonth()+ " " +today.minusWeeks(24).getDayOfMonth();
		
		
		
		
		ObservableList<String> list = FXCollections.observableArrayList();
		
		list.addAll( day6, day5, day4 , day3 , day2, day1, day0);
		
		categoryAxis.getCategories().clear();
		categoryAxis.setCategories(list);
		categoryAxis.setAutoRanging(false);
		numberAxis.setAutoRanging(true);
		numberAxis.setUpperBound(thisStockData.getQuote().getYearHigh().doubleValue());
		numberAxis.setLowerBound(thisStockData.getQuote().getYearLow().doubleValue());
		
		List<Double> quotes = new ArrayList<Double>();
		
		Calendar calendar = new GregorianCalendar(LocalDate.now().minusMonths(6).getYear(), LocalDate.now().minusMonths(6).getMonthValue()-1, LocalDate.now().minusMonths(6).getDayOfMonth());
		
		try {
			List<HistoricalQuote> quotesList = thisStockData.getHistory(calendar, Interval.WEEKLY);
			for(int i = 0 ; i < list.size(); i ++) {
				HistoricalQuote quote = quotesList.get(i * 4);
				quotes.add(quote.getClose().doubleValue());
			}
			
			chart.setTitle("Six Month");
			
			 
			
			for(int i = 0; i < list.size() - 1; i++) {
				aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(i), quotes.get(i)));
				aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(6), currentPriceDouble));
			}  
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void oneYear() {
		aSeries.getData().clear();
		LocalDate today = LocalDate.now();
		
		String day0 = "" + today.getMonth();
		String day1 = "" + today.minusMonths(2).getMonth();
		String day2 = "" + today.minusMonths(4).getMonth();
		String day3 = "" + today.minusMonths(6).getMonth();
		String day4 = "" + today.minusMonths(8).getMonth();
		String day5 = "" + today.minusMonths(10).getMonth();
		String day6 = "" + today.minusMonths(12).getMonth() + " " + today.minusMonths(12).getYear();
		
		
		
		
		ObservableList<String> list = FXCollections.observableArrayList();
		
		list.addAll( day6, day5, day4 , day3 , day2, day1, day0);
		
		categoryAxis.getCategories().clear();
		categoryAxis.setCategories(list);
		categoryAxis.setAutoRanging(false);
		numberAxis.setAutoRanging(true);
		numberAxis.setUpperBound(thisStockData.getQuote().getYearHigh().doubleValue());
		numberAxis.setLowerBound(thisStockData.getQuote().getYearLow().doubleValue());
		
		List<Double> quotes = new ArrayList<Double>();
		
		Calendar calendar = new GregorianCalendar(LocalDate.now().minusYears(1).getYear(), LocalDate.now().minusYears(1).getMonthValue()-1, LocalDate.now().minusYears(1).getDayOfMonth());
		
		try {
			List<HistoricalQuote> quotesList = thisStockData.getHistory(calendar, Interval.MONTHLY);
			for(int i = 0 ; i < list.size(); i ++) {
				HistoricalQuote quote = quotesList.get(i);
				quotes.add(quote.getClose().doubleValue());
			}
			
			chart.setTitle("One Year");
			
			 
			
			for(int i = 0; i < list.size() - 1; i++) {
				aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(i), quotes.get(i)));
				aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(6), currentPriceDouble));
			}  
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void twoYears() {
		aSeries.getData().clear();
		LocalDate today = LocalDate.now();
		
		String day0 = "" + today.getMonth();
		String day1 = "" + today.minusMonths(4).getMonth();
		String day2 = "" + today.minusMonths(8).getMonth();
		String day3 = "" + today.minusMonths(12).getMonth()+ " " + today.minusYears(1).getYear();
		String day4 = "" + today.minusMonths(16).getMonth()+ " " + today.minusYears(1).getYear();
		String day5 = "" + today.minusMonths(20).getMonth() + " " + today.minusYears(1).getYear();
		String day6 = "" + today.minusMonths(24).getMonth() + " " + today.minusYears(2).getYear();
		
		
		
		
		ObservableList<String> list = FXCollections.observableArrayList();
		
		list.addAll( day6, day5, day4 , day3 , day2, day1, day0);
		
		categoryAxis.getCategories().clear();
		categoryAxis.setCategories(list);
		categoryAxis.setAutoRanging(false);
		numberAxis.setAutoRanging(true);
		
		
		List<Double> quotes = new ArrayList<Double>();
		
		Calendar calendar = new GregorianCalendar(LocalDate.now().minusYears(2).getYear(), LocalDate.now().minusYears(2).getMonthValue()-1, LocalDate.now().minusYears(2).getDayOfMonth());
		
		try {
			List<HistoricalQuote> quotesList = thisStockData.getHistory(calendar, Interval.MONTHLY);
			double high = 0;
			double low = 0;
			for(int i = 0 ; i < list.size(); i ++) {
				HistoricalQuote quote = quotesList.get(i);
				quotes.add(quote.getClose().doubleValue());
				if(quote.getClose().doubleValue() > high) {
					high = quote.getClose().doubleValue();
				} 
				if(quote.getClose().doubleValue() < low) {
					low = quote.getClose().doubleValue();
				}
			}
			
			if(currentPriceDouble < low) {
				low = currentPriceDouble;
			}
			if(currentPriceDouble > high) {
				high = currentPriceDouble;
			}
			
			chart.setTitle("Two Years");
			numberAxis.setLowerBound(low);
			numberAxis.setUpperBound(high);
			 
			
			for(int i = 0; i < list.size() - 1; i++) {
				aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(i), quotes.get(i)));
				aSeries.getData().add(new XYChart.Data(categoryAxis.getCategories().get(6), currentPriceDouble));
			}  
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void allTime() {
		
	}
	
	@FXML
	public void home() {
		
	}
	
	@FXML
	public void onRefresh() {
		new Thread() {

            // runnable for that thread
            public void run() {
                final Stock currentAsset;
            	
            	try {
					currentAsset = YahooFinance.get(asset.getTicker());
					thisStockData = currentAsset;
					Platform.runLater(new Runnable() {

                        public void run() {
                        	if(currentAsset != null) {
                        		
                        		DecimalFormat decimalFormat = new DecimalFormat("#.##");
                                decimalFormat.setGroupingUsed(true);
                                decimalFormat.setGroupingSize(3);
                                
                                StockQuote quote = currentAsset.getQuote();
                                StockStats stats = currentAsset.getStats();
                                
                                asset.setPrice(quote.getPrice().doubleValue());
                                currentPriceDouble = asset.getPrice();
                                
                                title.setText(asset.getName() + " (" + asset.getTicker() + ") - " + asset.getTotalPriceString());
                        		currentPrice.setText("Current Price - " + asset.getPricePerShareString() + " (" + asset.getMovementString() + ")");
                        		if(quote.getOpen() != null) {
                        			open.setText("Open - $" + decimalFormat.format(quote.getOpen().doubleValue()));
                        		} else {
                        			open.setText("Open - $-");
                        		}
                        		
                        		if(quote.getDayHigh() != null) {
                        			high.setText("High - $" + decimalFormat.format(quote.getDayHigh().doubleValue()));
                        		} else {
                        			high.setText("High - $-");
                        		}
                        		
                        		if(quote.getDayLow() != null) {
                        			low.setText("Low - $" + decimalFormat.format(quote.getDayLow().doubleValue()));
                        		} else {
                        			low.setText("Low - $-");
                        		}
                        		
                        		yearHigh.setText("52 Week High - $" + decimalFormat.format(quote.getYearHigh().doubleValue()));
                        		yearLow.setText("52 Week Low - $" + decimalFormat.format(quote.getYearLow().doubleValue()));
                        		
                        		movingAverage50.setText("50 Day Moving Average - $" + decimalFormat.format(quote.getPriceAvg50().doubleValue()));
                        		movingAverage200.setText("200 Day Moving Average - $" + decimalFormat.format(quote.getPriceAvg200().doubleValue()));
                        		
                        		if(stats.getPe() != null) {
                        			pe.setText("Price Earnings - " + decimalFormat.format(stats.getPe().doubleValue()));
                        		} 
                        		
                        		oneWeek();
                        		
                        		
                        	}
                        }
                    });
					
				} catch (IOException e) {
					
				}
                   
                    
                
            }
        }.start();
	}
	
	@FXML
	public void onBack() {
		primaryStage.close();
	}
}
