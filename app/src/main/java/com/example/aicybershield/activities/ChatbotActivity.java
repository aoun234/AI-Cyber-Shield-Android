package com.example.aicybershield.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aicybershield.R;
import com.example.aicybershield.adapters.ChatAdapter;
import com.example.aicybershield.models.ChatMessage;
import com.example.aicybershield.network.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatbotActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText messageInput;
    MaterialButton sendButton;
    ProgressBar progressBar;

    ChatAdapter adapter;

    List<ChatMessage> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        recyclerView = findViewById(R.id.recyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        progressBar = findViewById(R.id.progressBar);

        messageList = new ArrayList<>();

        adapter = new ChatAdapter(messageList);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerView.setAdapter(adapter);

        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {

        String message =
                messageInput.getText().toString().trim();

        if (message.isEmpty()) {
            return;
        }

        // USER MESSAGE

        messageList.add(
                new ChatMessage(message, true)
        );

        adapter.notifyItemInserted(
                messageList.size() - 1
        );

        recyclerView.scrollToPosition(
                messageList.size() - 1
        );

        messageInput.setText("");

        progressBar.setVisibility(View.VISIBLE);

        sendButton.setEnabled(false);

        // API BODY

        JsonObject body = new JsonObject();
        body.addProperty("message", message);

        RetrofitClient.getApiService()
                .chat(body)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(
                            Call<JsonObject> call,
                            Response<JsonObject> response) {

                        progressBar.setVisibility(View.GONE);

                        sendButton.setEnabled(true);

                        if (response.isSuccessful()
                                && response.body() != null) {

                            String reply =
                                    response.body()
                                            .get("reply")
                                            .getAsString();

                            // BOT MESSAGE

                            messageList.add(
                                    new ChatMessage(reply, false)
                            );

                            adapter.notifyItemInserted(
                                    messageList.size() - 1
                            );

                            recyclerView.scrollToPosition(
                                    messageList.size() - 1
                            );

                        } else {

                            Toast.makeText(
                                    ChatbotActivity.this,
                                    "Failed",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<JsonObject> call,
                            Throwable t) {

                        progressBar.setVisibility(View.GONE);

                        sendButton.setEnabled(true);

                        Toast.makeText(
                                ChatbotActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}