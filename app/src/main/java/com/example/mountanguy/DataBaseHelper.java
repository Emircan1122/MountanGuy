package com.example.mountanguy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "USER_RECORD";
    private static final String TABLE_NAME = "USER_DATA";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "EMAIL";
    private static final String COL_4 = "PASSWORD";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Erstellt die Datenbank
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);

    }
    public void resetDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        createTable(db);
    }
    private void createTable(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " TEXT, "
                + COL_3 + " TEXT, "
                + COL_4 + " TEXT)";
        db.execSQL(createTableQuery);
    }

    //Checkt ob Datenbank existiert
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean registerUser(String username , String email , String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,username);
        values.put(COL_3,email);
        values.put(COL_4, password);
        long result = db.insert(TABLE_NAME , null, values);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean checkUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String [] columns = {COL_1};
        String selection = COL_2 + "=?" + " and " + COL_4 + "=?";
        String [] selectionargs = { username , password};
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionargs,null,null,null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        if (count > 0)
            return true;
        else
            return false;
    }
}


