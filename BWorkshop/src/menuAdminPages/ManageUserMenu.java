package menuAdminPages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import modelData.User;
import theHelper.DBConnection;

public class ManageUserMenu implements EventHandler<ActionEvent>{

	Scene scene;
	BorderPane bp, bpUtama; 
	GridPane gp;
	FlowPane fp;
	//ScrollPane sp;

	MenuBar menus;
	Menu menu;
	MenuItem manageSparepart, manageUSer, viewtransaction, logout;

	TableView<User> tabelManageUser;
	TableColumn<User, Integer> userId;
	TableColumn<User, String> username, email, password, role;

	Text newUsernameTxt, newPasswordTxt;
	TextField newUsernameTF;
	PasswordField newPasswordPF;
	Button updateUserBtn, deleteUserBtn;

	ArrayList<User> userData = new ArrayList<>();
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


	public void disable() {
		newUsernameTF.setDisable(true);
		newPasswordPF.setDisable(true);
		updateUserBtn.setDisable(true);
		deleteUserBtn.setDisable(true);
	}

	public void disableHandle() {
		disable();
		tabelManageUser.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<User> userSelect = tabelManageUser.getSelectionModel();
				userSelect.setSelectionMode(SelectionMode.SINGLE);

				User u = userSelect.getSelectedItem();
				if (u == null) {
					disable();

				}else {

					newUsernameTF.setDisable(false);
					newPasswordPF.setDisable(false);
					updateUserBtn.setDisable(false);
					deleteUserBtn.setDisable(false);
				}
			}
		});

	}

	public ManageUserMenu() {
		init();
		layout();
		disableHandle();
		setEventHandlerMenuBar();
		setMouse();
		unSelectTable();
	}



	public void init() {
		dbConnection = DBConnection.getConnection();

		bp = new BorderPane();
		bpUtama = new BorderPane();
		gp = new GridPane();
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


		tabelManageUser = new TableView<>();

		newUsernameTxt = new Text("New Username");
		newPasswordTxt = new Text("New Password");

		newUsernameTF = new TextField();
		newPasswordPF = new PasswordField();

		updateUserBtn = new Button("Update");
		deleteUserBtn = new Button("Delete");


		scene = new Scene(bpUtama, 1000, 750);
	}

	public void layout() {
		menus.getMenus().add(menu);
		menus.setMinSize(780, 10);

		tabel();

		gp.add(newUsernameTxt, 0, 0);
		gp.add(newUsernameTF, 1, 0);
		gp.add(newPasswordTxt, 0, 1);
		gp.add(newPasswordPF, 1, 1);
		newUsernameTF.setPrefWidth(190);
		newUsernameTF.setPrefHeight(30);
		newPasswordPF.setPrefWidth(190);
		newPasswordPF.setPrefHeight(30);

		gp.setVgap(90);
		gp.setHgap(140);
		gp.setPadding(new Insets(30));

		fp.getChildren().addAll(updateUserBtn, deleteUserBtn);
		updateUserBtn.setPrefSize(70, 30);
		deleteUserBtn.setPrefSize(70, 30);
		fp.setHgap(40);
		fp.setPadding(new Insets(0, 0, 30, 50));

		bp.setTop(tabelManageUser);
		bp.setCenter(gp);
		bp.setBottom(fp);

		//		sp.setContent(tabelManageUser);
		//		sp.setVbarPolicy(ScrollBarPolicy.NEVER);
		//		sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);

		bpUtama.setTop(menus);
		bpUtama.setCenter(bp);
	}

	public void tabel() {
		userId = new TableColumn<>("User ID");
		username = new TableColumn<>("Username");
		email = new TableColumn<>("Email");
		password = new TableColumn<>("Password");
		role = new TableColumn<>("Role");

		userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
		username.setCellValueFactory(new PropertyValueFactory<>("username"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		password.setCellValueFactory(new PropertyValueFactory<>("password"));
		role.setCellValueFactory(new PropertyValueFactory<>("role"));

		tabelManageUser.getColumns().addAll(Arrays.asList(userId, username, email, password, role));

		tabelManageUser.setPrefWidth(scene.getWidth());

		userId.setPrefWidth(scene.getWidth()/5);
		username.setPrefWidth(scene.getWidth()/5);
		email.setPrefWidth(scene.getWidth()/5);
		password.setPrefWidth(scene.getWidth()/5);
		role.setPrefWidth(scene.getWidth()/5);

		refreshTable();
	}


	public void refreshTable() {
		userData.clear();
		getUser();
		ObservableList<User> userObsL = FXCollections.observableArrayList(userData);
		tabelManageUser.setItems(userObsL);
	}

	public void getUser() {
		String query = "SELECT * FROM `user`";
		ResultSet rs = dbConnection.executeQuery(query);

		try {
			while(rs.next()) {
				Integer userId = rs.getInt("UserID");
				String username = rs.getString("Username");
				String email = rs.getString("Email");
				String password = rs.getString("Password");
				String role = rs.getString("Role");

				userData.add(new User(userId, username, email, password, role));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void cAlert(AlertType alertType, String msg) {
		Alert alert = new Alert(alertType);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	public void setMouse() {
		// TODO Auto-generated method stub

		updateUserBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				String newUsername = newUsernameTF.getText();
				String newPassword = newPasswordPF.getText();

				//Alphanumeric
				int countAlpha=0;
				int countDigit=0;

				for (int i = 0; i < newPassword.length(); i++) {
					if (Character.isAlphabetic(newPassword.charAt(i))==true) {
						countAlpha++; // nilai countalpha menjadi satu 
					}
					if (Character.isDigit(newPassword.charAt(i))==true) {
						countDigit++; // nilai countDigit = 1
					}
				}

				if (newUsername.isEmpty() && newPassword.isEmpty()) {
					cAlert(AlertType.ERROR, "Fields cannot be empty!");
				}else if (newUsername.isEmpty()) {
					cAlert(AlertType.ERROR, "New Username field cannot be empty!");
				}else if (newUsername.length() < 5 || newUsername.length() > 25) {
					cAlert(AlertType.ERROR, "New Username must be between 5 - 25 characters!");
				}else if (newPassword.isEmpty()) {
					cAlert(AlertType.ERROR, "New Password cannot be empty!");
				}else if (newPassword.length() <= 5 || countAlpha < 1 || countDigit < 1) {
					cAlert(AlertType.ERROR, "New Password must greater than 5 characters and must be alphanumeric!");
				}else {
					cAlert(AlertType.INFORMATION, "Account updated!");
					updateUser();
					kosongField();
					kosongSelect();
					disableHandle();
				}


			}
		});

		deleteUserBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				deleteUser();
				kosongField();
				kosongSelect();
				disableHandle();
			}
		});
	}

	public void kosongField() {
		newUsernameTF.setText("");
		newPasswordPF.setText("");
	}

	public void kosongSelect() {
		tabelManageUser.getSelectionModel().clearSelection();
	}

	public void unSelectTable() {
		bpUtama.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				tabelManageUser.getSelectionModel().clearSelection();
				kosongField();
				disable();

			}
		});
	}

	public void updateUser() {
		TableSelectionModel<User> selectUser = tabelManageUser.getSelectionModel();
		selectUser.setSelectionMode(SelectionMode.SINGLE);

		User u = selectUser.getSelectedItem();
		Integer userId = u.getUserId();
		String newUsername = newUsernameTF.getText();
		String newPassword = newPasswordPF.getText();

		PreparedStatement updatePs = dbConnection.preparedStatement("UPDATE `user` SET user.Username = ?, user.password = ? WHERE user.UserID = ?");

		try {
			updatePs.setString(1, newUsername);
			updatePs.setString(2, newPassword);
			updatePs.setInt(3, userId);
			updatePs.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//		String query = String.format("UPDATE `user` SET user.Username = '%s', user.password = '%s' WHERE user.UserID = %d", newUsername, newPassword, userId);
		//
		//		dbConnection.executeUpdate(query);

		refreshTable();

	}

	public void deleteUser() {
		TableSelectionModel<User> selectUser = tabelManageUser.getSelectionModel();
		selectUser.setSelectionMode(SelectionMode.SINGLE);

		User u = selectUser.getSelectedItem();
		Integer userId = u.getUserId();

		PreparedStatement deletePs = dbConnection.preparedStatement("DELETE FROM `user` WHERE user.UserID  = ?");

		try {
			deletePs.setInt(1, userId);
			deletePs.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//		String query = String.format("DELETE FROM `user` WHERE user.UserID  = '%d'", userId);
		//
		//		dbConnection.executeUpdate(query);

		refreshTable();
	}

	public Scene getManageUserMenu() {
		return scene;
	}

}
