package com.example.caller_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class person_assets extends SQLiteAssetHelper {

    public static String database_name="caller.db";
    public static String table_name="person";
    public static int version=8;
    public static String CUL_1="id";
    public static String CUL_2="name";
    public static String CUL_3="pirth_day";
    public static String CUL_4="image";
    public static String CUL_5="phone";
    public person_assets(Context context) {
        super(context, database_name, null, 1);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /* db.execSQL("Drop Table "+table_name);

        db.execSQL("CREATE TABLE "+table_name+" (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " name TEXT NOT NULL," +
                " pirth_day TEXT NOT NULL," +
                " image TEXT," +
                "phone TEXT" +
                ");");*/

        onCreate(db);
    }
}
