package com.example.ahmet.teknasyon.UI.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ahmet.teknasyon.MyLib.Typefaces;
import com.example.ahmet.teknasyon.R;
import com.example.ahmet.teknasyon.Util.Static;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Static.ScreenBarClear(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        ShimmerTextView StartTextview = findViewById(R.id.shimmer_tv);
        StartTextview.setTypeface(Typefaces.get(this,"Satisfy-Regular.ttf"));

        new Shimmer().start(StartTextview);

        Thread Splash = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(6000);
                    //
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    StartActivity.this.finish();
                } catch (InterruptedException e) {
                    Log.e("Splash Err",e.toString());
                }
            }
        };
        Splash.start();
    }
}
