package com.example.caller_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class person_table {
    private static person_table person;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    private person_table(Context context){
        openHelper = new person_assets(context);
    }
     public static person_table getinstance(Context context){
        if (person==null)person= new person_table(context);
        return person;
     }
     public void open(){
        database=openHelper.getWritableDatabase();
     }
     public void close(){
        if (database !=null){database.close();}
     }

     boolean insert(person per){
         ContentValues cv = new ContentValues();
         //cv.put(person_assets.CUL_1,per.getId());
         cv.put(person_assets.CUL_2,per.getName());
         cv.put(person_assets.CUL_3,per.getPirth_day());
         cv.put(person_assets.CUL_4,per.getImage());
         cv.put(person_assets.CUL_5,per.getPhone());
        long x=database.insert(person_assets.table_name,null,cv);
        return x>-1;
     }

     boolean update(person per){
         ContentValues cv = new ContentValues();
         cv.put(person_assets.CUL_1,per.getId());
         cv.put(person_assets.CUL_2,per.getName());
         cv.put(person_assets.CUL_3,per.getPirth_day());
         cv.put(person_assets.CUL_4,per.getImage());
         cv.put(person_assets.CUL_5,per.getPhone());
         String args[]={String.valueOf(per.getId())};
         int x=database.update(person_assets.table_name,cv,"id=?",args);
         return x>0;
     }

     boolean delete(int id){
         String args[]={String.valueOf(id)};
         int x=database.delete(person_assets.table_name,"id=?",args);
         return x>0;
     }

     ArrayList<person> show_all(){
        ArrayList<person> people=new ArrayList<>();
         Cursor c=database.rawQuery("Select * from "+ person_assets.table_name,null);
         if (c.moveToFirst()){
             do {
                 int id =c.getInt(0);
                 String name =c.getString(1);
                 String pirthday =c.getString(2);
                 String image =c.getString(3);
                 String phone =c.getString(4);
                 people.add(new person(id,name,pirthday,image,phone));
             }while (c.moveToNext());
         }
         c.close();
        return people;
     }

    ArrayList<person> select_by_name(String name2){
        ArrayList<person> people=new ArrayList<>();
        String args[]={"%"+name2+"%"};
        Cursor c=database.rawQuery("Select * from "+ person_assets.table_name+" where "+ person_assets.CUL_2+" like ?",args);
        if (c.moveToFirst()){
            do {
                int id =c.getInt(0);
                String name =c.getString(1);
                String pirthday =c.getString(2);
                String image =c.getString(3);
                String phone =c.getString(4);
                people.add(new person(id,name,pirthday,image,phone));
            }while (c.moveToNext());
        }
        c.close();
        return people;
    }

    ArrayList<person> select_by_phone(String phone2){
        ArrayList<person> people=new ArrayList<>();
        String args[]={"%"+phone2+"%"};
        Cursor c=database.rawQuery("Select * from "+ person_assets.table_name+" where "+ person_assets.CUL_5+" like ?",args);
        if (c.moveToFirst()){
            do {
                int id =c.getInt(0);
                String name =c.getString(1);
                String pirthday =c.getString(2);
                String image =c.getString(3);
                String phone =c.getString(4);
                people.add(new person(id,name,pirthday,image,phone));
            }while (c.moveToNext());
        }
        c.close();
        return people;
    }

    person select_by_id(int id){
        person people = null;
        String args[]={"%"+id+"%"};
        Cursor c=database.rawQuery("Select * from "+ person_assets.table_name+" where "+ person_assets.CUL_1+" like ?",args);
        if (c.moveToFirst()){
            do {

                String name =c.getString(1);
                String pirthday =c.getString(2);
                String image =c.getString(3);
                String phone =c.getString(4);
                people=new person(id,name,pirthday,image,phone);
            }while (c.moveToNext());
        }
        c.close();
        return people;
    }


}
