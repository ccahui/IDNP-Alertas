package com.example.proyecto.Registrarse.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.Login.View.IniciarSesion;
import com.example.proyecto.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrarse extends AppCompatActivity implements View.OnClickListener{

    private EditText nombre, apellido, email, password;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        this.setTitle(R.string.title_registrarse);


       nombre = (EditText) findViewById(R.id.inputNombres);
       apellido = (EditText)findViewById(R.id.inputApellidos);
       email = (EditText)findViewById(R.id.inputEmail);
       password = (EditText)findViewById(R.id.inputPassword);

       Button registrarse = (Button)findViewById(R.id.btnRegistrarse);
       TextView ir_a_login = (TextView)findViewById(R.id.ir_a_login);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        ir_a_login.setOnClickListener(this);
        registrarse.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBarRegistrarse);
    }

    public void eventoRegistrar(){

            if(validar()) {
                Map<String, String> data = mapearData();

            String email = getEmail();
            String password = getPassword();

            showProgressBar();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Map<String, String> data = mapearData();
                                String userId = mAuth.getCurrentUser().getUid();

                                myRef.child("usuarios").child(userId).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        hideProgresBar();
                                        if(task.isSuccessful()){
                                            toastShow("EXITO REGISTRADO");
                                            redirectToIniciarSesion();
                                        } else {
                                            toastShow("FRACASO REGISTRADO");
                                        }
                                    }
                                });
                                resetData();
                            } else {
                                hideProgresBar();
                                toastShow("ERROR REGISTRARSE.");
                            }

                        }

                    });
    }
    }

    public Map<String, String> mapearData(){
        Map<String, String> data = new HashMap<>();
        data.put("nombre", getNombre());
        data.put("apellido", getApellido());
        data.put("email", getEmail());
        data.put("password", getPassword());


        return data;
    }

    public boolean validar(){

        boolean valido = true;
        String email = getEmail();
        String password = getPassword();
        String nombre = getNombre();
        String apellido = getApellido();


        if("".equals(nombre)){
            this.nombre.setError("required");
            valido = false;
        }
        if("".equals(apellido)){
            this.apellido.setError("required");
            valido = false;
        }

        if ("".equals(email)){
            this.email.setError("required");
            valido  = false;
        } else if(!validarEmail(email)){
            this.email.setError("email no valido");
            valido = false;
        }

        if ("".equals(password)) {
            this.password.setError("Required");
            valido = false;
        } else if(password.length() < 6){
            this.password.setError("password > 6");
            valido = false;
        }

        return valido;
    }

    public void toastShow(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void resetData(){

        setNombre("");
        setApellido("");
        setEmail("");
        setPassword("");
    }

    public String getNombre(){
        return nombre.getText().toString();
    }
    public String getApellido(){
        return apellido.getText().toString();
    }
    public String getEmail(){
        return email.getText().toString();
    }
    public String getPassword(){
        return password.getText().toString();
    }

    public void setNombre(String _nombre){
        this.nombre.setText(_nombre);
    }
    public void setApellido(String _apellido){
        this.apellido.setText(_apellido);
    }
    public void setEmail(String _email){
        this.email.setText(_email);
    }
    public void setPassword(String _password){
        this.password.setText(_password);
    }



    public boolean validarEmail(String email){

        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);

        if (mather.find() == true) {
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegistrarse:
                eventoRegistrar();
                break;

            case R.id.ir_a_login:
                finish();//finishing activity
                break;
        }
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgresBar(){
        progressBar.setVisibility(View.GONE);
    }

    public void redirectToIniciarSesion(){
        startActivity(new Intent(Registrarse.this, IniciarSesion.class));
        finish();
    }
}
