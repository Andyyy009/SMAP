package com.example.andyyy.touristapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String Database_name = "Monument_DB";
    public static final String Table_name = "Monument_table";
    public static final String Col_1 = "ID";
    public static final String Col_2 = "NAZEV";
    public static final String Col_3 = "OBEC";
    public static final String Col_4 = "INFO";
    public static final String Col_5 = "NAVSTIVENO";
    public static final String Col_6 = "DATUM_NAVSTEVY";

    public DatabaseHelper(Context context) {
        super(context, Database_name, null, 1);
       // SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_String = "CREATE TABLE " + Table_name + "(" + Col_1 +
                "INTEGER PRIMARY KEY AUTOINCREMENT," + Col_2 + " NAZEV TEXT" + Col_3 + "OBEC TEXT)";
       // String SQL_String = "CREATE TABLE " + Table_name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + " NAZEV TEXT, OBEC TEXT)";
        db.execSQL(SQL_String);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + Table_name);
        onCreate(db);
    }

    public boolean addData(String nazev, String obec){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues1 = new ContentValues();
        contentValues.put(Col_2, nazev);
        contentValues1.put(Col_3, obec);

        db.insert(Table_name, null, contentValues1);
        long result = db.insert(Table_name, null, contentValues);
        if (result == -1){
            return false;}
        else{
            return true;}

    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + Table_name, null);
        return data;
    }
}
