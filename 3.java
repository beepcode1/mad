public package com.example.counterapp;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int counter = 0;
    private boolean isCounting = false;
    private Handler handler;
    private Runnable counterRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView counterValue = findViewById(R.id.counterValue);
        Button startButton = findViewById(R.id.startButton);
        Button stopButton = findViewById(R.id.stopButton);

        handler = new Handler();

        counterRunnable = new Runnable() {
            @Override
            public void run() {
                if (isCounting) {
                    counter++;
                    counterValue.setText(String.valueOf(counter));
                    handler.postDelayed(this, 1000); // Increment every second
                }
            }
        };

        startButton.setOnClickListener(v -> {
            isCounting = true;
            handler.post(counterRunnable);
        });

        stopButton.setOnClickListener(v -> isCounting = false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(counterRunnable);
    }
}
 {
    
}
