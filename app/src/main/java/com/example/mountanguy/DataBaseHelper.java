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
    private SQLiteDatabase db;
    protected static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "USER_RECORD";
    public static final String TABLE_NAME = "USER_DATA";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PASSWORD";
    public static final String COL_5 = "USERGESCHLECHT";
    public static final String COL_6 = "USERALTER";
    public static final String COL_7 = "USERGRÖßE";
    public static final String COL_8 = "USERGEWICHT";
    public static final String COL_9 = "USERNIVEAU";
    public static final String COL_10 = "USER_ZIEL_GEWICHT";
    public static final String COL_11 = "USER_ZIEL_PROTEIN";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    //synchronisiert die Datenbank über die Verschiedenen Activities
    public static synchronized DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context.getApplicationContext());
        }
        return instance;
    }


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
                + COL_6 + " Text, "
                + COL_7 + " TEXT, "
                + COL_8 + " TEXT, "
                + COL_9 + " TEXT, "
                + COL_10 + " TEXT,"
                + COL_11 + " TEXT)";
        db.execSQL(createTableQuery);
    }

    //Checkt ob Datenbank existiert
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }



    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, username);
        values.put(COL_3, email);
        values.put(COL_4, password);
        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

  /*  public boolean userDataUpdate(String userGeschlecht, String userAlter, String userGröße, String userGewicht, String userNiveau, String userZielGewicht, String userZielProtein) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_5, userGeschlecht);
        values.put(COL_6, userAlter);
        values.put(COL_7, userGröße);
        values.put(COL_8, userGewicht);
        values.put(COL_9, userNiveau);
        values.put(COL_10, userZielGewicht);
        values.put(COL_11, userZielProtein);

        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1)
            return false;
        else
            return true;
    }*/

    public boolean updateUserInfo(String username, String userGeschlecht, String userAlter, String userGröße, String userGewicht, String userNiveau, String userZielGewicht, String userZielProtein) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_5, userGeschlecht);
        values.put(COL_6, userAlter);
        values.put(COL_7, userGröße);
        values.put(COL_8, userGewicht);
        values.put(COL_9, userNiveau);
        values.put(COL_10, userZielGewicht);
        values.put(COL_11, userZielProtein);

        int rowsAffected = db.update(TABLE_NAME, values, COL_2 + " = ?", new String[]{username});

        Log.d("UpdateUserInfo", "Username: " + username);
        Log.d("UpdateUserInfo", "Rows affected: " + rowsAffected);

        return rowsAffected>0;

    }

    /*public boolean updateUserData(String username, String userGeschlecht, String userAlter, String userGröße, String userGewicht, String userNiveau, String userZielGewicht, String userZielProtein) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_5, userGeschlecht);
        values.put(COL_6, userAlter);
        values.put(COL_7, userGröße);
        values.put(COL_8, userGewicht);
        values.put(COL_9, userNiveau);
        values.put(COL_10, userZielGewicht);
        values.put(COL_11, userZielProtein);

        int rowsAffected = db.update(TABLE_NAME, values, COL_2 + " = ?", new String[]{username});
        return rowsAffected > 0;
    }*/


    /* public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_1};
        String selection = COL_2 + "=?" + " and " + COL_4 + "=?";
        String[] selectionargs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionargs, null, null, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        if (count > 0)
            return true;
        else
            return false;
    }*/
    public boolean checkusername(String USERNAME){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_DATA where USERNAME = ?", new String []{USERNAME});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public boolean checkuseremail(String USERNAME){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_DATA where EMail = ?", new String []{USERNAME});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkusernamepassword(String USERNAME, String PASSWORD){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_DATA where USERNAME = ? and PASSWORD = ?", new String[] {USERNAME,PASSWORD});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Cursor getUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + " = ?", new String[]{username});
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