/**
 * 
 */
package application;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import application.manager.Manager;
import application.popup.AddLongTermLiabilityController;
import application.popup.EditLongTermLiabilityController;
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
public class LongTermLiabilitiesController extends BorderPane{
	@FXML
	private ImageView logoMain;
	
	
	private static TableView<Liability> longTermLiabilitiesTable;
	
	@FXML
	private Label totalLongTermLiabilities;
	
	private User user;
	
	@FXML
	private VBox vbox;

	private Button addLiability;

	private Button edit;

	private Button delete;
	
	@FXML
	private GridPane grid;


	public LongTermLiabilitiesController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LongTermLiabilities.fxml"));
		fxmlLoader.setRoot(this);
	    fxmlLoader.setController(this);
	    
	    Manager manager = Manager.getInstance();

	    user = manager.getCurrentUser();
	    
	    try {
	        fxmlLoader.load();
	        
	        createTable();
	        createPieChart();
	        
	        Image thumb = new Image("/linkedin_banner_image_1.png");
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
			if(liability.getYearsToMaturity() > 1) {
				pieChartData.add(new PieChart.Data(liability.getName(), liability.getTotalAmount()));
				total += liability.getTotalAmount();
			}
		}
		
		
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
		totalLongTermLiabilities.setText("$" + decimalFormat.format(total));
		
		PieChart pieChart = new PieChart(pieChartData);
		
		pieChart.setPadding(new Insets(30, 0, 0, 0));
		pieChart.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		grid.add(pieChart, 1, 1);
		
		
	}

	private void createTable() {
		
		longTermLiabilitiesTable = new TableView<Liability>();
		
		longTermLiabilitiesTable.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		longTermLiabilitiesTable.setPrefHeight(675);
		
		TableColumn<Liability, String> name = new TableColumn<Liability, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Liability,String> totalAmount = new TableColumn<Liability,String>("Total Amount");
		totalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmountString"));
		TableColumn<Liability, Integer> yearsToMaturity = new TableColumn<Liability, Integer>("Years To Maturity");
		yearsToMaturity.setCellValueFactory(new PropertyValueFactory<>("yearsToMaturity"));
		
		ObservableList<Liability> liabilityList = FXCollections.observableArrayList();
        
        ArrayList<Liability> array = user.returnLiabilities().getLiabilities();
        
        
        
        for(Liability liability : array) {
        	if(liability.getYearsToMaturity() > 1) {
        		liabilityList.add(liability);
        	}
        }
        
        
        longTermLiabilitiesTable.setItems(liabilityList);
        
		
		longTermLiabilitiesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		longTermLiabilitiesTable.getColumns().add(name);
		longTermLiabilitiesTable.getColumns().add(totalAmount);
		longTermLiabilitiesTable.getColumns().add(yearsToMaturity);
		
		
		name.prefWidthProperty().bind(longTermLiabilitiesTable.widthProperty().multiply(0.33));
		name.setStyle("-fx-alignment: CENTER;");
        totalAmount.prefWidthProperty().bind(longTermLiabilitiesTable.widthProperty().multiply(0.33));
        yearsToMaturity.prefWidthProperty().bind(longTermLiabilitiesTable.widthProperty().multiply(0.336));
       

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
            	if(longTermLiabilitiesTable.getSelectionModel().getSelectedItem() != null) {
            		new EditLongTermLiabilityController(longTermLiabilitiesTable.getSelectionModel().getSelectedItem());
            	}
            }
        });
        delete = new Button("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(longTermLiabilitiesTable.getSelectionModel().getSelectedItem() != null) {
            		
            		user.returnLiabilities().deleteLiability(longTermLiabilitiesTable.getSelectionModel().getSelectedItem());;
            		longTermLiabilitiesTable.getItems().remove(longTermLiabilitiesTable.getSelectionModel().getSelectedItem());
            	}
            }
        });
        
        GridPane buttons = new GridPane();
        
        
        
        buttons.add(addLiability, 0, 0);
        buttons.add(edit, 2, 0);
        buttons.add(delete, 4, 0);
        
        buttons.setPadding(new Insets(0, 0, 0, 20));
        
        buttons.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        
        vbox.getChildren().add(longTermLiabilitiesTable);
        VBox.setMargin(longTermLiabilitiesTable, new Insets(0,0,0,20));
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
	public void openMain() {
		Main.setView(new MainController());
	}
	
	public void addLiability() {
		new AddLongTermLiabilityController();
	}

	public static void addLiabilityToTable(Liability asset) {
		longTermLiabilitiesTable.getItems().add(asset);
	}

	public static void removeLiabilityFromTable(Liability asset) {
		longTermLiabilitiesTable.getItems().remove(asset);
	}
}
