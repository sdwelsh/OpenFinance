/**
 * 
 */
package application.popup;

import java.io.IOException;

import application.ShortTermAssetsController;
import application.manager.Manager;
import application.users.User;
import data.assets.shortTerm.ShortTermAsset;
import data.assets.shortTerm.ShortTermAsset.AccountType;
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
public class AddShortTermAssetController extends BorderPane{
	private User user;
	
	@FXML
	private GridPane grid;
	
	private ChoiceBox<AccountType> accountType;
	
	private Stage primaryStage;
	
	@FXML
	private TextField bank;
	
	@FXML
	private TextField accountName;
	
	@FXML
	private TextField totalAmount;
	
	@FXML
	private Label error;
	
	
	public AddShortTermAssetController() {
		
		primaryStage = new Stage();
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddShortTermAsset.fxml"));
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
    		
    		
    		
    		
            
            accountType = new ChoiceBox<AccountType>();
            
            
            accountType.getItems().addAll(AccountType.Cash, AccountType.Cds, AccountType.Checking, AccountType.High_Yield_Savings, 
            		AccountType.Money_Market, AccountType.Savings);
            
            Label label = new Label("Select an Account Type");
            
            grid.add(label, 1, 5);
            grid.add(accountType, 1, 6);
            grid.getStylesheets().add(getClass().getResource("/button.css").toExternalForm());
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	@FXML
	public void submit() {
		try {
			
			if(accountType.getValue() == null) {
				throw new IllegalArgumentException("Enter an Account Type");
			}
			
			double total = 0;
			try {
				total =  Double.parseDouble(totalAmount.getText());
			}catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("Enter a valid total");
			}
			
			ShortTermAsset asset = new ShortTermAsset(bank.getText(), 
					accountName.getText(), accountType.getValue(),total);
			
			user.returnShortTermAssets().addShortTermAsset(asset);
			ShortTermAssetsController.addAssetToTable(asset);
			ShortTermAssetsController.refresh();
			primaryStage.close();
		} catch(IllegalArgumentException e) {
			error.setText(e.getMessage());
		}
	}
}
