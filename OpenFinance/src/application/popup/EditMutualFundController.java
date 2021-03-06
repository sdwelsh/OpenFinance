/**
 * 
 */
package application.popup;

import java.io.IOException;

import application.LongTermAssetsController;
import application.manager.Manager;
import application.users.User;
import data.assets.longterm.ETF;
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
public class EditMutualFundController extends BorderPane{

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
	
	private MutualFunds asset;
	
	
	public EditMutualFundController(MutualFunds asset) {
		
		primaryStage = new Stage();
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditMutualFund.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        user = Manager.getInstance().getCurrentUser();
        
        this.asset = asset;

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
            countryType = new ChoiceBox<String>();
            capType = new ChoiceBox<String>();
            investmentType = new ChoiceBox<String>();
            
            investmentBank.getItems().addAll("Schwab", "Merril_Lynch", "Vanguard", "TD_Ameritrade", "Fidelity", "Bank", "Robinhood", "E-Trade");
            accountType.getItems().addAll("Brokerage", "Roth_401K", "Roth_IRA", "IRA", "401K", "Taxable_Account");
            countryType.getItems().addAll("Domestic", "Foreign");
            capType.getItems().addAll("Small_Cap", "Mid_Cap", "Large_Cap");
            investmentType.getItems().addAll("NA", "Value", "Growth");
            investmentBank.getSelectionModel().select(asset.getBankString());
            accountType.getSelectionModel().select(asset.getAccountString());
            countryType.getSelectionModel().select(asset.getCountryString());
            capType.getSelectionModel().select(asset.getCapString());
            investmentType.getSelectionModel().select(asset.getInvestmentTypeString());
            
            
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
			LongTermAsset stock = new ETF(ticker.getText(), Double.parseDouble(initPrice.getText()), 
					Double.parseDouble(number.getText()), getBank(), getAccount(), getCounty(), getCap(), getInvType());
			
			user.deleteLongTermAsset(asset);
			
			user.addLongTermAsset(stock);
			LongTermAssetsController.removeMutualFundfromTable(asset, stock);
			
			primaryStage.close();
		} catch(IllegalArgumentException e) {
			System.out.print(e.getStackTrace());
			error.setText("Exception Caught");
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
		if(capType.getValue().equals("Small_Cap")) {
			return CapType.SMALL_CAP;
		} else if(capType.getValue().equals("Mid_Cap")) {
			return CapType.MID_CAP;
		}  else if(capType.getValue().equals("Large_Cap")) {
			return CapType.LARGE_CAP;
		}  else {
			return null;
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
			throw new IllegalArgumentException();
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
