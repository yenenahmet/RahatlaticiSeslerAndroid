package com.example.ahmet.teknasyon.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.io.File;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Ahmet on 08.09.2018.
 */

public class Static {
    public static final File mpFile = new File(Environment.getExternalStorageDirectory().toString() + "/Teknasyon/Mp3");

    public static SweetAlertDialog Loading(Context context ){
        SweetAlertDialog ssDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        ssDialog.setTitleText("Verileriniz Yükleniyor ...");
        ssDialog.setCancelable(false);
        return ssDialog;
    }
    public static boolean DeleteFile(String path){
        try{
            (new File( mpFile.getPath() +path)).delete();
            return true;
        }catch (Exception ex){
            Log.e("Delete File",ex.toString());
            return false;
        }
    }
    public static void ScreenBarClear(Activity context){
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            try{
                context.requestWindowFeature(Window.FEATURE_NO_TITLE);
            }catch (Exception ex){
                Log.e("ScreenClear 11",ex.toString());
            }
            context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if(Build.VERSION.SDK_INT >= 19) {
            View decorView = context.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }
    public  static boolean isStoragePermissionGranted(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}
