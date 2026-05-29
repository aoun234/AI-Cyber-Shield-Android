package com.example.aicybershield.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aicybershield.R;
import com.example.aicybershield.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> list;

    private static final int USER = 1;
    private static final int BOT = 2;

    public ChatAdapter(List<ChatMessage> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {

        if (list.get(position).isUser()) {
            return USER;
        } else {
            return BOT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        if (viewType == USER) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_user,
                            parent,
                            false);

            return new UserViewHolder(view);

        } else {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_bot,
                            parent,
                            false);

            return new BotViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            int position) {

        ChatMessage item = list.get(position);

        if (holder instanceof UserViewHolder) {

            ((UserViewHolder) holder)
                    .message
                    .setText(item.getMessage());

        } else {

            ((BotViewHolder) holder)
                    .message
                    .setText(item.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // USER VIEW HOLDER

    static class UserViewHolder
            extends RecyclerView.ViewHolder {

        TextView message;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.messageText);
        }
    }

    // BOT VIEW HOLDER

    static class BotViewHolder
            extends RecyclerView.ViewHolder {

        TextView message;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.messageText);
        }
    }
}