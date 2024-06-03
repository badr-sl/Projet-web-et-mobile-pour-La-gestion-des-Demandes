package com.example.projetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class DemandeListAdmine extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DemandeAdminAdapter adapter;
    private List<Demande> demandeList;
    private static final int REFRESH_INTERVAL = 900; // Interval de rafraîchissement en millisecondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_list_admine);

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        demandeList = new ArrayList<>();
        adapter = new DemandeAdminAdapter(this, demandeList);
        recyclerView.setAdapter(adapter);

        Button btnLogout = findViewById(R.id.button);
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(DemandeListAdmine.this, LoginActivity.class));
        });

        fetchDataFromApi();
        startAutoRefresh();
    }

    private void startAutoRefresh() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchDataFromApi();
                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        }, REFRESH_INTERVAL);
    }

    private void fetchDataFromApi() {
        SharedPreferences sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");
        String url = "http://10.0.2.2:9090/demandes";

        // Préparation du corps de la requête en tant que chaîne de caractères.
        final String requestBody = token;

        // Création et envoi de la requête
        StringRequest demandeRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        demandeList.clear(); // Effacer les données existantes
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            long idDemande = jsonObject.getLong("idDemande");
                            String title = jsonObject.getString("title");
                            String sujet = jsonObject.getString("sujet");
                            String date = jsonObject.getString("date");
                            String etat = jsonObject.getString("etat");
                            Demande demande = new Demande(idDemande, title, sujet, date, etat);
                            demandeList.add(demande);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Gestion des erreurs
                }) {
            @Override
            public byte[] getBody() {
                return requestBody.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "text/plain; charset=utf-8";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(demandeRequest);
    }
}
