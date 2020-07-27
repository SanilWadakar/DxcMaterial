package com.dxc.inventory;

import java.sql.SQLException;
import java.util.Scanner;

public class AddStockMain {
	public static void main(String[] args) {
		Stock stock=new Stock();
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter item name:");
		stock.setItemName(sc.nextLine());
		System.out.print("Price:");
		stock.setPrice(Integer.parseInt(sc.nextLine()));
		System.out.print("Quantity:");
		stock.setQuantityAvail(Integer.parseInt(sc.nextLine()));
		StockDao dao=new StockDao();
		try {
			System.out.println(dao.addStock(stock));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
