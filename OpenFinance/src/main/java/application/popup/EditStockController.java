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
public class EditStockController extends BorderPane{
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
	
	private LongTermAsset editedAsset;
	
	@FXML private TextField accountName;
	
	@FXML private CheckBox reinvestDividends;
	
	
	public EditStockController(LongTermAsset asset) {
		
		primaryStage = new Stage();
		
		editedAsset = asset;
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditStock.fxml"));
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
    		
    		ticker.setText(asset.getTicker());
    		initPrice.setText(asset.getInitPrice() + "");
    		number.setText(asset.getQuantity() + "");
    		
            
            investmentBank = new ChoiceBox<String>();
            accountType = new ChoiceBox<String>();
            
            investmentBank.getItems().addAll("Schwab", "Merril_Lynch", "Vanguard", "TD_Ameritrade", "Fidelity", "Bank", "Robinhood", "E-Trade");
            investmentBank.getSelectionModel().select(asset.getBankString());
            
            accountType.getItems().addAll("Brokerage", "Roth_401K", "Roth_IRA", "IRA", "401K", "Taxable_Account");
            accountType.getSelectionModel().select(asset.getAccountString());
            
            grid.add(investmentBank, 1, 4);
            grid.add(accountType, 1, 5);
            
            accountName.setText(asset.getAccountNameString());
            
            if(asset.isReinvestDividends()) {
            	reinvestDividends.setSelected(true);
            }
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

	@FXML
	public void submit() {
		try {

			LongTermAsset stock = new LongTermAsset(ticker.getText(), Double.parseDouble(initPrice.getText()), 
					Double.parseDouble(number.getText()), getBank(), getAccount(), accountName.getText(), 0, reinvestDividends.isSelected());
			
			user.deleteLongTermAsset(editedAsset);
			LongTermAssetsController.removeStockFromTable(editedAsset, stock);
			
			user.addLongTermAsset(stock);
			
			
			primaryStage.close();
		} catch(IllegalArgumentException e) {
			System.out.print(e.getStackTrace());
			error.setText("Exception Caught");
		}
	}

	private AccountType getAccount() {
		if(accountType.getValue().equals("Brokerage")) {
			return AccountType.BROKERAGE;
		} else if(accountType.getValue().equals("Roth_401K")) {
			return AccountType.ROTH401K;
		} else if(accountType.getValue().equals("Roth_IRA")) {
			return AccountType.ROTH_IRA;
		} else if(accountType.getValue().equals("IRA")) {
			return AccountType.IRA;
		} else if(accountType.getValue().equals("401K")) {
			return AccountType._401K;
		} else if(accountType.getValue().equals("Taxable_Account")) {
			return AccountType.TAXABLE_ACCOUNT;
		} else {
			return null;
		}
	}

	private Bank getBank() {
		if(investmentBank.getValue().equals("Schwab")) {
			return Bank.SCHWAB;
		} else if(investmentBank.getValue().equals("Merril_Lynch")) {
			return Bank.MERRIL_LYNCH;
		} else if(investmentBank.getValue().equals("Vanguard")) {
			return Bank.VANGUARD;
		} else if(investmentBank.getValue().equals("TD_Ameritrade")) {
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
