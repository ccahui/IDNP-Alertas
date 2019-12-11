package com.example.proyecto.Activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyecto.Model.Aviso;
import com.example.proyecto.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PublicarAviso extends AppCompatActivity {
    private static final int RC_GET_IMG = 0;
    private static final int RC_ACCESS = 1;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ArrayList<Aviso> avisos;
    private ImageView img;
    private Uri mImageUri;
    private EditText nombre;
    private EditText apellido;
    private EditText descripcion;
    private EditText telefono;
    private EditText mEditTextFileName;
    private Button btnaviso, subir_imagen;
    private StorageReference mStorageref;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private StorageTask storageTask;
    private TextView mTextViewShowUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_aviso);
        nombre = findViewById(R.id.editTextNombres);
        apellido = findViewById(R.id.editTextApellidos);
        descripcion = findViewById(R.id.editTextDescripcion);
        telefono = findViewById(R.id.editTextTelefono);
        img = findViewById(R.id.pd_imagen);
        btnaviso = findViewById(R.id.buttonaviso);
        subir_imagen = findViewById(R.id.subir_imagen);
        progressBar = findViewById(R.id.progress_bar);
        avisos = new ArrayList<>();

        mStorageref = FirebaseStorage.getInstance().getReference("fotos_avisos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("fotos_avisos");

        subir_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        btnaviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storageTask != null && storageTask.isInProgress()) {
                    Toast.makeText(PublicarAviso.this, "Upload in Progress", Toast.LENGTH_SHORT);
                } else {
                    uploadFile();
                }
            }
        });

        /*storageReference = FirebaseStorage.getInstance().getReference();
        inicializarFirebase();

        progressDialog = new ProgressDialog(PublicarAviso.this);
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
                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setTitle("Publicando Aviso");
                            progressDialog.show();
                        }
                    });
                    databaseReference.child("Avisos").child(UUID.randomUUID().toString()).setValue(map);
                    limpiarcajas();
                    Intent intent = new Intent(PublicarAviso.this, MainActivity.class);
                    startActivity(intent);


                }


            }
        });*/
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

           Glide.with(this).load(mImageUri).into(img);
            //Picasso.get().load(mImageUri).into(img);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (nombre.getText().toString().equals("") || apellido.getText().toString().equals("") || descripcion.getText().toString().equals("") || telefono.getText().toString().equals(""))
            validacion();
        else {
            final String nom = nombre.getText().toString();
            final String ape = apellido.getText().toString();
            final String des = descripcion.getText().toString();
            final String tel = telefono.getText().toString();

            if (mImageUri != null) {

                StorageReference fileReference = mStorageref.child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));
                mStorageref.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }

                        return mStorageref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Uri uri = task.getResult();
                            Log.e("URI", uri.toString()); //url para descargar foto
                            GuardarBaseDatos(uri);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PublicarAviso.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                /*
                storageTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(0);
                                    }
                                }, 5000);
                                Toast.makeText(PublicarAviso.this, "Upload successful", Toast.LENGTH_LONG).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PublicarAviso.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressBar.setProgress((int) progress);
                            }
                        });
                */
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }
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
    private void GuardarBaseDatos(Uri uri){
        Aviso avisos = new Aviso(uri.toString(),nombre.getText().toString(),apellido.getText().toString(), descripcion.getText().toString(),telefono.getText().toString());
        String uploadId = mDatabaseRef.push().getKey();
        limpiarcajas();
        mDatabaseRef.child(uploadId).setValue(avisos);
    }

    /*private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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
    }*/
}
