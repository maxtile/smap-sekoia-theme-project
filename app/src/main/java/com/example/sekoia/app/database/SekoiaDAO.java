package com.example.sekoia.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.appcompat.R;

import com.example.sekoia.app.models.SekoiaApp;

import java.sql.SQLException;
import java.util.ArrayList;

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

    //Table names
    public static final String DATABASE_RELATIVE_TABLE = "relative";
    public static final String DATABASE_PICTURE_TABLE = "picture";

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

    public void insertPicture(String filename, int currentRelativeId){
        ContentValues cv = new ContentValues();
        cv.put(PICTURE_KEY_FILENAME, filename);
        cv.put(PICTURE_KEY_RELATIVE, currentRelativeId);
        db.insert(DATABASE_PICTURE_TABLE, null, cv);
    }

    public ArrayList<String> getFilenames (int currentRelativeId){
        ArrayList<String> listToReturn = new ArrayList<String>();
        String columns [] = new String[] { PICTURE_KEY_FILENAME };
        String selection = PICTURE_KEY_RELATIVE + " = " + currentRelativeId;
        Cursor c = db.query(DATABASE_PICTURE_TABLE, columns, selection, null, null, null, null);

        if (c.moveToFirst()){
            do{
                String data = c.getString(c.getColumnIndex(PICTURE_KEY_FILENAME));
                listToReturn.add(data);
            }while(c.moveToNext());
        }
        c.close();

        return listToReturn;
    }
}
