package com.example.aicybershield.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aicybershield.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    // ================= VIEWS =================

    private TextView userEmail;

    private MaterialCardView scanCard;
    private MaterialCardView chatCard;
    private MaterialCardView historyCard;

    private MaterialToolbar toolbar;

    // ================= FIREBASE =================

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // ================= TOOLBAR =================

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        // ================= FIREBASE AUTH =================

        auth = FirebaseAuth.getInstance();

        // ================= INIT VIEWS =================

        userEmail = findViewById(R.id.userEmail);

        scanCard = findViewById(R.id.scanCard);
        chatCard = findViewById(R.id.chatCard);
        historyCard = findViewById(R.id.historyCard);

        // ================= CURRENT USER =================

        FirebaseUser user = auth.getCurrentUser();

        if (user != null && user.getEmail() != null) {

            userEmail.setText(user.getEmail());

        } else {

            userEmail.setText("Guest User");
        }

        // ================= SCAN CARD =================

        scanCard.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DashboardActivity.this,
                            ScanActivity.class
                    );

            startActivity(intent);
        });

        // ================= CHATBOT CARD =================

        chatCard.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DashboardActivity.this,
                            ChatbotActivity.class
                    );

            startActivity(intent);
        });

        // ================= HISTORY CARD =================

        historyCard.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DashboardActivity.this,
                            HistoryActivity.class
                    );

            startActivity(intent);
        });
    }

    // ================= MENU =================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.menu_dashboard,
                menu
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(
            @NonNull MenuItem item) {

        int id = item.getItemId();

        // ================= ABOUT =================

        if (id == R.id.menu_about) {

            startActivity(
                    new Intent(
                            DashboardActivity.this,
                            AboutActivity.class
                    )
            );

            return true;
        }

        // ================= PRIVACY =================

        else if (id == R.id.menu_privacy) {

            startActivity(
                    new Intent(
                            DashboardActivity.this,
                            PrivacyPolicyActivity.class
                    )
            );

            return true;
        }

        // ================= CONTACT =================

        else if (id == R.id.menu_contact) {

            contactSupport();

            return true;
        }

        // ================= LOGOUT =================

        else if (id == R.id.menu_logout) {

            logoutUser();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // ================= CONTACT SUPPORT =================

    private void contactSupport() {

        Intent emailIntent =
                new Intent(Intent.ACTION_SENDTO);

        emailIntent.setData(
                Uri.parse(
                        "mailto:support@aicybershield.com"
                )
        );

        emailIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                "AI Cyber Shield Support"
        );

        try {

            startActivity(emailIntent);

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "No email app found",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // ================= LOGOUT =================

    private void logoutUser() {

        auth.signOut();

        Intent intent =
                new Intent(
                        DashboardActivity.this,
                        LoginActivity.class
                );

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);

        finish();
    }
}