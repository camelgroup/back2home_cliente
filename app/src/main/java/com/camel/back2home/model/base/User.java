package com.camel.back2home.model.base;

import com.camel.back2home.model.Entity;
import com.google.gson.annotations.Expose;


/**
 * Created by hp on 5/10/2016.
 */
public class User extends Entity {


    @Expose
    private long pkusuario;
    @Expose
    private String nombre;
    @Expose
    private String idFirebase;
    @Expose
    private String email;
    @Expose
    private String nroTelefono;
    @Expose
    private String idFacebook;
    @Expose
    private String password;
    @Expose
    private String idGoogle;

    private transient String photoUrl;


    public User() {
    }

    public long getPkusuario() {
        return pkusuario;
    }

    public void setPkusuario(long pkusuario) {
        this.pkusuario = pkusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNroTelefono() {
        return nroTelefono;
    }

    public void setNroTelefono(String nroTelefono) {
        this.nroTelefono = nroTelefono;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
