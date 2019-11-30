package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IniciarSesion extends AppCompatActivity implements View.OnClickListener {

    private EditText email, password;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        this.setTitle(R.string.title_login);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);

        cargarUnUsuarioDePrueba();

        TextView registrarse = (TextView) findViewById(R.id.registrar);
        Button login = (Button) findViewById(R.id.button_login);

        login.setOnClickListener(this);
        registrarse.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBarIniciarSesion);
        progressBar.setVisibility(View.GONE);
    }

    private boolean validacionDeDatos() {

        boolean valido = true;
        String email = getEmail();
        String password = getPassword();

        if ("".equals(email)) {
            this.email.setError("required");
            valido = false;
        } else if (!validarEmail(email)) {
            this.email.setError("email no valido");
            valido = false;
        }


        if ("".equals(password)) {
            this.password.setError("required");
            valido = false;
        } else if (password.length() < 6) {
            this.password.setError("password > 6");
            valido = false;
        }
        return valido;

    }

    public String getEmail() {
        return email.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
    }

    public void toastShow(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void setEmail(String _email) {
        email.setText(_email);
    }

    public void setPassword(String _password) {
        password.setText(_password);
    }

    public void cargarUnUsuarioDePrueba() {
        setEmail("test@example.com");
        setPassword("12345678");
    }

    public boolean validarEmail(String email) {

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
            case R.id.button_login:
                if (validacionDeDatos()) {
                    String email = getEmail();
                    String password = getPassword();
                    iniciarSesion(email, password);
                }
                break;

            case R.id.registrar:
                Intent intent = new Intent(IniciarSesion.this, Registrarse.class);
                startActivityForResult(intent, 200);

                break;
        }
    }

    public void iniciarSesion(String email, String password) {

    showProgressBar();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgresBar();
                if (task.isSuccessful()) {
                    toastShow("LOGIN EXITO");
                    startActivity(new Intent(IniciarSesion.this, MainActivity.class));
                    finish();

                } else {
                    toastShow("Usuario o Password Incorrecto");
                }

            }
        });
    }
    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgresBar(){
        progressBar.setVisibility(View.GONE);
    }

}
