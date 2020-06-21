package com.shops;

import java.sql.Date;
//Setup Store Products
public class StoreProducts {
	
	//Store Part
	int sid;
	String sname;
	Date founded;
	
	//Product Part
	int id;
	String prodName;
	float price;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public Date getFounded() {
		return founded;
	}
	public void setFounded(Date founded) {
		this.founded = founded;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
}
