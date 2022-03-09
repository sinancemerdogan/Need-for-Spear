package model.database;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.bson.BsonType;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Projections.*;


/*
 * A specific implementation for DatabaseAdapter for using MongoDB
 */
public class MongoDB implements DatabaseAdapter {

	private final static String uri = "mongodb+srv://Admin:admin@cluster0.zbne5.mongodb.net/MongoDB?retryWrites=true&w=majority";
	private static MongoDatabase mongoDBInstance = null;
	
	public MongoDB() {
	}
	
	public void connect() {
		try {
			MongoClient mongoClient = MongoClients.create(uri);
			MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
			mongoDBInstance = mongoDatabase;
		} catch (Exception e) {
			System.out.println("Error while connecting to the MongoDB.");
		}
	}
	
	public void saveGame(GameData gameData) {
		byte[] gdByteArray = ObjectConverter.getByteArrayObject(gameData);
		MongoCollection<Document> collection = mongoDBInstance.getCollection("gameData");
		Document gameDoc = new Document("name", gameData.getName());
		gameDoc.append("byteCode", gdByteArray);
		collection.insertOne(gameDoc);
	}
	
	public GameData loadGame(String name) {
		MongoCollection<Document> collection = mongoDBInstance.getCollection("gameData");
		Document doc = collection.find(eq("name", name)).first();
		Binary loadedGameDatasBinary = (Binary)doc.get("byteCode");
		GameData loadedGameDataObject = ObjectConverter.getJavaObject(loadedGameDatasBinary);	
		return loadedGameDataObject;
	}
	
	public ArrayList<String> getGameNames() {
		MongoCollection<Document> collection = mongoDBInstance.getCollection("gameData");
		Bson filter = Filters.empty();
		Bson projection = Projections.fields(Projections.include("name"), Projections.excludeId());
		ArrayList<Document> docs = collection.find(filter).projection(projection).into(new ArrayList<Document>());
		ArrayList<String> gameNames = new ArrayList<String>();
		for (Document doc : docs) {
			gameNames.add((String) doc.get("name"));
		}
		return gameNames;
	}
}
