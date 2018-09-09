package com.example.ahmet.teknasyon.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.ahmet.teknasyon.Database.DB.DataBase;
import com.example.ahmet.teknasyon.Model.CategoryDetailModel;
import com.example.ahmet.teknasyon.MyLib.RecyclerViewBaseSelectedAdapter;
import com.example.ahmet.teknasyon.R;
import com.example.ahmet.teknasyon.Retrofit.Interface.RetInterface;
import com.example.ahmet.teknasyon.Retrofit.Util.RetUtil;
import com.example.ahmet.teknasyon.Util.Static;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

/**
 * Created by Ahmet on 08.09.2018.
 */

public class CategoryDetailsAdapter extends RecyclerViewBaseSelectedAdapter<CategoryDetailModel,CategoryDetailsAdapter.MyViewHolder> {
    private Context context;
    private DataBase dataBase;
    private boolean Mp3Bool =false;
    public CategoryDetailsAdapter( Context context,List<CategoryDetailModel> list){
        this.context = context;
        dataBase = new DataBase(context);
        setItems(list);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_category_details_item, parent, false);
        return new CategoryDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryDetailsAdapter.MyViewHolder holder, int position) {
        CategoryDetailModel val = getItem(position);
        holder.CategoryDetailsTitle.setText(val.getTitle());
        holder.CategoryDetailsSubTitle.setText(val.getExplanation());
        holder.CategoriDetailsAdd.setOnClickListener(view -> {
           DowloadMp3(position,context);
        });
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        private AppCompatTextView CategoryDetailsTitle,CategoryDetailsSubTitle;
        private AppCompatImageView CategoriDetailsAdd;
        public MyViewHolder(View itemView) {
            super(itemView);
            CategoryDetailsTitle = itemView.findViewById(R.id.CategoryDetailsTitle);
            CategoryDetailsSubTitle = itemView.findViewById(R.id.CategoryDetailsSubTitle);
            CategoriDetailsAdd = itemView.findViewById(R.id.CategoriDetailsAdd);
        }
    }
    private void DowloadMp3(int pos, Context context){
        Mp3Bool = false;
        SweetAlertDialog sweetAlertDialog = Static.Loading(context);
        sweetAlertDialog.show();
        RetUtil.CallEnque(RetUtil.createServis(RetInterface.class).Call_Mp3("/Mp3/"+getItem(pos).getCategoryMp3Path()));
        RetUtil.setRetUtilListener((RetUtil.RetUtilListener<ResponseBody>) (response, Status)->{
            if(Status == 1){
                AsyncTask(response.body(),pos,sweetAlertDialog);
            }else{
                Toast.makeText(context,"Bağlantızda Sorun Var !!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AsyncTask(ResponseBody body,int pos,SweetAlertDialog sweetAlertDialog){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Mp3Bool  =RetUtil.writeResponseBodyToDisk(body,getItem(pos).getCategoryMp3Path());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                sweetAlertDialog.cancel();
                if(Mp3Bool){
                    RecyclerItemDelete(pos,context);
                }else{
                    Toast.makeText(context,"Ses Dosyası Alınırken Sorun Oluştu !",Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
    private  void RecyclerItemDelete(int pos,Context context){
        CategoryDetailModel model = getItem(pos);
        if(dataBase.AddMyFavorite(model)){
            ((Activity)context).runOnUiThread(() -> {
                removeItem(pos);
            });
            Toast.makeText(context,model.getTitle() +" Favorilerinize Eklendi !",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Favorilerinize Eklenirken Sorun Oluştu  !",Toast.LENGTH_SHORT).show();
        }
    }
}
