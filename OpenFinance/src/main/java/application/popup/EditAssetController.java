package application.popup;

import java.io.IOException;

import application.LongTermAssetsController;
import application.manager.Manager;
import application.users.User;
import data.assets.longterm.Asset;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditAssetController extends BorderPane{
	
	private User user;

	@FXML
	private GridPane grid;
	
	private Stage primaryStage;
	
	@FXML
	private TextField name;
	
	@FXML
	private TextField value;
	
	@FXML
	private Label error;
	
	private Asset asset;
	
	
	public EditAssetController(Asset asset) {
		
		primaryStage = new Stage();
		
		this.asset = asset;
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddAsset.fxml"));
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
    		
    		name.setText(asset.getName());
    		value.setText(asset.getValue() + "");
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	@FXML
	public void submit() {
		try {
			Asset newAsset = new Asset(name.getText(), Double.parseDouble(value.getText()));
			
			user.getLongTermAssets().returnAssets().remove(asset);
			user.getLongTermAssets().addAsset(newAsset);
			LongTermAssetsController.removeAssetFromTable(asset);
			LongTermAssetsController.addAssetToTable(newAsset);
			
			primaryStage.close();
		} catch(IllegalArgumentException e) {
			System.out.print(e.getStackTrace());
			error.setText("Exception Caught");
		}
	}
}
