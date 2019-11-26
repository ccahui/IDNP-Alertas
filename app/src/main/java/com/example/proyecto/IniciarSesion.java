package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class IniciarSesion extends AppCompatActivity {

    private EditText email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);


        this.setTitle(R.string.title_login);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);

        cargarUnUsuarioDePrueba();

        TextView registrarse = (TextView) findViewById(R.id.registrar);
        Button login = (Button)findViewById(R.id.button_login);



        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(IniciarSesion.this, Registrarse.class);
                startActivityForResult(intent, 200);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getEmail();
                String password = getPassword();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            toastShow("LOGIN EXITO");
                            startActivity(new Intent(IniciarSesion.this,  MainActivity.class));
                            finish();

                        } else {
                            toastShow("Usuario o Password Incorrecto");
                        }
                    }
                });

            }
        });


    }
    public String getEmail(){
        return email.getText().toString();
    }
    public String getPassword(){
        return password.getText().toString();
    }
    public void toastShow(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void setEmail(String _email){
        email.setText(_email);
    }
    public void setPassword(String _password){
        password.setText(_password);
    }

    public void cargarUnUsuarioDePrueba(){
        setEmail("test@example.com");
        setPassword("12345678");
    }

}
