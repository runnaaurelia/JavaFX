package theHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "bworkshop_db"; //dumb_bw //bworkshop_db
	private final String HOST = "localhost:3306";
	private final String URL = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	private Connection connection;
	private Statement statement;
	private static DBConnection dbConnection;

	private DBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//singleton
	public static DBConnection getConnection() {
		if (dbConnection == null) return new DBConnection();
		else return dbConnection;
	}

	//ini tuh untuk select
	public ResultSet executeQuery(String query) {
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	//ini tuh untuk insert update delete
	public void executeUpdate(String query) {
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//nah ini biar cegah sql injection pki ini buat select insert update delete 
	public PreparedStatement preparedStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ps;
	}

	//pengen penjelasan lebih buat preparedStatement tpi udah ga ada pertemuan lagi seandainya ada kelas tambahan hiks


}
