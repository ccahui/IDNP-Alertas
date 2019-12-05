package com.example.proyecto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Model.Alerta;

import java.util.ArrayList;

public class AlertasAdapter extends RecyclerView.Adapter<AlertasAdapter.Avisosviewholder> {
    ArrayList<Alerta> alertas;

    public AlertasAdapter(ArrayList<Alerta> alertas) {
        this.alertas = alertas;
    }

    @NonNull
    @Override
    public Avisosviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler, parent, false);
        return new Avisosviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Avisosviewholder holder, int position) {
        holder.textView_tipo.setText(alertas.get(position).getTipo());
        holder.textView_nombre.setText(alertas.get(position).getNombres());
        holder.textView_apellido.setText(alertas.get(position).getApellidos());
        holder.textView_ubicacion.setText(alertas.get(position).getUbicacion());
        holder.imageView_tipo.setImageDrawable(alertas.get(position).getImagen_tipo());

    }

    @Override
    public int getItemCount() {
        return alertas.size();
    }

    public class Avisosviewholder extends RecyclerView.ViewHolder {
        TextView textView_tipo;
        TextView textView_nombre;
        TextView textView_apellido;
        TextView textView_ubicacion;
        ImageView imageView_tipo;

        public Avisosviewholder(View view) {
            super(view);
            this.textView_tipo = view.findViewById(R.id.textView_tipo);
            this.textView_nombre = view.findViewById(R.id.textView_nombre);
            this.textView_apellido = view.findViewById(R.id.textView_apellido);
            this.textView_ubicacion = view.findViewById(R.id.textView_descripcion);
            this.imageView_tipo = view.findViewById(R.id.imageView_tipo);


        }
    }


}
