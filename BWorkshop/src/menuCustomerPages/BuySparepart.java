package menuCustomerPages;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import modelData.Sparepart;
import modelData.SparepartCart;
import modelData.Transaction;
import modelData.User;
import theHelper.DBConnection;

public class BuySparepart implements EventHandler<ActionEvent>{

	Scene scene;
	BorderPane bp1, bp2, bp3, bp4, bpUtama;
	GridPane gpBS, gpCL, gp;
	//ScrollPane sp;

	MenuBar menus;
	Menu menu;
	MenuItem sparepartMarket, transactionHistory, logoutItem;

	TableView<Sparepart> tabelSparepart;

	TableView<SparepartCart> tabelCustomerCart;

	TableColumn<Sparepart, String> name, brand;
	TableColumn<Sparepart, Integer> price, stock;

	TableColumn<SparepartCart, String> sName, sBrand;
	TableColumn<SparepartCart, Integer> sQty, sPrice, sTotal;

	Text buySparepartTxt, qtyTxt, cartListTxt;
	Spinner<Integer> qtySpinner;
	Button cartBtn, checkOutBtn, clearCartBtn;

	ArrayList<Sparepart> sparepartData = new ArrayList<>();
	ArrayList<SparepartCart> sparepartCartData = new ArrayList<>();
	ArrayList<User> userData = new ArrayList<>();
	ArrayList<Transaction> transactionData = new ArrayList<>();

	LocalDate theInputDate = LocalDate.now();

	DBConnection dbConnection;

