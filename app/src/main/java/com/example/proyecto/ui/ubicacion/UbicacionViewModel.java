package com.example.proyecto.ui.ubicacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UbicacionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UbicacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Ubicación fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}