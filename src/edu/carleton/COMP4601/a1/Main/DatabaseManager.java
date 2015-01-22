package edu.carleton.COMP4601.a1.Main;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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
	private final String DOCUMENTS = "documents";

	public DatabaseManager() {

		try {
			this.mongoClient = new MongoClient( "localhost" );
			setDatabase(this.mongoClient.getDB( "comp4601A1-100846396" ));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	public boolean addNewDocument(Document document) {

		try {
			DBCollection col = db.getCollection(DOCUMENTS);
			col.insert(buildDBObject(document));
			
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 	
	}

	public boolean updateDocument(Document newDocument, Document oldDocument) {

		try {
			DBCollection col = db.getCollection(DOCUMENTS);
			col.update(buildDBObject(oldDocument), buildDBObject(newDocument));

		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 
	}

	public Document removeDocument(Integer id) {	

		try {
			BasicDBObject query = new BasicDBObject("id", id);
			DBCollection col = db.getCollection(DOCUMENTS);
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
			DBCollection col = db.getCollection(DOCUMENTS);
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
	
	public ArrayList<Document> getDocuments() {

		try {
			DBCollection col = db.getCollection(DOCUMENTS);
			DBCursor result = col.find();
			
			if(result != null) {
				ArrayList<Document> documents = new ArrayList<Document>();
				while(result.hasNext()) {
					documents.add(new Document(result.next().toMap()));
				}
				return documents;
			}

			return null;
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return null;
		}

	}

	public int getDocumentCollectionSize() {
		DBCollection col = db.getCollection(DOCUMENTS);
		return (int) col.getCount();
	}
	
	public BasicDBObject buildDBObject(Document document) {
		
		BasicDBObject newObj = new BasicDBObject();
		newObj.put("id", document.getId());
		newObj.put("name", document.getName());
		newObj.put("score", document.getScore());
		newObj.put("text", document.getText());
		newObj.put("tags", document.getTags());
		newObj.put("links", document.getLinks());
		return newObj;
		
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
	
	public int getNextIndex() {
		return getDocumentCollectionSize() + 1;
	}
}
