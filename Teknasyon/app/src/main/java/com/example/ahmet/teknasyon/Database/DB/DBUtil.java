package com.example.ahmet.teknasyon.Database.DB;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBUtil {
    // Standart //
    public static SQLiteDatabase db;
    // Sql sorguların yada içerde bir sorun olduğunda  ekranın her yerini Catchlemektense böyle
    // bir mekanizmayla kod kalabalığını önlemek için yazdım
    public static boolean BaseInsert(ContentValues values, String table){
        try {
            db.insert(table,null,values);
            return true;
        }catch (Exception ex){
            Log.e("Sql Hata BaseInsert ",ex.toString());
            return false;
        }
    }
    public static boolean BaseDelete(String sorgu){
        try{
            db.execSQL(sorgu);
            return true;
        }catch (Exception ex){
            Log.e("Sqllite Base Delete",ex.toString());
            return false;
        }
    }
    public static boolean BaseUpdate(ContentValues values,String  table,String Kisit){
        try{
            db.update(table,values,Kisit,null);
            return true;
        }catch (Exception ex){
            Log.e("Sqllite Base Update",ex.toString());
            return false;
        }
    }
    public static Cursor BaseGet(String sorgu){
        try{
            return  db.rawQuery(sorgu,null);
        }catch (Exception ex){
            Log.e("Sqllite Base Get",ex.toString());
            return null;
        }
    }
    // Standart //
}
