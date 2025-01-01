package com.example.signuploginapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameField, passwordField;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameField = findViewById(R.id.signupUsername);
        passwordField = findViewById(R.id.signupPassword);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(v -> {
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();

            if (validatePassword(password)) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.putExtra("USERNAME", username);
                intent.putExtra("PASSWORD", password);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, 
                    "Password must contain uppercase, lowercase, number, special character, " +
                    "and be at least 8 characters.", 
                    Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private boolean validatePassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()].*");
    }
}
