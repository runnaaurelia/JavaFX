package menuCustomerPages;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import modelData.Transaction;
import modelData.User;
import theHelper.DBConnection;

public class ViewYourTransaction implements EventHandler<ActionEvent>{
	Scene scene;
	BorderPane bp;
	//ScrollPane sp;

	MenuBar menus;
	Menu menu;
	MenuItem sparepartMarket, transactionHistory, logoutItem;

	TableView<Transaction> tabelTransaksi;

	TableColumn<Transaction, Integer> transactionId, quantity, sparepartPrice;
	TableColumn<Transaction, String> sparepart, brand;

	ArrayList<Transaction> transactionData = new ArrayList<>();
	DBConnection dbConnection;

	public ViewYourTransaction() {
		init();
		layout();
		setEventHandlerMenuBar();
		//refreshTable();
	}

	public void setEventHandlerMenuBar() {
		sparepartMarket.setOnAction(this);
		transactionHistory.setOnAction(this);
		logoutItem.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource() == sparepartMarket) {
			Main.authS.displayBuySparepart();
		} else if (event.getSource() == transactionHistory) {
			Main.authS.displayViewYourTransaction();
		} else if (event.getSource() == logoutItem) {
			Main.authS.displayLoginPage();
		}
	}

	public void init() {
		dbConnection = DBConnection.getConnection();

		bp = new BorderPane();
		//sp = new ScrollPane();

		menus = new MenuBar();

		menu = new Menu("Menu");

		sparepartMarket = new MenuItem("Buy Sparepart");
		transactionHistory = new MenuItem("View Your Transaction");
		logoutItem = new MenuItem("Logout");

		menu.getItems().add(sparepartMarket);
		menu.getItems().add(transactionHistory);
		menu.getItems().add(logoutItem);


		scene = new Scene(bp, 1000, 750);
	}

	public void table() {
		tabelTransaksi = new TableView<>();

		transactionId = new TableColumn<>("Transaction ID");
		sparepart = new TableColumn<>("Sparepart");
		brand = new TableColumn<>("Brand");
		quantity = new TableColumn<>("Quantity");
		sparepartPrice = new TableColumn<>("Sparepart Price");

		transactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		sparepart.setCellValueFactory(new PropertyValueFactory<>("sparepartName"));
		brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		sparepartPrice.setCellValueFactory(new PropertyValueFactory<>("sparepartPrice"));

		tabelTransaksi.getColumns().addAll(Arrays.asList(transactionId, sparepart, brand, quantity, sparepartPrice));


		tabelTransaksi.setPrefSize(scene.getWidth(), scene.getHeight());
		transactionId.setPrefWidth(scene.getWidth()/5);
		sparepart.setPrefWidth(scene.getWidth()/5);
		brand.setPrefWidth(scene.getWidth()/5);
		quantity.setPrefWidth(scene.getWidth()/5);
		sparepartPrice.setPrefWidth(scene.getWidth()/5);

		refreshTable();

	}

	public void layout() {
		menus.getMenus().add(menu);
		menus.setMinSize(780, 10);

		table();

		bp.setTop(menus);
		bp.setCenter(tabelTransaksi);

		//		sp.setContent(tabelTransaksi);
		//		sp.setVbarPolicy(ScrollBarPolicy.NEVER);
		//		sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		//		sp.setFitToHeight(true);
	}


	public void refreshTable() {
		transactionData.clear();
		getTransaction();
		ObservableList<Transaction> transactionObsL = FXCollections.observableArrayList(transactionData);
		tabelTransaksi.setItems(transactionObsL);
	}

	public void getTransaction() {
		String query = String.format("SELECT transactiondetail.TransactionID, transactionheader.UserID, user.username, sparepart.SparePartName, sparepart.Brand, transactiondetail.Quantity, sparepart.Price FROM `transactionheader` JOIN `transactiondetail` JOIN `user` JOIN `sparepart` WHERE transactionheader.TransactionID = transactiondetail.TransactionID AND transactionheader.UserID = user.UserID AND transactiondetail.SparePartID = sparepart.SparePartID AND transactionheader.UserID = '%d' ORDER BY transactiondetail.TransactionID ASC", User.us.getUserId());
		ResultSet rs = dbConnection.executeQuery(query);

		try {
			while(rs.next()) {
				Integer transactionId = rs.getInt("TransactionID");
				Integer userId = rs.getInt("UserID");
				String userName = rs.getString("Username");
				String sparepartName = rs.getString("SparePartName");
				String brand = rs.getString("Brand");
				Integer quantity = rs.getInt("Quantity");
				Integer sparepartPrice = rs.getInt("Price");

				transactionData.add(new Transaction(transactionId, userId, userName, sparepartName, brand, quantity, sparepartPrice));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(query);
		//System.out.println(transactionData.get(0).getTransactionId());
	}

	public Scene getViewYourTransaction() {
		return scene;
	}

}
