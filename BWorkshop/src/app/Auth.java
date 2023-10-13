package app;

import appStartPages.LoginPage;
import appStartPages.RegistPage;
import javafx.stage.Stage;
import mainMenuPages.MainMenuAdmin;
import mainMenuPages.MainMenuCustomer;
import menuAdminPages.ManageSparepartMenu;
import menuAdminPages.ManageUserMenu;
import menuAdminPages.ViewAllTransactionMenu;
import menuCustomerPages.BuySparepart;
import menuCustomerPages.ViewYourTransaction;

public class Auth extends Stage{
	//appStartPages
	LoginPage log;
	RegistPage regist;
	 
	//mainMenuPages
	MainMenuCustomer menuCustomer; 
	MainMenuAdmin menuAdmin;
	
	//menuCustomerPages
	BuySparepart buySparepart;
	ViewYourTransaction viewYourTransaction;
	
	//menuAdminPages
	ManageUserMenu manageUserMenu;
	ManageSparepartMenu manageSparepartMenu;
	ViewAllTransactionMenu viewAllTransaction;

	public Auth() {
		displayLoginPage(); //DONE 
		//ini yang tampil duluan didisplay jangan lupa uncomment lyyyy
		
		//displayRegistPage(); //DONE
		//displayMainMenuCustomer(); //DONE
		//displayBuySparepart(); //DONE
		//displayViewYourTransaction(); //DONE
		//displayMainMenuAdmin(); //DONE
		//displayManageSparepartMenu(); //DONE
		//displayManageUserMenu(); //DONE
		//displayViewAllTransaction(); //DONE
	}

	public void displayLoginPage() {
		if (log == null) {
			log = new LoginPage();
		}
		this.setScene(log.getLog());
	}

	public void displayRegistPage() {
		if (regist == null) {
			regist = new RegistPage();
		}
		this.setScene(regist.getRegist());
	}

	public void displayMainMenuCustomer() {
		if (menuCustomer == null) {
			menuCustomer = new MainMenuCustomer();
		}
		this.setScene(menuCustomer.getMainMenuCustomer());
	}

	public void displayBuySparepart() {
		if (buySparepart == null) {
			buySparepart = new BuySparepart();
		}
		this.setScene(buySparepart.getBuySparepart());
		buySparepart.refreshTableSparepart();
		buySparepart.refreshTableSparepartCart();
	}

	public void displayViewYourTransaction() {
		if (viewYourTransaction == null) {
			viewYourTransaction = new ViewYourTransaction();
		}
		this.setScene(viewYourTransaction.getViewYourTransaction());
		viewYourTransaction.refreshTable();
	}

	public void displayMainMenuAdmin() {
		if (menuAdmin == null) {
			menuAdmin = new MainMenuAdmin();
		}
		this.setScene(menuAdmin.getMainMenuAdmin());
	}


	public void displayManageUserMenu() {
		if (manageUserMenu == null) {
			manageUserMenu = new ManageUserMenu();
		}
		this.setScene(manageUserMenu.getManageUserMenu());
		manageUserMenu.refreshTable();
	}

	public void displayManageSparepartMenu() {
		if (manageSparepartMenu == null) {
			manageSparepartMenu = new ManageSparepartMenu();
		}
		this.setScene(manageSparepartMenu.getManageSparepartMenu());
		manageSparepartMenu.refreshTable();
	}

	public void displayViewAllTransaction() {
		if (viewAllTransaction == null) {
			viewAllTransaction = new ViewAllTransactionMenu();
		}
		this.setScene(viewAllTransaction.getViewAllTransactionMenu());
		viewAllTransaction.refreshTable();
	}
}
