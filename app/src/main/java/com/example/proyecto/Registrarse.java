package com.example.proyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Registrarse extends AppCompatActivity {

    private TextView ir_a_login;
    private EditText nombre, apellido, email, password;
    private Button registrarse;
    private ProgressBar spinner;

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

       registrarse = (Button)findViewById(R.id.btnRegistrarse);
       ir_a_login = (TextView)findViewById(R.id.ir_a_login);
       spinner = (ProgressBar)findViewById(R.id.progressBar);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        //Progres Bar
        spinnerHide();

        ir_a_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();//finishing activity
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventoRegistrar();
        }});
    }

    public void eventoRegistrar(){

            if(validar()) {
                Map<String, String> data = mapearData();

            String email = getEmail();
            String password = getPassword();

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
                                        if(task.isSuccessful()){
                                            toastShow("EXITO REGISTRADO");
                                        } else {
                                            toastShow("FRACASO REGISTRADO");
                                        }
                                    }
                                });
                                resetData();
                            } else {
                                toastShow("ERROR REGISTRARSE.");
                            }            }

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

    public void spinnerShow(){
        spinner.setVisibility(View.VISIBLE);
    }

    public void spinnerHide(){
        spinner.setVisibility(View.GONE);
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


}
