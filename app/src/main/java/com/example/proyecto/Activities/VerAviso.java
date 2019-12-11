package com.example.proyecto.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import java.util.List;

public class VerAviso extends AppCompatActivity implements AvisosAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private AvisosAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private List<Aviso> mAvisos;

   /* String textView_nombre_aviso;
    String textView_apellido_aviso;
    String textView_descripcion_aviso;
    String textView_telefono;*/
    //String imageView_foto;

    //private FirebaseDatabase firebaseDatabase;

    //private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_aviso);
        mRecyclerView = findViewById(R.id.recycler_view_ver_avisos);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mAvisos = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("datos_aviso");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Aviso a = postSnapshot.getValue(Aviso.class);
                    mAvisos.add(a);
                }
                mAdapter = new AvisosAdapter(VerAviso.this, mAvisos);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemCliclListener(VerAviso.this);
                mProgressCircle.setVisibility(View.INVISIBLE);
                /*if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        textView_nombre_aviso = ds.child("Nombre").getValue().toString();
                        textView_apellido_aviso = ds.child("Apellido").getValue().toString();
                        textView_descripcion_aviso = ds.child("Descripcion").getValue().toString();
                        textView_telefono = ds.child("Telefono").getValue().toString();
                        //avisos.add(new Aviso(textView_nombre_aviso, textView_apellido_aviso, textView_descripcion_aviso, textView_telefono));
                    }
                    AvisosAdapter avisosAdapter = new AvisosAdapter(avisos);
                    rv.setAdapter(avisosAdapter);
                    avisosAdapter.notifyDataSetChanged();
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(VerAviso.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"Normal click at position: " + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhattEverCLick(int position) {
        Toast.makeText(this,"Whatever click at position: " + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Toast.makeText(this,"delete click at position: " + position,Toast.LENGTH_SHORT).show();
    }
}
