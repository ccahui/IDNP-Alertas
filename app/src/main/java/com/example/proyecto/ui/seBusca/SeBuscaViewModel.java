package com.example.proyecto.ui.seBusca;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SeBuscaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SeBuscaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Buscar Persona Desaparecida fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}