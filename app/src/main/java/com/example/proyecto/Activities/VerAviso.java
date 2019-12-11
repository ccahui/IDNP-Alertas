package com.example.proyecto.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(VerAviso.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(VerAviso.this, VerMas_Activity.class);
        //intent.putExtra("telefono",mAvisos.get(position).getTelefono().toString());
        intent.putExtra("imagen",mAvisos.get(position).getmImageUrl());
        intent.putExtra("nombre",mAvisos.get(position).getNombre());
        intent.putExtra("apellido",mAvisos.get(position).getApellido());
        intent.putExtra("descripcion",mAvisos.get(position).getDescripcion());
        intent.putExtra("telefono",mAvisos.get(position).getTelefono());
        intent.putExtra("posicion",position);
        startActivity(intent);
        /*Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(mAvisos.get(position).getTelefono().toString()));
        if (ActivityCompat.checkSelfPermission(VerAviso.this, Manifest.permission.CALL_PHONE) !=
        PackageManager.PERMISSION_GRANTED)
        return;
        startActivity(i);*/
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
