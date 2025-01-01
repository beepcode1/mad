package com.example.wallpaperapp;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] wallpapers = {
        R.drawable.wallpaper1,
        R.drawable.wallpaper2,
        R.drawable.wallpaper3
    };

    private RelativeLayout layout;
    private Handler handler;
    private Runnable wallpaperChanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layout); // Assuming the root layout has the ID 'layout'
        Button changeWallpaperButton = findViewById(R.id.changeWallpaperButton);

        handler = new Handler();
        wallpaperChanger = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int randomIndex = random.nextInt(wallpapers.length);
                layout.setBackgroundResource(wallpapers[randomIndex]);
                handler.postDelayed(this, 30000); // 30 seconds
            }
        };

        changeWallpaperButton.setOnClickListener(v -> handler.post(wallpaperChanger));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(wallpaperChanger);
    }
}
