package com.example.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyecto.Model.Aviso;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;
    private Aviso aviso;
    private Context context;


    public CustomInfoWindowAdapter(LayoutInflater inflater,Context context, Aviso aviso){
        this.inflater = inflater;
        this.aviso = aviso;
        this.context = context;
    }

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.infowindow_layout, null);
        String[] info = m.getTitle().split("&");
        String url = m.getSnippet();
        ((TextView)v.findViewById(R.id.info_window_nombre)).setText("Nombre:"+aviso.getNombre());
        ((TextView)v.findViewById(R.id.info_window_Apellido)).setText("Apellido: "+aviso.getApellido());
        ((TextView)v.findViewById(R.id.info_window_descripcion)).setText("Descripción: "+aviso.getDescripcion());

        ImageView imageView = (ImageView)v.findViewById(R.id.info_window_imagen);

        Glide.with(context)
                .load(aviso.getmImageUrl())
                .centerCrop()
                .into(imageView);

        //((TextView)v.findViewById(R.id.info_window_estado)).setText("Estado: Activo");
        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }

}