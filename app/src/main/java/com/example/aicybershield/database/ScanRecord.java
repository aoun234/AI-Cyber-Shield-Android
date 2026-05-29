package com.example.aicybershield.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scan_records")
public class ScanRecord {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String url;
    public String date;
    public int score;
    public String vulnerabilitiesJson;

    // Optional constructor (helpful later)
    public ScanRecord(String url, String date, int score, String vulnerabilitiesJson) {
        this.url = url;
        this.date = date;
        this.score = score;
        this.vulnerabilitiesJson = vulnerabilitiesJson;
    }

    // Empty constructor (Room needs it sometimes)
    public ScanRecord() {
    }
}