package com.example.ahmet.teknasyon.UI.Activity;

import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.ahmet.teknasyon.R;
import com.example.ahmet.teknasyon.UI.Fragment.BookshelfFragment;
import com.example.ahmet.teknasyon.UI.Fragment.MyFavoritesFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayView(0);

        ClickEvent();
    }
    private void displayView(int position) {
        Fragment fragment = fragment(position);
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentManager.popBackStack();
            fragmentTransaction.addToBackStack(null).commit();
        }
    }
    private Fragment fragment(int position){
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MyFavoritesFragment();
                break;
            case 1:
                fragment = new BookshelfFragment();
                break;
        }
        return  fragment;
    }
    private void ClickEvent(){
        PercentRelativeLayout Myfavori =  (findViewById(R.id.MyFavoritesButton));
        RelativeLayout BookShelf =  (findViewById(R.id.BookshelfButton));
        Myfavori.setSelected(true);

             Myfavori.setOnClickListener(view1 -> {
                 displayView(0);
                 Myfavori.setSelected(true);
                 BookShelf.setSelected(false);
            });
            BookShelf.setOnClickListener(view12 -> {
                displayView(1);
                Myfavori.setSelected(false);
                BookShelf.setSelected(true);
            });
        }

}
