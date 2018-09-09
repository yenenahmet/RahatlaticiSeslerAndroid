package com.example.ahmet.teknasyon.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmet on 05.09.2018.
 */

public class BookShelfModel {
    @SerializedName("BookShelfID")
    @Expose
    private int BookShelfID;
    @SerializedName("BookShelfTitle")
    @Expose
    private String  Title;
    @SerializedName("BookShelfImgPath")
    @Expose
    private String ImgPath;


    public BookShelfModel(){
    }
    public BookShelfModel(int bookShelfID,String title, String imgPath ) {
        Title = title;
        ImgPath = imgPath;
        BookShelfID = bookShelfID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }

    public int getBookShelfID() {
        return BookShelfID;
    }

    public void setBookShelfID(int bookShelfID) {
        BookShelfID = bookShelfID;
    }
}
