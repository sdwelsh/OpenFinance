package application;

import java.text.DecimalFormat;
import java.util.ArrayList;

import application.manager.Manager;
import application.popup.AddAssetController;
import application.popup.EditAssetController;
import application.stock.StockViewController;
import application.users.User;
import data.assets.longterm.Asset;
import data.assets.longterm.ETF;
import data.assets.longterm.ETF.InvestmentType;
import data.assets.longterm.LongTermAsset;
import data.assets.longterm.LongTermAsset.AccountType;
import data.assets.longterm.MutualFunds;
import data.liabilities.Liability;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LTAAnalysisController extends VBox{
	
	private TableView<LongTermAsset> topGainersTable;
	
	private TableView<LongTermAsset> biggestDailyMovers;
	
	private TableView<LongTermAsset> largestInvestments;
	
	private GridPane viewGrid;
	
	private User user;
	
	private VBox returnBox;

	private Button showETFGain;

	private Button showMutualfundsGain;

	private Button showStocksGain;

	private ButtonBase etfMovement;

	private ButtonBase stockMovement;

	private ButtonBase mutualFundMovement;
	
	public LTAAnalysisController(){
		returnBox = new VBox();
		viewGrid = new GridPane();
		
		viewGrid.setStyle("-fx-background-color: white");
		
		this.getChildren().add(viewGrid);
		
		user = Manager.getInstance().getCurrentUser();
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);
		viewGrid.getColumnConstraints().addAll(column1, column2);
		
		createTopGainers(user.getLongTermAssets().returnStocks());
		createInvestmentType();
		createTaxable();
		createsBiggestMovers(user.getLongTermAssets().returnStocks());
		createLargestInvestments();
		createLargestInvestmentsChart();
		
	}

	private void createTopGainers(ArrayList<LongTermAsset> assets) {
		topGainersTable = new TableView<LongTermAsset>();
		
		topGainersTable.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		topGainersTable.setPrefHeight(575);
		
		TableColumn<LongTermAsset, String> name = new TableColumn<LongTermAsset, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<LongTermAsset,String> totalAmount = new TableColumn<LongTermAsset,String>("Gain");
		totalAmount.setCellValueFactory(new PropertyValueFactory<>("gainString"));
		
		
		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
        
        ArrayList<LongTermAsset> array = assets;
        
        
        
        LongTermAsset first = null;
        LongTermAsset second = null;
        LongTermAsset third = null;
        LongTermAsset fourth = null;
        LongTermAsset fifth = null;
        
        
        	if(first == null ) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < array.size(); j++) {
        			LongTermAsset asset = array.get(j);
        			if(mostGain == null) {
        				mostGain = asset;
        			} else {
        				if(mostGain.getGain() < asset.getGain()) {
        					mostGain = asset;
        				}
        			}
        		}
        		first = mostGain;
        		assetList.add(first);
        		
        	}
        	if (second == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < array.size(); j++) {
        			LongTermAsset asset = array.get(j);
        			if(mostGain == null) {
        				if(!asset.equals(first)) {
        					mostGain = asset;
        				}
        			} else if(asset.equals(first)) {
        				//Do Nothing
        			} else {
        				if(mostGain.getGain() < asset.getGain()) {
        					mostGain = asset;
        				}
        			}
        		}
        		second = mostGain;
        		assetList.add(second);
        		
        	}
        	if(third == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < array.size(); j++) {
        			LongTermAsset asset = array.get(j);
        			if(mostGain == null) {
        				if(!asset.equals(first) && !asset.equals(second)) {
        					mostGain = asset;
        				}
        			} else if(asset.equals(first)) {
        				//Do Nothing
        			} else if(asset.equals(second)) {
        				//Do Nothing
        			} else {
        				if(mostGain.getGain() < asset.getGain()) {
        					mostGain = asset;
        				}
        			}
        		}
        		third = mostGain;
        		assetList.add(third);
        		
        	} 
        	if (fourth == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < array.size(); j++) {
        			LongTermAsset asset = array.get(j);
        			if(mostGain == null) {
        				if(!asset.equals(first) && !asset.equals(second) && !asset.equals(third)) {
        					mostGain = asset;
        				}
        			} else if(asset.equals(first)) {
        				//Do Nothing
        			} else if(asset.equals(second)) {
        				//Do Nothing
        			} else if(asset.equals(third)) {
        				//Do Nothing
        			} else {
        				if(mostGain.getGain() < asset.getGain()) {
        					mostGain = asset;
        				}
        			}
        		}
        		fourth = mostGain;
        		assetList.add(fourth);
        		
        	}
        	if (fifth == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < array.size(); j++) {
        			LongTermAsset asset = array.get(j);
        			if(mostGain == null) {
        				if(!asset.equals(first) && !asset.equals(second) && !asset.equals(third) && !asset.equals(fourth)) {
        					mostGain = asset;
        				}
        			} else if(asset.equals(first)) {
        				//Do Nothing
        			} else if(asset.equals(second)) {
        				//Do Nothing
        			} else if(asset.equals(third)) {
        				//Do Nothing
        			} else if(asset.equals(fourth)) {
        				//Do Nothing
        			}else {
        				if(mostGain.getGain() < asset.getGain()) {
        					mostGain = asset;
        				}
        			}
        		}
        		fifth = mostGain;
        		assetList.add(fifth);
        	
        	}
        
        
        
        
        topGainersTable.setItems(assetList);
        
		
		topGainersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		topGainersTable.getColumns().add(name);
		topGainersTable.getColumns().add(totalAmount);
		
		
		
		name.prefWidthProperty().bind(topGainersTable.widthProperty().multiply(0.5));
		name.setStyle("-fx-alignment: CENTER;");
        totalAmount.prefWidthProperty().bind(topGainersTable.widthProperty().multiply(0.5));
       
       

        name.setResizable(false);
        totalAmount.setResizable(false);
        
        topGainersTable.setPrefHeight((5 * 50) + 26);
        
        GridPane pane = new GridPane();
        Label gridLabel = new Label("Top Gainers All Time");
        gridLabel.setStyle("-fx-font-size:25px;");
        gridLabel.setPadding(new Insets(10, 0, 10, 0));
        pane.add(gridLabel, 0, 0);
        pane.add(topGainersTable, 0, 1);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.CENTER);
		column1.setPercentWidth(100);
		pane.getColumnConstraints().add(column1);
		
		showStocksGain = new Button("Stocks");
        showStocksGain.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                createTopGainers(user.getLongTermAssets().returnStocks());
            }
        });
        
        
        showETFGain = new Button("ETF's");
        showETFGain.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	createTopGainers(user.getLongTermAssets().returnETFs());
            }
        });
        
        
        showMutualfundsGain = new Button("Mutual Funds");
        showMutualfundsGain.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	createTopGainers(user.getLongTermAssets().returnMutualFunds());
            }
        });
        
        topGainersTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		            	if(topGainersTable.getSelectionModel().getSelectedItem() != null) {
		            		new StockViewController(topGainersTable.getSelectionModel().getSelectedItem());
		            	} 
		            }
		        }
		    }
		});
        
        GridPane buttons = new GridPane();
        ColumnConstraints stocks = new ColumnConstraints();
        stocks.setPercentWidth(40);
        ColumnConstraints etfs = new ColumnConstraints(showETFGain.getPrefWidth());
        ColumnConstraints mutualFunds = new ColumnConstraints(showMutualfundsGain.getPrefWidth());
        stocks.setHalignment(HPos.RIGHT);
        etfs.setHalignment(HPos.CENTER);
        mutualFunds.setHalignment(HPos.LEFT);
        buttons.add(showStocksGain, 0, 0);
        buttons.add(showETFGain, 1, 0);
        buttons.add(showMutualfundsGain, 2, 0);
        
        buttons.getColumnConstraints().addAll(stocks, etfs, mutualFunds);
        buttons.setPadding(new Insets(10, 0, 20, 0));
        
        
        pane.add(buttons, 0, 2);
        GridPane.setMargin(topGainersTable, new Insets(0,10,0,10));
        pane.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        
        viewGrid.add(pane, 0, 0);
        
	}
	
	private void createInvestmentType() {
		ArrayList<LongTermAsset> mutualFunds = user.getLongTermAssets().returnMutualFunds();
		ArrayList<LongTermAsset> etfs = user.getLongTermAssets().returnETFs();
		
		double bondFundTotal = 0;
		double stockFundTotal = 0;
		double preferredStockTotal = 0;
		double reitTotal = 0;
		double otherTotal = 0;
		double total = 0;
		
		for(LongTermAsset etf : etfs) {
			ETF actualEtf = (ETF) etf;
			InvestmentType type = actualEtf.getInvestmentType();
			switch(type){
				case Bond_Fund:
					bondFundTotal += actualEtf.getTotalPrice();
					break;
				case Stock_Fund:
					stockFundTotal += actualEtf.getTotalPrice();
					break;
				case Preferred_Stock_Fund:
					preferredStockTotal += actualEtf.getTotalPrice();
					break;
				case REIT_Fund:
					reitTotal += actualEtf.getTotalPrice();
					break;
				case Other:
					otherTotal += actualEtf.getTotalPrice();
					break;
			}
		}
		
		for(LongTermAsset mutualFund : mutualFunds) {
			MutualFunds actualEtf = (MutualFunds) mutualFund;
			InvestmentType type = actualEtf.getInvType();
			switch(type){
				case Bond_Fund:
					bondFundTotal += actualEtf.getTotalPrice();
					break;
				case Stock_Fund:
					stockFundTotal += actualEtf.getTotalPrice();
					break;
				case Preferred_Stock_Fund:
					preferredStockTotal += actualEtf.getTotalPrice();
					break;
				case REIT_Fund:
					reitTotal += actualEtf.getTotalPrice();
					break;
				case Other:
					otherTotal += actualEtf.getTotalPrice();
					break;
			}
		}
		
		total = stockFundTotal + bondFundTotal + preferredStockTotal + reitTotal + otherTotal;
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        					new PieChart.Data("Stock Fund - \n$" + decimalFormat.format(stockFundTotal), stockFundTotal), 
        					new PieChart.Data("Bond Fund - \n$" + decimalFormat.format(bondFundTotal), bondFundTotal), 
        					new PieChart.Data("Prefered Stock Fund - \n$" + decimalFormat.format(preferredStockTotal), preferredStockTotal), 
        					new PieChart.Data("REIT Fund -\n$" + decimalFormat.format(reitTotal), reitTotal),
        					new PieChart.Data("Other -\n$" + decimalFormat.format(otherTotal), otherTotal));
		
		
		PieChart pieChart = new PieChart(pieChartData);
		pieChart.setTitle("Investment Type");
		pieChart.setLegendVisible(false);
		
		
		pieChart.setPadding(new Insets(30, 0, 0, 0));
		pieChart.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		
		viewGrid.add(pieChart, 1, 1);
	}
	
	private void createTaxable() {
		ArrayList<LongTermAsset> assets = user.getLongTermAssets().returnLongTermAssets();
		
		double taxable = 0;
		double rothIra = 0;
		double roth401K = 0;
		double ira = 0;
		double normal401K = 0;
		
		for(LongTermAsset asset : assets) {
			AccountType type = asset.getType();
			switch(type){
				case BROKERAGE:
					taxable += asset.getTotalPrice();
					break;
				case ROTH401K:
					roth401K += asset.getTotalPrice();
					break;
				case _401K:
					normal401K += asset.getTotalPrice();
					break;
				case ROTH_IRA:
					rothIra += asset.getTotalPrice();
					break;
				case IRA:
					ira += asset.getTotalPrice();
					break;
				case TAXABLE_ACCOUNT:
					taxable += asset.getTotalPrice();
					break;
			default:
				break;
			}
		}
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        					new PieChart.Data("Taxable - \n$" + decimalFormat.format(taxable), taxable), 
        					new PieChart.Data("Roth IRA - \n$" + decimalFormat.format(rothIra), rothIra), 
        					new PieChart.Data("Roth 401K - \n$" + decimalFormat.format(roth401K), roth401K), 
        					new PieChart.Data("IRA -\n$" + decimalFormat.format(ira), ira),
        					new PieChart.Data("401K -\n$" + decimalFormat.format(normal401K), normal401K));
		
		
		PieChart pieChart = new PieChart(pieChartData);
		pieChart.setTitle("Taxable vs. Non-Taxable Accounts");
		pieChart.setLegendVisible(false);
		
		
		pieChart.setPadding(new Insets(30, 0, 0, 0));
		pieChart.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		
		viewGrid.add(pieChart, 0, 1);
	}
	
	
	

	private void createsBiggestMovers(ArrayList<LongTermAsset> assets) {
		biggestDailyMovers = new TableView<LongTermAsset>();
		
		biggestDailyMovers.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		biggestDailyMovers.setPrefHeight(575);
		
		TableColumn<LongTermAsset, String> name = new TableColumn<LongTermAsset, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<LongTermAsset,String> totalAmount = new TableColumn<LongTermAsset,String>("Percent Movement");
		totalAmount.setCellValueFactory(new PropertyValueFactory<>("movementString"));
		
		
		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
        
		LongTermAsset first = null;
        LongTermAsset second = null;
        LongTermAsset third = null;
        LongTermAsset fourth = null;
        LongTermAsset fifth = null;
        
        
        	if(first == null ) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < assets.size(); j++) {
        			LongTermAsset asset = assets.get(j);
        			if(mostGain == null) {
        				mostGain = asset;
        			} else {
        				if(mostGain.getMovement() < asset.getMovement()) {
        					mostGain = asset;
        				}
        			}
        		}
        		first = mostGain;
        		assetList.add(first);
        		
        	}
        	if (second == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < assets.size(); j++) {
        			LongTermAsset asset = assets.get(j);
        			if(mostGain == null) {
        				if(!asset.getTicker().equals(first.getTicker())) {
        					mostGain = asset;
        				}
        			} else if(asset.getTicker().equals(first.getTicker())) {
        				//Do Nothing
        			} else {
        				if(mostGain.getMovement() < asset.getMovement()) {
        					mostGain = asset;
        				}
        			}
        		}
        		second = mostGain;
        		assetList.add(second);
        		
        	}
        	if(third == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < assets.size(); j++) {
        			LongTermAsset asset = assets.get(j);
        			if(mostGain == null) {
        				if(!asset.getTicker().equals(first.getTicker()) && !asset.getTicker().equals(second.getTicker())) {
        					mostGain = asset;
        				}
        			} else if(asset.getTicker().equals(first.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(second.getTicker())) {
        				//Do Nothing
        			} else {
        				if(mostGain.getMovement() < asset.getMovement()) {
        					mostGain = asset;
        				}
        			}
        		}
        		third = mostGain;
        		assetList.add(third);
        		
        	} 
        	if (fourth == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < assets.size(); j++) {
        			LongTermAsset asset = assets.get(j);
        			if(mostGain == null) {
        				if(!asset.getTicker().equals(first.getTicker()) && !asset.getTicker().equals(second.getTicker()) && !asset.getTicker().equals(third.getTicker())) {
        					mostGain = asset;
        				}
        			} else if(asset.getTicker().equals(first.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(second.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(third.getTicker())) {
        				//Do Nothing
        			} else {
        				if(mostGain.getMovement() < asset.getMovement()) {
        					mostGain = asset;
        				}
        			}
        		}
        		fourth = mostGain;
        		assetList.add(fourth);
        		
        	}
        	if (fifth == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < assets.size(); j++) {
        			LongTermAsset asset = assets.get(j);
        			if(mostGain == null) {
        				if(!asset.getTicker().equals(first.getTicker()) && !asset.getTicker().equals(second.getTicker()) && !asset.getTicker().equals(third.getTicker()) && !asset.getTicker().equals(fourth.getTicker())) {
        					mostGain = asset;
        				}
        			} else if(asset.getTicker().equals(first.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(second.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(third.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(fourth.getTicker())) {
        				//Do Nothing
        			}else {
        				if(mostGain.getMovement() < asset.getMovement()) {
        					mostGain = asset;
        				}
        			}
        		}
        		fifth = mostGain;
        		assetList.add(fifth);
        	
        	}
        
        
        
        
        biggestDailyMovers.setItems(assetList);
        
		
		biggestDailyMovers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		biggestDailyMovers.getColumns().add(name);
		biggestDailyMovers.getColumns().add(totalAmount);
		
		 biggestDailyMovers.setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent mouseEvent) {
			        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
			            if(mouseEvent.getClickCount() == 2){
			            	if(biggestDailyMovers.getSelectionModel().getSelectedItem() != null) {
			            		new StockViewController(biggestDailyMovers.getSelectionModel().getSelectedItem());
			            	} 
			            }
			        }
			    }
			});
		
		
		
		name.prefWidthProperty().bind(biggestDailyMovers.widthProperty().multiply(0.5));
		name.setStyle("-fx-alignment: CENTER;");
        totalAmount.prefWidthProperty().bind(biggestDailyMovers.widthProperty().multiply(0.5));
       
       

        name.setResizable(false);
        totalAmount.setResizable(false);
        
        biggestDailyMovers.setPrefHeight((5 * 50) + 26);
        
        GridPane pane = new GridPane();
        Label gridLabel = new Label("Biggest Movers Today");
        gridLabel.setStyle("-fx-font-size:25px;");
        gridLabel.setPadding(new Insets(10, 0, 10, 0));
        pane.add(gridLabel, 0, 0);
        pane.add(biggestDailyMovers, 0, 1);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.CENTER);
		column1.setPercentWidth(100);
		pane.getColumnConstraints().add(column1);
		
		stockMovement = new Button("Stocks");
        stockMovement.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                createsBiggestMovers(user.getLongTermAssets().returnStocks());
            }
        });
        
        
        etfMovement = new Button("ETF's");
        etfMovement.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	createsBiggestMovers(user.getLongTermAssets().returnETFs());
            }
        });
        
        
        mutualFundMovement = new Button("Mutual Funds");
        mutualFundMovement.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	createsBiggestMovers(user.getLongTermAssets().returnMutualFunds());
            }
        });
        
        GridPane buttons = new GridPane();
        ColumnConstraints stocks = new ColumnConstraints();
        stocks.setPercentWidth(40);
        ColumnConstraints etfs = new ColumnConstraints(etfMovement.getPrefWidth());
        ColumnConstraints mutualFunds = new ColumnConstraints(mutualFundMovement.getPrefWidth());
        stocks.setHalignment(HPos.RIGHT);
        etfs.setHalignment(HPos.CENTER);
        mutualFunds.setHalignment(HPos.LEFT);
        buttons.add(stockMovement, 0, 0);
        buttons.add(etfMovement, 1, 0);
        buttons.add(mutualFundMovement, 2, 0);
        
        buttons.getColumnConstraints().addAll(stocks, etfs, mutualFunds);
        buttons.setPadding(new Insets(10, 0, 20, 0));
        
        
        pane.add(buttons, 0, 2);
        GridPane.setMargin(biggestDailyMovers, new Insets(0,10,0,10));
        pane.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        
        viewGrid.add(pane, 1, 0);
	}

	private void createLargestInvestments() {
		largestInvestments = new TableView<LongTermAsset>();
		
		largestInvestments.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		largestInvestments.setPrefHeight(575);
		
		TableColumn<LongTermAsset, String> name = new TableColumn<LongTermAsset, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<LongTermAsset,String> totalAmount = new TableColumn<LongTermAsset,String>("Total Value");
		totalAmount.setCellValueFactory(new PropertyValueFactory<>("totalPriceString"));
		
		
		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
        
        
		LongTermAsset first = null;
        LongTermAsset second = null;
        LongTermAsset third = null;
        LongTermAsset fourth = null;
        LongTermAsset fifth = null;
        
        
        ArrayList<LongTermAsset> assets = new ArrayList<LongTermAsset>();
        
        	if(first == null ) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < assets.size(); j++) {
        			LongTermAsset asset = assets.get(j);
        			if(mostGain == null) {
        				mostGain = asset;
        			} else {
        				if(mostGain.getTotalPrice() < asset.getTotalPrice()) {
        					mostGain = asset;
        				}
        			}
        		}
        		first = mostGain;
        		assetList.add(first);
        		
        	}
        	if (second == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < assets.size(); j++) {
        			LongTermAsset asset = assets.get(j);
        			if(mostGain == null) {
        				if(!asset.getTicker().equals(first.getTicker())) {
        					mostGain = asset;
        				}
        			} else if(asset.getTicker().equals(first.getTicker())) {
        				//Do Nothing
        			} else {
        				if(mostGain.getMovement() < asset.getMovement()) {
        					mostGain = asset;
        				}
        			}
        		}
        		second = mostGain;
        		assetList.add(second);
        		
        	}
        	if(third == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < assets.size(); j++) {
        			LongTermAsset asset = assets.get(j);
        			if(mostGain == null) {
        				if(!asset.getTicker().equals(first.getTicker()) && !asset.getTicker().equals(second.getTicker())) {
        					mostGain = asset;
        				}
        			} else if(asset.getTicker().equals(first.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(second.getTicker())) {
        				//Do Nothing
        			} else {
        				if(mostGain.getMovement() < asset.getMovement()) {
        					mostGain = asset;
        				}
        			}
        		}
        		third = mostGain;
        		assetList.add(third);
        		
        	} 
        	if (fourth == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < assets.size(); j++) {
        			LongTermAsset asset = assets.get(j);
        			if(mostGain == null) {
        				if(!asset.getTicker().equals(first.getTicker()) && !asset.getTicker().equals(second.getTicker()) && !asset.getTicker().equals(third.getTicker())) {
        					mostGain = asset;
        				}
        			} else if(asset.getTicker().equals(first.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(second.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(third.getTicker())) {
        				//Do Nothing
        			} else {
        				if(mostGain.getMovement() < asset.getMovement()) {
        					mostGain = asset;
        				}
        			}
        		}
        		fourth = mostGain;
        		assetList.add(fourth);
        		
        	}
        	if (fifth == null) {
        		LongTermAsset mostGain = null;
        		
        		for(int j = 0; j < assets.size(); j++) {
        			LongTermAsset asset = assets.get(j);
        			if(mostGain == null) {
        				if(!asset.getTicker().equals(first.getTicker()) && !asset.getTicker().equals(second.getTicker()) && !asset.getTicker().equals(third.getTicker()) && !asset.getTicker().equals(fourth.getTicker())) {
        					mostGain = asset;
        				}
        			} else if(asset.getTicker().equals(first.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(second.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(third.getTicker())) {
        				//Do Nothing
        			} else if(asset.getTicker().equals(fourth.getTicker())) {
        				//Do Nothing
        			}else {
        				if(mostGain.getMovement() < asset.getMovement()) {
        					mostGain = asset;
        				}
        			}
        		}
        		fifth = mostGain;
        		assetList.add(fifth);
        	
        	}
        
        
        largestInvestments.setItems(assetList);
        
		
		largestInvestments.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		largestInvestments.getColumns().add(name);
		largestInvestments.getColumns().add(totalAmount);
		
		
		
		name.prefWidthProperty().bind(largestInvestments.widthProperty().multiply(0.5));
		name.setStyle("-fx-alignment: CENTER;");
        totalAmount.prefWidthProperty().bind(largestInvestments.widthProperty().multiply(0.5));
       
       

        name.setResizable(false);
        totalAmount.setResizable(false);
        
        largestInvestments.setPrefHeight((5 * 50) + 26);
        
        GridPane pane = new GridPane();
        Label gridLabel = new Label("Biggest Movers Today");
        gridLabel.setStyle("-fx-font-size:25px;");
        gridLabel.setPadding(new Insets(10, 0, 10, 0));
        pane.add(gridLabel, 0, 0);
        pane.add(largestInvestments, 0, 1);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.CENTER);
		column1.setPercentWidth(100);
		pane.getColumnConstraints().add(column1);
		
		
       
        GridPane.setMargin(largestInvestments, new Insets(0,10,0,10));
        pane.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        
        viewGrid.add(pane, 2, 0);
	}

	
	private void createLargestInvestmentsChart() {
		// TODO Auto-generated method stub
		
	}
	
	
	public VBox getVBox() {
		return returnBox;
	}

	
	
}
