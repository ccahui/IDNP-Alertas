package com.example.proyecto.Model;

import android.graphics.drawable.Drawable;

public class Alerta {

    private String tipo;
    private String nombres;
    private String apellidos;
    private String ubicacion;
    private Drawable imagen_tipo;

    public Alerta(String tipo, String nombres, String apellidos, String ubicacion, Drawable imagen_tipo ) {
        this.tipo = tipo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.ubicacion = ubicacion;
        this.imagen_tipo = imagen_tipo;

    }

    public Drawable getImagen_tipo() {
        return imagen_tipo;
    }

    public void setImagen_tipo(Drawable imagen_tipo) {
        this.imagen_tipo = imagen_tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
