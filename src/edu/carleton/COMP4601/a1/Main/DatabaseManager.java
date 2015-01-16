package edu.carleton.COMP4601.a1.Main;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import edu.carleton.COMP4601.a1.Model.Document;

public class DatabaseManager {

	public DB getDatabase() {
		return db;
	}

	public void setDatabase(DB db) {
		this.db = db;
	}

	public static void setInstance(DatabaseManager instance) {
		DatabaseManager.instance = instance;
	}

	private DB db;
	private MongoClient mongoClient;
	private static DatabaseManager instance;

	public DatabaseManager() {

		try {
			this.mongoClient = new MongoClient( "localhost" );
			setDatabase(this.mongoClient.getDB( "comp4601A1-100846396" ));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	public DBCollection getDocumentCollection() {

		DBCollection col;

		if (db.collectionExists("documents")) {
			col = db.getCollection("documents");
			col.setObjectClass(Document.class);
			return col;
		} else {
			DBObject options = BasicDBObjectBuilder.start().add("capped", false).add("size", 1000000001).get();
			col = db.createCollection("documents", options);
			col.setObjectClass(Document.class);
			return col;
		}
	}

	public boolean addNewDocument(Document document) {

		try {
			DBCollection col = this.getDocumentCollection();
			//col.insert(document);
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 	
	}

	public boolean updateDocument(Document newDocument, Document oldDocument) {

		try {
			DBCollection col = this.getDocumentCollection();
			//col.update(oldDocument, newDocument);

		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 
	}

	public Document removeDocument(Integer id) {	

		try {
			BasicDBObject query = new BasicDBObject("id", id);
			DBCollection col = this.getDocumentCollection();
			DBObject result = col.findAndRemove(query);
			return new Document(result.toMap());
			
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return null;
		}
	}

	public Document findDocument(Integer id) {

		try {

			BasicDBObject query = new BasicDBObject("id", id);
			DBCollection col = this.getDocumentCollection();
			DBObject result = col.findOne(query);

			if(result != null) {
				return new Document(result.toMap());
			}

			return null;
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return null;
		}

	}

	public int getAccountCollectionSize() {
		DBCollection col = this.getDocumentCollection();
		return (int) col.getCount();
	}

	public static DatabaseManager getInstance() {

		if (instance == null)
			instance = new DatabaseManager();
		return instance;

	}

	public void stopMongoClient() {

		if(this.mongoClient != null) {
			this.mongoClient.close();
		}

	}
}
