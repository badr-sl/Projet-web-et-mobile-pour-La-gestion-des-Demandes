package com.example.projetmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputTel;
    private EditText inputEmail;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = findViewById(R.id.inputUsername);
        inputTel = findViewById(R.id.inputTel);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> registerUser());

        TextView btnAlreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        btnAlreadyHaveAccount.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void registerUser() {
        String username = inputUsername.getText().toString().trim();
        String tel = inputTel.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (username.isEmpty() || tel.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("username", username);
            jsonRequest.put("tel", tel);
            jsonRequest.put("email", email);
            jsonRequest.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "Error creating JSON request.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2:9090/user/add";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                response -> {
                    try {
                        String responseCode = response.getString("response");
                        if ("200".equals(responseCode)) {
                            String token = response.getString("token");
                            // Optionally save the token locally for future use
                            SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", token);
                            editor.apply();

                            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, DemandeListActivity.class); // Change MainActivity to your main activity
                            startActivity(intent);
                            finish();
                        } else if ("400".equals(responseCode)) {
                            Toast.makeText(RegisterActivity.this, "Username is already used", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Error processing registration response.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    if (error.networkResponse != null) {
                        String errorMessage = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Toast.makeText(RegisterActivity.this, "Error during registration: " + errorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Network error during registration", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }
}
