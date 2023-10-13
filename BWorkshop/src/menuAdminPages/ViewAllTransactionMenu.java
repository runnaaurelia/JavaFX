package menuAdminPages;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import theHelper.DBConnection;

public class ViewAllTransactionMenu implements EventHandler<ActionEvent>{
	Scene scene;
	BorderPane bp;
	//ScrollPane sp;

	MenuBar menus;
	Menu menu;
	MenuItem manageSparepart, manageUSer, viewtransaction, logout;

	TableView<Transaction> tabelAllTransaksi;
	TableColumn<Transaction, Integer> transactionId, quantity;
	TableColumn<Transaction, String> username, sparepart;

	DBConnection dbConnection;

	ArrayList<Transaction> transactionData = new ArrayList<>();

	public void setEventHandlerMenuBar() {
		manageSparepart.setOnAction(this);
		manageUSer.setOnAction(this);
		viewtransaction.setOnAction(this);
		logout.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == manageSparepart) {
			Main.authS.displayManageSparepartMenu();
		} else if (event.getSource() == manageUSer) {
			Main.authS.displayManageUserMenu();
		} else if (event.getSource() == viewtransaction) {
			Main.authS.displayViewAllTransaction();
		} else if (event.getSource() == logout) {
			Main.authS.displayLoginPage();
		}

	}

	public ViewAllTransactionMenu() {
		init();
		layout();
		setEventHandlerMenuBar();
	}

	public void init() {
		dbConnection = DBConnection.getConnection();

		bp = new BorderPane();
		//sp = new ScrollPane();

		menus = new MenuBar();

		menu = new Menu("Menu");

		manageSparepart = new MenuItem("Manage Sparepart");
		manageUSer = new MenuItem("Manage User");
		viewtransaction = new MenuItem("View Transaction");
		logout = new MenuItem("Logout");

		menu.getItems().add(manageSparepart);
		menu.getItems().add(manageUSer);
		menu.getItems().add(viewtransaction);
		menu.getItems().add(logout);

		scene = new Scene(bp, 1000, 750);
	}

	public void tabel() {
		tabelAllTransaksi = new TableView<>();

		transactionId = new TableColumn<>("TransactionID");
		username = new TableColumn<>("Username");
		sparepart = new TableColumn<>("Sparepart");
		quantity = new TableColumn<>("Quantity");

		transactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		username.setCellValueFactory(new PropertyValueFactory<>("userName"));
		sparepart.setCellValueFactory(new PropertyValueFactory<>("sparepartName"));
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		tabelAllTransaksi.getColumns().addAll(Arrays.asList(transactionId, username, sparepart, quantity));

		tabelAllTransaksi.setPrefWidth(scene.getWidth());
		transactionId.setPrefWidth(scene.getWidth()/4);
		username.setPrefWidth(scene.getWidth()/4);
		sparepart.setPrefWidth(scene.getWidth()/4);
		quantity.setPrefWidth(scene.getWidth()/4);

		refreshTable();
	}

	public void layout() {
		menus.getMenus().add(menu);
		menus.setMinSize(780, 10);

		tabel();

		bp.setTop(menus);
		bp.setCenter(tabelAllTransaksi);

		//		sp.setContent(tabelAllTransaksi);
		//		sp.setVbarPolicy(ScrollBarPolicy.NEVER);
		//		sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		//		sp.setFitToHeight(true);

		BorderPane.setMargin(tabelAllTransaksi, new Insets(10, 0, 10, 0));
	}

	public void refreshTable() {
		transactionData.clear();
		getTransaction();
		ObservableList<Transaction> transactionObsL = FXCollections.observableArrayList(transactionData);
		tabelAllTransaksi.setItems(transactionObsL);
	}

	public void getTransaction() {
		String query = "SELECT transactiondetail.TransactionID, transactionheader.UserID, user.username, sparepart.SparePartName, sparepart.Brand, transactiondetail.Quantity, sparepart.Price FROM `transactionheader` JOIN `transactiondetail` JOIN `user` JOIN `sparepart` WHERE transactionheader.TransactionID = transactiondetail.TransactionID AND transactionheader.UserID = user.UserID AND transactiondetail.SparePartID = sparepart.SparePartID ORDER BY transactiondetail.TransactionID ASC";

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
	}



	public Scene getViewAllTransactionMenu() {
		return scene;
	}
}
