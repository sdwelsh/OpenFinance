/**
 * 
 */
package application;

import java.io.IOException;

import application.manager.Manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * @author Stephen Welsh
 *
 */
public class MainController extends BorderPane{

	
	@FXML
	private AreaChart <?, ?> netWorthChart;
	
	@FXML
	private Label name;
	
	public MainController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        Manager manager = Manager.getInstance();

        try {
            fxmlLoader.load();
            name.setText("Welcome " + manager.getCurrentUser().getFirstName() + " " + manager.getCurrentUser().getLastName());
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	@FXML
	public void longTermAssets() {
		Main.setView(new LongTermAssetsController());
	}
	
	@FXML
	public void logout() {
		Manager manager = Manager.getInstance();
		manager.logout();
		Main.login(new LoginController());
	}
}
