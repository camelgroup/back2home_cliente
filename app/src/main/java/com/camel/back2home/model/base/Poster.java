package com.camel.back2home.model.base;


import com.camel.back2home.model.Entity;

/**
 * Created by hp on 9/10/2016.
 */
public class Poster extends Entity {
    private Integer pkusuario;
    private String nombre;
    private String tamano;
    private String color;
    private String genero;
    private Integer pktipo_mascota;
    private Integer pkraza;
    private Integer pktipo_poster;
    private String latitud;
    private String longitud;
    private String recompensa;
    private String tipo_moneda;
    private String descripcion;
    private String imagen;
    private Integer estado;
    private String urlImagen;
    private String tipo_mascota;
    private String raza;
    private String streetName;
    private String referenceNumber;

    public Poster() {

    }

    public Poster(String nombre, String descripcion, String urlImagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
    }

    public Integer getPkusuario() {
        return pkusuario;
    }

    public void setPkusuario(Integer pkusuario) {
        this.pkusuario = pkusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPktipo_mascota() {
        return pktipo_mascota;
    }

    public void setPktipo_mascota(Integer pktipo_mascota) {
        this.pktipo_mascota = pktipo_mascota;
    }

    public Integer getPkraza() {
        return pkraza;
    }

    public void setPkraza(Integer pkraza) {
        this.pkraza = pkraza;
    }

    public Integer getPktipo_poster() {
        return pktipo_poster;
    }

    public void setPktipo_poster(Integer pktipo_poster) {
        this.pktipo_poster = pktipo_poster;
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

    public String getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(String recompensa) {
        this.recompensa = recompensa;
    }

    public String getTipo_moneda() {
        return tipo_moneda;
    }

    public void setTipo_moneda(String tipo_moneda) {
        this.tipo_moneda = tipo_moneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getTipo_mascota() {
        return tipo_mascota;
    }

    public void setTipo_mascota(String tipo_mascota) {
        this.tipo_mascota = tipo_mascota;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
