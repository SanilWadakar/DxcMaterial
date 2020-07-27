package com.dxc.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockDao {
	Connection connection=null;
	PreparedStatement pst=null;
	
	public String generateStockId() throws SQLException {
		connection=ConnectionHelper.getConnection();
		String cmd="select count(*) from stock";
		pst=connection.prepareStatement(cmd);
		ResultSet rs=pst.executeQuery();
		rs.next();
		int stockNo=rs.getInt(1)+1;
		String stockId="S"+stockNo;
		return stockId;
	}
	public String generateOrderId() throws SQLException {
		connection=ConnectionHelper.getConnection();
		String cmd="select count(*) from orders";
		pst=connection.prepareStatement(cmd);
		ResultSet rs=pst.executeQuery();
		rs.next();
		int orderNo=rs.getInt(1)+1;
		String orderId="O"+orderNo;
		return orderId;
	}
	public String addStock(Stock stock) throws SQLException {
		connection=ConnectionHelper.getConnection();
		String cmd="insert into stock values(?,?,?,?)";
		pst=connection.prepareStatement(cmd);
		pst.setString(1, generateStockId());
		pst.setString(2, stock.getItemName());
		pst.setInt(3, stock.getPrice());
		pst.setInt(4, stock.getQuantityAvail());
		pst.executeUpdate();
		return "Stock added";
	}
	public Stock searchStock(String stockId) throws SQLException {
		Stock stock=null;
		connection=ConnectionHelper.getConnection();
		String cmd="select * from stock where stockid=?";
		pst=connection.prepareStatement(cmd);
		pst.setString(1, stockId);
		ResultSet rs=pst.executeQuery();
		if(rs.next()) {
			stock=new Stock();
			stock.setStockId(stockId);
			stock.setItemName(rs.getString(2));
			stock.setPrice(rs.getInt(3));
			stock.setQuantityAvail(rs.getInt(4));
		}
		return stock;
	}
	public String orderPlace(String stockId,int qtyOrd) throws SQLException {
		String result="";
		connection=ConnectionHelper.getConnection();
		String cmd="select quantityavail,price from stock where stockid=?";
		pst=connection.prepareStatement(cmd);
		pst.setString(1, stockId);
		ResultSet rs=pst.executeQuery();
		if(rs.next()) {
			int qtyAvail=rs.getInt(1);
			int price=rs.getInt(2);
			pst.close();
			if(qtyAvail>qtyOrd) {
				cmd="update stock set quantityAvail=quantityAvail-? where stockid=?";
				pst=connection.prepareStatement(cmd);
				pst.setInt(1, qtyOrd);
				pst.setString(2, stockId);
				pst.executeUpdate();
				pst.close();
				cmd="insert into orders(orderid,stockid,qtyord,billamt) values(?,?,?,?)";
				pst=connection.prepareStatement(cmd);
				pst.setString(1, generateOrderId());
				pst.setString(2, stockId);
				pst.setInt(3, qtyOrd);
				pst.setInt(4, price*qtyOrd);
				pst.executeUpdate();
				pst.close();
				cmd="update amount set gamt=gamt+?";
				pst=connection.prepareStatement(cmd);
				pst.setInt(1, qtyOrd*price);
				pst.executeUpdate();
				result="Order place Successfully";
			}else {
				result="Insufficient stock";
			}
		}else {
			result="Wrong Item Selected";
		}
		return result;
	}
}