package com.example.projetmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemandeAdminAdapter extends RecyclerView.Adapter<DemandeAdminAdapter.ViewHolder> {
    private Context contexte;
    private List<Demande> demandeListe;

    public DemandeAdminAdapter(Context context, List<Demande> demandeList) {
        this.contexte = context;
        this.demandeListe = demandeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexte).inflate(R.layout.demand_admin_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Demande demande = demandeListe.get(position);
        holder.titleTextView.setText(demande.getTitle());
        holder.sujetTextView.setText(demande.getSujet());
        holder.dateTextView.setText(demande.getDate());
        holder.etatTextView.setText(demande.getEtat());

        holder.btnAccept.setOnClickListener(v -> {
            // Récupération du token stocké dans les SharedPreferences
            SharedPreferences sharedPref = contexte.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String token = sharedPref.getString("token", "");

            // ID de la demande
            long idDemande = demandeListe.get(holder.getAdapterPosition()).getIdDemande(); // Assurez-vous que cette méthode existe et renvoie l'ID correct

            // Préparation de l'URL de l'API avec l'ID de la demande comme paramètre de requête
            String url = "http://10.0.2.2:9090/reclamation/solve?id=" + idDemande;

            // Création de la requête
            StringRequest solveRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        // La demande a été acceptée avec succès
                        Toast.makeText(contexte, "Demande acceptée", Toast.LENGTH_SHORT).show();
                        // Mettez à jour votre UI ici
                    },
                    error -> {
                        // Gérer l'erreur
                        Toast.makeText(contexte, "Erreur lors de l'acceptation de la demande", Toast.LENGTH_SHORT).show();
                    }) {
                @Override
                public byte[] getBody() {
                    return token.getBytes(StandardCharsets.UTF_8);
                }

                @Override
                public String getBodyContentType() {
                    // Assurez-vous que le serveur accepte ce type de contenu pour le token
                    return "text/plain; charset=utf-8";
                }
            };

            // Ajout de la requête à la file d'attente de Volley
            RequestQueue queue = Volley.newRequestQueue(contexte);
            queue.add(solveRequest);
        });




        holder.btnRefuse.setOnClickListener(v -> {
            // Récupération du token stocké dans les SharedPreferences
            SharedPreferences sharedPref = contexte.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String token = sharedPref.getString("token", "");

            // ID de la demande
            long idDemande = demandeListe.get(holder.getAdapterPosition()).getIdDemande(); // Assurez-vous que cette méthode existe et renvoie l'ID correct

            // Préparation de l'URL de l'API avec l'ID de la demande comme paramètre de requête
            String url = "http://10.0.2.2:9090/reclamation/reject?id=" + idDemande;

            // Création de la requête pour rejeter la demande
            StringRequest rejectRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        // La demande a été rejetée avec succès, notifier l'utilisateur
                        Toast.makeText(contexte, "Demande rejetée", Toast.LENGTH_SHORT).show();

                        // Mettez à jour votre liste locale ou notifiez l'adapter du changement
                        demandeListe.get(holder.getAdapterPosition()).setEtat("Rejected");
                        notifyItemChanged(holder.getAdapterPosition());
                    },
                    error -> {
                        // Gérer l'erreur
                        Toast.makeText(contexte, "Erreur lors du rejet de la demande", Toast.LENGTH_SHORT).show();
                    }) {
                @Override
                public byte[] getBody() {
                    return token.getBytes(StandardCharsets.UTF_8);
                }

                @Override
                public String getBodyContentType() {
                    return "text/plain; charset=utf-8";
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", String.valueOf(idDemande));
                    return params;
                }
            };

            // Ajout de la requête à la file d'attente de Volley
            RequestQueue queue = Volley.newRequestQueue(contexte);
            queue.add(rejectRequest);
        });

    }

    @Override
    public int getItemCount() {
        return demandeListe.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, sujetTextView, dateTextView, etatTextView;
        Button btnAccept, btnRefuse;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            sujetTextView = itemView.findViewById(R.id.sujetTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            etatTextView = itemView.findViewById(R.id.etatTextView);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnRefuse = itemView.findViewById(R.id.btnRefuse);
        }
    }
}
