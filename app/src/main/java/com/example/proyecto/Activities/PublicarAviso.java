package com.example.proyecto.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.proyecto.Model.Aviso;
import com.example.proyecto.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class PublicarAviso extends AppCompatActivity {
    private static final int RC_GET_IMG = 0;
    private static final int RC_ACCESS = 1;
    ImageView img;
    private Uri fileURI;
    private EditText nombre;
    private EditText apellido;
    private EditText descripcion;
    private Button btnaviso;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_aviso);
        nombre = (EditText) findViewById(R.id.editTextNombres);
        apellido = (EditText) findViewById(R.id.editTextApellidos);
        descripcion = (EditText) findViewById(R.id.editTextDescripcion);
        img = (ImageView) findViewById(R.id.pd_imagen);
        inicializarFirebase();
        btnaviso = (Button) findViewById(R.id.buttonaviso);
        btnaviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().equals("") || apellido.getText().toString().equals("") || descripcion.getText().toString().equals(""))
                    validacion();
                else {
                    Aviso aviso = new Aviso();
                    aviso.setUid(UUID.randomUUID().toString());
                    aviso.setNombre(nombre.getText().toString());
                    aviso.setApellido(apellido.getText().toString());
                    aviso.setDescripcion(descripcion.getText().toString());
                    aviso.setImagen(fileURI.getLastPathSegment());
                    databaseReference.child("Aviso").child(aviso.getUid()).setValue(aviso);

                    limpiarcajas();
                }
                Toast.makeText(PublicarAviso.this,"Aviso Publicado",Toast.LENGTH_SHORT);

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void validacion() {
        String nom = nombre.getText().toString();
        String ape = apellido.getText().toString();
        String des = descripcion.getText().toString();
        if (nom.equals(""))
            nombre.setError("Required");
        if (ape.equals(""))
            apellido.setError("Required");
        if (des.equals(""))
            descripcion.setError("Required");
    }

    private void limpiarcajas() {
        nombre.setText("");
        apellido.setText("");
        descripcion.setText("");
    }


    public void getImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Seleccione Imagen"), RC_GET_IMG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GET_IMG && resultCode == RESULT_OK) {
            fileURI = data.getData();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_ACCESS);
            else {
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(fileURI);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    img.setImageBitmap(bitmap);
                    is.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this,"Entro al catch FileNotFoundException", Toast.LENGTH_SHORT);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this,"Entro al catch IOException", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_ACCESS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(fileURI);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    img.setImageBitmap(bitmap);
                    is.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
