package com.example.sekoia.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.sekoia.app.database.SekoiaDAO.*;

/**
 * Created by Maxi on 02-06-14.
 */
public class SekoiaDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Sekoia.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_RELATIVE_TABLE =
            "CREATE TABLE " + DATABASE_RELATIVE_TABLE + " (" + RELATIVE_KEY_ID +
            " integer primary key autoincrement, " +
            RELATIVE_KEY_FIRSTNAME + " TEXT not null, " +
            RELATIVE_KEY_LASTNAME + " TEXT not null);";

    private static final String DATABASE_CREATE_PICTURE_TABLE =
            "CREATE TABLE " + DATABASE_PICTURE_TABLE + " (" + PICTURE_KEY_ID +
                    " INTEGER primary key autoincrement, " +
                    PICTURE_KEY_RELATIVE + " INTEGER not null, " +
                    PICTURE_KEY_FILENAME + " TEXT not null);";


    public SekoiaDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_PICTURE_TABLE);
        db.execSQL(DATABASE_CREATE_RELATIVE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {

    }
}
