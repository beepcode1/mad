package com.example.asynctaskdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView bannerText;
    private Button startTaskButton, stopTaskButton;
    private boolean isRunning = false;
    private BannerTask bannerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bannerText = findViewById(R.id.bannerText);
        startTaskButton = findViewById(R.id.startTaskButton);
        stopTaskButton = findViewById(R.id.stopTaskButton);

        startTaskButton.setOnClickListener(v -> {
            if (!isRunning) {
                isRunning = true;
                bannerTask = new BannerTask();
                bannerTask.execute();
            }
        });

        stopTaskButton.setOnClickListener(v -> {
            isRunning = false;
            if (bannerTask != null) {
                bannerTask.cancel(true);
            }
        });
    }

    private class BannerTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String text = "Demonstration of Asynchronous Task";

            while (isRunning) {
                try {
                    Thread.sleep(300); // Delay to simulate scrolling effect
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!isRunning) break;

                text = text.substring(1) + text.charAt(0); // Scroll text
                publishProgress(text);

                if (isCancelled()) break;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            bannerText.setText(values[0]); // Update the banner text
        }
    }
}
