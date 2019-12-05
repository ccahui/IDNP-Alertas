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
import androidx.fragment.app.FragmentManager;

import com.example.proyecto.MainActivity;
import com.example.proyecto.Model.Aviso;
import com.example.proyecto.R;
import com.example.proyecto.ui.seBusca.SeBuscaFragment;
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
import java.util.HashMap;
import java.util.Map;
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
    private StorageReference storageReference;
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
        storageReference = FirebaseStorage.getInstance().getReference();
        inicializarFirebase();
        btnaviso = (Button) findViewById(R.id.buttonaviso);
        btnaviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().equals("") || apellido.getText().toString().equals("") || descripcion.getText().toString().equals(""))
                    validacion();
                else {
                    Map<String,String>map= new HashMap<String,String>();
                    map.put("UID",UUID.randomUUID().toString());
                    map.put("Nombre",nombre.getText().toString());
                    map.put("Apellido",apellido.getText().toString());
                    map.put("Descripcion",descripcion.getText().toString());
                    map.put("Imagen",fileURI.getLastPathSegment());
                    StorageReference filePath = storageReference.child("fotos").child(fileURI.getLastPathSegment());
                    filePath.putFile(fileURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
                    databaseReference.child("Avisos").child(UUID.randomUUID().toString()).setValue(map);
                    limpiarcajas();
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
