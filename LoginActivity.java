package com.example.signuploginapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField, passwordField;
    private Button loginButton;
    private int loginAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.loginUsername);
        passwordField = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);

        String validUsername = getIntent().getStringExtra("USERNAME");
        String validPassword = getIntent().getStringExtra("PASSWORD");

        loginButton.setOnClickListener(v -> {
            String inputUsername = usernameField.getText().toString();
            String inputPassword = passwordField.getText().toString();

            if (loginAttempts < 2) {
                if (inputUsername.equals(validUsername) && inputPassword.equals(validPassword)) {
                    Toast.makeText(this, "Successful Login", Toast.LENGTH_SHORT).show();
                } else {
                    loginAttempts++;
                    Toast.makeText(this, 
                        "Login Failed. Attempts left: " + (2 - loginAttempts), 
                        Toast.LENGTH_SHORT
                    ).show();
                }
            } else {
                loginButton.setEnabled(false);
                Toast.makeText(this, "Failed Login Attempts", Toast.LENGTH_LONG).show();
            }
        });
    }
}
