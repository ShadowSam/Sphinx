package helpers;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;

public class MongoControlCenter {

	private MongoClient mongoClient;
	private DB db;

	public MongoControlCenter(String address, int port)
			throws UnknownHostException {

		mongoClient = new MongoClient(address, port);

	}

	/**
	 * 
	 * Sets the database of the Mongo DB controller.
	 * 
	 * @param dbName  The name of the Database to access
	 */
	public void setDatabase(String dbName) {
		db = mongoClient.getDB(dbName);
	}

	/**
	 * 
	 * Closes the connection to the database.
	 */
	public void closeConnection() {
		mongoClient.close();
	}

	/**
	 * 
	 * Gets all the entities the supplied user is a part of. 
	 * 
	 * 
	 * @param user	The user supplied.
	 * @return	An array of entity documents the user belongs to.
	 */
	public Object[] getEntitiesByUser(String user) {

		DBObject query = new QueryBuilder()
				.or(new BasicDBObject("assignee", user))
				.or(new BasicDBObject("watchers", user)).get();
		DBCollection coll = db.getCollection("entities");

		DBCursor cursor = coll.find(query);

		ArrayList<DBObject> list = new ArrayList<DBObject>();

		try {
			while (cursor.hasNext()) {
				list.add(cursor.next());
			}
		} finally {
			cursor.close();
		}
		return list.toArray();

	}

	/**
	 * 
	 * Gets all of the documents in a specific collection
	 * 
	 * @param collection	The collection being queried.
	 * @return	An array of all documents in that collection.
	 */
	public Object[] getAllDocuments(String collection) {
		DBCollection coll = db.getCollection(collection);

		DBCursor cursor = coll.find();
		ArrayList<DBObject> list = new ArrayList<DBObject>();

		try {
			while (cursor.hasNext()) {
				list.add(cursor.next());
			}
		} finally {
			cursor.close();
		}
		return list.toArray();
	}

}
