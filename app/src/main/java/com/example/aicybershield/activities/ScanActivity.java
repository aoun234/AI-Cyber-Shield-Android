package com.example.aicybershield.activities;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.aicybershield.R;
import com.example.aicybershield.database.AppDatabase;
import com.example.aicybershield.database.ScanRecord;
import com.example.aicybershield.models.ScanResponse;
import com.example.aicybershield.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanActivity extends AppCompatActivity {

    EditText urlInput;
    View scanButton;
    ProgressBar progressBar;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        urlInput = findViewById(R.id.urlInput);
        scanButton = findViewById(R.id.scanButton);
        progressBar = findViewById(R.id.progressBar);

        db = AppDatabase.getInstance(this);

        createNotificationChannel();
        checkNotificationPermission();

        scanButton.setOnClickListener(v -> startScan());
    }

    private void startScan() {

        String url = String.valueOf(urlInput.getText()).trim();

        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter URL", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        JsonObject body = new JsonObject();
        body.addProperty("url", url);

        RetrofitClient.getApiService().scanWebsite(body)
                .enqueue(new Callback<ScanResponse>() {

                    @Override
                    public void onResponse(Call<ScanResponse> call, Response<ScanResponse> response) {

                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful() && response.body() != null) {

                            ScanResponse data = response.body();

                            saveToDatabase(data);
                            showNotification(data.getUrl());
                            openResultScreen(data);

                        } else {
                            Toast.makeText(ScanActivity.this,
                                    "Scan failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ScanResponse> call, Throwable t) {

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(ScanActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveToDatabase(ScanResponse data) {

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.getDefault()).format(new Date());

        String json = new Gson().toJson(data.getVulnerabilities());

        ScanRecord record = new ScanRecord(
                data.getUrl(),
                date,
                data.getScore(),
                json
        );

        new Thread(() -> db.scanDao().insertScan(record)).start();
    }

    private void openResultScreen(ScanResponse data) {

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("url", data.getUrl());
        intent.putExtra("score", data.getScore());
        intent.putExtra("vuln_json", new Gson().toJson(data.getVulnerabilities()));
        startActivity(intent);
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    "scan_channel",
                    "Scan Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void showNotification(String url) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "scan_channel")
                .setSmallIcon(R.drawable.ic_scan)
                .setContentTitle("Scan Complete")
                .setContentText("Tap to view results for " + url)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {

            manager.notify(1, builder.build());
        }
    }

    private void checkNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        101);
            }
        }
    }
}