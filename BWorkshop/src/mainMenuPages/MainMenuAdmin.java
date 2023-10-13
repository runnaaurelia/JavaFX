package mainMenuPages;

import app.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainMenuAdmin implements EventHandler<ActionEvent>{
	Scene sc;
	BorderPane bp;

	MenuBar menus;
	Menu menu;
	MenuItem manageSparepart, manageUSer, viewtransaction, logout;
	Text welcomeAdmin;

	Image backGround;
	BackgroundImage backGroundImage;

	public MainMenuAdmin() {
		init();
		vbg();
		layout();
		style();
		setEventHandler();
	}

	public void setEventHandler() {
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

	public void init() {
		bp = new BorderPane();

		welcomeAdmin = new Text("Welcome The Administrator");

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

		sc = new Scene(bp, 1250, 835);
	}

	public void vbg() {
		backGround = new Image("https://goldlabeldoor.com/wp-content/uploads/2022/03/iStock-1335742085.jpg");
		backGroundImage = new BackgroundImage(backGround, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		bp.setBackground(new Background(backGroundImage));

	}

	public void layout() {
		menus.getMenus().add(menu);
		menus.setMinSize(780, 10);

		bp.setTop(menus);
		bp.setCenter(welcomeAdmin);
	}

	public void style() {
		welcomeAdmin.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
		welcomeAdmin.setFill(Color.ANTIQUEWHITE);
	}

	public Scene getMainMenuAdmin() {
		return sc;
	}


}
