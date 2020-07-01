/**
 * 
 */
package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.manager.Manager;
import application.popup.AddStockController;
import application.users.User;
import data.assets.longterm.LongTermAsset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	
	private ScrollPane pane;
	
	@FXML
	private VBox size;
	
	private VBox vbox;
	
	private static LongTermAssetsController lta;
	
	
	public LongTermAssetsController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LongTermAssets.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        user = Manager.getInstance().getCurrentUser();

        try {
            fxmlLoader.load();
            pane = new ScrollPane();
            pane.setFitToHeight(true);
            pane.setFitToWidth(true);
            size.getChildren().add(pane);
            vbox = new VBox();
            pane.setContent(vbox);
            addStocksTable();
            lta = this;
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	
	public static LongTermAssetsController getInstance() {
		return lta;
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
		
		stockTable.setPrefHeight(pane.getHeight());
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
        
        vbox.getChildren().add(stockTable);
		
	}
	
	@FXML 
	public void addStock() {
		new AddStockController();
	}
	
	@FXML 
	public void addEtf() {
		
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
		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
        
        ArrayList<LongTermAsset> array = user.getLongTermAssets().returnStocks();
        
        for(int i = 0; i < array.size(); i++) {
        	assetList.add(array.get(i));
        }
        
        stockTable.setItems(assetList);
	}
}
