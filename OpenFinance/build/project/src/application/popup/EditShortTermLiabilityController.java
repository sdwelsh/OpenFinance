/**
 * 
 */
package application.popup;

import java.io.IOException;

import application.ShortTermLiabilitiesController;
import application.manager.Manager;
import application.users.User;
import data.liabilities.Liability;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
public class EditShortTermLiabilityController extends BorderPane {
private User user;
	
	@FXML
	private GridPane grid;
	
	private Stage primaryStage;
	
	@FXML
	private TextField name;
	
	@FXML
	private TextField totalAmount;
	
	@FXML
	private Label error;
	
	private Liability liability;
	
	
	public EditShortTermLiabilityController(Liability liability) {
		
		primaryStage = new Stage();
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditShortTermLiability.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        user = Manager.getInstance().getCurrentUser();
        
       this.liability = liability;

        try {
            BorderPane pane = fxmlLoader.load();
            Scene scene = new Scene(pane);
    		
    		
    		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    		//primaryStage.initStyle(StageStyle.DECORATED);
    		primaryStage.setScene(scene);
    		primaryStage.initModality(Modality.WINDOW_MODAL);
    		primaryStage.show();
    		
    		
    		name.setText(liability.getName());
    		totalAmount.setText(liability.getTotalAmount() + "");
    		
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	@FXML
	public void submit() {
		try {
			Liability liability = new Liability(name.getText(), Double.parseDouble(totalAmount.getText()), 1);
			
			user.returnLiabilities().deleteLiability(this.liability);
			user.returnLiabilities().addLiability(liability);
			ShortTermLiabilitiesController.removeLiabilityFromTable(this.liability);
			ShortTermLiabilitiesController.addLiabilityToTable(liability);;
			
			primaryStage.close();
		} catch(IllegalArgumentException e) {
			System.out.print(e.getStackTrace());
			error.setText("Exception Caught");
		}
	}
}
