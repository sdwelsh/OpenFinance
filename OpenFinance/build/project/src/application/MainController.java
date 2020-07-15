/**
 * 
 */
package application;

import java.io.IOException;
import java.text.DecimalFormat;

import application.manager.Manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * @author Stephen Welsh
 *
 */
public class MainController extends BorderPane{

	
	@FXML
	private AreaChart <?, ?> netWorthChart;
	
	@FXML
	private Label name;
	
	@FXML 
	private ImageView logoMain;
	
	@FXML private Label longTermAssets;
	
	@FXML private Label shortTermAssets;
	
	@FXML private Label totalAssets;
	
	@FXML private Label longTermLiabilitiesString;
	
	@FXML private Label shortTermLiabilities;
	
	@FXML private Label totalLiabilities;
	
	@FXML private Label netWorth;
	
	public MainController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        Manager manager = Manager.getInstance();

        try {
            fxmlLoader.load();
            name.setText(manager.getCurrentUser().getFirstName() + " " + manager.getCurrentUser().getLastName());
            
            Image thumb = new Image("/application/resources/linkedin_banner_image_1.png");
            logoMain.setImage(thumb);
            
            
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
            double totalLongTermAssets = manager.getCurrentUser().getLongTermAssetsTotal();
            double shortTermAsset = manager.getCurrentUser().getShortTermAssetsTotal();
            double assetsTotal = totalLongTermAssets + shortTermAsset;
            
            double totalLongTermLiabilities = manager.getCurrentUser().getTotalLongTermLiabilities();
            double totalShortTermLiabilities = manager.getCurrentUser().getTotalShortTermLiabilities();
            double totalLiabilitiesDouble = totalLongTermLiabilities + totalShortTermLiabilities;
            
            double total = assetsTotal - totalLiabilitiesDouble;
        
            longTermAssets.setText("$" + decimalFormat.format(totalLongTermAssets));
            shortTermAssets.setText("$" + decimalFormat.format(shortTermAsset));
            totalAssets.setText("$" + decimalFormat.format(assetsTotal));
            longTermLiabilitiesString.setText("$" + decimalFormat.format(totalLongTermLiabilities));
            shortTermLiabilities.setText("$" + decimalFormat.format(totalShortTermLiabilities));
            totalLiabilities.setText("$" + decimalFormat.format(totalLiabilitiesDouble));
            netWorth.setText("$" + decimalFormat.format(total));
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
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
	
	
}
