package com.shops;
import javax.faces.bean.ManagedBean;

//Store Setup - Managed Bean

@ManagedBean
public class Store {
	int storeID;
	String name;
	String founded;
	
	public int getStoreID() {
		return storeID;
	}
	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFounded() {
		return founded;
	}
	public void setFounded(String founded) {
		this.founded = founded;
	}
	

	
	
	
	
}
