package com.example.proyecto.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.proyecto.Model.Aviso;
import com.example.proyecto.R;

public class VerMas_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mas_);
        Aviso aviso = (Aviso) getIntent().getSerializableExtra("Aviso");
        int posicion = getIntent().getIntExtra("Posicion",0);
        System.out.println("Activity_Ver_Mas");
        System.out.println(aviso);
        System.out.println(posicion);

    }
}
