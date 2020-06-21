package com.shops;

import java.util.ArrayList;
import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDAO {
	//Setup Variables Needed , DAO is needed for CheckStoreExists();
	DAO dao;
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;
	String storeHeadOfficeDB = "storeHeadOfficeDB";
	String storeHeadOffice = "storeHeadOffice";
	/* ======================================================================================================
	 * Constructor
	 * ====================================================================================================== */
	public MongoDAO() throws Exception {
		mongoClient = new MongoClient();
		database = mongoClient.getDatabase(storeHeadOfficeDB);
		collection = database.getCollection(storeHeadOffice);
	}

	//Load All the Store Heads using Gson Google Converter
public ArrayList<StoreHead> loadStoreHeads() throws Exception {
		
	ArrayList<StoreHead> storeheadArray = new ArrayList<StoreHead>();
	
	Gson gson = new Gson();
	
	FindIterable<Document> sHead = collection.find();
	
	for(Document d : sHead) {
		//System.out.println(d);
		StoreHead sh = gson.fromJson((d.toJson()), StoreHead.class);
		//System.out.println(sh._id);
		storeheadArray.add(sh);
	}
	
	return  storeheadArray;
	}

//Add Store Head if The Store Exists , else do not
public void addStoreHead(int id,String locale) throws Exception{
	
	StoreController sc = new StoreController();
	if(sc.checkExists(id) == true) {
		System.out.println("Store "+id+" Exists");
		Document myDoc = new Document();
		myDoc.append("_id", id).append("location", locale);
		collection.insertOne(myDoc);
	}
	else {
		System.out.println("Store "+id+" Does not Exist");
	}	
}

//Delete One StoreHead of the ID Specified
public void deleteStoreHead(int _id) throws Exception{
	
	BasicDBObject document = new BasicDBObject();
	document.put("_id", _id);
	collection.deleteOne(document);
	
}
		
	
	
	
}




