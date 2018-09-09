package com.example.ahmet.teknasyon.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmet.teknasyon.Model.BookShelfModel;
import com.example.ahmet.teknasyon.MyLib.RecyclerViewBaseSelectedAdapter;
import com.example.ahmet.teknasyon.R;
import com.example.ahmet.teknasyon.Retrofit.Util.RetUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ahmet on 06.09.2018.
 */

public class BookShelfAdapter  extends RecyclerViewBaseSelectedAdapter<BookShelfModel,BookShelfAdapter.MyViewHolder> {
    private Context context;

    public BookShelfAdapter(Context context,List<BookShelfModel> modelList){
        this.context = context;
        setItems(modelList);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_bookshelf_list_item, parent, false);
        return new BookShelfAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BookShelfModel val = getItem(position);
        holder.BookShelfTextviewTitle.setText(val.getTitle());
        Picasso.get().load(RetUtil.URL +"/Img/"+val.getImgPath()).fit().into( holder.BookshelfImageView);
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private AppCompatTextView BookShelfTextviewTitle;
        private AppCompatImageView BookshelfImageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            BookShelfTextviewTitle = itemView.findViewById(R.id.BookshelfTextViewTitle);
            BookshelfImageView = itemView.findViewById(R.id.BookshelfImageView);
        }
    }
}
