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

public class AddShortTermLiabilityController extends BorderPane{
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
	
	
	public AddShortTermLiabilityController() {
		
		primaryStage = new Stage();
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddShortTermLiability.fxml"));
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
    		
    		
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	@FXML
	public void submit() {
		try {
			
			double total = 0;
			try {
				total = Double.parseDouble(totalAmount.getText());
			} catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("Enter a Valid Total");
			}
			
			Liability liability = new Liability(name.getText(), total, 1);
			
			user.returnLiabilities().addLiability(liability);
			ShortTermLiabilitiesController.addLiabilityToTable(liability);
			ShortTermLiabilitiesController.refresh();
			primaryStage.close();
		} catch(IllegalArgumentException e) {
			error.setText(e.getMessage());
		}
	}
}
