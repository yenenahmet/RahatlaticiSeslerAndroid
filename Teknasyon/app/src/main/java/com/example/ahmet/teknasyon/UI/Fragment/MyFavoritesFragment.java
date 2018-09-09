package com.example.ahmet.teknasyon.UI.Fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ahmet.teknasyon.Adapter.MyFavoritesAdapter;
import com.example.ahmet.teknasyon.R;
import com.example.ahmet.teknasyon.UI.Activity.MainActivity;
import com.example.ahmet.teknasyon.Util.Static;

public class MyFavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyFavoritesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_my_favorites, container, false);

        if(Static.isStoragePermissionGranted(getActivity())){
            adapter =new MyFavoritesAdapter(getActivity());
            RecyclerLoad(view);
        }else{
            Toast.makeText(getActivity(),"Uygulamaya izin verilmediği için işlem yapılmaz...",Toast.LENGTH_LONG).show();
        }

        return  view;
    }
    private void RecyclerLoad(View view){
        recyclerView = view.findViewById(R.id.MyFavoritesRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onPause() {
        super.onPause();
        // Kapat
        if(adapter != null)
        adapter.Shutdown();
    }
}
