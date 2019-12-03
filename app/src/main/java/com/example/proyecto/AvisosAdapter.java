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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler, parent, false);
        return new Avisosviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Avisosviewholder holder, int position) {
        holder.textView_nombre.setText(avisos.get(position).getNombre());
        holder.textView_apellido.setText(avisos.get(position).getApellido());
        holder.textView_descripcion.setText(avisos.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return avisos.size();
    }

    public class Avisosviewholder extends RecyclerView.ViewHolder {
        TextView textView_nombre;
        TextView textView_apellido;
        TextView textView_descripcion;

        public Avisosviewholder(View view) {
            super(view);
            this.textView_nombre = view.findViewById(R.id.textView_nombre);
            this.textView_apellido = view.findViewById(R.id.textView_apellido);
            this.textView_descripcion = view.findViewById(R.id.textView_descripcion);

        }
    }


}
