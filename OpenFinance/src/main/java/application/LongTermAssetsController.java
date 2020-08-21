/**
 * 
 */
package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import application.manager.Manager;
import application.popup.AddAssetController;
import application.popup.AddETFController;
import application.popup.AddMutualFundController;
import application.popup.AddStockController;
import application.popup.EditAssetController;
import application.popup.EditETFController;
import application.popup.EditMutualFundController;
import application.popup.EditStockController;
import application.stock.StockViewController;
import application.users.User;
import application.view.LTA.LTAHome;
import data.assets.longterm.Asset;
import data.assets.longterm.ETF;
import data.assets.longterm.ETF.InvestmentType;
import data.assets.longterm.LongTermAsset;
import data.assets.longterm.MutualFunds;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * @author Stephen Welsh
 *
 */
public class LongTermAssetsController extends BorderPane {
	
	/** get the current user */
	private static User user;
	
	/** Displays the long term assets on the account */
	private static TableView<LongTermAsset> stockTable;
	
	/** Displays the long term assets on the account */
	private static TableView<LongTermAsset> etfTable;
	
	/** Displays the long term assets on the account */
	private static TableView<LongTermAsset> mutualFundTable;
	
	private static TableView<Asset> assetsTable;
	
	
	private ScrollPane pane;
	
	@FXML
	private ImageView logoMain;
	
	@FXML
	private VBox size;
	
	private VBox vbox;
	
	private Button addStock;
	private Button stockEdit;
	private Button stockDelete;
	private Button stockRefresh;
	
	private Button addEtf;
	private Button etfEdit;
	private Button etfDelete;
	private Button etfRefresh;
	
	private Button addMutualFund;
	private Button mutualEdit;
	private Button mutualDelete;
	
	private Button addAsset;
	private Button assetEdit;
	private Button assetDelete;

	private Button viewStock;

	private ButtonBase viewMutualFund;

	private Button viewETF;

	private Button printStocks;


	private static Label stockTotal;
	private static Label mutualTotal;
	private static Label etfTotal;
	private static Label assetsTotal;
	
	
	public LongTermAssetsController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LongTermAssets.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        user = Manager.getInstance().getCurrentUser();