	public BuySparepart() {
		init();
		layout();
		style();
		setEventHandlerMenuBar();
		disableHandle();
		setMouseHandleButton();
		unSelectTable();
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


	public void disableHandle() {
		disable();
		tabelSparepart.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<Sparepart> sparepartSelect = tabelSparepart.getSelectionModel();
				sparepartSelect.setSelectionMode(SelectionMode.SINGLE);

				Sparepart s = sparepartSelect.getSelectedItem();
				if (s == null) {
					disable();
				}else {
					qtyTxt.setFill(Color.BLACK);
					qtySpinner.setDisable(false);
					cartBtn.setDisable(false);
				}
			}
		});



	}

	public void unSelectTable() {
		bpUtama.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				tabelSparepart.getSelectionModel().clearSelection();
				disable();
				resetQtySpin();
				tabelCustomerCart.getSelectionModel().clearSelection();
			}
		});
	}


	public void disable() {
		qtyTxt.setFill(Color.GRAY);
		qtySpinner.setDisable(true);
		cartBtn.setDisable(true);
	}

	public void init() {
		dbConnection = DBConnection.getConnection();

		bp1 = new BorderPane();
		bp2 = new BorderPane();
		bp3 = new BorderPane();
		bp4 = new BorderPane();
		bpUtama = new BorderPane();		
		gp = new GridPane();
		gpBS = new GridPane();
		gpCL = new GridPane();

		//sp = new ScrollPane();

		menus = new MenuBar();

		menu = new Menu("Menu");

		sparepartMarket = new MenuItem("Buy Sparepart");
		transactionHistory = new MenuItem("View Your Transaction");
		logoutItem = new MenuItem("Logout");

		menu.getItems().add(sparepartMarket);
		menu.getItems().add(transactionHistory);
		menu.getItems().add(logoutItem);

		tabelSparepart = new TableView<>();
		tabelCustomerCart = new TableView<>();

		buySparepartTxt = new Text("Buy Sparepart");
		qtyTxt = new Text("Quantity");
		cartListTxt = new Text("Cart List");

		qtySpinner = new Spinner<>(0, Integer.MAX_VALUE, 1);

		cartBtn = new Button("Add to cart");
		checkOutBtn = new Button("Check Out");
		clearCartBtn = new Button("Clear cart");

		scene = new Scene(bpUtama, 1000, 750);
	}

	public void table() {
		name = new TableColumn<>("Name");
		brand = new TableColumn<>("Brand");
		price = new TableColumn<>("Price");
		stock = new TableColumn<>("Stock");

		sName = new TableColumn<>("Sparepart Name");
		sBrand = new TableColumn<>("Brand");
		sQty = new TableColumn<>("Quantity");
		sPrice = new TableColumn<>("Price");
		sTotal = new TableColumn<>("Total");

		name.setCellValueFactory(new PropertyValueFactory<>("sparepartName"));
		brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		stock.setCellValueFactory(new PropertyValueFactory<>("stock"));

		sName.setCellValueFactory(new PropertyValueFactory<>("sparepartName"));
		sBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
		sQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		sPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		sTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

		tabelSparepart.getColumns().addAll(Arrays.asList(name, brand, price, stock));
		tabelCustomerCart.getColumns().addAll(Arrays.asList(sName, sBrand, sQty, sPrice, sTotal));

		tabelSparepart.setPrefWidth(600);
		tabelSparepart.setPrefHeight(bpUtama.getHeight()/2 - 100);
		name.setPrefWidth(600/3);
		brand.setPrefWidth(600/3);
		price.setPrefWidth(600/6);
		stock.setPrefWidth(600/6);

		tabelCustomerCart.setPrefWidth(600);
		tabelCustomerCart.setPrefHeight(bpUtama.getHeight()/2 + 100);
		sName.setPrefWidth(600/3);
		sBrand.setPrefWidth(600/6);
		sQty.setPrefWidth(600/6);
		sPrice.setPrefWidth(600/6);
		sTotal.setPrefWidth(600/6);

		refreshTableSparepart();
		refreshTableSparepartCart();
	}

	public void layout() {
		menus.getMenus().add(menu);
		menus.setMinSize(780, 10);

		table();

		gpBS.add(buySparepartTxt, 0, 0);
		gpBS.add(qtyTxt, 0, 1);
		gpBS.add(qtySpinner, 1, 1);
		gpBS.add(cartBtn, 0, 2);
		gpBS.setHgap(30);
		gpBS.setVgap(20);

		qtySpinner.setPrefWidth(110);

		gpCL.add(cartListTxt, 0, 0);
		gpCL.add(checkOutBtn, 0, 1);
		gpCL.add(clearCartBtn, 1, 1);
		gpCL.setVgap(20);
		gpCL.setHgap(30);

		checkOutBtn.setPrefWidth(110);
		checkOutBtn.setPrefHeight(35);
		clearCartBtn.setPrefWidth(110);
		clearCartBtn.setPrefHeight(35);

		bp1.setLeft(tabelSparepart);
		bp3.setLeft(gpBS);

		bp2.setLeft(tabelCustomerCart); 
		bp4.setLeft(gpCL);

		//		sp.setContent(bp1);
		//		sp.setVbarPolicy(ScrollBarPolicy.NEVER);
		//		sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		//		sp.setFitToHeight(true);

		gp.add(tabelSparepart, 0, 1);
		gp.add(bp3, 1, 1);
		gp.add(bp2, 0, 2);
		gp.add(bp4, 1, 2);

		bpUtama.setTop(menus);
		bpUtama.setCenter(gp);

		GridPane.setMargin(bp3, new Insets(60, 40, 80, 40));
		GridPane.setMargin(bp4, new Insets(120, 40, 60, 40));
	}

	public void style() {
		buySparepartTxt.setFont(Font.font(null, FontWeight.BOLD, 20));
		cartListTxt.setFont(Font.font(null, FontWeight.BOLD, 20));
	}

	public void setAlertHandle() {
		cartBtn.setOnAction(this);
		checkOutBtn.setOnAction(this);
		clearCartBtn.setOnAction(this);
	}

	public void cAlert(AlertType alertType, String msg) {
		Alert alert = new Alert(alertType);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	public void resetQtySpin() {
		qtySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
	}

	public void setMouseHandleButton() {


		cartBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				int quantity = qtySpinner.getValue();	

				TableSelectionModel<Sparepart> selectSparepart = tabelSparepart.getSelectionModel();
				selectSparepart.setSelectionMode(SelectionMode.SINGLE);

				Sparepart s = selectSparepart.getSelectedItem();

				if(quantity <= 0) {
					cAlert(AlertType.ERROR, "Quantity must be more than 0!");
				}else if(quantity > s.getStock()) { 
					cAlert(AlertType.ERROR, "Quantity cannot be more than stock!");
				}else {
					insertBuySparepart();
					cAlert(AlertType.INFORMATION, "Sparepart has added to your cart!");
					disable();
					resetQtySpin();
				}
			}
		});

		checkOutBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				//ini add database dulu tpi belum tambahin

				if (tabelCustomerCart.getItems().isEmpty()) {
					cAlert(AlertType.INFORMATION, "Cart is empty! \nCustomer cannot checkout!");
				}else {
					checkout();
					String queryClearCart = String.format("DELETE FROM `sparepartcart` WHERE sparepartcart.UserID = '%d'", User.us.getUserId());
					dbConnection.executeUpdate(queryClearCart);

					refreshTableSparepartCart();

					cAlert(AlertType.INFORMATION, "Check out success!");
				}
			}
		});

		clearCartBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(tabelCustomerCart.getItems().isEmpty() && sparepartCartData.isEmpty()) {

					cAlert(AlertType.INFORMATION, "Cart is already empty!");

				} else {
					deleteIsiCart();
					cAlert(AlertType.INFORMATION, "Cart is cleared!");

				}
			}
		});
	}

	public void deleteIsiCart() {

		for (int i = 0; i < sparepartCartData.size(); i++) {

			for (int j = 0; j < sparepartData.size(); j++) {

				if (sparepartCartData.get(i).getSparepartId().equals(tabelSparepart.getItems().get(j).getSparepartId())) {
					String queryUpdate = String.format("UPDATE `sparepart` SET `Stock`='%d' WHERE SparePartID = '%s'",(tabelSparepart.getItems().get(j).getStock() + sparepartCartData.get(i).getQuantity()), tabelSparepart.getItems().get(j).getSparepartId());
					dbConnection.executeUpdate(queryUpdate);
					refreshTableSparepart();
				}

			}

		}

		String queryClearCart = String.format("DELETE FROM `sparepartcart` WHERE sparepartcart.UserID = '%d'", User.us.getUserId());
		dbConnection.executeUpdate(queryClearCart);

		refreshTableSparepartCart();

		//		getSparepart();
		//	getSparepartCart();

		//		for (SparepartCart sparepartCart : sparepartCartData) {
		//			
		//			for (Sparepart sparepart : sparepartData) {
		//				
		//				if (sparepartCart.getSparepartId().equals(((Sparepart) tabelSparepart.getItems()).getSparepartId())) {
		//					String queryUpdate = String.format("UPDATE `sparepart` SET `Stock`='%d' WHERE SparePartID = '%s'",(((Sparepart) tabelSparepart.getItems()).getStock() + sparepartCart.getQuantity()), ((Sparepart) tabelSparepart.getItems()).getSparepartId());
		//					dbConnection.executeUpdate(queryUpdate);
		//					refreshTableSparepart();
		//				}
		//			}
		//		}

	}


	public void insertBuySparepart() {
		getSparepart();
		TableSelectionModel<Sparepart> selectSparepart = tabelSparepart.getSelectionModel();
		selectSparepart.setSelectionMode(SelectionMode.SINGLE);

		Sparepart s = selectSparepart.getSelectedItem();

		String sparepartId = s.getSparepartId();
		Integer quantity = qtySpinner.getValue();
		Integer userId = User.us.getUserId();

		String querySC = null;
		//	String queryUpdate = null;

		querySC = String.format("INSERT INTO `sparepartcart` (`SparePartID`, `UserID`, `Quantity`) VALUES ('%s', '%d', '%d')", sparepartId, userId, quantity);


		for (SparepartCart sparepartCart : sparepartCartData) {

			if (sparepartCart.getSparepartId().equals(sparepartId)) {
				querySC = String.format("UPDATE `sparepartcart` SET sparepartcart.Quantity = '%d' WHERE sparepartcart.SparePartID = '%s' AND sparepartcart.UserID = '%d'", (sparepartCart.getQuantity()+quantity), sparepartId, userId);
			} 
		}

		dbConnection.executeUpdate(querySC);

		refreshTableSparepartCart();

		//		for (Sparepart sparepart : sparepartData) {
		//			if (sparepart.getSparepartId().equals(sparepartId)) {
		//				queryUpdate = String.format("UPDATE `sparepart` SET sparepart.Stock = '%d' WHERE sparepart.SparePartID = '%s'", (sparepart.getStock() - quantity),sparepartId);
		//			}
		//		}
		//		dbConnection.executeUpdate(queryUpdate);
		//		refreshTableSparepart();
	}

	public void refreshTableSparepart() {
		sparepartData.clear();
		getSparepart();
		ObservableList<Sparepart> sparepartObsL = FXCollections.observableArrayList(sparepartData);
		tabelSparepart.setItems(sparepartObsL);
	}


	public void refreshTableSparepartCart() {
		sparepartCartData.clear();
		getSparepartCart();
		ObservableList<SparepartCart> sparepartCartObsL = FXCollections.observableArrayList(sparepartCartData);
		tabelCustomerCart.setItems(sparepartCartObsL);
	}


	public void checkout() {
		getSparepartCart();
		Integer transactionId = autoIncrementTR(0);
		Integer userId = User.us.getUserId();
		String dateTransaction = theInputDate.toString();

		for (int i = 0; i < sparepartCartData.size(); i++) {
			String queryTR = String.format("INSERT INTO `transactionheader` (`TransactionID`, `UserID`, `TransactionDate`) VALUES ('%d', '%s', '%s')", transactionId, userId, dateTransaction);
			dbConnection.executeUpdate(queryTR);

			//System.out.println(queryTR);

			String queryTR2 = String.format("INSERT INTO `transactiondetail` (`TransactionID`, `SparePartID`, `Quantity`) VALUES ('%d', '%s', '%d')", transactionId, sparepartCartData.get(i).getSparepartId(), sparepartCartData.get(i).getQuantity());
			dbConnection.executeUpdate(queryTR2);

			//System.out.println(queryTR2);

			for (Sparepart sparepart : sparepartData) {
				if (sparepart.getSparepartId().equals(sparepartCartData.get(i).getSparepartId())) {
					String queryTR3 = String.format("UPDATE `sparepart` SET sparepart.Stock = '%d' WHERE sparepart.SparePartID = '%s'", (sparepart.getStock() - sparepartCartData.get(i).getQuantity()), sparepartCartData.get(i).getSparepartId());
					dbConnection.executeUpdate(queryTR3);
					//System.out.println(queryTR3);
				}
			}
			refreshTableSparepart();
			refreshTableSparepartCart();
			transactionId++;
		}

	}


	public void getTransaction() {
		String query = "SELECT transactiondetail.TransactionID, transactionheader.UserID, user.username, sparepart.SparePartName, sparepart.Brand, sparepartcart.Quantity, sparepart.Price FROM `transactionheader` JOIN `transactiondetail` JOIN `user` JOIN `sparepart` JOIN `sparepartcart` WHERE transactionheader.TransactionID = transactiondetail.TransactionID AND transactionheader.UserID = user.UserID AND transactiondetail.SparePartID = sparepart.SparePartID AND sparepart.SparePartID = sparepartcart.SparePartID AND transactionheader.UserID = "+User.us.getUserId()+" ORDER BY transactionheader.TransactionID ASC";
		//	String query = "SELECT transactiondetail.TransactionID, transactionheader.UserID, user.username, sparepart.SparePartName, sparepart.Brand, sparepartcart.Quantity, sparepart.Price FROM `transactionheader` JOIN `transactiondetail` JOIN `user` JOIN `sparepart` JOIN `sparepartcart` WHERE transactionheader.TransactionID = transactiondetail.TransactionID AND transactionheader.UserID = user.UserID AND transactiondetail.SparePartID = sparepart.SparePartID AND sparepart.SparePartID = sparepartcart.SparePartID AND transactionheader.UserID = user.UserID AND userID = "+User.us.getUserId()+" ORDER BY transactionheader.TransactionID ASC";
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

	public void getSparepart() {
		//String query = "SELECT * FROM `sparepart` WHERE stock >= 0";
		String query = "SELECT * FROM `sparepart`";
		ResultSet rs = dbConnection.executeQuery(query);

		try {
			while(rs.next()) {
				String sparepartId = rs.getString("SparePartID");
				String sparepartName = rs.getString("SparePartName");
				String brand = rs.getString("Brand"); 
				Integer price = rs.getInt("Price");
				Integer stock = rs.getInt("Stock");

				sparepartData.add(new Sparepart(sparepartId, sparepartName, brand, price, stock));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getSparepartCart() {
		String query = "SELECT sparepart.SparePartID, sparepartcart.UserID, sparepart.SparePartName, sparepart.Brand, sparepartcart.Quantity, sparepart.Price FROM `sparepartcart` JOIN `sparepart` WHERE sparepart.SparePartID = sparepartcart.SparepartID AND sparepartcart.UserID = "+User.us.getUserId();
		ResultSet rs = dbConnection.executeQuery(query);

		try {
			while(rs.next()) {
				String sparepartId = rs.getString("SparePartID");
				Integer userId = rs.getInt("UserID");
				String sparepartName = rs.getString("SparePartName");
				String brand = rs.getString("Brand");
				Integer price = rs.getInt("Price");
				Integer quantity = rs.getInt("Quantity");

				sparepartCartData.add(new SparepartCart(sparepartId, userId, sparepartName, brand, quantity, price, (quantity * price)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer autoIncrementTR(Integer id) {
		String query = "SELECT * FROM `transactionheader` ORDER BY transactionheader.TransactionID DESC LIMIT 1";

		ResultSet rs = dbConnection.executeQuery(query);
		try {
			while(rs.next()) {
				id = rs.getInt("TransactionID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id+1;
	}


	public Scene getBuySparepart() {
		return scene;

	}
}
