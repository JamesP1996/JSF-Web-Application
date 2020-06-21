package com.shops;

import javax.faces.bean.ManagedBean;
//Setup StoreHead
@ManagedBean
public class StoreHead {

	
	int _id;
	String location;
	
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
}
