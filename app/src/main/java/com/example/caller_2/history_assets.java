package com.example.caller_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class history_assets extends SQLiteAssetHelper {

    public static String database_name="caller.db";
    public static String table_name="history";
    public static int version=2;
    public static String CUL_1="id";
    public static String CUL_2="name";
    public static String CUL_3="phone";
    public static String CUL_4="time";
    public history_assets(Context context) {
        super(context, database_name, null, version);
    }
    

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }
}
