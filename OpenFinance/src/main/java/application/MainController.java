/**
 * 
 */
package application;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import application.manager.Manager;
import application.users.User;
import data.assets.longterm.Asset;
import data.assets.longterm.LongTermAsset;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * @author Stephen Welsh
 *
 */
public class MainController extends BorderPane{

	
	@FXML
	public AreaChart <String, Number> netWorthChart;
	
	@FXML
	public Label name;
	
	@FXML private GridPane grid;
	
	@FXML 
	public ImageView logoMain;
	
	@FXML public Label longTermAssets;
	
	@FXML public Label shortTermAssets;
	
	@FXML public Label totalAssets;
	
	@FXML public Label longTermLiabilitiesString;
	
	@FXML public Label shortTermLiabilities;
	
	@FXML public Label totalLiabilities;
	
	@FXML public Label netWorth;
	
	private User user;
	
	public MainController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Main.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        Manager manager = Manager.getInstance();
        
        user = manager.getCurrentUser();

        try {
            fxmlLoader.load();
            name.setText(manager.getCurrentUser().getFirstName() + " " + manager.getCurrentUser().getLastName());
            
            Image thumb = new Image("/linkedin_banner_image_1.png");
            logoMain.setImage(thumb);
            
            
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
            double totalLongTermAssets = manager.getCurrentUser().getLongTermAssetsTotal();
            double shortTermAsset = manager.getCurrentUser().getShortTermAssetsTotal();
            double assetsTotal = totalLongTermAssets + shortTermAsset;
            
            double totalLongTermLiabilities = manager.getCurrentUser().getTotalLongTermLiabilities();
            double totalShortTermLiabilities = manager.getCurrentUser().getTotalShortTermLiabilities();
            double totalLiabilitiesDouble = totalLongTermLiabilities + totalShortTermLiabilities;
            
            double total = assetsTotal - totalLiabilitiesDouble;
        
            longTermAssets.setText("$" + decimalFormat.format(totalLongTermAssets));
            shortTermAssets.setText("$" + decimalFormat.format(shortTermAsset));
            totalAssets.setText("$" + decimalFormat.format(assetsTotal));
            longTermLiabilitiesString.setText("$" + decimalFormat.format(totalLongTermLiabilities));
            shortTermLiabilities.setText("$" + decimalFormat.format(totalShortTermLiabilities));
            totalLiabilities.setText("$" + decimalFormat.format(totalLiabilitiesDouble));
            
            if(total < 0) {
            	netWorth.setStyle("-fx-text-fill: red");;
            }
            netWorth.setText("$" + decimalFormat.format(total));
            
            setLongTermAssetsChart();
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	private void setLongTermAssetsChart() {
		final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setGapStartAndEnd(false);
        xAxis.setStartMargin(0);
        xAxis.setEndMargin(0);
		netWorthChart = new AreaChart<String, Number>(xAxis, yAxis);
		netWorthChart.setTitle("Long Term Assets Performance");
		
		netWorthChart.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		int[] monthTotals = new int[12];
		
		ArrayList<LongTermAsset> assets = user.getLongTermAssets().returnLongTermAssets();
		
		double physicalAssetTotal = 0;
		
		ArrayList<String> monthsFromToday = new ArrayList<String>();
		LocalDate today = LocalDate.now();
		for(int i = 11; i >= 0; i--) {
			monthsFromToday.add(today.minusMonths(i).getMonth().toString());
		}
		
		for(Asset asset : user.getLongTermAssets().returnAssets()) {
			physicalAssetTotal += asset.getValue();
		}
		
		for(int i = 0; i < monthTotals.length - 1; i++) {
			for(LongTermAsset asset : assets) {
				double getQuote = 0;
				try {
					getQuote = asset.getHistoricalPrices().get(i);
				} catch(Exception e) {
					//Do Nothing
				}
				monthTotals[i] += (int) Math.round(getQuote * asset.getQuantity());
			}
			monthTotals[i] += physicalAssetTotal;
		}
		
		for(LongTermAsset asset : assets) {
			monthTotals[11] += (int) asset.getTotalPrice();
		}
		monthTotals[11] += physicalAssetTotal;
		
		Series<String, Number> seriesAssets = new Series<String, Number>();
        seriesAssets.setName("Long Term Assets");
        
        for(int i = 0; i < monthTotals.length; i++) {
        	seriesAssets.getData().add(new XYChart.Data(monthsFromToday.get(i), monthTotals[i]));
        }
        
        Series<String, Number> seriesLiabilities = new Series<String, Number>();
        seriesLiabilities.setName("Long Term Liabilities");
        seriesLiabilities.getData().add(new XYChart.Data(monthsFromToday.get(0), user.getTotalLongTermLiabilities()));
        seriesLiabilities.getData().add(new XYChart.Data(monthsFromToday.get(11), user.getTotalLongTermLiabilities()));
        
        
        netWorthChart.getData().add(seriesAssets);
        netWorthChart.getData().add(seriesLiabilities);
        netWorthChart.setPadding(new Insets(0,20,0,0));
        
        netWorthChart.setCreateSymbols(false);
        netWorthChart.setHorizontalGridLinesVisible(false);
        netWorthChart.setVerticalGridLinesVisible(false);
        
        
        
        grid.add(netWorthChart, 0, 1);
		
	}

	@FXML
	public void longTermAssets() {
		Main.setView(new LongTermAssetsController());
	}
	
	@FXML
	public void shortTermAssets() {
		Main.setView(new ShortTermAssetsController());
	}
	
	@FXML
	public void longTermLiabilities() {
		Main.setView(new LongTermLiabilitiesController());
	}
	
	@FXML
	public void shortTermLiabilities() {
		Main.setView(new ShortTermLiabilitiesController());
	}
	
	@FXML
	public void retirement() {
		Main.setView(new RetirementController()); 
	}
	
	@FXML
	public void logout() {
		Manager manager = Manager.getInstance();
		manager.logout();
		Main.login(new LoginController());
	}
	
	
}
