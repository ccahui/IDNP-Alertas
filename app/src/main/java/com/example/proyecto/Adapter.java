package com.example.proyecto;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Model.Aviso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Avisosviewholder> {
    List<Aviso> avisos;

    public Adapter(List<Aviso> avisos) {
        this.avisos = avisos;
    }

    @NonNull
    @Override
    public Avisosviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler, parent, false);
        Avisosviewholder holder  = new Avisosviewholder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull Avisosviewholder holder, int position) {
        Aviso aviso = avisos.get(position);
        holder.textView_title.setText(aviso.getNombre());
        holder.textView_descripcion.setText(aviso.getDescripcion());
        holder.imageView.setImageURI(Uri.parse(aviso.getImagen()));
    }

    @Override
    public int getItemCount() {
        return avisos.size();
    }

    public static class Avisosviewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView_title;
        TextView textView_descripcion;

        public Avisosviewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_paviso);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_descripcion = itemView.findViewById(R.id.textView_descripcion);
        }
    }


}
