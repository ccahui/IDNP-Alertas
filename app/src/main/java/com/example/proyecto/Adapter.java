package com.example.proyecto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Model.Aviso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Avisosviewholder> {
    ArrayList<Aviso> avisos;
    private int resource;

    public Adapter(ArrayList<Aviso> avisos, int resource) {
        this.avisos = avisos;
        this.resource = resource;
    }

    @NonNull
    @Override
    public Avisosviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new Avisosviewholder(view);

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

    public class Avisosviewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView_title;
        TextView textView_descripcion;
        View view;

        public Avisosviewholder(View view) {
            super(view);
            this.view = view;
            this.imageView = view.findViewById(R.id.img_aviso);
            this.textView_title = view.findViewById(R.id.textView_title);
            this.textView_descripcion = view.findViewById(R.id.textView_descripcion);

        }
    }


}
