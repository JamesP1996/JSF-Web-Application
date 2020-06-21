package com.shops;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
// Store Product Controller Connected to DAO Methods
@ManagedBean
@SessionScoped
public class StoreProductController {
		DAO dao;
		ArrayList<StoreProducts> storeproduct;
		int storeID;
		
		public StoreProductController() {
			super();
			try {
				dao = new DAO();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Load Store Products
		public void loadStoreProduct() throws Exception {
			System.out.println("In Load Store Product \n");
			
			try {
				storeproduct = dao.loadStoreProducts(storeID);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public int getStoreID() {
			return storeID;
		}

		public void setStoreID(int storeID) {
			this.storeID = storeID;
			
		}
		// Store Product Page Loader
		public String productStorePageLoad(int storeID) {
			try {
				this.setStoreID(storeID);
				System.out.println("Seeing What Products are in store "+storeID);
				return "list_store_products";
			} catch (Exception e) {
				System.out.println("Store does not exist");
				e.printStackTrace();
			}
			return "";	
			
		}
		//Return Store Products
		public ArrayList<StoreProducts> getStoreproduct() {
			return storeproduct;
		}

	
		
		
}
