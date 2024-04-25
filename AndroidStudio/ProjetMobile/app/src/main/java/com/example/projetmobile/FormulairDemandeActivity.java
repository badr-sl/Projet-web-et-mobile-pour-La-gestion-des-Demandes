package com.example.projetmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormulairDemandeActivity extends AppCompatActivity {

    private EditText inputTitle;
    private EditText inputSujet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulair_demande);

        inputTitle = findViewById(R.id.titreEditText);
        inputSujet = findViewById(R.id.sujetEditText);

        Button btnEnvoyerDemande = findViewById(R.id.btnEnvoyerDemande);
        btnEnvoyerDemande.setOnClickListener(v -> sendDemandeToApi());
    }

    private void sendDemandeToApi() {
        String title = inputTitle.getText().toString();
        String sujet = inputSujet.getText().toString();
        String date = getCurrentDateTime(); // "Pending" status is assigned by default on the server side

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("title", title);
            jsonRequest.put("sujet", sujet);
            jsonRequest.put("date", date);
            // Assuming "etat" (status) is set server-side; otherwise add it here
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating JSON request.", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String url = "http://10.0.2.2:9090/demandes/add?token=" + token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                response -> Toast.makeText(FormulairDemandeActivity.this, "Demande submitted successfully.", Toast.LENGTH_SHORT).show(),
                error -> {
                    Toast.makeText(FormulairDemandeActivity.this, "Failed to submit demande: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Error: " + error.getMessage());
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

        // Redirect to demande list activity after submission
        startActivity(new Intent(this, DemandeListActivity.class));
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
