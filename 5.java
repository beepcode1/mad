package com.example.texttospeechapp;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private Button convertButton;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        convertButton = findViewById(R.id.convertButton);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                // Set the language
                int result = textToSpeech.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Initialization failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Set button click listener
        convertButton.setOnClickListener(v -> {
            String text = inputText.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show();
            } else {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
