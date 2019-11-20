package com.example.proyecto.ui.seBusca;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto.Interfaces.IComunicaFragments;
import com.example.proyecto.R;

public class SeBuscaFragment extends Fragment {

    private SeBuscaViewModel seBuscaViewModel;
    View vista;
    Activity actividad;
    CardView ver_aviso, publicar_Aviso;
    IComunicaFragments interfaceComunicaFragments;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        seBuscaViewModel = ViewModelProviders.of(this).get(SeBuscaViewModel.class);
        View root = inflater.inflate(R.layout.alertas, container, false);

        vista = inflater.inflate(R.layout.fragment_se_busca, container, false);
        ver_aviso = vista.findViewById(R.id.ver_aviso);
        ver_aviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.ver_aviso();
            }
        });
        //return vista;
        publicar_Aviso = vista.findViewById(R.id.publicar_aviso);
        publicar_Aviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.publicar_aviso();
            }
        });

        return vista;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            actividad = (Activity) context;
            interfaceComunicaFragments = (IComunicaFragments) actividad;
        }
    }
}