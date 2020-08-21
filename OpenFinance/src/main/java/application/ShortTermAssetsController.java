/**
 * 
 */
package application;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import application.manager.Manager;
import application.popup.AddShortTermAssetController;
import application.popup.EditShortTermAssetsController;
import application.users.User;
import data.assets.shortTerm.ShortTermAsset;
import data.assets.shortTerm.ShortTermAsset.AccountType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * @author Stephen Welsh
 *
 */
public class ShortTermAssetsController extends BorderPane{
	
	@FXML
	private ImageView logoMain;
	
	
	private static TableView<ShortTermAsset> shortTermAssetsTable;
	
	private static Label totalShortTermAssets;
	
	private static User user;
	
	@FXML
	private VBox vbox;

	private Button addAsset;

	private Button edit;

	private Button delete;
	
	@FXML
	private GridPane grid;
	
	private static PieChart pieChart;


	public ShortTermAssetsController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ShortTermAssets.fxml"));
		fxmlLoader.setRoot(this);
	    fxmlLoader.setController(this);
	    
	    Manager manager = Manager.getInstance();

	    user = manager.getCurrentUser();
	    
	    try {
	        fxmlLoader.load();
	        
	        createTable();
	        createPieChart();
	        
	        Image thumb = new Image("/linkedin_banner_image_1.png");
	        logoMain.setImage(thumb);
	        
	        
	        
	    } catch (IOException exception) {
	        throw new RuntimeException(exception);
	    }
	}
	
	public void createPieChart() {
		
		ArrayList<ShortTermAsset> assets = user.returnShortTermAssets().returnShortTermAssets();
		
		double cashTotal = 0;
		double cdsTotal = 0;
		double checkingTotal = 0;
		double highYieldTotal = 0;
		double moneyMarketTotal = 0;
		double savingsTotal = 0;
		
		for(int i = 0; i < assets.size(); i++) {
			ShortTermAsset asset = assets.get(i);
			if(asset.getAccountType() == AccountType.Cash) {
				cashTotal += asset.getAmount();
			} else if(asset.getAccountType() == AccountType.Cds) {
				cdsTotal += asset.getAmount();
			} else if(asset.getAccountType() == AccountType.Checking) {
				checkingTotal += asset.getAmount();
			} else if(asset.getAccountType() == AccountType.High_Yield_Savings) {
				highYieldTotal += asset.getAmount();
			} else if(asset.getAccountType() == AccountType.Money_Market) {
				moneyMarketTotal += asset.getAmount();
			} else if(asset.getAccountType() == AccountType.Savings) {
				savingsTotal += asset.getAmount();
			}
		}
		
		double total = cashTotal + cdsTotal + checkingTotal + highYieldTotal + moneyMarketTotal + savingsTotal; 
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
		totalShortTermAssets = new Label("$" + decimalFormat.format(total));
		totalShortTermAssets.setStyle("-fx-font-size: 30");
		totalShortTermAssets.setAlignment(Pos.TOP_CENTER);
		totalShortTermAssets.setPadding(new Insets(0,0,0,30));
		grid.add(totalShortTermAssets, 1, 3);
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
				   new PieChart.Data("Cash", cashTotal), 
				   new PieChart.Data("CD's", cdsTotal), 
				   new PieChart.Data("Checking", checkingTotal), 
				   new PieChart.Data("High Yield Savings", highYieldTotal),
				   new PieChart.Data("Money Market", moneyMarketTotal),
				   new PieChart.Data("S"
				   		+ "avings", savingsTotal)); 
		
		pieChart = new PieChart(pieChartData);
		pieChart.setLegendVisible(false);
		
		pieChart.setPadding(new Insets(30, 0, 0, 0));
		
		grid.add(pieChart, 1, 1);
		
	}

	private void createTable() {
		
		shortTermAssetsTable = new TableView<ShortTermAsset>();
		
		shortTermAssetsTable.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		shortTermAssetsTable.setPrefHeight(575);
		
		TableColumn<ShortTermAsset, String> bank = new TableColumn<ShortTermAsset, String>("Bank");
        bank.setCellValueFactory(new PropertyValueFactory<>("bank"));
		TableColumn<ShortTermAsset,String> accountName = new TableColumn<ShortTermAsset,String>("Account Name");
		accountName.setCellValueFactory(new PropertyValueFactory<>("accountName"));
		TableColumn<ShortTermAsset, AccountType> accountType = new TableColumn<ShortTermAsset,AccountType>("Asset Type");
		accountType.setCellValueFactory(new PropertyValueFactory<>("accountType"));
		TableColumn<ShortTermAsset, String> totalAmount = new TableColumn<ShortTermAsset, String>("Total Amount");
		totalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
		
		ObservableList<ShortTermAsset> assetList = FXCollections.observableArrayList();
        
        ArrayList<ShortTermAsset> array = user.returnShortTermAssets().returnShortTermAssets();
        
       
        for(ShortTermAsset asset : array) {
        	assetList.add(asset);
        }
        
        shortTermAssetsTable.setItems(assetList);
        
		
		shortTermAssetsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		shortTermAssetsTable.getColumns().add(bank);
		shortTermAssetsTable.getColumns().add(accountName);
		shortTermAssetsTable.getColumns().add(accountType);
		shortTermAssetsTable.getColumns().add(totalAmount);
		
		
		bank.prefWidthProperty().bind(shortTermAssetsTable.widthProperty().multiply(0.25));
		bank.setStyle("-fx-alignment: CENTER;");
        accountName.prefWidthProperty().bind(shortTermAssetsTable.widthProperty().multiply(0.25));
        accountType.prefWidthProperty().bind(shortTermAssetsTable.widthProperty().multiply(0.25));
        totalAmount.prefWidthProperty().bind(shortTermAssetsTable.widthProperty().multiply(0.245));

        bank.setResizable(false);
        accountName.setResizable(false);
        accountType.setResizable(false);
        totalAmount.setResizable(false);
        
        
        addAsset = new Button("Add Asset");
        addAsset.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
               addAsset();
               refresh();
            }
        });
        edit = new Button("Edit");
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(shortTermAssetsTable.getSelectionModel().getSelectedItem() != null) {
            		new EditShortTermAssetsController(shortTermAssetsTable.getSelectionModel().getSelectedItem());
            		refresh();
            	}
            }
        });
        delete = new Button("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(shortTermAssetsTable.getSelectionModel().getSelectedItem() != null) {
            		
            		user.returnShortTermAssets().removeShortTermAsset(shortTermAssetsTable.getSelectionModel().getSelectedItem());
            		shortTermAssetsTable.getItems().remove(shortTermAssetsTable.getSelectionModel().getSelectedItem());
            		shortTermAssetsTable.getSelectionModel().clearSelection();
            		refresh();
            	}
            }
        });
        
        GridPane buttons = new GridPane();
        
        
        
        buttons.add(addAsset, 0, 0);
        buttons.add(edit, 2, 0);
        buttons.add(delete, 4, 0);
        
        buttons.setPadding(new Insets(20, 0, 0, 20));
        
        buttons.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        
        vbox.getChildren().add(shortTermAssetsTable);
        VBox.setMargin(shortTermAssetsTable, new Insets(0,0,0,20));
        vbox.getChildren().add(buttons);
	}

	
	
	@FXML
	public void openMain() {
		Main.setView(new MainController());
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
	
	public void addAsset() {
		new AddShortTermAssetController();
	}

	public static void addAssetToTable(ShortTermAsset asset) {
		shortTermAssetsTable.getItems().add(asset);
	}

	public static void removeAssetFromTable(ShortTermAsset asset) {
		shortTermAssetsTable.getItems().remove(asset);
	}
	
	public static void refresh() {
		ArrayList<ShortTermAsset> assets = user.returnShortTermAssets().returnShortTermAssets();
		
		double cashTotal = 0;
		double cdsTotal = 0;
		double checkingTotal = 0;
		double highYieldTotal = 0;
		double moneyMarketTotal = 0;
		double savingsTotal = 0;
		
		for(int i = 0; i < assets.size(); i++) {
			ShortTermAsset asset = assets.get(i);
			if(asset.getAccountType() == AccountType.Cash) {
				cashTotal += asset.getAmount();
			} else if(asset.getAccountType() == AccountType.Cds) {
				cdsTotal += asset.getAmount();
			} else if(asset.getAccountType() == AccountType.Checking) {
				checkingTotal += asset.getAmount();
			} else if(asset.getAccountType() == AccountType.High_Yield_Savings) {
				highYieldTotal += asset.getAmount();
			} else if(asset.getAccountType() == AccountType.Money_Market) {
				moneyMarketTotal += asset.getAmount();
			} else if(asset.getAccountType() == AccountType.Savings) {
				savingsTotal += asset.getAmount();
			}
		}
		
		double total = cashTotal + cdsTotal + checkingTotal + highYieldTotal + moneyMarketTotal + savingsTotal; 
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
		totalShortTermAssets.setText("$" + decimalFormat.format(total));
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
				   new PieChart.Data("Cash", cashTotal), 
				   new PieChart.Data("CD's", cdsTotal), 
				   new PieChart.Data("Checking", checkingTotal), 
				   new PieChart.Data("High Yield Savings", highYieldTotal),
				   new PieChart.Data("Money Market", moneyMarketTotal),
				   new PieChart.Data("S"
				   		+ "avings", savingsTotal)); 
		
		pieChart.getData().clear();
		pieChart.getData().addAll(pieChartData);
		pieChart.setLegendVisible(false);
		
		pieChart.setPadding(new Insets(30, 0, 0, 0));
	}
}
