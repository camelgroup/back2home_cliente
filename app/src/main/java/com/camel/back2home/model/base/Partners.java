package com.camel.back2home.model.base;


import com.camel.back2home.model.Entity;

/**
 * Created by hp on 14/10/2016.
 */
public class Partners extends Entity {

    private Integer pkafiliado;
    private String nombre;
    private String direccion;
    private String telefono;
    private String urlImagen;
    private String descripcion;
    private String latitud;
    private String longitud;
    private Integer fkusuario;
    private Integer plan;

    public Partners() {

    }

    public Integer getPkafiliado() {
        return pkafiliado;
    }

    public void setPkafiliado(Integer pkafiliado) {
        this.pkafiliado = pkafiliado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Integer getFkusuario() {
        return fkusuario;
    }

    public void setFkusuario(Integer fkusuario) {
        this.fkusuario = fkusuario;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }
}
