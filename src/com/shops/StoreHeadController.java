package com.shops;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;

//StoreHead Controller Linked to MongoDAO
@ManagedBean
@SessionScoped
public class StoreHeadController {
	
	MongoDAO dao;
	ArrayList<StoreHead> storeHeads;
	
	public StoreHeadController() {
		super();
		try {
			dao = new MongoDAO();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	//Load StoreHead Offices
	public void loadStoreHeads() throws Exception{
		System.out.println("In loadStoreHeads()");
		try {
			storeHeads = dao.loadStoreHeads();
		
	} catch (MongoSocketOpenException e) {
		System.out.println("Could not Connect to Mongo Server");
		FacesMessage message = new FacesMessage("Error: Cannot Connect to Mongo Database");
		FacesContext.getCurrentInstance().addMessage(null,message);
	} catch(MongoTimeoutException e) {
		System.out.println("Could not Connect to Mongo Server, Timed Out");
		FacesMessage message = new FacesMessage("Error: Cannot Connect to Mongo Database");
		FacesContext.getCurrentInstance().addMessage(null,message);
	}
}
	//Delete Store Head Offices
	public String deleteStoreHead(int _id,String Location) {
		System.out.println("Removing Store: " + _id+" As StoreHead in " + Location);
	
			try {
				dao.deleteStoreHead(_id);
				return "list_offices";
				
			} catch (MongoWriteException e) {
				FacesMessage message = 
						new FacesMessage("Error: StoreHead of " + _id + " has not been deleted from Mongo DB");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
			catch (MongoSocketOpenException e) {
				FacesMessage  message = 
						new FacesMessage("Error: Cannot Connect to Mongo Server");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";	
			}
	
	//Add Store Head Offices
	public String addStoreHead(StoreHead sc) {
		try {
			dao.addStoreHead(sc.get_id(), sc.getLocation());
			return "list_offices";
		}catch(MongoWriteException e) {
			FacesMessage message = 
					new FacesMessage("Error: Store ID: "+sc.get_id()+" already has location");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (MongoSocketOpenException e) {
			System.out.println("Could not Connect to Mongo Server");
			FacesMessage message = new FacesMessage("Error: Cannot Connect to Mongo Database");
			FacesContext.getCurrentInstance().addMessage(null,message);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	//Return Store Heads
	public ArrayList<StoreHead> getStoreHeads() {
		return storeHeads;
	}
}