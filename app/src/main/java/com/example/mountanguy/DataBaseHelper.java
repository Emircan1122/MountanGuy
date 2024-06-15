package com.example.mountanguy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static DataBaseHelper instance;
    protected static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "USER_RECORD";
    protected static final String TABLE_NAME = "USER_DATA";
    protected static final String COL_1 = "ID";
    protected static final String COL_2 = "USERNAME";
    protected static final String COL_3 = "EMAIL";
    protected static final String COL_4 = "PASSWORD";
    protected static final String COL_5 = "ALTER1";
    protected static final String COL_6 = "GROßE1";
    protected static final String COL_7 = "GEWICHT1";
    protected static final String COL_8 = "GESCHLECHT1";
    private SQLiteDatabase db;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = getWritableDatabase();
    }
    //synchronisiert die Datenbank über die Verschiedenen Activities
    public static synchronized DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context.getApplicationContext());
        }
        return instance;
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
                + COL_4 + " TEXT, "
                + COL_5 + " TEXT, "
                + COL_6 + " TEXT, "
                + COL_7 + " TEXT, "
                + COL_8 + " TEXT) ";
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

    public void insertIntoDatabase(String userID, String col, Object value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (value instanceof String) {
            contentValues.put(col, (String) value);
        } else if (value instanceof Integer) {
            contentValues.put(col, (Integer) value);
        } else {
            throw new IllegalArgumentException("Dieser Datentyp wird nicht unterstützt");
        }

        // Beispiel: Aktualisieren der Zeile mit der gegebenen userID
        int rowsAffected = db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[]{userID});

        if (rowsAffected > 0) {
            Log.d("DataBaseHelper", "Daten erfolgreich aktualisiert für UserID: " + userID);
        } else {
            Log.d("DataBaseHelper", "Fehler beim Aktualisieren der Daten für UserID: " + userID);
        }
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
    //zum öffnen der datenbank
    public SQLiteDatabase openDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("TAG", "Database opened");
        return db;
    }

    public void closeDatabase() {
        this.close();
        Log.d("TAG", "Database closed");
    }
}


