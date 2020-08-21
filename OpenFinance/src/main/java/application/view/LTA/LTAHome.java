/**
 * 
 */
package application.view.LTA;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import application.manager.Manager;
import application.users.User;
import data.assets.longterm.Asset;
import data.assets.longterm.LongTermAsset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author stephenwelsh
 *
 */
public class LTAHome extends VBox{
	@FXML private Label stockTotal;
	@FXML private Label stockDayChange;
	
	@FXML private Label etfTotal;
	@FXML private Label etfDayChange;
	
	@FXML private Label mutualFundTotal;
	@FXML private Label mutualFundDayChange;
	
	@FXML private Label otherAssets;
	
	@FXML private Label total;
	@FXML private Label totalDayChange;
	
	@FXML private VBox vbox;
	
	@FXML private HBox stockH;
	@FXML private HBox etfH;
	@FXML private HBox mutualH;
	
	@FXML private AreaChart<String, Number> chart;
	@FXML private CategoryAxis categoryAxis;
	@FXML private NumberAxis numberAxis;
	
	private User user;
	private Series<String, Number> assetSeries;
	
	public LTAHome() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LTAHome.fxml"));
		fxmlLoader.setRoot(this);
	    fxmlLoader.setController(this);
	    
	    try {
			fxmlLoader.load();
			
			user = Manager.getInstance().getCurrentUser();
			
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
	        decimalFormat.setGroupingUsed(true);
	        decimalFormat.setGroupingSize(3);
			
			stockTotal.setText("$" + decimalFormat.format(user.getLongTermAssets().getStocksTotal()));
			etfTotal.setText("$" + decimalFormat.format(user.getLongTermAssets().getETFTotal()));
			mutualFundTotal.setText("$" + decimalFormat.format(user.getLongTermAssets().getMutualTotal()));
			otherAssets.setText("$" + decimalFormat.format(user.getLongTermAssets().getAssetTotal()));
			total.setText("$" + decimalFormat.format(user.getLongTermAssetsTotal()));
			
			setDayChanges();
			
			chart.setHorizontalZeroLineVisible(false);
			chart.setVerticalZeroLineVisible(false);
			chart.setAlternativeColumnFillVisible(false);
			categoryAxis.getCategories().clear();
			addChart();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setDayChanges() {
		double stockChangeTotal = user.getLongTermAssets().getStockDayChange();
		double etfChangeTotal = user.getLongTermAssets().getETFDayChange();
		double mutualFundChangeTotal = user.getLongTermAssets().getMutualFundDayChange();
		double totalChange = stockChangeTotal + etfChangeTotal + mutualFundChangeTotal;
		
		double totalStocks = user.getLongTermAssets().getStocksTotal();
		double totalETFs = user.getLongTermAssets().getETFTotal();
		double totalMutualFunds = user.getLongTermAssets().getMutualTotal();
		double totalFunds = user.getLongTermAssetsTotal();
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        
        double stockPercentChange = (stockChangeTotal/totalStocks) * 100.00;
        stockDayChange.setText("$" + decimalFormat.format(stockChangeTotal) + " (" + decimalFormat.format(stockPercentChange) + "%)");
        if(stockPercentChange < 0) {
        	stockDayChange.setStyle("-fx-text-fill: red");
        } else {
        	stockDayChange.setStyle("-fx-text-fill: green");
        }
        
        double etfPercentChange = (etfChangeTotal / totalETFs) * 100.00;
        etfDayChange.setText("$" + decimalFormat.format(etfChangeTotal) + " (" + decimalFormat.format(etfPercentChange) + "%)");
        if(etfPercentChange < 0) {
        	etfDayChange.setStyle("-fx-text-fill: red");
        } else {
        	etfDayChange.setStyle("-fx-text-fill: green");
        }
        
        double mutualPercentChange = (mutualFundChangeTotal / totalMutualFunds) * 100.00;
        mutualFundDayChange.setText("$" + decimalFormat.format(mutualFundChangeTotal) + " (" + decimalFormat.format(mutualPercentChange) + "%)");
        if(mutualPercentChange < 0) {
        	mutualFundDayChange.setStyle("-fx-text-fill: red");
        } else {
        	mutualFundDayChange.setStyle("-fx-text-fill: green");
        }
        
        double totalPercentChange = (totalChange / totalFunds) * 100.00;
        totalDayChange.setText("$" + decimalFormat.format(totalChange) + " (" + decimalFormat.format(totalPercentChange) + "%)");
        if(totalPercentChange < 0) {
        	totalDayChange.setStyle("-fx-text-fill: red");
        } else {
        	totalDayChange.setStyle("-fx-text-fill: green");
        }
	}

	private void addChart() {
		assetSeries = new Series<String, Number>();
		assetSeries.setName("Assets");
		
		chart.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		int[] monthTotals = new int[12];
		
		ArrayList<LongTermAsset> assets = user.getLongTermAssets().returnLongTermAssets();
		
		ArrayList<String> monthsFromToday = new ArrayList<String>();
		LocalDate today = LocalDate.now();
		for(int i = 11; i >= 0; i--) {
			monthsFromToday.add(today.minusMonths(i).getMonth().toString());
		}
		
		
		for(int i = 0; i < monthTotals.length - 1; i++) {
			for(LongTermAsset asset : assets) {
				monthTotals[i] += (int) Math.round(asset.getHistoricalPrices().get(i)) * asset.getQuantity();
			}
		}
		
		for(LongTermAsset asset : assets) {
			monthTotals[11] += (int) asset.getTotalPrice();
		}
        
        for(int i = 0; i < monthTotals.length; i++) {
        	assetSeries.getData().add(new XYChart.Data(monthsFromToday.get(i), monthTotals[i]));
        }
        
        chart.getData().add(assetSeries);
        
        chart.setCreateSymbols(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setLegendVisible(false);
		
	}

	public void fitSize(double width) {
		stockH.setPrefWidth(width);
		etfH.setPrefWidth(width);
		mutualH.setPrefWidth(width);
	}
}
