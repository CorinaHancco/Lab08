package com.example.lab02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class PostulanteAdapter extends RecyclerView.Adapter<PostulanteAdapter.ViewHolder> {
    private List<Postulante> postulantes;

    public PostulanteAdapter(List<Postulante> postulantes) {
        this.postulantes = postulantes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_postulante, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Configura los TextViews con los datos del postulante
        Postulante postulante = postulantes.get(position);
        holder.textViewDni.setText("DNI: " + postulante.getDni());

    }

    @Override
    public int getItemCount() {
        return postulantes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDni;
        // TextViews para los campos

        public ViewHolder(@NonNull View itemView) {
            super(Objects.requireNonNull(itemView));
            // Inicializa el TextViews
            textViewDni = itemView.findViewById(R.id.textViewDni);

        }
    }
    public void setPostulantes(List<Postulante> postulantes) {
        this.postulantes = postulantes;
    }
}
