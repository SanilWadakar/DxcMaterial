package com.dxc.inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionHelper {
	public static Connection getConnection() {
		Connection connection=null;
		ResourceBundle rb=ResourceBundle.getBundle("db");
		try {
			Class.forName(rb.getString("driver"));
			connection=DriverManager.getConnection(rb.getString("url"),
					rb.getString("user"),rb.getString("pass"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}
