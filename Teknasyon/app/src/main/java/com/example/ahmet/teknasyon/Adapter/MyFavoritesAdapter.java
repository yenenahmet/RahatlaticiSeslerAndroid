package com.example.ahmet.teknasyon.Adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import com.example.ahmet.teknasyon.Database.DB.DataBase;
import com.example.ahmet.teknasyon.Model.CategoryDetailModel;
import com.example.ahmet.teknasyon.MyLib.RecyclerViewBaseSelectedAdapter;
import com.example.ahmet.teknasyon.R;
import com.example.ahmet.teknasyon.Util.Static;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyFavoritesAdapter  extends RecyclerViewBaseSelectedAdapter<CategoryDetailModel,MyFavoritesAdapter.MyViewHolder> {

    private DataBase dataBase;
    private Context context;
    private List<MediaPlayer> mediaPlayers = new ArrayList<>();
    private SweetAlertDialog sweetAlertDialog;
    private int val;
    public MyFavoritesAdapter(Context context){
        this.context = context;
        dataBase = new DataBase(context);
        setItems(dataBase.getMyFavorites());
        for (int i=0 ; i<getItemCount(); i++){
            mediaPlayers.add(MediaPlayer.create(context,getUri(i)));
        }
        ControlAudio();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_my_favorites_list_item, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoryDetailModel values = getItem(position);
        holder.MyFavoritesTextviewTitle.setText(values.getTitle());
        holder.MyFavoritesTextviewSubTitle.setText(values.getExplanation());
        holder.MyFavoritesSeekBar.setProgress(mediaPlayers.get(position).getCurrentPosition() / 1000);
        Click(holder,position);
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        private AppCompatTextView MyFavoritesTextviewTitle,MyFavoritesTextviewSubTitle;
        private AppCompatSeekBar MyFavoritesSeekBar;
        private PercentRelativeLayout MyFavoritesDeleteButton,MyFavoritesStartButton;
        private AppCompatImageView MyfavoriteImageStartPause,MyfavoriteImageDelete;
        public MyViewHolder(View itemView) {
            super(itemView);
            MyFavoritesTextviewTitle = itemView.findViewById(R.id.MyFavoritesTextviewTitle);
            MyFavoritesTextviewSubTitle = itemView.findViewById(R.id.MyFavoritesTextviewSubTitle);
            MyFavoritesSeekBar = itemView.findViewById(R.id.MyFavoritesSeekBar);
            MyFavoritesStartButton = itemView.findViewById(R.id.MyFavoritesStartButton);
            MyFavoritesDeleteButton = itemView.findViewById(R.id.MyFavoritesDeleteButton);
            MyfavoriteImageStartPause = itemView.findViewById(R.id.MyfavoriteImageStartPause);
            MyfavoriteImageDelete = itemView.findViewById(R.id.MyfavoriteImageDelete);
        }
    }

    private void RemoveMyFavorite(int position){
        CategoryDetailModel model = getItem(position);
        if(dataBase.DeleteMyFavorite(model.getCategoryDetailID())){
            Static.DeleteFile("/"+model.getCategoryMp3Path());
            removeItem(position);
            if(sweetAlertDialog != null) sweetAlertDialog.cancel();
            Toast.makeText(context,model.getTitle() + "Favorilerinizden Çıkartıldı ...",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,model.getTitle() +" "+ "Favorilerinizden Çıkarılırken Sorun Oluştu ...",Toast.LENGTH_LONG).show();
        }
    }
    private void StartMusic(int pos,MyViewHolder holder){
            MediaPlayer mediaPlayer = mediaPlayers.get(pos);
            if(mediaPlayer.isPlaying()){
                StopMusic(pos,holder);
            }else{
                mediaPlayer.start();
                holder.MyfavoriteImageStartPause.setImageResource(R.mipmap.pause);
                holder.MyFavoritesSeekBar.setProgress(val);
            }
        mediaPlayer.setOnCompletionListener(mediaPlayer1 -> {
            holder.MyfavoriteImageStartPause.setImageResource(R.mipmap.play);
        });
    }
    private void StopMusic(int pos,MyViewHolder holder){
        mediaPlayers.get(pos).pause();
        holder.MyfavoriteImageStartPause.setImageResource(R.mipmap.play);
    }
    private Uri getUri(int pos){
        return Uri.fromFile(new File(Static.mpFile.getAbsolutePath() +"/"+getItem(pos).getCategoryMp3Path()));
    }
    private void Click(MyViewHolder holder, int position){
        holder.MyFavoritesDeleteButton.setOnClickListener(view -> {
            sweetAlertDialog = Static.Loading(context);
            sweetAlertDialog.show();
            RemoveMyFavorite(position);
        });
        holder.MyFavoritesStartButton.setOnClickListener(view -> {
            StartMusic(position,holder);
        });
        holder.MyFavoritesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaPlayers.get(position) != null){
                    // mediaPlayers.get(position).seekTo(i*1000);
                    float volume = (float) (1 - (Math.log(100 - i) / Math.log(100)));
                    mediaPlayers.get(position).setVolume(volume, volume);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
    private void ControlAudio(){
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int val = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if(max != val){
            Toast.makeText(context,"Telefon Sesini Sona Çıkartın Ve Açtığınız Müzikler üzerinde Ses Kontrolü Yapıp Uygulamanın Keyfini Çıkartın :)",Toast.LENGTH_LONG).show();
        }
        float drap =  ( (((float)val) /((float)max)) *100 );
        this.val = (int)drap;
    }
    public void Shutdown(){
        for(MediaPlayer media :mediaPlayers){
            if(media != null){
                try{
                    media.stop();
                    media.release();
                }catch (Exception ex){
                    Log.e("Shutdown ",ex.toString());
                }
            }
        }
    }

}
