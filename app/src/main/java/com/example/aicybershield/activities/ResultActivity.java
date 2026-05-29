package com.example.aicybershield.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aicybershield.R;
import com.example.aicybershield.adapters.VulnerabilitiesAdapter;
import com.example.aicybershield.models.Vulnerability;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class ResultActivity extends AppCompatActivity {

    TextView urlText, scoreText;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        urlText = findViewById(R.id.urlText);
        scoreText = findViewById(R.id.scoreText);
        recyclerView = findViewById(R.id.recyclerView);

        // Get data from intent
        String url = getIntent().getStringExtra("url");
        int score = getIntent().getIntExtra("score", 0);
        String vulnJson = getIntent().getStringExtra("vuln_json");

        urlText.setText(url);
        scoreText.setText(String.valueOf(score));

        // Color system
        if (score < 40) {
            scoreText.setTextColor(Color.RED);
        } else if (score < 70) {
            scoreText.setTextColor(Color.parseColor("#FF9800"));
        } else {
            scoreText.setTextColor(Color.parseColor("#4CAF50"));
        }

        // Convert JSON → List
        Type type = new TypeToken<List<Vulnerability>>() {}.getType();
        List<Vulnerability> list = new Gson().fromJson(vulnJson, type);

        // RecyclerView setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new VulnerabilitiesAdapter(list));
    }
}