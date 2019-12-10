package com.example.proyecto.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class VerAviso extends AppCompatActivity {

    RecyclerView rv;
    String textView_nombre_aviso;
    String textView_apellido_aviso;
    String textView_descripcion_aviso;
    String textView_telefono;
    //String imageView_foto;
    ArrayList<Aviso> avisos;
    //private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    //private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_aviso);
        avisos = new ArrayList<>();
        rv = findViewById(R.id.recycler_view_ver_avisos);
        rv.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Avisos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        textView_nombre_aviso = ds.child("Nombre").getValue().toString();
                        textView_apellido_aviso = ds.child("Apellido").getValue().toString();
                        textView_descripcion_aviso = ds.child("Descripcion").getValue().toString();
                        textView_telefono = ds.child("Telefono").getValue().toString();
                        avisos.add(new Aviso(textView_nombre_aviso, textView_apellido_aviso, textView_descripcion_aviso, textView_telefono));
                    }
                    AvisosAdapter avisosAdapter = new AvisosAdapter(avisos);
                    rv.setAdapter(avisosAdapter);
                    avisosAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
