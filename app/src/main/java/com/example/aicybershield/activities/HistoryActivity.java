package com.example.aicybershield.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aicybershield.R;
import com.example.aicybershield.adapters.HistoryAdapter;
import com.example.aicybershield.database.AppDatabase;
import com.example.aicybershield.database.ScanRecord;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        db = AppDatabase.getInstance(this);

        loadHistory();
    }

    private void loadHistory() {

        new Thread(() -> {

            List<ScanRecord> list =
                    db.scanDao().getAllScans();

            runOnUiThread(() -> {

                HistoryAdapter adapter =
                        new HistoryAdapter(
                                this,
                                list
                        );

                recyclerView.setAdapter(adapter);
            });

        }).start();
    }
}