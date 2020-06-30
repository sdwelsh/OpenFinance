/**
 * 
 */
package application;

import java.io.IOException;

import application.manager.Manager;
import application.users.User;
import data.assets.longterm.LongTermAsset;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Stephen Welsh
 *
 */
public class LongTermAssetsController extends BorderPane {
	
	/** get the current user */
	private User user;
	
	/** Displays the long term assets on the account */
	private TableView<LongTermAsset> stockTable;
	
	@FXML
	private ScrollPane pane;
	
	
	public LongTermAssetsController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LongTermAssets.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        user = Manager.getInstance().getCurrentUser();

        try {
            fxmlLoader.load();
            addStocksTable();
            
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
		TableColumn<LongTermAsset,String> ticker = new TableColumn<LongTermAsset,String>("Ticker");
		TableColumn<LongTermAsset,String> price = new TableColumn<LongTermAsset,String>("Price");
		TableColumn<LongTermAsset,String> quantity = new TableColumn<LongTermAsset,String>("Quantity");
		TableColumn<LongTermAsset,String> pricePerShare = new TableColumn<LongTermAsset,String>("Price per Share");
		TableColumn<LongTermAsset,String> years = new TableColumn<LongTermAsset,String>("Years to Maturity");
		//TableColumn<LongTermAsset,String> future = new TableColumn<LongTermAsset,String>("Future Value");
		TableColumn<LongTermAsset,String> bank = new TableColumn<LongTermAsset,String>("Bank");
		TableColumn<LongTermAsset,String> accountType = new TableColumn<LongTermAsset,String>("Account Type");
		
		stockTable.getColumns().add(ticker);
		stockTable.getColumns().add(price);
		stockTable.getColumns().add(quantity);
		stockTable.getColumns().add(pricePerShare);
		stockTable.getColumns().add(years);
		stockTable.getColumns().add(bank);
		stockTable.getColumns().add(accountType);
		
		stockTable.setPrefHeight(600);
		stockTable.setPrefWidth(1200);
		
		ticker.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.05));
        price.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.2));
        quantity.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.1));
        pricePerShare.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.1));
        years.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.1));
        bank.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.1));
        accountType.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.2));

        ticker.setResizable(false);
        price.setResizable(false);
        quantity.setResizable(false);
        pricePerShare.setResizable(false);
        years.setResizable(false);
        bank.setResizable(false);
        accountType.setResizable(false);
		
		
		pane.setContent(stockTable);
	}
	
	@FXML 
	public void addStock() {
		
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
}
