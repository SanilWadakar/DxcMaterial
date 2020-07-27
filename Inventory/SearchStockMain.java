package com.dxc.inventory;

import java.sql.SQLException;
import java.util.Scanner;

public class SearchStockMain {
	public static void main(String[] args) {
		System.out.print("Enter Stock Id:");
		String stockId=new Scanner(System.in).nextLine();
		try {
			System.out.println(new StockDao().searchStock(stockId));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
