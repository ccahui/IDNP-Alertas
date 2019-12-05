package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.proyecto.Activities.GenerarAlerta;
import com.example.proyecto.Activities.PublicarAviso;
import com.example.proyecto.Interfaces.IComunicaFragments;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements IComunicaFragments  {


    private FirebaseAuth mAuth;
    LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                Intent intent = new Intent(this, IniciarSesion.class);
                startActivity(intent);
                return (true);

            case R.id.logout:
                mAuth.signOut();
               startActivity(new Intent(this, MainActivity.class));
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }


    @Override
    public void robo() {
        Intent intent = new Intent(this, GenerarAlerta.class);
        startActivity(intent);
    }

    @Override
    public void vandalismo() {
        Intent intent = new Intent(this, GenerarAlerta.class);
        startActivity(intent);
    }

    @Override
    public void pelea() {
        Intent intent = new Intent(this, GenerarAlerta.class);
        startActivity(intent);
    }

    @Override
    public void acoso() {
        Intent intent = new Intent(this, GenerarAlerta.class);
        startActivity(intent);
    }

    @Override
    public void desaparecidos() {
        Intent intent = new Intent(this, PublicarAviso.class);
        startActivity(intent);
    }

    @Override
    public void emergencia() {
        Intent intent = new Intent(this, Emergencia.class);
        startActivity(intent);
    }
}
