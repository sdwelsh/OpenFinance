/**
 * 
 */
package application.popup;

import java.io.IOException;
import java.util.ArrayList;

import application.LongTermAssetsController;
import application.manager.Manager;
import application.users.User;
import data.assets.longterm.LongTermAsset;
import data.assets.longterm.LongTermAsset.AccountType;
import data.assets.longterm.LongTermAsset.Bank;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Stephen Welsh
 *
 */
public class AddStockController extends BorderPane{
	
	private User user;
	
	@FXML
	private GridPane grid;
	
	private ChoiceBox<String> investmentBank;
	
	private ChoiceBox<String> accountType;
	
	private Stage primaryStage;
	
	@FXML
	private TextField ticker;
	
	@FXML
	private TextField initPrice;
	
	@FXML
	private TextField number;
	
	@FXML
	private Label error;
	
	
	public AddStockController() {
		
		primaryStage = new Stage();
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddStock.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        user = Manager.getInstance().getCurrentUser();
        
       

        try {
            BorderPane pane = fxmlLoader.load();
            Scene scene = new Scene(pane);
    		
    		
    		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    		//primaryStage.initStyle(StageStyle.DECORATED);
    		primaryStage.setScene(scene);
    		primaryStage.initModality(Modality.WINDOW_MODAL);
    		primaryStage.show();
    		
    		primaryStage.setFullScreen(false);
    		
    		
    		
            
            investmentBank = new ChoiceBox<String>();
            accountType = new ChoiceBox<String>();
            
            investmentBank.getItems().addAll("Schwab", "Merril Lynch", "Vanguard", "TD Ameritrade", "Fidelity", "Bank", "Robinhood", "E-Trade");
            
            accountType.getItems().addAll("Brokerage", "Roth 401K", "Roth IRA", "IRA", "Taxable Account");
            
            grid.add(investmentBank, 1, 4);
            grid.add(accountType, 1, 5);
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	@FXML
	public void submit() {
		try {
			System.out.println(1);
			LongTermAsset stock = new LongTermAsset(ticker.getText(), Double.parseDouble(initPrice.getText()), 
					Double.parseDouble(number.getText()), getBank(), getAccount());
			
			user.addLongTermAsset(stock);
			System.out.println(user.getStocks()[0][0]);
			LongTermAssetsController.getInstance().refresh();
			
			primaryStage.close();
		} catch(IllegalArgumentException e) {
			System.out.print(e.getStackTrace());
			error.setText("Exception Caught");
		}
	}

	private AccountType getAccount() {
		if(accountType.getValue().equals("Brokerage")) {
			System.out.println(2);
			return AccountType.BROKERAGE;
		} else if(accountType.getValue().equals("Roth 401K")) {
			System.out.println(2);
			return AccountType.ROTH401K;
		} else if(accountType.getValue().equals("Roth IRA")) {
			System.out.println(2);
			return AccountType.ROTH_IRA;
		} else if(accountType.getValue().equals("IRA")) {
			System.out.println(2);
			return AccountType.IRA;
		} else if(accountType.getValue().equals("Taxable Account")) {
			System.out.println(2);
			return AccountType.TAXABLE_ACCOUNT;
		} else {
			System.out.println(2);
			return null;
		}
	}

	private Bank getBank() {
		if(investmentBank.getValue().equals("Schwab")) {
			System.out.println(3);
			return Bank.SCHWAB;
		} else if(investmentBank.getValue().equals("Merril Lynch")) {
			System.out.println(3);
			return Bank.MERRIL_LYNCH;
		} else if(investmentBank.getValue().equals("Vanguard")) {
			System.out.println(3);
			return Bank.VANGUARD;
		} else if(investmentBank.getValue().equals("TD Ameritrade")) {
			System.out.println(3);
			return Bank.TD;
		} else if(investmentBank.getValue().equals("Fidelity")) {
			System.out.println(3);
			return Bank.FIDELITY;
		} else if(investmentBank.getValue().equals("Bank")) {
			System.out.println(3);
			return Bank.BANK;
		} else if(investmentBank.getValue().equals("Robinhood")) {
			System.out.println(3);
			return Bank.ROBINHOOD;
		} else if(investmentBank.getValue().equals("E-Trade")) {
			System.out.println(3);
			return Bank.ETRADE;
		}else {
			System.out.println(3);
			return null;
		}
	}
}
