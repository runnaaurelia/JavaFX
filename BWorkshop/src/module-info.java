module BWorkshop {
	opens app;
	opens appStartPages;
	opens mainMenuPages;
	opens menuAdminPages;
	opens menuCustomerPages;
	opens modelData;
	opens theHelper;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
	requires java.sql;
	//requires mysql.connector.java;
}