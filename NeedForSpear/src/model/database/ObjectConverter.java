package model.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bson.types.Binary;

/*
 * A class to provide us converter functions.
 */
public class ObjectConverter {
	
	// Convert GameData object to byte[]. To store in the database as bytes not Java objects.
    public static byte[] getByteArrayObject(GameData gd){
        byte[] byteArrayObject = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(gd);
            oos.close();
            bos.close();
            byteArrayObject = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return byteArrayObject;
        }
        return byteArrayObject;
    }
       
    // Convert Binary to GameData. When we get data from the database it will be binary therefore we convert those binaries to Java Objects 
    public static GameData getJavaObject(Binary convertObject){
        GameData objGd = null;
        byte[] byteArrayOfObject = convertObject.getData();
        ByteArrayInputStream bais;
        ObjectInputStream ins;
        try {
	        bais = new ByteArrayInputStream(byteArrayOfObject);
	        ins = new ObjectInputStream(bais);
	        objGd =(GameData)ins.readObject();
	        ins.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return objGd;
}
	
}
