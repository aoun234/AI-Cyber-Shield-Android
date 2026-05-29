package com.example.aicybershield.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aicybershield.R;

public class ContactActivity extends AppCompatActivity {

    Button emailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        emailButton = findViewById(R.id.emailButton);

        emailButton.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_SENDTO);

            intent.setData(Uri.parse(
                    "mailto:support@aicybershield.com"
            ));

            startActivity(intent);
        });
    }
}