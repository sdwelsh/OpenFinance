package application.view.retirement;

import java.io.IOException;
import java.text.DecimalFormat;

import application.manager.Manager;
import application.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class FutureValue extends VBox{
	
	@FXML private TextField currentAge;
	
	@FXML private TextField retirementAge;
	
	@FXML private Label fivePercentReturn;
	
	@FXML private Label sevenPercentReturn;
	
	@FXML private Label tenPercentReturn;
	
	@FXML private Label error;
	
	private User user;
	
	@FXML private AreaChart<Number, String> assetChart;
	@FXML private CategoryAxis categoryAxis;
	@FXML private NumberAxis numberAxis;
	
	private Series<Number, String> fivePercentSeries;
	private Series<Number, String> sevenPercentSeries;
	private Series<Number, String> tenPercentSeries;
	
	public FutureValue() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/futureValue.fxml"));
		fxmlLoader.setRoot(this);
	    fxmlLoader.setController(this);
	    
	    try {
			fxmlLoader.load();
			
			user = Manager.getInstance().getCurrentUser();
			
			assetChart.setHorizontalZeroLineVisible(false);
			assetChart.setVerticalZeroLineVisible(false);
			assetChart.setAlternativeColumnFillVisible(false);
			categoryAxis.getCategories().clear();
	        
	        if(user.getAge() != 0) {
	        	currentAge.setText(user.getAge() + "");
	        	if(user.getRetirementAge() != 0) {
	            	retirementAge.setText(user.getRetirementAge() + "");
	            	double totalAssets = Manager.getInstance().getCurrentUser().getLongTermAssetsTotal();
	            	DecimalFormat decimalFormat = new DecimalFormat("#.##");
			        decimalFormat.setGroupingUsed(true);
			        decimalFormat.setGroupingSize(3);
			        
			        double difference = user.getRetirementAge() - user.getAge();
			        
			        double fivePercent = totalAssets * Math.pow(1.05, difference); 
			        fivePercentReturn.setText(" - $" + decimalFormat.format(fivePercent));
			        
			        double sevenPercent = totalAssets * Math.pow(1.07, difference); 
			        sevenPercentReturn.setText(" - $" + decimalFormat.format(sevenPercent));
			        
			        double tenPercent = totalAssets * Math.pow(1.1, difference); 
			        tenPercentReturn.setText(" - $" + decimalFormat.format(tenPercent));
	            }
	        }
	        
	        addAssetChart();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML 
	public void calculate() {
		double totalAssets = Manager.getInstance().getCurrentUser().getLongTermAssetsTotal();
		try {
			
			int age = 0;
			try {
				age = Integer.parseInt(currentAge.getText());
			} catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("Enter A Valid Age");
			}
			
			int retirement = 0;
			
			try {
				retirement = Integer.parseInt(retirementAge.getText());
			} catch(IllegalArgumentException e){
				throw new IllegalArgumentException("Enter a Valid Retirment Age");
			}
			
			
			double difference = retirement - age;
			
			
			if(difference > 0.0) {
				DecimalFormat decimalFormat = new DecimalFormat("#.##");
		        decimalFormat.setGroupingUsed(true);
		        decimalFormat.setGroupingSize(3);
		        
		        double fivePercent = totalAssets * Math.pow(1.05, difference); 
		        fivePercentReturn.setText(" - $" + decimalFormat.format(fivePercent));
		        
		        double sevenPercent = totalAssets * Math.pow(1.07, difference); 
		        sevenPercentReturn.setText(" - $" + decimalFormat.format(sevenPercent));
		        
		        double tenPercent = totalAssets * Math.pow(1.1, difference); 
		        tenPercentReturn.setText(" - $" + decimalFormat.format(tenPercent));
		        
		        user.setAge(age);
		        user.setRetirementAge(retirement);
		        
		        error.setText("");
		        refresh();
			} else {
				error.setText("Enter A Valid Age Range");
			}
			
			
	        
			
		} catch(Exception e) {
			error.setText(e.getMessage());
		}
		
	}
	
	private void refresh() {
		categoryAxis.getCategories().clear();
		fivePercentSeries.getData().clear();
		sevenPercentSeries.getData().clear();
		tenPercentSeries.getData().clear();
		
		double totalAssets = Manager.getInstance().getCurrentUser().getLongTermAssets().getETFTotal();
		totalAssets += Manager.getInstance().getCurrentUser().getLongTermAssets().getStocksTotal();
		totalAssets += Manager.getInstance().getCurrentUser().getLongTermAssets().getMutualTotal();
		
		ObservableList<String> list = FXCollections.observableArrayList();
		for(int i = user.getAge(); i <= user.getRetirementAge(); i++) {
			list.add(i + "");
			double difference = i - user.getAge();
			double fivePercent = totalAssets * Math.pow(1.05, difference);
			fivePercent += Manager.getInstance().getCurrentUser().getLongTermAssets().getAssetTotal();
			double sevenPercent = totalAssets * Math.pow(1.07, difference);
			sevenPercent += Manager.getInstance().getCurrentUser().getLongTermAssets().getAssetTotal();
			double tenPercent = totalAssets * Math.pow(1.1, difference);
			tenPercent += Manager.getInstance().getCurrentUser().getLongTermAssets().getAssetTotal();
			
			fivePercentSeries.getData().add(new XYChart.Data(i + "", fivePercent));
			sevenPercentSeries.getData().add(new XYChart.Data(i + "", sevenPercent));
			tenPercentSeries.getData().add(new XYChart.Data(i + "", tenPercent));
		}
		categoryAxis.getCategories().addAll(list);
	}
	
	private void addAssetChart() {
		if(user.getRetirementAge() != 0 && user.getAge() != 0) {
			fivePercentSeries = new Series<Number, String>();
			fivePercentSeries.setName("Five Percent Return");
			sevenPercentSeries = new Series<Number, String>();
			sevenPercentSeries.setName("Seven Percent Return");
			tenPercentSeries = new Series<Number, String>();
			tenPercentSeries.setName("Ten Percent Return");
			
			ObservableList<String> list = FXCollections.observableArrayList();
			double totalAssets = Manager.getInstance().getCurrentUser().getLongTermAssetsTotal();
			
			for(int i = user.getAge(); i <= user.getRetirementAge(); i++) {
				list.add(i + "");
				double difference = i - user.getAge();
				double fivePercent = totalAssets * Math.pow(1.05, difference);
				double sevenPercent = totalAssets * Math.pow(1.07, difference);
				double tenPercent = totalAssets * Math.pow(1.1, difference);
				
				fivePercentSeries.getData().add(new XYChart.Data(i + "", fivePercent));
				sevenPercentSeries.getData().add(new XYChart.Data(i + "", sevenPercent));
				tenPercentSeries.getData().add(new XYChart.Data(i + "", tenPercent));
			}
			
			
			categoryAxis.setCategories(list);
			categoryAxis.setAutoRanging(false);
			numberAxis.setAutoRanging(true);
			
			
			assetChart.getData().addAll(fivePercentSeries, sevenPercentSeries, tenPercentSeries);
		}
		
	}
	
}
