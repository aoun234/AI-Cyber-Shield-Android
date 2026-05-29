package com.example.aicybershield;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aicybershield.activities.DashboardActivity;
import com.example.aicybershield.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // CHECK LOGIN

        if (FirebaseAuth.getInstance()
                .getCurrentUser() != null) {

            startActivity(
                    new Intent(
                            this,
                            DashboardActivity.class
                    )
            );

        } else {

            startActivity(
                    new Intent(
                            this,
                            LoginActivity.class
                    )
            );
        }

        finish();
    }
}