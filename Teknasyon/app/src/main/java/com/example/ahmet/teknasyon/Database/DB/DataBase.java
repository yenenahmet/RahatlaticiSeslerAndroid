package com.example.ahmet.teknasyon.Database.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ahmet.teknasyon.Model.CategoryDetailModel;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME   = "Teknasyon";

    public DataBase(Context context) {
        super(context,DATABASE_NAME,null,5);
        DBUtil.db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE MyFavorites ( FavoritesID INTEGER PRIMARY KEY , Title TEXT,SubTitle TEXT ,Mp3Path TEXT )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS MyFavorites");
        onCreate(sqLiteDatabase);
    }
    //****** Insert ******///
    public boolean AddMyFavorite(CategoryDetailModel model){
        ContentValues values = new ContentValues();
        values.put("FavoritesID",model.getCategoryDetailID());
        values.put("Title",model.getTitle());
        values.put("SubTitle",model.getExplanation());
        values.put("Mp3Path",model.getCategoryMp3Path());
        return DBUtil.BaseInsert(values,"MyFavorites");
    }
    //****** Insert ******///
    //****** Select ******///
    public List<CategoryDetailModel> getMyFavorites(){
        List<CategoryDetailModel> array = new ArrayList<>();
        Cursor cursor = DBUtil.BaseGet("SELECT * FROM MyFavorites");
        if(cursor != null){
            while (cursor.moveToNext()){
                array.add(new CategoryDetailModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }
            cursor.close();
        }
        return array;
    }
    public  List<Integer> getMyFavoritesId(){
        List<Integer> array = new ArrayList<>();
        Cursor cursor = DBUtil.BaseGet("Select FavoritesID from MyFavorites");
        if(cursor!=null){
            while (cursor.moveToNext()){
                array.add(cursor.getInt(0));
            }
            cursor.close();
        }
        return array;
    }
    //****** Select ******///
    //****** Delete ******///
    public boolean DeleteMyFavorite(int ID){
        return DBUtil.BaseDelete("DELETE FROM MyFavorites WHERE FavoritesID=" +ID);
    }
    public boolean AllDeleteMyFavorite(){
        return DBUtil.BaseDelete("DELETE FROM MyFavorites");
    }
    //****** Delete ******///
}
