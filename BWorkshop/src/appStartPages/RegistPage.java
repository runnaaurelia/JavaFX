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
//DONE
public class RegistPage implements EventHandler<ActionEvent>{
	Scene scene;
	BorderPane borderPane;
	GridPane gridPane;
	FlowPane flowPane;

	Text title, username, email, password, confirmp;
	TextField usernameTF, emailTF;  
	PasswordField passwordPf, confirmpPf;
	Button registerButton;

	ArrayList<User> userData = new ArrayList<>();
	DBConnection dbConnection;

	public RegistPage() {
		init();
		layout();
		style();
		setEventHandler();
	}

	public void init() {
		dbConnection = DBConnection.getConnection();

		title = new Text("Register");
		username = new Text("Username"); 
		email = new Text ("Email");
		password = new Text("Password"); 
		confirmp = new Text ("Confirm Password");

		usernameTF = new TextField(); 
		emailTF = new TextField();
		passwordPf = new PasswordField(); 
		confirmpPf = new PasswordField(); 

		registerButton = new Button("Register");

		gridPane = new GridPane();

		borderPane = new BorderPane();

		scene = new Scene(borderPane, 560, 600) ;

	}

	public void layout() {
		gridPane.add(username, 0, 0);
		gridPane.add(email, 0, 1);
		gridPane.add(password, 0, 2);	
		gridPane.add(confirmp, 0, 3);
		gridPane.add(usernameTF, 1, 0);
		gridPane.add(emailTF, 1, 1);
		gridPane.add(passwordPf, 1, 2);
		gridPane.add(confirmpPf, 1, 3);

		gridPane.setHgap(50);
		gridPane.setVgap(45);
		gridPane.setPadding(new Insets(50));
		gridPane.setAlignment(Pos.CENTER);

		borderPane.setTop(title);
		borderPane.setCenter(gridPane);
		borderPane.setBottom(registerButton);

		borderPane.setPadding(new Insets(20, 0, 70, 0));

		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		BorderPane.setAlignment(gridPane, Pos.CENTER);	
		BorderPane.setAlignment(registerButton, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(title, new Insets(20, 0, 0, 0));
		BorderPane.setMargin(registerButton, new Insets(0, 0, 50, 0));
	}

	public void style() {
		title.setFont(Font.font(null, FontWeight.SEMI_BOLD, 30));

		title.setFill(Color.WHITE);
		username.setFill(Color.WHITE);
		email.setFill(Color.WHITE);
		password.setFill(Color.WHITE);
		confirmp.setFill(Color.WHITE);
		
		username.setFont(Font.font(null, FontWeight.NORMAL, 14));
		email.setFont(Font.font(null, FontWeight.NORMAL, 14));
		password.setFont(Font.font(null, FontWeight.NORMAL, 14));
		confirmp.setFont(Font.font(null, FontWeight.NORMAL, 14));
		
		usernameTF.setPrefSize(200, 30);
		emailTF.setPrefSize(200, 30);
		passwordPf.setPrefSize(200, 30);
		confirmpPf.setPrefSize(200, 30);

		registerButton.setPrefSize(100, 35);
		registerButton.setFont(Font.font(null, FontWeight.NORMAL, 14));

		borderPane.setStyle("-fx-background-color: gray;");
	}

	public void setEventHandler() {
		registerButton.setOnAction(this);
	}


	public void createAlert(AlertType alertType, String message) {
		Alert alert = new Alert(alertType);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public boolean cekNoDuplicateUser() {
		String cekUsername = usernameTF.getText();
		String cekEmail = emailTF.getText();
		String cekPass = passwordPf.getText();

		String query = "SELECT * FROM `user`";
		ResultSet rs = dbConnection.executeQuery(query);

		try {
			while(rs.next()) {
				String username = rs.getString("Username");
				String email = rs.getString("Email");
				String pass = rs.getString("Password");

				if (cekUsername.equals(username)) {
					return true;
				}
				if (cekEmail.equals(email)) {
					return true;
				}
				if (cekPass.equals(pass)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void handle(ActionEvent event) {
		String name, mail, pass, confirmPass = "";

		if (event.getSource() == registerButton) {
			name = usernameTF.getText();
			mail = emailTF.getText();
			pass = passwordPf.getText();
			confirmPass = confirmpPf.getText();

			//Alphanumeric
			int countAlpha=0;
			int countDigit=0;

			for (int i = 0; i < pass.length(); i++) {
				if (Character.isAlphabetic(pass.charAt(i))==true) {
					countAlpha++; // nilai countalpha menjadi satu 
				}
				if (Character.isDigit(pass.charAt(i))==true) {
					countDigit++; // nilai countDigit = 1
				}
			}

			if (name.isEmpty() && mail.isEmpty() && pass.isEmpty() && confirmPass.isEmpty()) {
				createAlert(AlertType.ERROR, "Fields cannot be empty!");
			} else if (name.isEmpty()) {
				createAlert(AlertType.ERROR, "Username cannot be empty!");
			} else if (name.length() < 5 || name.length() > 25) {
				createAlert(AlertType.ERROR, "Username must be between 5 - 25 characters");
			} else if (mail.isEmpty()) {
				createAlert(AlertType.ERROR, "Email cannot be empty!");
			} else if (!mail.contains("@") || !mail.endsWith(".com") || mail.startsWith("@")) {
				createAlert(AlertType.ERROR, "Email must be valid");
			} else if (pass.length() <= 5 || countAlpha < 1 || countDigit < 1) {
				createAlert(AlertType.ERROR, "Password must greater than 5 characters and must be alphanumeric!");
			} else if (pass.isEmpty()) {
				createAlert(AlertType.ERROR, "Password cannot be empty!");
			} else if (confirmPass.isEmpty()) {
				createAlert(AlertType.ERROR, "Confirm Password cannot be empty!");
			} else if (!confirmPass.equals(pass)) {
				createAlert(AlertType.ERROR, "Confirm Password must be same to the Password!");
			}else if (cekNoDuplicateUser()) {
				createAlert(AlertType.ERROR, "The username/email/password already exist!");
			}else {
				createAlert(AlertType.INFORMATION, "Account Created!");
				registeredUser();
				Main.authS.displayLoginPage();
				kosongField();
			}
		}
	}

	public void kosongField() {
		usernameTF.setText("");
		emailTF.setText("");
		passwordPf.setText("");
		confirmpPf.setText("");
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
	
	// query dptin user id paling tinggi trs + 1
	public Integer autoIncrement() {
		String query = "SELECT * FROM `user` ORDER BY user.UserID DESC LIMIT 1";
		
		ResultSet rs = dbConnection.executeQuery(query);
		Integer id = 0;
		try {
			while(rs.next()) {
				id = rs.getInt("UserID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		id++;
		return id;
	}

	public void registeredUser() {
		getUser();

		Integer userId = autoIncrement();
		String username = usernameTF.getText();
		String email = emailTF.getText();
		String pass = passwordPf.getText();
		String role = "customer"; //karena yang bisa register hanya untuk customer dan admin sudah ada pada database jadi ga perlu adanya register admin

		String query = String.format("INSERT INTO `user` (`UserID`,`Username`, `Email`, `Password`, `Role`) VALUES ('%d', '%s', '%s', '%s', '%s')", userId, username, email, pass, role);

		dbConnection.executeUpdate(query);
	}
	


	public Scene getRegist() {
		return scene;
	}


}


