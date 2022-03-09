package model.database;

import java.util.ArrayList;

/*
 * A singleton Database class which uses a specificDatabase which satisfies the DatabaseAdapter
 */
public class Database {
	
	private static Database dbInstance = null;
	private static DatabaseAdapter specificDatabase = null;

	private Database() {

	}

	public static Database getInstance(String databaseType) throws Exception {
		if (databaseType.equalsIgnoreCase("mongoDB")) {
			if (dbInstance == null) {
				dbInstance = new Database();
				specificDatabase = new MongoDB();
			}
			return dbInstance;
		}
		throw new Exception("Invalid database type.");
	}

	public void connect() {
		specificDatabase.connect();
	}
	
	public void saveGame(GameData gameData) {
		specificDatabase.saveGame(gameData);
	}
	
	public GameData loadGame(String name) {
		return specificDatabase.loadGame(name);
	}
	
	public ArrayList<String> getGameNames() {
		 return specificDatabase.getGameNames();
	}
	
	
}
