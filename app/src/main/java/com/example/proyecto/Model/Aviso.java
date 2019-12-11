package com.example.proyecto.Model;

public class Aviso {
    private String uid;
    private String nombre;
    private String apellido;
    private String descripcion;
    private String telefono;
    private String nombre_imagen;
    private String mImageUrl;

    public Aviso() {
    }

    public Aviso(String mImageUrl, String nombre, String apellido, String descripcion, String telefono) {
        this.mImageUrl = mImageUrl;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcion = descripcion;
        this.telefono = telefono;

    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre_imagen() {
        return nombre_imagen;
    }

    public void setNombre_imagen(String nombre_imagen) {
        this.nombre_imagen = nombre_imagen;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
