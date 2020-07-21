/**
 * 
 */
package application;

import java.io.IOException;
import java.text.DecimalFormat;

import application.manager.Manager;
import application.users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	
	@FXML private TextField currentAge;
	
	@FXML private TextField retirementAge;
	
	@FXML private Label fivePercentReturn;
	
	@FXML private Label sevenPercentReturn;
	
	@FXML private Label tenPercentReturn;
	
	@FXML private Label error;
	
	private User user;
	
	@FXML private VBox vbox;
	
	@FXML private ScrollPane pane;

	public RetirementController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Retirement.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
          
            
            Image thumb = new Image("/linkedin_banner_image_1.png");
            logoMain.setImage(thumb);
            
            user = Manager.getInstance().getCurrentUser();
            
            pane.setFitToWidth(true);
            pane.setFitToHeight(true);
            
            vbox.setPrefWidth(pane.getWidth());
            vbox.setPrefHeight(pane.getHeight());
            
            if(user.getAge() != 0) {
            	currentAge.setText(user.getAge() + "");
            	if(user.getRetirementAge() != 0) {
                	retirementAge.setText(user.getRetirementAge() + "");
                	double totalAssets = Manager.getInstance().getCurrentUser().getLongTermAssetsTotal();
                	DecimalFormat decimalFormat = new DecimalFormat("#.##");
    		        decimalFormat.setGroupingUsed(true);
    		        decimalFormat.setGroupingSize(3);
    		        
    		        double difference = user.getRetirementAge() - user.getAge();
    		        
    		        double fivePercent = totalAssets * Math.pow(1.05, difference); 
    		        fivePercentReturn.setText(" - $" + decimalFormat.format(fivePercent));
    		        
    		        double sevenPercent = totalAssets * Math.pow(1.07, difference); 
    		        sevenPercentReturn.setText(" - $" + decimalFormat.format(sevenPercent));
    		        
    		        double tenPercent = totalAssets * Math.pow(1.1, difference); 
    		        tenPercentReturn.setText(" - $" + decimalFormat.format(tenPercent));
                }
            }
            
            
            
            
            
            
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
	public void calculate() {
		double totalAssets = Manager.getInstance().getCurrentUser().getLongTermAssetsTotal();
		try {
			
			int age = 0;
			try {
				age = Integer.parseInt(currentAge.getText());
			} catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("Enter A Valid Age");
			}
			
			int retirement = 0;
			
			try {
				retirement = Integer.parseInt(retirementAge.getText());
			} catch(IllegalArgumentException e){
				throw new IllegalArgumentException("Enter a Valid Retirment Age");
			}
			
			
			double difference = retirement - age;
			
			
			if(difference > 0.0) {
				DecimalFormat decimalFormat = new DecimalFormat("#.##");
		        decimalFormat.setGroupingUsed(true);
		        decimalFormat.setGroupingSize(3);
		        
		        double fivePercent = totalAssets * Math.pow(1.05, difference); 
		        fivePercentReturn.setText(" - $" + decimalFormat.format(fivePercent));
		        
		        double sevenPercent = totalAssets * Math.pow(1.07, difference); 
		        sevenPercentReturn.setText(" - $" + decimalFormat.format(sevenPercent));
		        
		        double tenPercent = totalAssets * Math.pow(1.1, difference); 
		        tenPercentReturn.setText(" - $" + decimalFormat.format(tenPercent));
		        
		        user.setAge(age);
		        user.setRetirementAge(retirement);
		        
		        error.setText("");
			} else {
				error.setText("Enter A Valid Age Range");
			}
			
			
	        
			
		} catch(Exception e) {
			error.setText(e.getMessage());
		}
		
		
		
	}
}
