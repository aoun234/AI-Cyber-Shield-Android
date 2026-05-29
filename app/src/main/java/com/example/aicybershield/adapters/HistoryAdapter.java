package com.example.aicybershield.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aicybershield.R;
import com.example.aicybershield.activities.ResultActivity;
import com.example.aicybershield.database.ScanRecord;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    List<ScanRecord> list;

    public HistoryAdapter(Context context, List<ScanRecord> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_history,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        ScanRecord item = list.get(position);

        holder.url.setText(item.url);
        holder.date.setText(item.date);

        holder.score.setText(
                String.valueOf(item.score)
        );

        // SCORE COLOR

        if (item.score < 40) {

            holder.score.setTextColor(Color.RED);

        } else if (item.score < 70) {

            holder.score.setTextColor(
                    Color.parseColor("#FF9800")
            );

        } else {

            holder.score.setTextColor(
                    Color.parseColor("#4CAF50")
            );
        }

        // CLICK → RESULT SCREEN

        holder.itemView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(context,
                            ResultActivity.class);

            intent.putExtra("url", item.url);
            intent.putExtra("score", item.score);
            intent.putExtra("vuln_json",
                    item.vulnerabilitiesJson);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView url, date, score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            url = itemView.findViewById(R.id.url);
            date = itemView.findViewById(R.id.date);
            score = itemView.findViewById(R.id.score);
        }
    }
}