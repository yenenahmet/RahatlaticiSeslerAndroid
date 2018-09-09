package com.example.ahmet.teknasyon.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmet on 05.09.2018.
 */

public class CategoryDetailModel {
    @SerializedName("CategoryId")
    @Expose
    private int CategoryDetailID;
    @SerializedName("CategoryDeatilTitle")
    @Expose
    private String Title;
    @SerializedName("CategoryExplanation")
    @Expose
    private String Explanation;
    @SerializedName("CategoryMp3Path")
    @Expose
    private String CategoryMp3Path;

    public String getCategoryMp3Path() {
        return CategoryMp3Path;
    }

    public void setCategoryMp3Path(String categoryMp3Path) {
        CategoryMp3Path = categoryMp3Path;
    }

    public CategoryDetailModel(){

    }
    public CategoryDetailModel(int categoryDetailID,String title, String explanation,String categoryMp3Path) {
        Title = title;
        Explanation = explanation;
        CategoryDetailID = categoryDetailID;
        CategoryMp3Path = categoryMp3Path;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getExplanation() {
        return Explanation;
    }

    public void setExplanation(String explanation) {
        Explanation = explanation;
    }

    public int getCategoryDetailID() {
        return CategoryDetailID;
    }

    public void setCategoryDetailID(int categoryDetailID) {
        CategoryDetailID = categoryDetailID;
    }
}
