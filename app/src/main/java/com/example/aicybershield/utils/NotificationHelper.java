package com.example.aicybershield.utils;

import android.content.Context;
import android.widget.Toast;

public class NotificationHelper {

    public static void showNotification(Context context, String url) {

        Toast.makeText(
                context,
                "Scan Completed: " + url,
                Toast.LENGTH_SHORT
        ).show();
    }
}