package com.dxc.inventory;

import java.sql.SQLException;
import java.util.Scanner;

public class OrderPlaceMain {
	  public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter Stock Id:");
		String stockId=sc.nextLine();
		System.out.print("Quantity:");
		int qty=Integer.parseInt(sc.nextLine());
		try {
			System.out.println(new StockDao().orderPlace(stockId, qty));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
