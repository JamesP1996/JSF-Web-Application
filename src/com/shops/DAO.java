package com.shops;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DAO {

	//Setup SQL Variables
	private DataSource mysqlDS;
	private Connection myConn;

	
	/* ======================================================================================================
	 * Constructor
	 * ====================================================================================================== */
	public DAO() throws Exception {
		Context context = new InitialContext();
		String jndiName = "java:comp/env/shops";
		mysqlDS = (DataSource) context.lookup(jndiName);
	}

	//Load Stores From MYSQL Datbase
public ArrayList<Store> loadStores() throws Exception {
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		myConn = mysqlDS.getConnection();
		//Query
		String sql = "select * from store";

		myStmt = myConn.createStatement();
		//Execute Query
		myRs = myStmt.executeQuery(sql);
		
		ArrayList<Store> stores = new ArrayList<Store>();

		// process result set
		while (myRs.next()) {
			Store s = new Store();
			s.setStoreID(myRs.getInt("ID"));
			s.setName(myRs.getString("NAME"));
			Date date = null;
			date = myRs.getDate("FOUNDED");
			s.setFounded(date.toString());
			stores.add(s);
		}
		myStmt.close();
		myConn.close();
		return stores;
	}


//Add Store to MYSQL Database
public void addStore(Store store) throws Exception{
	
		Connection myConn = mysqlDS.getConnection();
		//Insertion Update
		String sql = "insert into Store (Name,Founded) values (?,?)";
		PreparedStatement myStmt = myConn.prepareStatement(sql);
		myStmt.setString(1,store.getName());
		myStmt.setString(2,store.getFounded());
		//Execute the Update
		myStmt.executeUpdate();
		
		myStmt.close();
		myConn.close();
	} 

//Delete MYSQL Store From DataBase
public void deleteStore(int id) throws Exception{
	
	Connection myConn = null;
	PreparedStatement myStmt = null;
	
	myConn = mysqlDS.getConnection();
	// Deletion
	String sql = "delete from store where id = ?";
	myStmt = myConn.prepareStatement(sql);
	myStmt.setInt(1, id);
	//Execute Delete
	myStmt.execute();
	
	myStmt.close();
	myConn.close();
}
	


	


// Load Products
public ArrayList<Product> loadProducts() throws Exception {
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	myConn = mysqlDS.getConnection();
	//Select All Products from MYSQL
	String sql = "select * from product";

	myStmt = myConn.createStatement();
	//Execute Query
	myRs = myStmt.executeQuery(sql);
	
	ArrayList<Product> products = new ArrayList<Product>();

	// process result set
	while (myRs.next()) {
		Product p = new Product();
		p.setPid(myRs.getInt("PID"));
		p.setSid(myRs.getInt("SID"));
		p.setProdName(myRs.getString("PRODNAME"));
		p.setPrice(myRs.getFloat("PRICE"));
		products.add(p);
	}
	
	myStmt.close();
	myConn.close();
	
	return products;
}
//  Load Products Relevant to Each Store
public ArrayList<StoreProducts> loadStoreProducts(int id) throws Exception {
	
	ArrayList<StoreProducts> storeproduct = new ArrayList<StoreProducts>();
	
	Connection myConn = null;
	PreparedStatement myStmt = null;
	ResultSet myRS = null;
	
	
	myConn = mysqlDS.getConnection();
	//Prepared Query taking in Store ID
	myStmt = myConn.prepareStatement("\r\n" + 
			"SELECT store.id,store.name,store.founded,product.pid,product.prodName,product.price\r\n" + 
			"			FROM store\r\n" + 
			"			INNER JOIN Product\r\n" + 
			"			ON store.id = product.sid\r\n" + 
			"			Where store.id = ?");
	myStmt.setInt(1, id);
	//Execute Prepared Query
	myRS = myStmt.executeQuery();
	while (myRS.next()) {
		StoreProducts sp = new StoreProducts();
		sp.setSid(myRS.getInt("ID"));
		sp.setSname(myRS.getString("NAME"));
		sp.setFounded(myRS.getDate("FOUNDED"));
		sp.setId(myRS.getInt("PID"));
		sp.setProdName(myRS.getString("PRODNAME"));
		sp.setPrice(myRS.getFloat("PRICE"));
		storeproduct.add(sp);
	}
	
	myStmt.close();
	myConn.close();
	return storeproduct;
	
	}
	

// Delete Product
public void deleteProduct(int pid) throws Exception {
	Connection myConn = null;
	PreparedStatement myStmt = null;
	

	myConn = mysqlDS.getConnection();
	//Delete Product Where pid = passed in Pid
	String sql = "delete from product where pid = ?";
	myStmt = myConn.prepareStatement(sql);
	myStmt.setInt(1, pid);
	//Execute Delete
	myStmt.execute();	
	
	myStmt.close();
	myConn.close();
}

//Add a Product
public void addProduct(Product product) throws Exception{
	
	Connection myConn = mysqlDS.getConnection();
	//Insertion Update
	String sql = "insert into Product (sid,prodName,price) values (?,?,?)";
	PreparedStatement myStmt = myConn.prepareStatement(sql);
	myStmt.setInt(1,product.getSid());
	myStmt.setString(2,product.getProdName());
	myStmt.setFloat(3, product.getPrice());
	//Execute Update
	myStmt.executeUpdate();
	
	myStmt.close();
	myConn.close();
} 

//Check if Store Exists - Will be used for Mongo Section
public boolean storeExists(int id) throws Exception{
	
	Connection myConn = null;
	
	ResultSet myRs = null;
	
	myConn = mysqlDS.getConnection();
	//Get the Count of stores with ID specified
	String sql = "select count(id) as 'EXISTS' from store where id = ?";
	PreparedStatement myStmt = myConn.prepareStatement(sql);
	myStmt.setInt(1, id);
	myRs = myStmt.executeQuery();
	
	
	int result = 0;
	//Print Result
	while (myRs.next()) {
		result = myRs.getInt("EXISTS");
		System.out.println(result);
	}
	myConn.close();
	myStmt.close();
	// If the Result Count is > 0 , then the Store exists
	if(result > 0) {
		System.out.println("The Store of ID "+id+" Exists");
		return true;
	}
	else 
		//The Store Does Not Exist
	System.out.println("The Store  of ID "+id+" does not Exist");
	return false;
	
}



}


