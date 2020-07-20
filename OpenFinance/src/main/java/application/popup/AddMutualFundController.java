/**
 * 
 */
package application.popup;

import java.io.IOException;

import application.LongTermAssetsController;
import application.manager.Manager;
import application.users.User;
import data.assets.longterm.LongTermAsset;
import data.assets.longterm.ETF.CapType;
import data.assets.longterm.ETF.CountryType;
import data.assets.longterm.ETF.InvestmentType;
import data.assets.longterm.LongTermAsset.AccountType;
import data.assets.longterm.LongTermAsset.Bank;
import data.assets.longterm.MutualFunds;
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
public class AddMutualFundController extends BorderPane {
	
	private User user;
	
	@FXML
	private GridPane grid;
	
	private ChoiceBox<String> investmentBank;
	
	private ChoiceBox<String> accountType;
	
	private ChoiceBox<String> countryType;
	
	private ChoiceBox<String> capType;
	
	private ChoiceBox<String> investmentType;
	
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
	
	
	public AddMutualFundController() {
		
		primaryStage = new Stage();
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddMutualFund.fxml"));
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
            countryType = new ChoiceBox<String>();
            capType = new ChoiceBox<String>();
            investmentType = new ChoiceBox<String>();
            
            investmentBank.getItems().addAll("Select an Investment Bank", "Schwab", "Merril Lynch", "Vanguard", "TD Ameritrade", "Fidelity", "Bank", "Robinhood", "E-Trade");
            accountType.getItems().addAll("Select an Account Type", "Brokerage", "Roth 401K", "Roth IRA", "IRA", "401K", "Taxable Account");
            countryType.getItems().addAll("Select a Country Type", "Domestic", "Foreign");
            capType.getItems().addAll("Select a Cap Type", "Small Cap", "Mid Cap", "Large Cap", "NA");
            investmentType.getItems().addAll("Select an Investment Type", "NA", "Value", "Growth");
            
            investmentBank.getSelectionModel().select("Select an Investment Bank");
            accountType.getSelectionModel().select("Select an Account Type");
            countryType.getSelectionModel().select("Select a Country Type");
            capType.getSelectionModel().select("Select a Cap Type");
            investmentType.getSelectionModel().select("Select an Investment Type");
            
            
            grid.add(investmentBank, 1, 4);
            grid.add(accountType, 1, 5);
            grid.add(capType, 1, 6);
            grid.add(investmentType, 1, 7);
            grid.add(countryType, 1, 8);
            
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
			} else if(countryType.getValue().equals("Select a Country Type")) {
				throw new IllegalArgumentException("Please select a Country Type");
			} else if(capType.getValue().equals("Select a Cap Type")) {
				throw new IllegalArgumentException("Please select a Cap Type");
			} else if(investmentType.getValue().equals("Select an Investment Type")) {
				throw new IllegalArgumentException("please select an Investment Type");
			}
			
			double numberDouble = 0;
			double price = 0;
			
			try {
				numberDouble = Double.parseDouble(number.getText());
			} catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("Enter a valid quantity");
			}
			
			try {
				price = Double.parseDouble(initPrice.getText());
			} catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("Enter a valid Initial Price");
			}
			
			LongTermAsset stock = new MutualFunds(ticker.getText(), price, 
					numberDouble, getBank(), getAccount(), accountName.getText(), 0, 
					reinvestDividends.isSelected(),
					getCounty(), getCap(), getInvType());
			
			user.addLongTermAsset(stock);
			LongTermAssetsController.addMutualFundToTable(stock);
			
			primaryStage.close();
		} catch(IllegalArgumentException e) {
			error.setText(e.getMessage());
		}
	}

	private CountryType getCounty() {
		if(countryType.getValue().equals("Domestic")) {
			return CountryType.DOMESTIC;
		} else if(countryType.getValue().equals("Foreign")){
			return CountryType.FOREIGN;
		} else {
			return null;
		}
	}

	private InvestmentType getInvType() {
		if(investmentType.getValue().equals("NA")) {
			return InvestmentType.NA;
		} else if(investmentType.getValue().equals("Growth")) {
			return InvestmentType.GROWTH;
		}  else if(investmentType.getValue().equals("Value")) {
			return InvestmentType.GROWTH;
		}  else {
			return null;
		}
	}

	private CapType getCap() {
		if(capType.getValue().equals("Small Cap")) {
			return CapType.SMALL_CAP;
		} else if(capType.getValue().equals("Mid Cap")) {
			return CapType.MID_CAP;
		}  else if(capType.getValue().equals("Large Cap")) {
			return CapType.LARGE_CAP;
		}  else if(capType.getValue().equals("NA")) {
			return CapType.NA;
		} else {
			return null;
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
			throw new IllegalArgumentException();
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
