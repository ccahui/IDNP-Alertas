package com.example.proyecto.ui.seBusca;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.AlertasAdapter;
import com.example.proyecto.Model.Alerta;
import com.example.proyecto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SeBuscaFragment extends Fragment {

    private SeBuscaViewModel seBuscaViewModel;
    RecyclerView recyclerView;
    ArrayList<Alerta> alertas;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ImageView imageView;
    private String tipo, nombre, apellidos, ubicacion;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_se_busca, container, false);
        final Drawable[] drawable = new Drawable[1];
        alertas = new ArrayList<>();
        imageView = vista.findViewById(R.id.imageView_tipo);
        recyclerView = vista.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Alertas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        tipo = ds.child("Tipo").getValue().toString();
                        if (tipo.equals("Robo"))
                            drawable[0] = getResources().getDrawable(R.drawable.robo);
                        if (tipo.equals("Acoso"))
                            drawable[0] = getResources().getDrawable(R.drawable.acoso);
                        if (tipo.equals("Pelea"))
                            drawable[0] = getResources().getDrawable(R.drawable.pelea);
                        if (tipo.equals("Vandalismo"))
                            drawable[0] = getResources().getDrawable(R.drawable.vandalismo);
                        nombre = ds.child("Nombre").getValue().toString();
                        apellidos = ds.child("Apellido").getValue().toString();
                        ubicacion = ds.child("Ubicacion").getValue().toString();
                        alertas.add(new Alerta(tipo, nombre, apellidos, ubicacion, drawable[0]));

                    }
                    AlertasAdapter avisosAdapter = new AlertasAdapter(alertas);
                    recyclerView.setAdapter(avisosAdapter);
                    avisosAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return vista;
    }

}