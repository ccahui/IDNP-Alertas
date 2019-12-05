package com.example.proyecto.ui.seBusca;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto.AvisosAdapter;
import com.example.proyecto.Model.Aviso;
import com.example.proyecto.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class SeBuscaFragment extends Fragment {

    private SeBuscaViewModel seBuscaViewModel;
    RecyclerView recyclerView;
    ArrayList<Aviso> avisos;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ImageView imageView;
    private String nombre,apellidos,descripcion,imagen;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_se_busca, container, false);
        avisos = new ArrayList<>();
        imageView = vista.findViewById(R.id.imageView_row);
        recyclerView = vista.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Avisos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        nombre = ds.child("Nombre").getValue().toString();
                        apellidos = ds.child("Apellido").getValue().toString();
                        descripcion = ds.child("Descripcion").getValue().toString();
                        imagen = ds.child("Imagen").getValue().toString();
                        avisos.add(new Aviso(nombre, apellidos, descripcion, imagen));
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
        return vista;
    }

}