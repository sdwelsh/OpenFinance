/**
 * 
 */
package application;

import java.io.IOException;
import java.text.DecimalFormat;

import application.manager.Manager;
import application.users.User;
import application.view.retirement.FutureValue;
import application.view.retirement.RetirementSpendingModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Stephen Welsh
 *
 */
public class RetirementController extends BorderPane{

	@FXML 
	private ImageView logoMain;
	
	private User user;
	
	@FXML private VBox vbox;
	
	@FXML private ScrollPane pane;
	
	

	public RetirementController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Retirement.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            
            vbox.getChildren().add(new FutureValue());
          
            
            Image thumb = new Image("/linkedin_banner_image_1.png");
            logoMain.setImage(thumb);
            
            user = Manager.getInstance().getCurrentUser();
            
    		
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	

	@FXML
	public void openMain() {
		Main.setView(new MainController());
	}
	
	@FXML
	public void longTermAssets() {
		Main.setView(new LongTermAssetsController());
	}
	
	@FXML
	public void shortTermAssets() {
		Main.setView(new ShortTermAssetsController());
	}
	
	@FXML
	public void longTermLiabilities() {
		Main.setView(new LongTermLiabilitiesController());
	}
	
	@FXML
	public void shortTermLiabilities() {
		Main.setView(new ShortTermLiabilitiesController());
	}
	
	@FXML
	public void retirement() {
		Main.setView(new RetirementController());
	}
	
	@FXML
	public void logout() {
		Manager manager = Manager.getInstance();
		manager.logout();
		Main.login(new LoginController());
	}
	
	
	
	@FXML
	private void futureValue() {
		vbox.getChildren().clear();
		vbox.getChildren().add(new FutureValue());
	}
	
	@FXML
	private void retirementSpendingModel() {
		vbox.getChildren().clear();
		vbox.getChildren().add(new RetirementSpendingModel());
	}
}
