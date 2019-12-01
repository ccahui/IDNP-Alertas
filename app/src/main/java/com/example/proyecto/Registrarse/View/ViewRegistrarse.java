package com.example.proyecto.Registrarse.View;

public interface ViewRegistrarse {

    public void mostrarProgressBar();
    public void ocultarProgressBar();
    public void setEmailError(String error);
    public void setPassworError(String error);
    public void setNombreError(String error);
    public void setApellidoError(String error);
    public void onErrorRegistrarse(String error);
    public void redirecToLogin();

}
