package com.example.proyecto.ui.alertas;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto.Interfaces.IComunicaFragments;
import com.example.proyecto.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AlertasFragment extends Fragment {

    private AlertasViewModel alertasViewModel;
    Activity actividad;
    CardView robo, vandalismo, pelea, acoso, desaparecidos, emergencia;
    IComunicaFragments interfaceComunicaFragments;


    private FirebaseAuth mAuth;
    private TextView nombreDePerfil;
    private DatabaseReference myRef;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        alertasViewModel = ViewModelProviders.of(this).get(AlertasViewModel.class);


        View vista = inflater.inflate(R.layout.alertas, container, false);
        robo = vista.findViewById(R.id.robo);
        robo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.robo();
            }
        });
        //return vista;
        vandalismo = vista.findViewById(R.id.vandalismo);
        vandalismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.vandalismo();
            }
        });
        //return vista;
        pelea = vista.findViewById(R.id.pelea);
        pelea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.pelea();
            }
        });
        //return vista;
        acoso = vista.findViewById(R.id.acoso);
        acoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.acoso();
            }
        });

        desaparecidos = vista.findViewById(R.id.desaparecido);
        desaparecidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.desaparecidos();
            }
        });

        emergencia = vista.findViewById(R.id.prueba);
        emergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.emergencia();
            }
        });



        mAuth = FirebaseAuth.getInstance();
        myRef =  FirebaseDatabase.getInstance().getReference();

        nombreDePerfil = (TextView)vista.findViewById(R.id.textViewNombrePerfil);

        getUserInfo();
        return vista;
    }

    public void getUserInfo(){


        if(usuarioLogeado()){
            String id = mAuth.getCurrentUser().getUid();
            myRef.child("usuarios").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String nombre = dataSnapshot.child("nombre").getValue().toString();
                        String apellido = dataSnapshot.child("apellido").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();

                        setNombreDePerfil(nombre + " " +apellido);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



    }
    public boolean usuarioLogeado(){
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            actividad = (Activity) context;
            interfaceComunicaFragments = (IComunicaFragments) actividad;
        }
    }

    public void setNombreDePerfil(String _nombreDePerfil){
        nombreDePerfil.setText(_nombreDePerfil);
    }

    public void toastShow(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

}