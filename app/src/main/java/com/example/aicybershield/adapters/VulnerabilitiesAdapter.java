package com.example.aicybershield.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aicybershield.R;
import com.example.aicybershield.models.Vulnerability;

import java.util.List;

public class VulnerabilitiesAdapter extends RecyclerView.Adapter<VulnerabilitiesAdapter.VH> {

    private List<Vulnerability> list;

    public VulnerabilitiesAdapter(List<Vulnerability> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vulnerability, parent, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {

        Vulnerability v = list.get(position);

        h.type.setText(v.getType());
        h.attack.setText(v.getAttack());
        h.impact.setText(v.getImpact());
        h.fix.setText(v.getFix());

        String risk = v.getRisk();

        h.risk.setText(risk);

        // 🔥 CYBER SECURITY THEME COLORS
        if ("HIGH".equalsIgnoreCase(risk)) {
            h.risk.setTextColor(Color.parseColor("#FF3B30")); // red
        } else if ("MEDIUM".equalsIgnoreCase(risk)) {
            h.risk.setTextColor(Color.parseColor("#FF9500")); // orange
        } else {
            h.risk.setTextColor(Color.parseColor("#34C759")); // green
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class VH extends RecyclerView.ViewHolder {

        TextView type, risk, attack, impact, fix;

        public VH(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.vType);
            risk = itemView.findViewById(R.id.vRisk);
            attack = itemView.findViewById(R.id.vAttack);
            impact = itemView.findViewById(R.id.vImpact);
            fix = itemView.findViewById(R.id.vFix);
        }
    }
}