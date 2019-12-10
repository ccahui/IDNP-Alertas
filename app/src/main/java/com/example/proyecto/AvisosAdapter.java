package com.example.proyecto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Activities.VerMas_Activity;
import com.example.proyecto.Model.Aviso;

import java.util.ArrayList;

public class AvisosAdapter extends RecyclerView.Adapter<AvisosAdapter.Avisosviewholder> {
    ArrayList<Aviso> avisos;
    int posicion;

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
        holder.textView_nombre_aviso.setText(avisos.get(position).getNombre());
        holder.textView_apellido_aviso.setText(avisos.get(position).getApellido());
        holder.textView_descripcion_aviso.setText(avisos.get(position).getDescripcion());
        holder.setOnClickListeners(position);
    }

    @Override
    public int getItemCount() {
        return avisos.size();
    }


    public class Avisosviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        TextView textView_nombre_aviso;
        TextView textView_apellido_aviso;
        TextView textView_descripcion_aviso;
        Button button_ver_mas;

        public Avisosviewholder(View view) {
            super(view);
            context = view.getContext();
            this.textView_nombre_aviso = view.findViewById(R.id.textView_nombre_aviso);
            this.textView_apellido_aviso = view.findViewById(R.id.textView_apellido_aviso);
            this.textView_descripcion_aviso = view.findViewById(R.id.textView_descripcion_aviso);
            this.button_ver_mas = view.findViewById(R.id.button_ver_mas_aviso);
        }

        void setOnClickListeners(int position) {
            button_ver_mas.setOnClickListener(this);
            posicion = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, VerMas_Activity.class);
            intent.putExtra("Avisos", avisos);
            intent.putExtra("Posicion",posicion);
            context.startActivity(intent);
        }
    }

}
