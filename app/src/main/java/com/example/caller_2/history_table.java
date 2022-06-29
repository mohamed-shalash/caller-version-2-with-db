package com.example.caller_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class history_table {
    private static history_table history;
    SQLiteDatabase database;
    SQLiteOpenHelper helper;

    private history_table(Context context){
        helper=new history_assets(context);
    }
    public static history_table get_instance(Context context){
        if (history ==null)history =new history_table(context);
        return history;
    }
    public void open(){database =helper.getWritableDatabase();}
    public void close(){
        if (database!=null)database.close();
    }

    boolean insert(history his){
        ContentValues cv = new ContentValues();
        //cv.put(history_assets.CUL_1,his.getId());
        cv.put(history_assets.CUL_2,his.getName());
        cv.put(history_assets.CUL_3,his.getPhone());
        cv.put(history_assets.CUL_4,his.getTime());
        long x=database.insert(history_assets.table_name,null,cv);
        return x>-1;
    }

    boolean update(history his){
        ContentValues cv = new ContentValues();
        cv.put(history_assets.CUL_1,his.getId());
        cv.put(history_assets.CUL_2,his.getName());
        cv.put(history_assets.CUL_3,his.getPhone());
        cv.put(history_assets.CUL_4,his.getTime());
        String args[]={String.valueOf(his.getId())};
        int x=database.update(history_assets.table_name,cv,"id=?",args);
        return x>0;
    }

    boolean delete(int id){
       // String args[]={String.valueOf(id)};
        int x=database.delete(history_assets.table_name,history_assets.CUL_1+" = "+id,null);
        return x>0;
    }

    ArrayList<history> show_all(){
        ArrayList<history> his=new ArrayList<>();//SELECT * FROM Table ORDER BY date(dateColumn) DESC Limit 1
        //Cursor c=database.rawQuery("Select * from "+ history_assets.table_name,null);
        Cursor c=database.rawQuery("Select * from "+ history_assets.table_name+" ORDER BY "+history_assets.CUL_4+" DESC ",null);
        if (c.moveToFirst()){
            do {
                int id =c.getInt(0);
                String name =c.getString(1);
                String phone =c.getString(2);
                String time =c.getString(3);
                his.add(new history(id,name,phone,time));
            }while (c.moveToNext());
        }
        c.close();
        return his;
    }


    ArrayList<history> select_by_phone(String phone2){
        ArrayList<history> his=new ArrayList<>();
        String args[]={"%"+phone2+"%"};
        Cursor c=database.rawQuery("Select * from "+ history_assets.table_name+" where "+ history_assets.CUL_3+" like ?",args);
        if (c.moveToFirst()){
            do {
                int id =c.getInt(0);
                String name =c.getString(1);
                String phone =c.getString(2);
                String time =c.getString(3);
                his.add(new history(id,name,phone,time));
            }while (c.moveToNext());
        }
        c.close();
        return his;
    }

}
