/**
 * 
 */
package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.manager.Manager;
import application.popup.AddETFController;
import application.popup.AddStockController;
import application.users.User;
import data.assets.longterm.LongTermAsset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Stephen Welsh
 *
 */
public class LongTermAssetsController extends BorderPane {
	
	/** get the current user */
	private static User user;
	
	/** Displays the long term assets on the account */
	private static TableView<LongTermAsset> stockTable;
	
	/** Displays the long term assets on the account */
	private static TableView<LongTermAsset> etfTable;
	
	/** Displays the long term assets on the account */
	private static TableView<LongTermAsset> mutualFundTable;
	
	
	private ScrollPane pane;
	
	@FXML
	private ImageView logoMain;
	
	@FXML
	private VBox size;
	
	private VBox vbox;
	
	
	public LongTermAssetsController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LongTermAssets.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        user = Manager.getInstance().getCurrentUser();

        try {
            fxmlLoader.load();
            
            Image thumb = new Image("/application/resources/linkedin_banner_image_1.png");
            
            logoMain.setImage(thumb);
            
            pane = new ScrollPane();
            pane.setFitToHeight(true);
            pane.setFitToWidth(true);
            size.getChildren().add(pane);
            vbox = new VBox();
            pane.setContent(vbox);
            addStocksTable();
            addMutualFundsTable();
            addETFTable();
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	@FXML
	public void logout() {
		Manager manager = Manager.getInstance();
		manager.logout();
		Main.login(new LoginController());
	}
	
	public void addStocksTable() {
		
		stockTable = new TableView<LongTermAsset>();

        TableColumn<LongTermAsset, String> ticker = new TableColumn<LongTermAsset, String>("Ticker");
        ticker.setCellValueFactory(new PropertyValueFactory<>("tickerString"));
		TableColumn<LongTermAsset,String> price = new TableColumn<LongTermAsset,String>("Price");
		price.setCellValueFactory(new PropertyValueFactory<>("totalPriceString"));
		TableColumn<LongTermAsset,String> quantity = new TableColumn<LongTermAsset,String>("Quantity");
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantityString"));
		TableColumn<LongTermAsset,String> pricePerShare = new TableColumn<LongTermAsset,String>("Price per Share");
		pricePerShare.setCellValueFactory(new PropertyValueFactory<>("pricePerShareString"));
		TableColumn<LongTermAsset,String> bank = new TableColumn<LongTermAsset,String>("Bank");
		bank.setCellValueFactory(new PropertyValueFactory<>("bankString"));
		TableColumn<LongTermAsset,String> accountType = new TableColumn<LongTermAsset,String>("Account Type");
		accountType.setCellValueFactory(new PropertyValueFactory<>("accountString"));
		
		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
        
        ArrayList<LongTermAsset> array = user.getLongTermAssets().returnStocks();
        
        for(int i = 0; i < array.size(); i++) {
        	assetList.add(array.get(i));
        }
        
        stockTable.setItems(assetList);
        
		
		stockTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		stockTable.getColumns().add(ticker);
		stockTable.getColumns().add(price);
		stockTable.getColumns().add(quantity);
		stockTable.getColumns().add(pricePerShare);
		stockTable.getColumns().add(bank);
		stockTable.getColumns().add(accountType);
		
		stockTable.setPrefHeight(array.size() * 50);
		stockTable.setPrefWidth(pane.getWidth());
		
		ticker.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.05));
		ticker.setStyle("-fx-alignment: CENTER;");
        price.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.2));
        quantity.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.1));
        pricePerShare.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.1));
        bank.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.1));
        accountType.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.2));

        ticker.setResizable(false);
        price.setResizable(false);
        quantity.setResizable(false);
        pricePerShare.setResizable(false);
        bank.setResizable(false);
        accountType.setResizable(false);
        
        stockTable.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        Label label = new Label("Stocks");
        
        label.setStyle("-fx-font-size:44px;-fx-font-weight: bold;");
        label.setPadding(new Insets(20));
        
        vbox.getChildren().add(label);
        
        vbox.getChildren().add(stockTable);
		
	}
	
	public void addMutualFundsTable() {
		
		mutualFundTable = new TableView<LongTermAsset>();

        TableColumn<LongTermAsset, String> ticker = new TableColumn<LongTermAsset, String>("Ticker");
        ticker.setCellValueFactory(new PropertyValueFactory<>("tickerString"));
		TableColumn<LongTermAsset,String> price = new TableColumn<LongTermAsset,String>("Price");
		price.setCellValueFactory(new PropertyValueFactory<>("totalPriceString"));
		TableColumn<LongTermAsset,String> quantity = new TableColumn<LongTermAsset,String>("Quantity");
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantityString"));
		TableColumn<LongTermAsset,String> pricePerShare = new TableColumn<LongTermAsset,String>("Price per Share");
		pricePerShare.setCellValueFactory(new PropertyValueFactory<>("pricePerShareString"));
		TableColumn<LongTermAsset,String> bank = new TableColumn<LongTermAsset,String>("Bank");
		bank.setCellValueFactory(new PropertyValueFactory<>("bankString"));
		TableColumn<LongTermAsset,String> accountType = new TableColumn<LongTermAsset,String>("Account Type");
		accountType.setCellValueFactory(new PropertyValueFactory<>("accountString"));
		
		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
        
        ArrayList<LongTermAsset> array = user.getLongTermAssets().returnMutualFunds();
        
        for(int i = 0; i < array.size(); i++) {
        	assetList.add(array.get(i));
        }
        
        mutualFundTable.setItems(assetList);
        
		
		mutualFundTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		mutualFundTable.getColumns().add(ticker);
		mutualFundTable.getColumns().add(price);
		mutualFundTable.getColumns().add(quantity);
		mutualFundTable.getColumns().add(pricePerShare);
		mutualFundTable.getColumns().add(bank);
		mutualFundTable.getColumns().add(accountType);
		
		mutualFundTable.setPrefHeight(array.size() * 50);
		mutualFundTable.setPrefWidth(pane.getWidth());
		
		ticker.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.05));
		ticker.setStyle("-fx-alignment: CENTER;");
        price.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.2));
        quantity.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.1));
        pricePerShare.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.1));
        bank.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.1));
        accountType.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.2));

        ticker.setResizable(false);
        price.setResizable(false);
        quantity.setResizable(false);
        pricePerShare.setResizable(false);
        bank.setResizable(false);
        accountType.setResizable(false);
        
        mutualFundTable.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        Label label = new Label("Mutual Funds");
        
        label.setStyle("-fx-font-size:44px;-fx-font-weight: bold;");
        label.setPadding(new Insets(20));
        
        vbox.getChildren().add(label);
        
        vbox.getChildren().add(mutualFundTable);
		
	}
	
	public void addETFTable() {
		
		etfTable = new TableView<LongTermAsset>();

        TableColumn<LongTermAsset, String> ticker = new TableColumn<LongTermAsset, String>("Ticker");
        ticker.setCellValueFactory(new PropertyValueFactory<>("tickerString"));
		TableColumn<LongTermAsset,String> price = new TableColumn<LongTermAsset,String>("Price");
		price.setCellValueFactory(new PropertyValueFactory<>("totalPriceString"));
		TableColumn<LongTermAsset,String> quantity = new TableColumn<LongTermAsset,String>("Quantity");
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantityString"));
		TableColumn<LongTermAsset,String> pricePerShare = new TableColumn<LongTermAsset,String>("Price per Share");
		pricePerShare.setCellValueFactory(new PropertyValueFactory<>("pricePerShareString"));
		TableColumn<LongTermAsset,String> bank = new TableColumn<LongTermAsset,String>("Bank");
		bank.setCellValueFactory(new PropertyValueFactory<>("bankString"));
		TableColumn<LongTermAsset,String> accountType = new TableColumn<LongTermAsset,String>("Account Type");
		accountType.setCellValueFactory(new PropertyValueFactory<>("accountString"));
		TableColumn<LongTermAsset,String> capType = new TableColumn<LongTermAsset,String>("Cap Type");
		capType.setCellValueFactory(new PropertyValueFactory<>("capString"));
		TableColumn<LongTermAsset,String> investmentType = new TableColumn<LongTermAsset,String>("Investment Type");
		investmentType.setCellValueFactory(new PropertyValueFactory<>("investmentTypeString"));
		TableColumn<LongTermAsset,String> countryType = new TableColumn<LongTermAsset,String>("Country Type");
		countryType.setCellValueFactory(new PropertyValueFactory<>("countryString"));
		
		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
        
        ArrayList<LongTermAsset> array = user.getLongTermAssets().returnETFs();
        
        for(int i = 0; i < array.size(); i++) {
        	assetList.add(array.get(i));
        }
        
        etfTable.setItems(assetList);
        
		
		etfTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		etfTable.getColumns().add(ticker);
		etfTable.getColumns().add(price);
		etfTable.getColumns().add(quantity);
		etfTable.getColumns().add(pricePerShare);
		etfTable.getColumns().add(bank);
		etfTable.getColumns().add(accountType);
		etfTable.getColumns().add(capType);
		etfTable.getColumns().add(investmentType);
		etfTable.getColumns().add(countryType);
		
		etfTable.setPrefHeight(array.size() * 50);
		etfTable.setPrefWidth(pane.getWidth());
		
		ticker.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.05));
		ticker.setStyle("-fx-alignment: CENTER;");
        price.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.2));
        quantity.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));
        pricePerShare.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));
        bank.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));
        accountType.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));
        capType.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));
        investmentType.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));
        countryType.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));

        ticker.setResizable(false);
        price.setResizable(false);
        quantity.setResizable(false);
        pricePerShare.setResizable(false);
        bank.setResizable(false);
        accountType.setResizable(false);
        capType.setResizable(false);
        investmentType.setResizable(false);
        countryType.setResizable(false);
        
        etfTable.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        Label label = new Label("ETF's");
        
        label.setStyle("-fx-font-size:44px;-fx-font-weight: bold;");
        label.setPadding(new Insets(20));
        
        vbox.getChildren().add(label);
        
        vbox.getChildren().add(etfTable);
		
	}
	
	
	
	@FXML 
	public void addStock() {
		new AddStockController();
	}
	
	@FXML 
	public void addEtf() {
		new AddETFController();
	}
	
	@FXML
	public void addMutual() {
		
	}
	
	@FXML
	public void edit() {
		
	}
	
	@FXML 
	public void delete() {
		
	}
	
	@FXML
	public void refresh() {
        
        
//        /** Refreshing the mutual fund table */
//        ObservableList<LongTermAsset> mutualList = FXCollections.observableArrayList();
//        
//        ArrayList<LongTermAsset> mutualFunds = user.getLongTermAssets().returnMutualFunds();
//        
//        for(int i = 0; i < mutualFunds.size(); i++) {
//        	mutualList.add(mutualFunds.get(i));
//        }
//        
//        mutualFundTable.setItems(mutualList);
//        mutualFundTable.setPrefHeight(mutualFunds.size()*30);
//        
//        
//        /** Refreshing the etf fund table */
//        ObservableList<LongTermAsset> etfList = FXCollections.observableArrayList();
//        
//        ArrayList<LongTermAsset> etfs = user.getLongTermAssets().returnMutualFunds();
//        
//        for(int i = 0; i < etfs.size(); i++) {
//        	etfList.add(etfs.get(i));
//        }
//        
//        mutualFundTable.setItems(etfList);
//        mutualFundTable.setPrefHeight(etfs.size()*30);
	}
	
	public static void addStockToTable(LongTermAsset stock) {
        
        stockTable.getItems().add(stock);
        stockTable.setPrefHeight(stockTable.getHeight() + 50);
	}
	
	public static void addETFToTable(LongTermAsset stock) {
		etfTable.getItems().add(stock);
		etfTable.setPrefHeight(etfTable.getHeight() + 50);
	}
	
	@FXML
	public void openMain() {
		Main.setView(new MainController());
	}
}
