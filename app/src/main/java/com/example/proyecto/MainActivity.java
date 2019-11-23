package com.example.proyecto;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.proyecto.Activities.GenerarAlerta;
import com.example.proyecto.Activities.PublicarAviso;
import com.example.proyecto.Activities.VerAviso;
import com.example.proyecto.Interfaces.IComunicaFragments;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements IComunicaFragments {

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
                Intent intent2 = new Intent(this, MapsActivity.class);
                startActivity(intent2);
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }


    @Override
    public void robo() {
        Intent intent = new Intent(this, GenerarAlerta.class);
        startActivity(intent);
        /*final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.alerta_dialog, null);
        builder.setView(v);
        final AlertDialog alert = builder.create();
        alert.show();
        Button signin = (Button) v.findViewById(R.id.buttonAlerta);
        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "ALERTA ENVIADA", Toast.LENGTH_SHORT).show();
                        alert.dismiss();


                    }
                }

        );
*/
    }

    @Override
    public void vandalismo() {
        Intent intent = new Intent(this, GenerarAlerta.class);
        startActivity(intent);
        /*final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.alerta_dialog, null);
        builder.setView(v);
        final AlertDialog alert = builder.create();
        alert.show();
        Button signin = (Button) v.findViewById(R.id.buttonAlerta);
        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "ALERTA ENVIADA", Toast.LENGTH_SHORT).show();
                        alert.dismiss();


                    }
                }

        );*/
    }

    @Override
    public void pelea() {
        Intent intent = new Intent(this, GenerarAlerta.class);
        startActivity(intent);
        /*final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.alerta_dialog, null);
        builder.setView(v);
        final AlertDialog alert = builder.create();
        alert.show();
        Button signin = (Button) v.findViewById(R.id.buttonAlerta);
        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "ALERTA ENVIADA", Toast.LENGTH_SHORT).show();
                        alert.dismiss();


                    }
                }

        );*/
    }

    @Override
    public void acoso() {
        Intent intent = new Intent(this, GenerarAlerta.class);
        startActivity(intent);
        /*final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.alerta_dialog, null);
        builder.setView(v);
        final AlertDialog alert = builder.create();
        alert.show();
        Button signin = (Button) v.findViewById(R.id.buttonAlerta);
        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "ALERTA ENVIADA", Toast.LENGTH_SHORT).show();
                        alert.dismiss();


                    }
                }

        );*/
    }

    @Override
    public void ver_aviso() {
        ///Toast.makeText(MainActivity.this, "VER_AVISO", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, VerAviso.class);
        startActivityForResult(intent, 1);

    }

    @Override
    public void publicar_aviso() {
        //Toast.makeText(MainActivity.this, "PUBLICAR_AVISO", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, PublicarAviso.class);
        startActivityForResult(intent, 2);
    }


}
