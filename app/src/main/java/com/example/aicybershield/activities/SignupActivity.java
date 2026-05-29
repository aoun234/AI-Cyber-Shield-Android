package com.example.aicybershield.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aicybershield.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText confirmPasswordInput;

    private MaterialButton signupButton;

    private ProgressBar progressBar;

    private TextView loginText;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Firebase
        auth = FirebaseAuth.getInstance();

        // Views
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);

        signupButton = findViewById(R.id.signupButton);

        progressBar = findViewById(R.id.progressBar);

        loginText = findViewById(R.id.loginText);

        // Signup click
        signupButton.setOnClickListener(v -> registerUser());

        // Back to login
        loginText.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {

        String email = emailInput.getText().toString().trim();

        String password = passwordInput.getText().toString().trim();

        String confirmPassword =
                confirmPasswordInput.getText().toString().trim();

        // Validation
        if (email.isEmpty()) {
            emailInput.setError("Email required");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter valid email");
            return;
        }

        if (password.isEmpty()) {
            passwordInput.setError("Password required");
            return;
        }

        if (password.length() < 6) {
            passwordInput.setError("Minimum 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Passwords do not match");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        signupButton.setEnabled(false);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);

                                signupButton.setEnabled(true);

                                if (task.isSuccessful()) {

                                    Toast.makeText(
                                            SignupActivity.this,
                                            "Account Created Successfully",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    Intent intent =
                                            new Intent(
                                                    SignupActivity.this,
                                                    DashboardActivity.class
                                            );

                                    startActivity(intent);

                                    finish();

                                } else {

                                    Toast.makeText(
                                            SignupActivity.this,
                                            task.getException().getMessage(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                        });
    }
}