/**
 * 
 */
package application.popup;

import java.io.IOException;

import application.LongTermAssetsController;
import application.manager.Manager;
import application.users.User;
import data.assets.longterm.LongTermAsset;
import data.assets.longterm.LongTermAsset.AccountType;
import data.assets.longterm.LongTermAsset.Bank;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
	
	@FXML private TextField accountName;
	
	@FXML private CheckBox reinvestDividends;
	
	
	public AddStockController() {
		
		primaryStage = new Stage();
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddStock.fxml"));
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
    		
    		
    		
    		
            
            investmentBank = new ChoiceBox<String>();
            accountType = new ChoiceBox<String>();
            
            investmentBank.getItems().addAll("Select an Investment Bank", "Schwab", "Merril Lynch", "Vanguard", "TD Ameritrade", "Fidelity", "Bank", "Robinhood", "E-Trade");
            accountType.getItems().addAll("Select an Account Type", "Brokerage", "Roth 401K", "Roth IRA", "IRA", "401K", "Taxable Account");
            
            investmentBank.getSelectionModel().select(0);
            accountType.getSelectionModel().select(0);
            
            grid.add(investmentBank, 1, 4);
            grid.add(accountType, 1, 5);
            grid.getStylesheets().add(getClass().getResource("/button.css").toExternalForm());
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	@FXML
	public void submit() {
		try {
			
			if(investmentBank.getValue().equals("Select an Investment Bank")) {
				throw new IllegalArgumentException("Please select an Investment Bank");
			} else if(accountType.getValue().equals("Select an Account Type")) {
				throw new IllegalArgumentException("Please select an Account Type");
			}
			
			double price = 0;
			double numberDouble = 0;
			
			try {
				price = Double.parseDouble(initPrice.getText());
			} catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("Enter a valid Initial Price");
			}
			
			try {
				numberDouble = Double.parseDouble(number.getText());
			} catch(IllegalArgumentException e){
				throw new IllegalArgumentException("Enter a valid Quanity of Stock");
			}
			
			LongTermAsset stock = new LongTermAsset(ticker.getText(), price, 
					numberDouble, getBank(), getAccount(), accountName.getText(), 0, reinvestDividends.isSelected());
			stock.initStock();
			user.addLongTermAsset(stock);
			LongTermAssetsController.addStockToTable(stock);;
			
			primaryStage.close();
		} catch(IllegalArgumentException e) {
			error.setText(e.getMessage());
		}
	}

	private AccountType getAccount() {
		if(accountType.getValue().equals("Brokerage")) {
			return AccountType.BROKERAGE;
		} else if(accountType.getValue().equals("Roth 401K")) {
			return AccountType.ROTH401K;
		} else if(accountType.getValue().equals("Roth IRA")) {
			return AccountType.ROTH_IRA;
		} else if(accountType.getValue().equals("IRA")) {
			return AccountType.IRA;
		} else if(accountType.getValue().equals("401K")) {
			return AccountType._401K;
		} else if(accountType.getValue().equals("Taxable Account")) {
			return AccountType.TAXABLE_ACCOUNT;
		} else {
			return null;
		}
	}

	private Bank getBank() {
		if(investmentBank.getValue().equals("Schwab")) {
			return Bank.SCHWAB;
		} else if(investmentBank.getValue().equals("Merril Lynch")) {
			return Bank.MERRIL_LYNCH;
		} else if(investmentBank.getValue().equals("Vanguard")) {
			return Bank.VANGUARD;
		} else if(investmentBank.getValue().equals("TD Ameritrade")) {
			return Bank.TD;
		} else if(investmentBank.getValue().equals("Fidelity")) {
			return Bank.FIDELITY;
		} else if(investmentBank.getValue().equals("Bank")) {
			return Bank.BANK;
		} else if(investmentBank.getValue().equals("Robinhood")) {
			return Bank.ROBINHOOD;
		} else if(investmentBank.getValue().equals("E-Trade")) {
			return Bank.ETRADE;
		}else {
			return null;
		}
	}
}
