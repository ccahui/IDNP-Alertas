package com.example.proyecto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Model.Aviso;

import java.util.ArrayList;

public class AvisosAdapter extends RecyclerView.Adapter<AvisosAdapter.Avisosviewholder> {
    ArrayList<Aviso> avisos;

    public AvisosAdapter(ArrayList<Aviso> avisos) {
        this.avisos = avisos;
    }

    @NonNull
    @Override
    public Avisosviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_avisos, parent, false);
        return new Avisosviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvisosAdapter.Avisosviewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return avisos.size();
    }

    public class Avisosviewholder extends RecyclerView.ViewHolder {
        TextView textView_nombre_aviso;
        TextView textView_apellido_aviso;
        TextView textView_descripcion_aviso;

        public Avisosviewholder(View view) {
            super(view);
            this.textView_nombre_aviso = view.findViewById(R.id.textView_nombre_aviso);
            this.textView_apellido_aviso = view.findViewById(R.id.textView_apellido_aviso);
            this.textView_descripcion_aviso = view.findViewById(R.id.textView_descripcion_aviso);
        }
    }

}
