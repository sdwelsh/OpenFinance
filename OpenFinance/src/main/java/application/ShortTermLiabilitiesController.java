/**
 * 
 */
package application;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import application.manager.Manager;
import application.popup.AddShortTermLiabilityController;
import application.popup.EditShortTermLiabilityController;
import application.users.User;
import data.liabilities.Liability;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * @author Stephen Welsh
 *
 */
public class ShortTermLiabilitiesController extends BorderPane{
	@FXML
	private ImageView logoMain;
	
	
	private static TableView<Liability> shortTermLiabilitiesTable;
	
	@FXML
	private Label totalShortTermLiabilities;
	
	private User user;
	
	@FXML
	private VBox vbox;

	private Button addLiability;

	private Button edit;

	private Button delete;
	
	@FXML
	private GridPane grid;


	public ShortTermLiabilitiesController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShortTermLiabilities.fxml"));
		fxmlLoader.setRoot(this);
	    fxmlLoader.setController(this);
	    
	    Manager manager = Manager.getInstance();

	    user = manager.getCurrentUser();
	    
	    try {
	        fxmlLoader.load();
	        
	        createTable();
	        createPieChart();
	        
	        Image thumb = new Image("/application/resources/linkedin_banner_image_1.png");
	        logoMain.setImage(thumb);
	        
	        
	        
	    } catch (IOException exception) {
	        throw new RuntimeException(exception);
	    }
	}
	
	private void createPieChart() {
		
		ArrayList<Liability> liabilities = user.returnLiabilities().getLiabilities();
		
		
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(); 
		
		double total = 0; 
		
		for(Liability liability : liabilities) {
			if(liability.getYearsToMaturity() <= 1) {
				pieChartData.add(new PieChart.Data(liability.getName(), liability.getTotalAmount()));
				total += liability.getTotalAmount();
			}
		}
		
		
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
		totalShortTermLiabilities.setText("$" + decimalFormat.format(total));
		
		PieChart pieChart = new PieChart(pieChartData);
		
		pieChart.setPadding(new Insets(30, 0, 0, 0));
		pieChart.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		grid.add(pieChart, 1, 1);
		
		
	}

	private void createTable() {
		
		shortTermLiabilitiesTable = new TableView<Liability>();
		
		shortTermLiabilitiesTable.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		shortTermLiabilitiesTable.setPrefHeight(675);
		
		TableColumn<Liability, String> name = new TableColumn<Liability, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Liability,String> totalAmount = new TableColumn<Liability,String>("Total Amount");
		totalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmountString"));
		TableColumn<Liability, Integer> yearsToMaturity = new TableColumn<Liability, Integer>("Years To Maturity");
		yearsToMaturity.setCellValueFactory(new PropertyValueFactory<>("yearsToMaturity"));
		
		ObservableList<Liability> liabilityList = FXCollections.observableArrayList();
        
        ArrayList<Liability> array = user.returnLiabilities().getLiabilities();
        
        
        
        for(Liability liability : array) {
        	if(liability.getYearsToMaturity() <= 1) {
        		liabilityList.add(liability);
        	}
        }
        
        
        shortTermLiabilitiesTable.setItems(liabilityList);
        
		
		shortTermLiabilitiesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		shortTermLiabilitiesTable.getColumns().add(name);
		shortTermLiabilitiesTable.getColumns().add(totalAmount);
		shortTermLiabilitiesTable.getColumns().add(yearsToMaturity);
		
		
		name.prefWidthProperty().bind(shortTermLiabilitiesTable.widthProperty().multiply(0.33));
		name.setStyle("-fx-alignment: CENTER;");
        totalAmount.prefWidthProperty().bind(shortTermLiabilitiesTable.widthProperty().multiply(0.33));
        yearsToMaturity.prefWidthProperty().bind(shortTermLiabilitiesTable.widthProperty().multiply(0.336));
       

        name.setResizable(false);
        totalAmount.setResizable(false);
        yearsToMaturity.setResizable(false);
       
        
        
        addLiability = new Button("Add Liability");
        addLiability.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
               addLiability();
            }
        });
        edit = new Button("Edit");
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(shortTermLiabilitiesTable.getSelectionModel().getSelectedItem() != null) {
            		new EditShortTermLiabilityController(shortTermLiabilitiesTable.getSelectionModel().getSelectedItem());
            	}
            }
        });
        delete = new Button("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(shortTermLiabilitiesTable.getSelectionModel().getSelectedItem() != null) {
            		
            		user.returnLiabilities().deleteLiability(shortTermLiabilitiesTable.getSelectionModel().getSelectedItem());;
            		shortTermLiabilitiesTable.getItems().remove(shortTermLiabilitiesTable.getSelectionModel().getSelectedItem());
            	}
            }
        });
        
        GridPane buttons = new GridPane();
        
        
        
        buttons.add(addLiability, 0, 0);
        buttons.add(edit, 2, 0);
        buttons.add(delete, 4, 0);
        
        buttons.setPadding(new Insets(0, 0, 0, 20));
        
        buttons.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        vbox.getChildren().add(shortTermLiabilitiesTable);
        VBox.setMargin(shortTermLiabilitiesTable, new Insets(0,0,0,20));
        vbox.getChildren().add(buttons);
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
	public void logout() {
		Manager manager = Manager.getInstance();
		manager.logout();
		Main.login(new LoginController());
	}
	
	@FXML
	public void retirement() {
		Main.setView(new RetirementController());
	}
	
	@FXML
	public void openMain() {
		Main.setView(new MainController());
	}

	
	public void addLiability() {
		new AddShortTermLiabilityController();
	}

	public static void addLiabilityToTable(Liability asset) {
		shortTermLiabilitiesTable.getItems().add(asset);
	}

	public static void removeLiabilityFromTable(Liability asset) {
		shortTermLiabilitiesTable.getItems().remove(asset);
	}
}
