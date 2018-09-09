package com.example.ahmet.teknasyon.Retrofit.Interface;

import com.example.ahmet.teknasyon.Model.BookShelfModel;
import com.example.ahmet.teknasyon.Model.CategoryDetailModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * Created by Ahmet on 08.09.2018.
 */

public interface RetInterface {

    @GET("/api/BookShelf")
    Call<List<BookShelfModel>> Call_BookShelf() ;

    @GET("/api/CateGoryDeatil?")
    Call<List<CategoryDetailModel>> Call_CategoryDetails(@Query("id") int id) ;

    @Streaming
    @GET
    Call<ResponseBody> Call_Mp3(@Url String url) ;

}
