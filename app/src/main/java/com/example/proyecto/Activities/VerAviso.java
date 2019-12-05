package com.example.proyecto.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.AlertasAdapter;
import com.example.proyecto.AvisosAdapter;
import com.example.proyecto.Model.Aviso;
import com.example.proyecto.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VerAviso extends AppCompatActivity {

    RecyclerView rv;
    TextView textView_nombre_aviso;
    TextView textView_apellido_aviso;
    TextView textView_descripcion_aviso;
    ArrayList<Aviso> avisos = new ArrayList<>();
    AvisosAdapter adapter;

    private DatabaseReference mRef;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_aviso);
        rv = findViewById(R.id.recycler_view_ver_avisos);






    }


}
