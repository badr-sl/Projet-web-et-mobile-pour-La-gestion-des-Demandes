package com.example.projetmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulair_demande);

        inputTitle = findViewById(R.id.titreEditText);
        inputSujet = findViewById(R.id.sujetEditText);

        Button btnEnvoyerDemande = findViewById(R.id.btnEnvoyerDemande);
        btnEnvoyerDemande.setOnClickListener(v -> sendDemandeToApi());
        TextView btnSignUp = findViewById(R.id.btnAnnuler);
        btnSignUp.setOnClickListener(v -> startActivity(new Intent(FormulairDemandeActivity.this, DemandeListActivity.class)));
    }

    private void sendDemandeToApi() {
        String title = inputTitle.getText().toString().trim();
        String sujet = inputSujet.getText().toString().trim();

        // Vérifier si les champs sont vides
        if (title.isEmpty() || sujet.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = getCurrentDateTime(); // "Pending" status is assigned by default on the server side

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("title", title);
            jsonRequest.put("sujet", sujet);
            jsonRequest.put("date", date);
            // Assuming "etat" (status) is set server-side; otherwise add it here
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la création de la demande JSON.", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String url = "http://10.0.2.2:9090/demandes/add?token=" + token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                response -> {
                    Toast.makeText(FormulairDemandeActivity.this, "Demande soumise avec succès.", Toast.LENGTH_SHORT).show();
                    // Redirect to demande list activity after submission
                    startActivity(new Intent(FormulairDemandeActivity.this, DemandeListActivity.class));
                },
                error -> {
                    Toast.makeText(FormulairDemandeActivity.this, "Échec de la soumission de la demande: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Erreur: " + error.getMessage());
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }



    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
