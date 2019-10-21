package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Registrarse extends AppCompatActivity {

    private TextView ir_a_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

       ir_a_login = (TextView)findViewById(R.id.ir_a_login);

        ir_a_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();//finishing activity
            }
        });
    }
}