        try {
            fxmlLoader.load();
            
            Image thumb = new Image("/linkedin_banner_image_1.png");
            
            logoMain.setImage(thumb);
            
            pane = new ScrollPane();
           
            pane.setFitToWidth(true);
            size.getChildren().add(pane);
            vbox = new VBox();
            vbox.setStyle("-fx-background-color: white");
            pane.setContent(vbox);
            
            vbox.setOnScroll(new EventHandler<ScrollEvent>() {
                @Override
                public void handle(ScrollEvent event) {
                    double deltaY = event.getDeltaY()*6; // *6 to make the scrolling a bit faster
                    double width = pane.getContent().getBoundsInLocal().getWidth();
                    double vvalue = pane.getVvalue();
                    pane.setVvalue(vvalue + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
                }
            });
            addHome();
            
            
            
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	

	private void addHome() {
		LTAHome home = new LTAHome();
		
		home.setPrefHeight(1200);
		vbox = new VBox();
		pane.setContent(vbox);
		home.setPrefWidth(vbox.getWidth());
		home.setFillWidth(true);
		vbox.getChildren().add(home);
		//home.fitSize(home.getPrefWidth());
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
	public void onHome() {
		addHome();
	}
	
	@FXML
	public void addStocksTable() {
		vbox = new VBox();
		vbox.setStyle("-fx-background-color: white");
        pane.setContent(vbox);
		
		stockTable = new TableView<LongTermAsset>();

        TableColumn<LongTermAsset, String> ticker = new TableColumn<LongTermAsset, String>("Ticker");
        ticker.setCellValueFactory(new PropertyValueFactory<>("tickerString"));
        TableColumn<LongTermAsset, String> name = new TableColumn<LongTermAsset, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<LongTermAsset,String> price = new TableColumn<LongTermAsset,String>("Price");
		price.setCellValueFactory(new PropertyValueFactory<>("totalPriceString"));
		TableColumn<LongTermAsset,String> quantity = new TableColumn<LongTermAsset,String>("Quantity");
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantityString"));
		TableColumn<LongTermAsset,String> pricePerShare = new TableColumn<LongTermAsset,String>("Price per Share");
		pricePerShare.setCellValueFactory(new PropertyValueFactory<>("pricePerShareString"));
		TableColumn<LongTermAsset,String> bank = new TableColumn<LongTermAsset,String>("Bank");
		bank.setCellValueFactory(new PropertyValueFactory<>("bankString"));
		TableColumn<LongTermAsset,String> accountName = new TableColumn<LongTermAsset,String>("Account Name");
		accountName.setCellValueFactory(new PropertyValueFactory<>("accountNameString"));
		TableColumn<LongTermAsset,String> accountType = new TableColumn<LongTermAsset,String>("Account Type");
		accountType.setCellValueFactory(new PropertyValueFactory<>("accountString"));
		
		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
        
        ArrayList<LongTermAsset> array = user.getLongTermAssets().returnStocks();
        
        double total = 0;
        
        for(int i = 0; i < array.size(); i++) {
        	LongTermAsset asset = array.get(i);
        	total += asset.getTotalPrice();
        	assetList.add(array.get(i));
        	
        }
        
        stockTable.setItems(assetList);
        
		
		stockTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		stockTable.getColumns().add(ticker);
		stockTable.getColumns().add(name);
		stockTable.getColumns().add(price);
		stockTable.getColumns().add(quantity);
		stockTable.getColumns().add(pricePerShare);
		stockTable.getColumns().add(bank);
		stockTable.getColumns().add(accountName);
		stockTable.getColumns().add(accountType);
		
		stockTable.setPrefHeight(array.size() * 50 + 26);
		stockTable.setPrefWidth(pane.getWidth());
		
		ticker.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.05));
		ticker.setStyle("-fx-alignment: CENTER;");
		name.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.2));
        price.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.2));
        quantity.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.1));
        pricePerShare.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.1));
        bank.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.1));
        accountName.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.147));
        accountType.prefWidthProperty().bind(stockTable.widthProperty().multiply(0.1));

        ticker.setResizable(false);
        name.setResizable(false);
        price.setResizable(false);
        quantity.setResizable(false);
        pricePerShare.setResizable(false);
        bank.setResizable(false);
        accountName.setResizable(false);
        accountType.setResizable(false);
        
        stockTable.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        
        
        
        addStock = new Button("Add Stock");
        addStock.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                addStock();
            }
        });
        stockEdit = new Button("Edit");
        stockEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(stockTable.getSelectionModel().getSelectedItem() != null) {
            		new EditStockController(stockTable.getSelectionModel().getSelectedItem());
            	}
            }
        });
        stockDelete = new Button("Delete");
        stockDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(stockTable.getSelectionModel().getSelectedItem() != null) {
            		LongTermAsset asset = stockTable.getSelectionModel().getSelectedItem();
                    user.deleteLongTermAsset(asset);
                    stockTable.getItems().remove(stockTable.getSelectionModel().getSelectedIndex());
                    stockTable.getSelectionModel().clearSelection();
            	}
            }
        });
        stockRefresh = new Button("Refresh");
        stockRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                refreshStocks();
            }
        });
        viewStock = new Button("View Stock");
        viewStock.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(stockTable.getSelectionModel().getSelectedItem() != null) {
            		new StockViewController(stockTable.getSelectionModel().getSelectedItem());
            	} 
            }
        });
        printStocks = new Button("Print");
        printStocks.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	WritableImage nodeshot = stockTable.snapshot(new SnapshotParameters(), null);
                File file = new File("chart.png");

                try {
                	ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
                } catch (IOException j) {

                }

                PDDocument doc = new PDDocument();
                PDPage page = new PDPage();
                PDImageXObject pdimage;
                PDPageContentStream content;
                try {
                    pdimage = PDImageXObject.createFromFile("chart.png",doc);
                    content = new PDPageContentStream(doc, page);
                    content.drawImage(pdimage, 100, 100);
                    content.close();
                    doc.addPage(page);
                    doc.save("pdf_file.pdf");
                    doc.close();
                    file.delete();
                } catch (IOException ex) {
                    throw new IllegalArgumentException();
                }

            }
        });
        stockTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		            	if(stockTable.getSelectionModel().getSelectedItem() != null) {
		            		new StockViewController(stockTable.getSelectionModel().getSelectedItem());
		            	} 
		            }
		        }
		    }
		});
        
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        
        Label stockLabel = new Label("Stocks " );
        
        stockTotal = new Label("$" + (decimalFormat.format(total)));
        stockTotal.setStyle("-fx-font-size:40px;");
        stockLabel.setPadding(new Insets(20));
        
        stockLabel.setStyle("-fx-font-size:44px;");
        stockLabel.setPadding(new Insets(20));
        
        GridPane gridPane = new GridPane();
        gridPane.add(stockLabel, 0, 0);
        gridPane.add(stockTotal, 1, 0);
        
        
        
        GridPane buttons = new GridPane();
        
        buttons.add(addStock, 0, 0);
        buttons.add(stockEdit, 2, 0);
        buttons.add(stockDelete, 4, 0);
        buttons.add(stockRefresh, 6, 0);
        buttons.add(viewStock, 7, 0);
        buttons.add(printStocks, 8, 0);
        
        buttons.setAlignment(Pos.CENTER_RIGHT);
        
        
        buttons.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        ColumnConstraints column1 = new ColumnConstraints(stockLabel.getPrefWidth());
        ColumnConstraints column2 = new ColumnConstraints(stockTotal.getPrefWidth());
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(40);
        ColumnConstraints column4 = new ColumnConstraints(buttons.getPrefWidth());
        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4);
        gridPane.add(buttons, 3,0);
        GridPane.setHalignment(buttons, HPos.RIGHT);
        vbox.getChildren().add(gridPane);
        
        vbox.getChildren().add(stockTable);
		
	}
	
	protected void refreshStocks() {
		
		new Thread() {

            // runnable for that thread
            public void run() {
                
            	user.getLongTermAssets().update();
                   
                    Platform.runLater(new Runnable() {

                        public void run() {
                        	stockTable.getItems().clear();
                    		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
                    		
                    		
                            
                            ArrayList<LongTermAsset> array = user.getLongTermAssets().returnStocks();
                            
                            double total = 0;
                            
                            for(int i = 0; i < array.size(); i++) {
                            	LongTermAsset asset = array.get(i);
                            	total += asset.getTotalPrice();
                            	assetList.add(array.get(i));
                            	
                            }
                            
                            DecimalFormat decimalFormat = new DecimalFormat("#.##");
                            decimalFormat.setGroupingUsed(true);
                            decimalFormat.setGroupingSize(3);
                            
                            stockTotal.setText("$" + decimalFormat.format(total));
                            
                            stockTable.getItems().addAll(assetList);
                        }
                    });
                
            }
        }.start();
		
		
	}

	@FXML
	public void addMutualFundsTable() {
		vbox = new VBox();
		vbox.setStyle("-fx-background-color: white");
        pane.setContent(vbox);
		
		mutualFundTable = new TableView<LongTermAsset>();

        TableColumn<LongTermAsset, String> ticker = new TableColumn<LongTermAsset, String>("Ticker");
        ticker.setCellValueFactory(new PropertyValueFactory<>("tickerString"));
        TableColumn<LongTermAsset, String> name = new TableColumn<LongTermAsset, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<LongTermAsset,String> price = new TableColumn<LongTermAsset,String>("Price");
		price.setCellValueFactory(new PropertyValueFactory<>("totalPriceString"));
		TableColumn<LongTermAsset,String> quantity = new TableColumn<LongTermAsset,String>("Quantity");
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantityString"));
		TableColumn<LongTermAsset,String> pricePerShare = new TableColumn<LongTermAsset,String>("Price per Share");
		pricePerShare.setCellValueFactory(new PropertyValueFactory<>("pricePerShareString"));
		TableColumn<LongTermAsset,String> bank = new TableColumn<LongTermAsset,String>("Bank");
		bank.setCellValueFactory(new PropertyValueFactory<>("bankString"));
		TableColumn<LongTermAsset,String> accountType = new TableColumn<LongTermAsset,String>("Account Type");
		accountType.setCellValueFactory(new PropertyValueFactory<>("accountString"));
		TableColumn<LongTermAsset,String> accountName = new TableColumn<LongTermAsset,String>("Account Name");
		accountName.setCellValueFactory(new PropertyValueFactory<>("accountNameString"));
		TableColumn<LongTermAsset,InvestmentType> investmentType = new TableColumn<LongTermAsset, InvestmentType>("Investment Type");
		investmentType.setCellValueFactory(new PropertyValueFactory<>("invType"));
		
		
		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
        
        ArrayList<LongTermAsset> array = user.getLongTermAssets().returnMutualFunds();
        
        double total = 0;
        
        for(int i = 0; i < array.size(); i++) {
        	LongTermAsset asset = array.get(i);
        	total += asset.getTotalPrice();
        	assetList.add(asset);
        }
        
        mutualFundTable.setItems(assetList);
        
		
		mutualFundTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		mutualFundTable.getColumns().add(ticker);
		mutualFundTable.getColumns().add(name);
		mutualFundTable.getColumns().add(price);
		mutualFundTable.getColumns().add(quantity);
		mutualFundTable.getColumns().add(pricePerShare);
		mutualFundTable.getColumns().add(bank);
		mutualFundTable.getColumns().add(accountType);
		mutualFundTable.getColumns().add(accountName);
		mutualFundTable.getColumns().add(investmentType);
		
		mutualFundTable.setPrefHeight(array.size() * 50 + 26);
		mutualFundTable.setPrefWidth(pane.getWidth());
		
		ticker.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.05));
		ticker.setStyle("-fx-alignment: CENTER;");
		name.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.2));
        price.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.1));
        quantity.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.1));
        pricePerShare.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.1));
        bank.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.1));
        accountType.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.1));
        accountName.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.14));
        investmentType.prefWidthProperty().bind(mutualFundTable.widthProperty().multiply(0.108));

        ticker.setResizable(false);
        name.setResizable(false);
        price.setResizable(false);
        quantity.setResizable(false);
        pricePerShare.setResizable(false);
        bank.setResizable(false);
        accountType.setResizable(false);
        accountName.setResizable(false);
        investmentType.setResizable(false);
        
        mutualFundTable.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        
        
        addMutualFund = new Button("Add Mutual Fund");
        addMutualFund.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                addMutual();
            }
        });
        mutualEdit = new Button("Edit");
        mutualEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
               if(mutualFundTable.getSelectionModel().getSelectedItem() != null) {
            	   new EditMutualFundController((MutualFunds) mutualFundTable.getSelectionModel().getSelectedItem() );
               }
            }
        });
        mutualDelete = new Button("Delete");
        mutualDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(mutualFundTable.getSelectionModel().getSelectedItem() != null) {
            		LongTermAsset asset = mutualFundTable.getSelectionModel().getSelectedItem();
                    user.deleteLongTermAsset(asset);
                    mutualFundTable.getItems().remove(mutualFundTable.getSelectionModel().getSelectedIndex());
            	}
            }
        });
        viewMutualFund = new Button("View Mutual Fund");
        viewMutualFund.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(mutualFundTable.getSelectionModel().getSelectedItem() != null) {
            		new StockViewController(mutualFundTable.getSelectionModel().getSelectedItem());
            	} 
            }
        });
        mutualFundTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		            	if(mutualFundTable.getSelectionModel().getSelectedItem() != null) {
		            		new StockViewController(mutualFundTable.getSelectionModel().getSelectedItem());
		            	} 
		            }
		        }
		    }
		});
        
        
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        
        Label mutualFundLabel = new Label("Mutual Funds " );
        
        mutualTotal = new Label("$" + (decimalFormat.format(total)));
        mutualTotal.setStyle("-fx-font-size:40px;");
        mutualFundLabel.setPadding(new Insets(20));
        
        mutualFundLabel.setStyle("-fx-font-size:44px;");
        mutualFundLabel.setPadding(new Insets(20));
        
        GridPane gridPane = new GridPane();
        gridPane.add(mutualFundLabel, 0, 0);
        gridPane.add(mutualTotal, 1, 0);
        
        
        
        GridPane buttons = new GridPane();
        
        buttons.add(addMutualFund, 0, 0);
        buttons.add(mutualEdit, 2, 0);
        buttons.add(mutualDelete, 4, 0);
        buttons.add(viewMutualFund, 5, 0);
        
        buttons.setAlignment(Pos.CENTER_RIGHT);
        
        
        buttons.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        ColumnConstraints column1 = new ColumnConstraints(mutualFundLabel.getPrefWidth());
        ColumnConstraints column2 = new ColumnConstraints(mutualTotal.getPrefWidth());
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(30);
        ColumnConstraints column4 = new ColumnConstraints(buttons.getPrefWidth());
        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4);
        gridPane.add(buttons, 3,0);
        GridPane.setHalignment(buttons, HPos.RIGHT);
        vbox.getChildren().add(gridPane);
        
        vbox.getChildren().add(mutualFundTable);
		
	}
	
	
	@FXML
	public void addETFTable() {
		vbox = new VBox();
		vbox.setStyle("-fx-background-color: white");
        pane.setContent(vbox);
		
		etfTable = new TableView<LongTermAsset>();

        TableColumn<LongTermAsset, String> ticker = new TableColumn<LongTermAsset, String>("Ticker");
        ticker.setCellValueFactory(new PropertyValueFactory<>("tickerString"));
        TableColumn<LongTermAsset, String> name = new TableColumn<LongTermAsset, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<LongTermAsset,String> price = new TableColumn<LongTermAsset,String>("Price");
		price.setCellValueFactory(new PropertyValueFactory<>("totalPriceString"));
		TableColumn<LongTermAsset,String> quantity = new TableColumn<LongTermAsset,String>("Quantity");
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantityString"));
		TableColumn<LongTermAsset,String> pricePerShare = new TableColumn<LongTermAsset,String>("Price per Share");
		pricePerShare.setCellValueFactory(new PropertyValueFactory<>("pricePerShareString"));
		TableColumn<LongTermAsset,String> bank = new TableColumn<LongTermAsset,String>("Bank");
		bank.setCellValueFactory(new PropertyValueFactory<>("bankString"));
		TableColumn<LongTermAsset,String> accountType = new TableColumn<LongTermAsset,String>("Account Type");
		accountType.setCellValueFactory(new PropertyValueFactory<>("accountString"));
		TableColumn<LongTermAsset,String> accountName = new TableColumn<LongTermAsset,String>("Account Name");
		accountName.setCellValueFactory(new PropertyValueFactory<>("accountNameString"));
		TableColumn<LongTermAsset,InvestmentType> investmentType = new TableColumn<LongTermAsset,InvestmentType>("Investment Type");
		investmentType.setCellValueFactory(new PropertyValueFactory<>("investmentType"));
		
		
		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
        
        ArrayList<LongTermAsset> array = user.getLongTermAssets().returnETFs();
        
        double total = 0;
        
        for(int i = 0; i < array.size(); i++) {
        	LongTermAsset asset = array.get(i);
        	total += asset.getTotalPrice();
        	assetList.add(array.get(i));
        }
        
        etfTable.setItems(assetList);
        
		
		etfTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		etfTable.getColumns().add(ticker);
		etfTable.getColumns().add(name);
		etfTable.getColumns().add(price);
		etfTable.getColumns().add(quantity);
		etfTable.getColumns().add(pricePerShare);
		etfTable.getColumns().add(bank);
		etfTable.getColumns().add(accountType);
		etfTable.getColumns().add(accountName);
		etfTable.getColumns().add(investmentType);
		
		etfTable.setPrefHeight(array.size() * 50 + 26);
		etfTable.setPrefWidth(pane.getWidth());
		
		ticker.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.05));
		ticker.setStyle("-fx-alignment: CENTER;");
		name.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.2));
        price.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));
        quantity.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));
        pricePerShare.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));
        bank.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));
        accountType.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.1));
        accountName.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.15));
        investmentType.prefWidthProperty().bind(etfTable.widthProperty().multiply(0.094));
        

        ticker.setResizable(false);
        name.setResizable(false);
        price.setResizable(false);
        quantity.setResizable(false);
        pricePerShare.setResizable(false);
        bank.setResizable(false);
        accountType.setResizable(false);
        accountName.setResizable(false);
        investmentType.setResizable(false);
        
        etfTable.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        
        
        addEtf = new Button("Add ETF");
        addEtf.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                addEtf();
            }
        });
        
        etfEdit = new Button("Edit");
        etfEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(etfTable.getSelectionModel().getSelectedItem() != null) {
            		new EditETFController((ETF) etfTable.getSelectionModel().getSelectedItem());
            	}
            }
        });
        etfDelete = new Button("Delete");
        etfDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(etfTable.getSelectionModel().getSelectedItem() != null) {
            		LongTermAsset asset = etfTable.getSelectionModel().getSelectedItem();
                    user.deleteLongTermAsset(asset);
                    etfTable.getItems().remove(etfTable.getSelectionModel().getSelectedIndex());
            	}
            }
        });
        etfRefresh = new Button("Refresh");
        etfRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                refreshETFs();
            }
        });
        viewETF = new Button("View ETF");
        viewETF.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(etfTable.getSelectionModel().getSelectedItem() != null) {
            		new StockViewController(etfTable.getSelectionModel().getSelectedItem());
            	} 
            }
        });
        etfTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		            	if(etfTable.getSelectionModel().getSelectedItem() != null) {
		            		new StockViewController(etfTable.getSelectionModel().getSelectedItem());
		            	} 
		            }
		        }
		    }
		});
        
       
        
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        
        Label etfLabel = new Label("ETF'S " );
        
        etfTotal = new Label("$" + (decimalFormat.format(total)));
        etfTotal.setStyle("-fx-font-size:40px;");
        etfLabel.setPadding(new Insets(20));
        
        etfLabel.setStyle("-fx-font-size:44px;");
        etfLabel.setPadding(new Insets(20));
        
        GridPane gridPane = new GridPane();
        gridPane.add(etfLabel, 0, 0);
        gridPane.add(etfTotal, 1, 0);
        
        
        
        GridPane buttons = new GridPane();
        
        buttons.add(addEtf, 0, 0);
        buttons.add(etfEdit, 2, 0);
        buttons.add(etfDelete, 4, 0);
        buttons.add(etfRefresh, 6, 0);
        buttons.add(viewETF, 7, 0);
        
        buttons.setAlignment(Pos.CENTER_RIGHT);
        
        
        buttons.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        ColumnConstraints column1 = new ColumnConstraints(etfLabel.getPrefWidth());
        ColumnConstraints column2 = new ColumnConstraints(etfTotal.getPrefWidth());
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(40);
        ColumnConstraints column4 = new ColumnConstraints(buttons.getPrefWidth());
        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4);
        gridPane.add(buttons, 3,0);
        GridPane.setHalignment(buttons, HPos.RIGHT);
        vbox.getChildren().add(gridPane);
        
        vbox.getChildren().add(etfTable);
		
	}
	
	protected void refreshETFs() {
		etfTable.getItems().clear();
		ObservableList<LongTermAsset> assetList = FXCollections.observableArrayList();
		
		user.getLongTermAssets().update();
        
        ArrayList<LongTermAsset> array = user.getLongTermAssets().returnETFs();
        
        double total = 0;
        
        for(int i = 0; i < array.size(); i++) {
        	LongTermAsset asset = array.get(i);
        	total += asset.getTotalPrice();
        	assetList.add(array.get(i));
        	
        }
        
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        
        etfTotal.setText("$" + decimalFormat.format(total));
        
        etfTable.getItems().addAll(assetList);
	}
	
	@FXML
	private void addAssetsTable() {
		vbox = new VBox();
		vbox.setStyle("-fx-background-color: white");
        pane.setContent(vbox);
        
		assetsTable = new TableView<Asset>();

        TableColumn<Asset, String> name = new TableColumn<Asset, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Asset,String> price = new TableColumn<Asset,String>("Value");
		price.setCellValueFactory(new PropertyValueFactory<>("valueString"));
		
		
		ObservableList<Asset> assetList = FXCollections.observableArrayList();
        
        ArrayList<Asset> array = user.getLongTermAssets().returnAssets();
        
        double total = 0;
        
        for(int i = 0; i < array.size(); i++) {
        	Asset asset = array.get(i);
        	total += asset.getValue();
        	assetList.add(asset);
        }
        
        assetsTable.setItems(assetList);
        
		
		assetsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		assetsTable.getColumns().add(name);
		assetsTable.getColumns().add(price);
		
		
		assetsTable.setPrefHeight(array.size() * 50 + 26);
		assetsTable.setPrefWidth(pane.getWidth());
		
		name.prefWidthProperty().bind(assetsTable.widthProperty().multiply(0.5));
        price.prefWidthProperty().bind(assetsTable.widthProperty().multiply(0.49));
       

        name.setResizable(false);
        price.setResizable(false);
        
        
        assetsTable.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        
        
        addAsset = new Button("Add Asset");
        addAsset.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                new AddAssetController();
            }
        });
        assetEdit = new Button("Edit");
        assetEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
               if(assetsTable.getSelectionModel().getSelectedItem() != null) {
            	   new EditAssetController(assetsTable.getSelectionModel().getSelectedItem() );
               }
            }
        });
        assetDelete = new Button("Delete");
        assetDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(assetsTable.getSelectionModel().getSelectedItem() != null) {
            		Asset asset = assetsTable.getSelectionModel().getSelectedItem();
                    user.getLongTermAssets().returnAssets().remove(asset);
                    assetsTable.getItems().remove(assetsTable.getSelectionModel().getSelectedIndex());
            	}
            }
        });
        
       
        
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        
        Label assetLabel = new Label("Physical and Other Assets " );
        
        assetsTotal = new Label("$" + (decimalFormat.format(total)));
        assetsTotal.setStyle("-fx-font-size:40px;");
        assetLabel.setPadding(new Insets(20));
        
        assetLabel.setStyle("-fx-font-size:44px;");
        assetLabel.setPadding(new Insets(20));
        
        GridPane gridPane = new GridPane();
        gridPane.add(assetLabel, 0, 0);
        gridPane.add(assetsTotal, 1, 0);
        
        
        
        GridPane buttons = new GridPane();
        
        buttons.add(addAsset, 0, 0);
        buttons.add(assetEdit, 2, 0);
        buttons.add(assetDelete, 4, 0);
        
        buttons.setAlignment(Pos.CENTER_RIGHT);
        
        
        buttons.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        ColumnConstraints column1 = new ColumnConstraints(assetLabel.getPrefWidth());
        ColumnConstraints column2 = new ColumnConstraints(assetsTotal.getPrefWidth());
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20);
        ColumnConstraints column4 = new ColumnConstraints(buttons.getPrefWidth());
        column4.setHalignment(HPos.RIGHT);
        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4);
        gridPane.add(buttons, 3,0);
        GridPane.setHalignment(buttons, HPos.RIGHT);
        vbox.getChildren().add(gridPane);
        
        vbox.getChildren().add(assetsTable);
		
	}
	
	public void addStock() {
		new AddStockController();
	}
	
	@FXML 
	public void addEtf() {
		new AddETFController();
	}
	
	@FXML
	public void addMutual() {
		new AddMutualFundController();
	}
	
	@FXML
	public void refresh() {
        
        
//        /** Refreshing the mutual fund table */
//        ObservableList<LongTermAsset> mutualList = FXCollections.observableArrayList();
//        
//        ArrayList<LongTermAsset> mutualFunds = user.getLongTermAssets().returnMutualFunds();
//        
//        for(int i = 0; i < mutualFunds.size(); i++) {
//        	mutualList.add(mutualFunds.get(i));
//        }
//        
//        mutualFundTable.setItems(mutualList);
//        mutualFundTable.setPrefHeight(mutualFunds.size()*30);
//        
//        
//        /** Refreshing the etf fund table */
//        ObservableList<LongTermAsset> etfList = FXCollections.observableArrayList();
//        
//        ArrayList<LongTermAsset> etfs = user.getLongTermAssets().returnMutualFunds();
//        
//        for(int i = 0; i < etfs.size(); i++) {
//        	etfList.add(etfs.get(i));
//        }
//        
//        mutualFundTable.setItems(etfList);
//        mutualFundTable.setPrefHeight(etfs.size()*30);
	}
	
	public static void addStockToTable(LongTermAsset stock) {
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        
        stockTable.getItems().add(stock);
        stockTable.setPrefHeight(stockTable.getHeight() + 50);
        
        double total = 0;
        for(LongTermAsset asset : user.getLongTermAssets().returnStocks()) {
        	total += asset.getTotalPrice();
        }
        
        stockTotal.setText("$" + decimalFormat.format(total));
	}
	
	public static void addETFToTable(LongTermAsset stock) {
		etfTable.getItems().add(stock);
		etfTable.setPrefHeight(etfTable.getHeight() + 50);
		
		 double total = 0;
	     for(LongTermAsset asset : user.getLongTermAssets().returnETFs()) {
	        total += asset.getTotalPrice();
	     }
	        
	        DecimalFormat decimalFormat = new DecimalFormat("#.##");
	        decimalFormat.setGroupingUsed(true);
	        decimalFormat.setGroupingSize(3);
	        
	     etfTotal.setText("$" + decimalFormat.format(total));
	}

	public static void addMutualFundToTable(LongTermAsset stock) {
		mutualFundTable.getItems().add(stock);
		mutualFundTable.setPrefHeight(mutualFundTable.getHeight() + 50);
		
		 double total = 0;
	     for(LongTermAsset asset : user.getLongTermAssets().returnMutualFunds()) {
	        total += asset.getTotalPrice();
	     }
	        
	        DecimalFormat decimalFormat = new DecimalFormat("#.##");
	        decimalFormat.setGroupingUsed(true);
	        decimalFormat.setGroupingSize(3);
	        
	     mutualTotal.setText("$" + decimalFormat.format(total));
	}

	public static void removeStockFromTable(LongTermAsset before, LongTermAsset after) {
		stockTable.getItems().remove(before);
		stockTable.getItems().add(after);
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
		
		double total = 0;
        for(LongTermAsset asset : user.getLongTermAssets().returnStocks()) {
        	total += asset.getTotalPrice();
        }
         
        stockTotal.setText("$" + decimalFormat.format(total));
	}

	public static void removeETFfromTable(LongTermAsset before, LongTermAsset after) {
		etfTable.getItems().remove(before);
		etfTable.getItems().add(after);
		
		double total = 0;
	     for(LongTermAsset asset : user.getLongTermAssets().returnETFs()) {
	        total += asset.getTotalPrice();
	     }
	        
	        DecimalFormat decimalFormat = new DecimalFormat("#.##");
	        decimalFormat.setGroupingUsed(true);
	        decimalFormat.setGroupingSize(3);
	        
	     etfTotal.setText("$" + decimalFormat.format(total));
	}
	
	public static void removeMutualFundfromTable(LongTermAsset before, LongTermAsset after) {
		mutualFundTable.getItems().remove(before);
		mutualFundTable.getItems().add(after);
		
		double total = 0;
	     for(LongTermAsset asset : user.getLongTermAssets().returnMutualFunds()) {
	        total += asset.getTotalPrice();
	     }
	        
	        DecimalFormat decimalFormat = new DecimalFormat("#.##");
	        decimalFormat.setGroupingUsed(true);
	        decimalFormat.setGroupingSize(3);
	        
	     mutualTotal.setText("$" + decimalFormat.format(total));
	}



	public static void addAssetToTable(Asset asset) {
		assetsTable.getItems().add(asset);
		assetsTable.setPrefHeight(assetsTable.getHeight() + 50);
		
		double total = 0;
	     for(Asset i : user.getLongTermAssets().returnAssets()) {
	        total += i.getValue();
	     }
	        
	        DecimalFormat decimalFormat = new DecimalFormat("#.##");
	        decimalFormat.setGroupingUsed(true);
	        decimalFormat.setGroupingSize(3);
	        
	     assetsTotal.setText("$" + decimalFormat.format(total));
	}



	public static void removeAssetFromTable(Asset asset) {
		assetsTable.getItems().remove(asset);
	}
	
	@FXML
	public void analysisOpen() {
		LTAAnalysisController analysis = new LTAAnalysisController();
		vbox = new LTAAnalysisController();
		pane.setContent(vbox);
	}
}
