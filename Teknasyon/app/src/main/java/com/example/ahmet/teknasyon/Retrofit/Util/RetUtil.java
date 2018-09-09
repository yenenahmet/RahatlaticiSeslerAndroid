package com.example.ahmet.teknasyon.Retrofit.Util;


import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahmet on 08.09.2018.
 */

public class RetUtil {
    public static final String URL ="http://192.168.1.2:8089";
    private static final String Tag ="Retrofit";

    private static RetUtilListener retUtilListener;

    public interface  RetUtilListener<T>{
        void bas(Response<T> response, int Status);
    }
    public synchronized static <T> void setRetUtilListener(RetUtilListener<T> din){
        retUtilListener= din;
    }

    private static OkHttpClient defaultClient() {
       OkHttpClient client = new OkHttpClient.Builder()
               .connectTimeout(10,TimeUnit.SECONDS)
               .writeTimeout(20,TimeUnit.SECONDS)
               .readTimeout(100,TimeUnit.SECONDS)
               .build();
        return client;
    }
    public static <S> S createServis(Class<S> sClass){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(defaultClient())
                .build();
        return retrofit.create(sClass);
    }
        // <T , S extend T> :) .^
        // Java 8 Özelliğinden yararlanmak ve sürekli aynı kodları tekrar edip satır sayısını Arttırmamak için yazmıştım buraya eklemek istedim

    public  synchronized static  <T>  void   CallEnque(Call<T> call){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if(response.isSuccessful()){
                    retUtilListener.bas(response,1);
                }else{
                    Log.e(Tag, "Model Tanımlamalarında Sorun Var");
                    retUtilListener.bas(null,2);
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                Log.e(Tag, "İnternette Bağlantısında Veya Veri Alış Verişinde Sorun Var -- " +throwable.toString());
                retUtilListener.bas(null,0);
            }
        });
    }

    public static boolean writeResponseBodyToDisk(ResponseBody body,String Mp3Name) {
        try {
            File mpFile = new File(Environment.getExternalStorageDirectory().toString() + "/Teknasyon/Mp3");
            File file = new File(mpFile.getAbsolutePath() +"/"+Mp3Name);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();

                    if (!mpFile.exists())
                        mpFile.mkdirs();

                    outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            }
        } catch (IOException e) {
            return false;
        }
    }
}
