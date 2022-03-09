package model.database;

import java.util.ArrayList;

/*  
 * General functions we expect from a database
 */
public interface DatabaseAdapter {

	public void connect();
	public void saveGame(GameData gameData);
	public GameData loadGame(String name);
	public ArrayList<String> getGameNames();
}
