package com.shops;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sun.jmx.snmp.daemon.CommunicationException;

//All the Methods are Linked to the DAO Method Counter-parts.
@ManagedBean
@SessionScoped
public class ProductController {
	
	DAO dao;
	ArrayList<Product> products;
	//Load DAO
	public ProductController() {
		super();
		try {
			dao = new DAO();
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Load Products from DAO , 
	//If issues post them to Faces Messages to be displayed to user
	public void loadProducts() {
		System.out.println("In loadProducts()");
		try {
			products = dao.loadProducts();
		}catch (CommunicationException e) {
			System.out.println("Cannot Connect :: Database is Offline!");
			FacesMessage message = new FacesMessage("Error : Cannot connect to MYSQL Database");
			FacesContext.getCurrentInstance().addMessage(null,message);
			
		}catch (SQLException e) {
			System.out.println("Cannot Load the SQL Data for Products!");
			FacesMessage message = new FacesMessage("Error : Cannot connect to MYSQL Database");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	//Add a Product
	public String addProduct(Product p) {
		System.out.println(p.getSid() + " " + p.getProdName() + " " + p.getPrice());
		
			try {
				dao.addProduct(p);
				return "list_products";
			} catch (CommunicationException e) {
				FacesMessage message = new FacesMessage("Error: Can't Communicate with MYSQL Server");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null; 
	}
	//Delete Product 
	public String deleteProduct(int pid) throws SQLException {
			System.out.println("Deleting Product " + pid);
		
				try {
					dao.deleteProduct(pid);
					return "list_products"; }
				catch (CommunicationException e) {
					FacesMessage  message = new FacesMessage("Error: Cannot Connect to mySQL Server");
					FacesContext.getCurrentInstance().addMessage(null, message);
				}	
				 catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "";	
	}
	//Retrieve Product List
	public ArrayList<Product> getProducts() {
		return products;
	}
}
