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

import com.example.proyecto.MainActivity;
import com.example.proyecto.Model.Aviso;
import com.example.proyecto.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PublicarAviso extends AppCompatActivity {
    private static final int RC_GET_IMG = 0;
    private static final int RC_ACCESS = 1;
    private ArrayList<Aviso> avisos;
    ImageView img;
    private Uri fileURI;
    private EditText nombre;
    private EditText apellido;
    private EditText descripcion;
    private EditText telefono;
    private Button btnaviso;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_aviso);
        nombre = findViewById(R.id.editTextNombres);
        apellido = findViewById(R.id.editTextApellidos);
        descripcion = findViewById(R.id.editTextDescripcion);
        telefono = findViewById(R.id.editTextTelefono);
        img = findViewById(R.id.pd_imagen);
        avisos = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference();
        inicializarFirebase();
        btnaviso = findViewById(R.id.buttonaviso);
        btnaviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().equals("") || apellido.getText().toString().equals("") || descripcion.getText().toString().equals("") || telefono.getText().toString().equals(""))
                    validacion();
                else {
                    Map<String, String> map = new HashMap<>();
                    map.put("UID",UUID.randomUUID().toString());
                    map.put("Nombre",nombre.getText().toString());
                    map.put("Apellido",apellido.getText().toString());
                    map.put("Descripcion",descripcion.getText().toString());
                    map.put("Telefono", telefono.getText().toString());
                    map.put("Imagen",fileURI.getLastPathSegment());
                    StorageReference filePath = storageReference.child("fotos").child(fileURI.getLastPathSegment());
                    filePath.putFile(fileURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
                    databaseReference.child("Avisos").child(UUID.randomUUID().toString()).setValue(map);
                    limpiarcajas();
                    Toast.makeText(PublicarAviso.this,"Aviso Publicado",Toast.LENGTH_SHORT);
                    Intent intent = new Intent(PublicarAviso.this, MainActivity.class);
                    startActivity(intent);

                }


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
        String tel = telefono.getText().toString();
        if (nom.equals(""))
            nombre.setError("Required");
        if (ape.equals(""))
            apellido.setError("Required");
        if (des.equals(""))
            descripcion.setError("Required");
        if (tel.equals(""))
            telefono.setError("Required");
    }

    private void limpiarcajas() {
        nombre.setText("");
        apellido.setText("");
        descripcion.setText("");
        telefono.setText("");
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
                } catch (IOException e) {
                    e.printStackTrace();
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
