package com.example.proyecto.ui.seBusca;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto.IniciarSesion;
import com.example.proyecto.R;
import com.example.proyecto.Registrarse;

public class SeBuscaFragment extends Fragment {

    private TextView registrarse;
    private SeBuscaViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(SeBuscaViewModel.class);

        View root = inflater.inflate(R.layout.fragment_se_busca, container, false);

        TextView registrarse = (TextView) root.findViewById(R.id.registrar_fragment);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), Registrarse.class);
                startActivityForResult(intent, 200);
            }
        });
        return root;
    }
}