package com.shops;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sun.jmx.snmp.daemon.CommunicationException;

// All Methods Are Linked to DAO Methods 
@ManagedBean
@SessionScoped
public class StoreController {
	
	DAO dao;
	ArrayList<Store> stores;
	
	public StoreController() {
		super();
		try {
			dao = new DAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Load Stores with FaceErrors
	public void loadStores() throws Exception {
		System.out.println("In loadStores()");
		try {
		stores = dao.loadStores();
		}
		catch(CommunicationException e){
			System.out.println("Cannot Connect :: Database is Offline!");
			FacesMessage message = new FacesMessage("Error : Cannot connect to MYSQL Database");
			FacesContext.getCurrentInstance().addMessage(null,message);
		}
		catch(SQLException e) {
			System.out.println("Cannot Grab Data for Stores Page");
			FacesMessage message = new FacesMessage("Error : Cannot connect to MYSQL Database");
			FacesContext.getCurrentInstance().addMessage(null,message);
		}
	}
	//Add Store
	public String addStore(Store s) {
		System.out.println(s.getName() + " " + s.getFounded());
		
			try {
				dao.addStore(s);
				return "list_stores";
			} catch (SQLIntegrityConstraintViolationException e) {
				FacesMessage message = 
						new FacesMessage("Error: Store "+s.getName()+" already exists");
						FacesContext.getCurrentInstance().addMessage(null, message);
			} catch (CommunicationException e) {
				FacesMessage message = new FacesMessage("Error: Can't Communicate with MYSQL Server");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null; 
	}
	//Delete Store
	public String deleteStore(int id,String Name) {
		System.out.println("Deleting Store " + id);
	
			try {
				dao.deleteStore(id);
				return "list_stores";
			} catch (SQLIntegrityConstraintViolationException e) {
				FacesMessage message = 
						new FacesMessage("Error: Store " + Name + " has not been deleted from MySQL DB, it contains products");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
			catch (CommunicationException e) {
				FacesMessage  message = 
						new FacesMessage("Error: Cannot Connect to mySQL Server");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";	
}
		
		//Check Exists - For Mongo Usage
	public boolean checkExists(int id) {
		try {
			boolean exist = dao.storeExists(id);
			if (exist == true) {
				
				return true;
			}
			else {
				FacesMessage message = new FacesMessage("Error: Store ID: "+id+" does not exist");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return false;
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return false;
		
	
		
	}
	

	public ArrayList<Store> getStores() {
		return stores;
	}
}
