package com.example.proyecto.Registrarse.Model;

import com.example.proyecto.Registrarse.Presenter.ListenerRegistrarse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class ModeloRegistrarseImp implements ModelRegistrarse {

    private ListenerRegistrarse listenerRegistrarse;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    public ModeloRegistrarseImp(ListenerRegistrarse listenerRegistrarse) {
        this.listenerRegistrarse = listenerRegistrarse;
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void registrarUsuario(final String nombre, final String apellido, final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Map<String, String> data = parseMapUsuario(nombre, apellido, email, password);
                            String userId = mAuth.getCurrentUser().getUid();

                            myRef.child("usuarios").child(userId).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        listenerRegistrarse.onSuccess();
                                    } else {
                                        listenerRegistrarse.onError("ERROR REGISTRADO");
                                    }
                                }
                            });
                        } else {
                            listenerRegistrarse.onError("ERROR REGISTRARSE.");
                        }

                    }

                });
    }

    private Map<String, String> parseMapUsuario(String nombre, String apellido, String email, String password) {
        Map<String, String> data = new HashMap<>();
        data.put("nombre", nombre);
        data.put("apellido", apellido);
        data.put("email", email);
        data.put("password", password);

        return data;
    }
}
