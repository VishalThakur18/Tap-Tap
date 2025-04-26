package com.example.taptap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taptap.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.googleSignInBtn.setOnClickListener(v -> {
            // TODO: Add actual Google Sign-In logic here if needed
            startActivity(new Intent(Login.this, MainActivity.class));
            finish(); // Optional: Close Login activity so it doesn't stay in back stack
        });
    }
}
