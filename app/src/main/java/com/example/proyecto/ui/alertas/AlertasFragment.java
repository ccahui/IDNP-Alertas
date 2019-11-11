package com.example.proyecto.ui.alertas;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto.Interfaces.IComunicaFragments;
import com.example.proyecto.R;

public class AlertasFragment extends Fragment {

    private AlertasViewModel alertasViewModel;
    View vista;
    Activity actividad;
    CardView robo, vandalismo, pelea, acoso;
    IComunicaFragments interfaceComunicaFragments;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        alertasViewModel = ViewModelProviders.of(this).get(AlertasViewModel.class);
        View root = inflater.inflate(R.layout.alertas, container, false);

        vista = inflater.inflate(R.layout.alertas, container, false);
        robo = vista.findViewById(R.id.robo);
        robo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.robo();
            }
        });
        //return vista;
        vandalismo = vista.findViewById(R.id.vandalismo);
        vandalismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.vandalismo();
            }
        });
        //return vista;
        pelea = vista.findViewById(R.id.pelea);
        pelea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.pelea();
            }
        });
        //return vista;
        acoso = vista.findViewById(R.id.acoso);
        acoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.acoso();
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