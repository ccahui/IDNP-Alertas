package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.proyecto.Interfaces.IComunicaFragments;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements IComunicaFragments {

    @Override
    public void robo() {
        Toast.makeText(getApplicationContext(),"Alerta Robo desde el activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void vandalismo() {
        Toast.makeText(getApplicationContext(),"Alerta Vandalismo desde el activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pelea() {
        Toast.makeText(getApplicationContext(),"Alerta Pelea desde el activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void acoso() {
        Toast.makeText(getApplicationContext(),"Alerta Acoso desde el activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.login:
            Intent intent = new Intent(this, IniciarSesion.class);
            startActivity(intent);
            return(true);
        case R.id.logout:

            Intent intent2 = new Intent(this, MapsActivity.class);
            startActivity(intent2);
            //finish();
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }



}
