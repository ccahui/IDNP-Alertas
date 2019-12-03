package com.example.proyecto.ui.seBusca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.AvisosAdapter;
import com.example.proyecto.Model.Aviso;
import com.example.proyecto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeBuscaFragment extends Fragment {

    private SeBuscaViewModel seBuscaViewModel;
    RecyclerView recyclerView;
    ArrayList<Aviso> avisos;
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_se_busca, container, false);
        avisos = new ArrayList<>();
        recyclerView = vista.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Avisos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String nombre = ds.child("Nombre").getValue().toString();
                        String apellidos = ds.child("Apellido").getValue().toString();
                        String descripcion = ds.child("Descripcion").getValue().toString();
                        avisos.add(new Aviso(nombre, apellidos, descripcion));
                    }
                    AvisosAdapter avisosAdapter = new AvisosAdapter(avisos);
                    recyclerView.setAdapter(avisosAdapter);
                    avisosAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*databaseReference = mRef.child("Avisos");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String nombre = ds.getValue().toString();
                        String apellido = ds.getValue().toString();
                        String descripcion = ds.getValue().toString();
                        avisos.add(new Aviso(nombre, apellido, descripcion));
                    }

                    avisosAdapter[0] = new AvisosAdapter(avisos);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(avisosAdapter[0]);
                    avisosAdapter[0].notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        return vista;
    }

}