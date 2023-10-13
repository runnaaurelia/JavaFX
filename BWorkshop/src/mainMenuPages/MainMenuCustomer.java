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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainMenuCustomer implements EventHandler<ActionEvent>{
	Scene sc;
	GridPane gp;
	BorderPane bp;

	MenuBar menus;
	Menu menu;
	MenuItem sparepartMarket, transactionHistory, logoutItem;
	Text welcomeCustomer;

	Image backGround;
	BackgroundImage backGroundImage;

	public MainMenuCustomer(){
		init();
		vbg();
		layout();
		style();
		setEventHandlerMenuBar();
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
		gp = new GridPane();
		bp = new BorderPane();

		welcomeCustomer = new Text("Welcome Our Customer");

		menus = new MenuBar();

		menu = new Menu("Menu");

		sparepartMarket = new MenuItem("Buy Sparepart");
		transactionHistory = new MenuItem("View Your Transaction");
		logoutItem = new MenuItem("Logout");

		menu.getItems().add(sparepartMarket);
		menu.getItems().add(transactionHistory);
		menu.getItems().add(logoutItem);

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
		bp.setCenter(welcomeCustomer);	
	}

	public void style() {
		welcomeCustomer.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
		welcomeCustomer.setFill(Color.ANTIQUEWHITE);
	}

	public Scene getMainMenuCustomer() {
		return sc;

	}

}
