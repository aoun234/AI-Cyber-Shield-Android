package com.example.aicybershield.database;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScanDao {

    @Insert
    void insertScan(ScanRecord scanRecord);

    @Query("SELECT * FROM scan_records ORDER BY id DESC")
    List<ScanRecord> getAllScans();

    @Query("SELECT * FROM scan_records WHERE id = :id")
    ScanRecord getScanById(int id);
}