package com.example.sekoia.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by Maxi on 02-06-14.
 */
public class SekoiaDAO {
    // Variable to hold the database instance
    private SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private SekoiaDbHelper dbHelper;

    // Keys for relative table.
    public static final String RELATIVE_KEY_FIRSTNAME="firstname";
    public static final String RELATIVE_KEY_LASTNAME="lastname";
    public static final String RELATIVE_KEY_ID = "relative_id";

    //Keys for picture table.
    public static final String PICTURE_KEY_ID="picture_id";
    public static final String PICTURE_KEY_FILENAME="filename";
    public static final String PICTURE_KEY_RELATIVE="relative_id";


    public SekoiaDAO(Context context){
        this.context = context;
        this.dbHelper = new SekoiaDbHelper(context);
    }

    public SekoiaDAO open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        db.close();
    }
}
