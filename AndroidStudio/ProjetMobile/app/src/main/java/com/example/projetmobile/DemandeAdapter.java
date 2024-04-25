package com.example.projetmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DemandeAdapter extends RecyclerView.Adapter<DemandeAdapter.DemandeViewHolder> {
    private Context context;
    private List<Demande> demandeList;

    public DemandeAdapter(Context context, List<Demande> demandeList) {
        this.context = context;
        this.demandeList = demandeList;
    }

    @NonNull
    @Override
    public DemandeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.demande_item, parent, false);
        return new DemandeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DemandeViewHolder holder, int position) {
        Demande demande = demandeList.get(position);
        holder.titleTextView.setText(demande.getTitle());
        holder.sujetTextView.setText(demande.getSujet());
        holder.dateTextView.setText(demande.getDate());
        holder.etatTextView.setText(demande.getEtat());
    }

    @Override
    public int getItemCount() {
        return demandeList.size();
    }

    public static class DemandeViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView sujetTextView;
        private TextView dateTextView;
        private TextView etatTextView;

        public DemandeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            sujetTextView = itemView.findViewById(R.id.sujetTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            etatTextView = itemView.findViewById(R.id.etatTextView);
        }
    }
}
