package me.hdcookie.cookieCore.Manager;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import me.hdcookie.cookieCore.CookieCore;

public class DatabaseManager {
    private MongoClient client;
    private MongoDatabase database;

    public DatabaseManager(CookieCore cookieCore){
        // Code to initialize the database
        client = new MongoClient();
        database = client.getDatabase("cookiecore");
    }




    public MongoDatabase getDatabase(){
        // Code to get the database
        return database;
    }


}
