package menuAdminPages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import modelData.Sparepart;
import theHelper.DBConnection;
//DONEE
public class ManageSparepartMenu implements EventHandler<ActionEvent> {
	Scene sc;
	GridPane gp, gp1;
	BorderPane bp, bpBottom, bp1, bp2;
	FlowPane fp;
	//ScrollPane sp;

	MenuBar menus;
	Menu menu;
	MenuItem manageSparepart, manageUSer, viewtransaction, logout;

	Text idTxt, nameTxt, brandTxt, priceTxt, stockTxt, quantityTxt, updateStockTxt;
	TextField nameTf, brandtTf;
	Spinner<Integer> quantitySpinner, priceSpinner, updateStockSpinner;
	Button insertBtn, updateBtn, deleteBtn;
	TableView<Sparepart> tableSparepart;

	ArrayList<Sparepart> sparepartData = new ArrayList<>();
	DBConnection dbConnection;

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

	public ManageSparepartMenu() {
		init();
		addComponent();
		arrangeComponet();
		setEventHandlerMenuBar();
		disableHandle();
		setMouse();
		unSelectTable();
	}

	public void disableHandle() {
		disable();
		tableSparepart.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<Sparepart> sparepartSelect = tableSparepart.getSelectionModel();
				sparepartSelect.setSelectionMode(SelectionMode.SINGLE);

				Sparepart s = sparepartSelect.getSelectedItem();
				if (s == null) {
					disable();

				} else {
					updateStockTxt.setFill(Color.BLACK);
					updateStockSpinner.setDisable(false);
					updateBtn.setDisable(false);
					deleteBtn.setDisable(false);

				}
			}
		});

	}

	public void disable() {
		updateStockTxt.setFill(Color.GRAY);
		updateStockSpinner.setDisable(true);
		updateBtn.setDisable(true);
		deleteBtn.setDisable(true);
	}


	public void init() {
		dbConnection = DBConnection.getConnection();

		gp = new GridPane();
		gp1 = new GridPane();
		bp = new BorderPane();
		bpBottom = new BorderPane();
		bp1 = new BorderPane();
		bp2 = new BorderPane();
		fp = new FlowPane();
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

		menus.setMinSize(780, 10);
		menus.getMenus().add(menu);

		nameTxt = new Text("Name");
		brandTxt = new Text("Brand");
		quantityTxt = new Text("Quantity");
		priceTxt = new Text("Price");
		updateStockTxt = new Text("Update Stock");

		nameTf = new TextField();
		brandtTf = new TextField();

		quantitySpinner = new Spinner<Integer>(1, 100, 0, 1);
		priceSpinner = new Spinner<Integer>(10000, Integer.MAX_VALUE, 0, 1000);
		updateStockSpinner = new Spinner<Integer>(0, 100, 0, 1);

		insertBtn = new Button("Insert");
		updateBtn = new Button("Update");
		deleteBtn = new Button("Delete");

		tableSparepart = new TableView<>();

		sc = new Scene(bp, 1000, 750);
	}

	public void addComponent() {
		gp.add(nameTxt, 0, 0);
		gp.add(nameTf, 2, 0);

		gp.add(brandTxt, 0, 1);
		gp.add(brandtTf, 2, 1);

		gp.add(quantityTxt, 0, 2);
		gp.add(quantitySpinner, 2, 2);

		gp.add(priceTxt, 0, 3);
		gp.add(priceSpinner, 2, 3);

		gp1.add(updateStockTxt, 0, 0);
		gp1.add(updateStockSpinner, 2, 0);

		fp.getChildren().add(updateBtn);
		fp.getChildren().add(deleteBtn);

		fp.setHgap(35);

		setTable(); 

		bp1.setCenter(gp);
		bp1.setBottom(insertBtn);

		bp2.setCenter(gp1);
		bp2.setBottom(fp);

		bpBottom.setLeft(bp1);
		bpBottom.setRight(bp2);

		bp.setTop(menus);	
		bp.setCenter(tableSparepart);
		bp.setBottom(bpBottom); 
		//		sp.setContent(tableSparepart);
		//		sp.setVbarPolicy(ScrollBarPolicy.NEVER);
		//		sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		//		sp.setFitToHeight(true);
	}

	public void arrangeComponet() {
		gp.setHgap(80);
		gp.setVgap(10);

		gp1.setHgap(35);
		gp1.setVgap(10);

		BorderPane.setAlignment(bp1, Pos.CENTER_LEFT);
		BorderPane.setAlignment(bp2, Pos.CENTER_LEFT);
		BorderPane.setAlignment(insertBtn, Pos.CENTER);
		BorderPane.setMargin(gp, new Insets(0, 0, 20, 0));
		BorderPane.setMargin(fp, new Insets(0, 0, 20, 0));
		BorderPane.setMargin(bp1, new Insets(10, 10, 10, 10));
		BorderPane.setMargin(bp2, new Insets(40, 80, 70, 0));
		BorderPane.setMargin(insertBtn, new Insets(0, 0, 10, 0));
	}


	public void cAlert(AlertType alertType, String msg) {
		Alert alert = new Alert(alertType);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	public void setMouse() {

		insertBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String name = nameTf.getText();
				String brand = brandtTf.getText();
				int quantity = quantitySpinner.getValue();
				int price = priceSpinner.getValue();

				if(name.length() == 0) {
					cAlert(AlertType.ERROR, "Sparepart Name cannot be empty!");
				}else if (name.length()< 5 || name.length() > 20){
					cAlert(AlertType.ERROR, "Sparepart Name must be between 5 - 20 characters!");
				}else if(brand.length() == 0) {
					cAlert(AlertType.ERROR, "Sparepart Brand cannot be empty!");
				}else if(quantity <= 0) {
					cAlert(AlertType.ERROR, "Sparepart Quantity must be more than 0!");
				}else if(price <= 10000) {
					cAlert(AlertType.ERROR, "Sparepart Price must be greater than 10000!");
				}else {
					cAlert(AlertType.INFORMATION, "Sparepart has been inserted!");
					//tableSparepart.getItems().add(new Sparepart(id, name, brand, quantity, price));
					insertSparepart();
					kosongFieldInsert();
					disableHandle();
				}
			}
		});

		updateBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				updateSparepart();
				kosongFieldUpDel();
				disableHandle();
			}
		});

		deleteBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure want to delete the sparepart?");
				Optional<ButtonType> option = alert.showAndWait();

				if(option.get().equals(ButtonType.OK)) {
					deleteSparepart();
					cAlert(AlertType.INFORMATION, "Sparepart has been deleted!");
				}else if (option.get().equals(ButtonType.CANCEL)) {
					cAlert(AlertType.INFORMATION, "Sparepart was canceled for deletion!");
				}
				kosongFieldUpDel();
				disableHandle();
			}
		});
	}

	public void kosongFieldInsert() {
		nameTf.setText("");
		brandtTf.setText("");
		quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0, 1));
		priceSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10000, Integer.MAX_VALUE, 0, 1000));

	}

	public void kosongFieldUpDel() {
		updateStockSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
		tableSparepart.getSelectionModel().clearSelection();
	}

	public void unSelectTable() {
		bp.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				tableSparepart.getSelectionModel().clearSelection();
				kosongFieldUpDel();
				disable();
			}
		});
	}

	public void setTable() {
		TableColumn<Sparepart, String> idColumn = new TableColumn<>("ID");
		TableColumn<Sparepart, String> nameColumn = new TableColumn<>("Name");
		TableColumn<Sparepart, String> brandColumn = new TableColumn<>("Brand");
		TableColumn<Sparepart, Integer> priceColumn = new TableColumn<>("Price");
		TableColumn<Sparepart, Integer> stockColumn = new TableColumn<>("Stock");

		tableSparepart.setPrefWidth(sc.getWidth());

		idColumn.setMinWidth(sc.getWidth() / 5);
		nameColumn.setMinWidth(sc.getWidth() / 5);
		brandColumn.setMinWidth(sc.getWidth() / 5);
		priceColumn.setMinWidth(sc.getWidth() / 5);
		stockColumn.setMinWidth(sc.getWidth() / 5);

		idColumn.setCellValueFactory(new PropertyValueFactory<>("sparepartId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("sparepartName"));
		brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

		tableSparepart.getColumns().addAll(Arrays.asList(idColumn, nameColumn, brandColumn, priceColumn, stockColumn));

		refreshTable();
	}

	public void refreshTable() {
		sparepartData.clear();
		getSparepart();
		ObservableList<Sparepart> sparepartObsL = FXCollections.observableArrayList(sparepartData);
		tableSparepart.setItems(sparepartObsL);
	}

	public void getSparepart() {
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

	public String IDString() {
		String query = "SELECT * FROM `sparepart` ORDER BY sparepart.SparePartID DESC LIMIT 1";

		ResultSet rs = dbConnection.executeQuery(query);
		String id = null;
		try {
			while(rs.next()) {
				id = rs.getString("SparePartID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sp ="";
		String xxx = "";

		for (int i = 0; i < id.length(); i++) {
			if (i < 2) {
				sp = sp + id.charAt(i);
			}else {
				xxx = xxx + id.charAt(i);
			}
		}

		int xxxTemp = Integer.parseInt(xxx);
		xxxTemp = xxxTemp + 1;
		xxx = Integer.toString(xxxTemp);

		if (xxx.length() == 1) {
			xxx = "00" + xxx;
		} else if (xxx.length() == 2) {
			xxx = "0" + xxx;
		}

		return sp + xxx;
	}


	public void insertSparepart() {
		String sparepartId = IDString();
		String sparepartName = nameTf.getText();
		String brand = brandtTf.getText();
		Integer price = priceSpinner.getValue();
		Integer stock = quantitySpinner.getValue();

		PreparedStatement ps = dbConnection.preparedStatement("INSERT INTO `sparepart` (`SparePartID`, `SparePartName`, `Brand`, `Price`, `Stock`) VALUES (?, ?, ?, ?, ?)");

		try {
			ps.setString(1, sparepartId);
			ps.setString(2, sparepartName);
			ps.setString(3, brand);
			ps.setInt(4, price);
			ps.setInt(5, stock);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//		String query = String.format("INSERT INTO `sparepart` (`SparePartID`, `SparePartName`, `Brand`, `Price`, `Stock`) VALUES ('%s', '%s', '%s', '%d', '%d')", sparepartId, sparepartName, brand, price, stock);
		//
		//		dbConnection.executeUpdate(query);

		refreshTable();
	}

	public void updateSparepart() {
		TableSelectionModel<Sparepart> selectSparepart = tableSparepart.getSelectionModel();
		selectSparepart.setSelectionMode(SelectionMode.SINGLE);

		Sparepart s = selectSparepart.getSelectedItem();
		Integer stock = updateStockSpinner.getValue();
		String sparepartId = s.getSparepartId();

		PreparedStatement ps = dbConnection.preparedStatement("UPDATE `sparepart` SET Stock = ? WHERE sparepart.SparePartID = ?");

		try {
			ps.setInt(1, stock);
			ps.setString(2, sparepartId);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//	String query = String.format("UPDATE `sparepart` SET Stock = %d WHERE sparepart.SparePartID = '%s'", stock, sparepartId);
		//
		//	dbConnection.executeUpdate(query);

		refreshTable();
	}

	public void deleteSparepart() {
		TableSelectionModel<Sparepart> selectSparepart = tableSparepart.getSelectionModel();
		selectSparepart.setSelectionMode(SelectionMode.SINGLE);

		Sparepart s = selectSparepart.getSelectedItem();
		String sparepartId = s.getSparepartId();

		PreparedStatement ps = dbConnection.preparedStatement("DELETE FROM `sparepart` WHERE sparepart.SparePartID = ?");

		try {
			ps.setString(1, sparepartId);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//		String query = String.format("DELETE FROM `sparepart` WHERE sparepart.SparePartID = '%s'", sparepartId);
		//
		//		dbConnection.executeUpdate(query);

		refreshTable();
	}


	public Scene getManageSparepartMenu() {
		return sc;
	}
}
