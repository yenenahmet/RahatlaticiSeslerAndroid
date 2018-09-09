package com.example.ahmet.teknasyon.UI.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.ahmet.teknasyon.Adapter.CategoryDetailsAdapter;
import com.example.ahmet.teknasyon.Database.DB.DataBase;
import com.example.ahmet.teknasyon.Model.CategoryDetailModel;
import com.example.ahmet.teknasyon.R;
import com.example.ahmet.teknasyon.Retrofit.Interface.RetInterface;
import com.example.ahmet.teknasyon.Retrofit.Util.RetUtil;
import com.example.ahmet.teknasyon.Util.Static;
import java.util.ArrayList;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class CategoryDetails extends AppCompatActivity {
    private RecyclerView recyclerView;
    public static Boolean aBoolean =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DataLoad(getIntent().getExtras().getInt("ID"));
        Static.isStoragePermissionGranted(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void DataLoad(int id){
        SweetAlertDialog sweetAlertDialog = Static.Loading(this);
        sweetAlertDialog.show();
        RetUtil.CallEnque(RetUtil.createServis(RetInterface.class).Call_CategoryDetails(id));
        RetUtil.setRetUtilListener((RetUtil.RetUtilListener<List<CategoryDetailModel>>) (response, Status) ->{
            if(Status ==1){
                RecyclerLoad(response.body());
            }else{
                Toast.makeText(this,"Bağlanıtızda Sorun Var !!!",Toast.LENGTH_SHORT).show();
            }
            sweetAlertDialog.cancel();
        });
    }
    private void RecyclerLoad(List<CategoryDetailModel> val){
        recyclerView = findViewById(R.id.CategoriDetailsRecyc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new CategoryDetailsAdapter(this,CategoryIdControl(val)));
    }

    private List<CategoryDetailModel> CategoryIdControl(List<CategoryDetailModel> val){
        DataBase db = new DataBase(this);
        List<CategoryDetailModel> Pos = new ArrayList<>();
        List<CategoryDetailModel> CategoryList = val;
        for(Integer values :db.getMyFavoritesId()){
            for(int i =0;i<CategoryList.size();i++){
                if(values ==CategoryList.get(i).getCategoryDetailID()){ // Dahaönceden favorilere eklenmişliste için alınmasınınn anlamı yok
                    Pos.add(CategoryList.get(i)); // Listeden çıkarılcak ID çıkarma işlemini burda yapmıyoruz çünkü liste değişiklik olduğunu algılanmadığı için exception fırlatabiliyor
                }
            }
        }
        for(CategoryDetailModel value:Pos){
            CategoryList.remove(value);
        }
        return CategoryList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    aBoolean = true;
                }else{
                    Toast.makeText(this,"Dosya Yazma  İzni Verilmedi",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


}
