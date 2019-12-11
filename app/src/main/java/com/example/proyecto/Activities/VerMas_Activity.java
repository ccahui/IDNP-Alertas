package com.example.proyecto.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.proyecto.R;

public class VerMas_Activity extends AppCompatActivity {
    private TextView nombre;
    private TextView apellido;
    private TextView descripcion;
    private ImageView imageView;
    private Button btn_llamar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mas_);
        nombre = findViewById(R.id.textView_vermas_nombre);
        apellido = findViewById(R.id.textView_vermas_apellido);
        descripcion = findViewById(R.id.textView_vermas_descripcion);
        imageView = findViewById(R.id.imageView_vermas);
        Bundle datos = this.getIntent().getExtras();
        int posicion = datos.getInt("posicion");
        String imagen = datos.getString("imagen");
        String nom = datos.getString("nombre");
        String ape = datos.getString("apellido");
        String desc = datos.getString("descripcion");
        final String tel = datos.getString("telefono");
        System.out.println("Activity_Ver_Mas");
        System.out.println(posicion);
        Glide.with(VerMas_Activity.this)
                .load(imagen)
                .into(imageView);
        nombre.setText(nom);
        apellido.setText(ape);
        descripcion.setText(desc);
        btn_llamar = findViewById(R.id.button_llamar);
        btn_llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(tel));
                if (ActivityCompat.checkSelfPermission(VerMas_Activity.this, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED)
                    return;
                startActivity(i);
            }
        });


    }
}
