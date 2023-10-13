package appStartPages;

import java.sql.ResultSet;
import java.util.ArrayList;

import app.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import modelData.User;
import theHelper.DBConnection;

public class LoginPage implements EventHandler<ActionEvent>{
//DONE
	Scene sc;
	BorderPane bp;
	GridPane gp;
	FlowPane fp;

	Text titleTxt, emailTxt, passTxt;
	TextField emailTF;
	PasswordField passPF;
	Button submitBtn, registBtn;

	ArrayList<User> userData = new ArrayList<>();
	DBConnection dbConnection;

	public LoginPage() {
		init();
		layout();
		style();
		setEventHandler();
	}

	public void init() {
		dbConnection = DBConnection.getConnection();

		titleTxt = new Text("Login");
		emailTxt = new Text("Email");
		passTxt = new Text("Password");

		emailTF = new TextField();

		passPF = new PasswordField();

		submitBtn = new Button("Submit");

		registBtn = new Button("Register");

		bp = new BorderPane();
		gp = new GridPane();
		fp = new FlowPane();
		sc = new Scene(bp, 750, 500);
	}

	public void layout() {
		bp.setTop(titleTxt);
		bp.setCenter(gp);
		bp.setBottom(fp);
		bp.setPadding(new Insets(50));

		gp.add(emailTxt, 0, 0);
		gp.add(emailTF, 1, 0);
		gp.add(passTxt, 0, 1);
		gp.add(passPF, 1, 1);

		gp.setHgap(50);
		gp.setVgap(50);
		gp.setAlignment(Pos.CENTER);


		fp.getChildren().addAll(registBtn, submitBtn);
		fp.setHgap(50);
		fp.setAlignment(Pos.CENTER);


		BorderPane.setAlignment(titleTxt, Pos.TOP_CENTER);
		BorderPane.setAlignment(gp, Pos.CENTER);
		BorderPane.setAlignment(fp, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(fp, new Insets(0, 0, 50, 0));
	}

	public void style() {
		bp.setStyle("-fx-background-color: gray;");
		titleTxt.setFont(Font.font(null, FontWeight.SEMI_BOLD, 40));

		titleTxt.setFill(Color.WHITE);
		emailTxt.setFill(Color.WHITE);
		passTxt.setFill(Color.WHITE);

		emailTF.setPrefWidth(220);
		emailTF.setPrefHeight(25);
		passPF.setPrefWidth(220);
		passPF.setPrefHeight(25);

		registBtn.setMinSize(110, 32);
		submitBtn.setMinSize(110, 32);
	}


	public void setEventHandler() {
		registBtn.setOnAction(this);
		submitBtn.setOnAction(this);
	}

	public void cAlert(AlertType at, String msg) {
		Alert a = new Alert(at);
		a.setContentText(msg);
		a.showAndWait();
	} 

	public boolean cekExistEmailUser() {
		String cekEmail = emailTF.getText();

		String query = "SELECT * FROM `user`";
		ResultSet rs = dbConnection.executeQuery(query);

		try {
			while(rs.next()) {
				String email = rs.getString("Email");

				if (cekEmail.equals(email)) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean cekRightPassUser() {
		String cekPass = passPF.getText();

		String query = "SELECT * FROM `user`";
		ResultSet rs = dbConnection.executeQuery(query);

		try {
			while(rs.next()) {
				String pass = rs.getString("Password");

				if (cekPass.equals(pass)) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		getUser();
		String email, password;
		if (event.getSource() == submitBtn) {
			email = emailTF.getText();
			password = passPF.getText();
			if (email.length() == 0 && password.length() == 0) {
				cAlert(AlertType.ERROR, "Fields cannot be empty!");
			} else if (email.isEmpty()) {
				cAlert(AlertType.ERROR, "Email cannot be empty!");
			} else if (password.isEmpty()) {
				cAlert(AlertType.ERROR, "Password cannot be empty!");
			} else if (cekExistEmailUser()) {
				cAlert(AlertType.ERROR, "Email not exist!");
			}else if (cekRightPassUser()) {
				cAlert(AlertType.ERROR, "Password Incorrect/ Password not exist!");
			}{
				for (User user : userData) {
					if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
						cAlert(AlertType.INFORMATION, "Login Success!\nWelcome "+user.getUsername());
						if (user.getRole().equals("customer")) {
							User.us = user;
							Main.authS.displayMainMenuCustomer();
							kosongField();
							return;
						} else if (user.getRole().equals("admin")) {
							User.us = user;
							Main.authS.displayMainMenuAdmin();
							kosongField();
							return;
						}
					}
				}
			}
		}
		else if (event.getSource() == registBtn) {
			Main.authS.displayRegistPage();
			kosongField();
		}

	}

	public void kosongField() {
		emailTF.setText("");
		passPF.setText("");
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


	public Scene getLog() {
		return sc;
	}

}
