package com.camel.back2home.model.base;

import com.camel.back2home.model.Entity;

/**
 * Created by hp on 14/10/2016.
 */
public class PosterReceiver extends Entity {

    private String imagen;
    private String mascota_nombre;
    private String mascota_genero;

    private String mascota_tamano;

    private String mascota_color;

    private Integer mascota_estado;

    private String tipoMascota_nombre;

    private String raza_nombre;

    private String usuario_nombre;

    private String usuario_id_facebook;

    private Integer pkposter;

    private Integer fkusuario;

    private Integer fkmascota;

    private Integer fktipoPoster;

    private String latitud;

    private String longitud;

    private String recompensa;
    private String tipoMoneda;

    private String descripcion;

    private String fecha;

    private String hora;

    private String telefono;

    public PosterReceiver() {

    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getMascota_nombre() {
        return mascota_nombre;
    }

    public void setMascota_nombre(String mascota_nombre) {
        this.mascota_nombre = mascota_nombre;
    }

    public String getMascota_genero() {
        return mascota_genero;
    }

    public void setMascota_genero(String mascota_genero) {
        this.mascota_genero = mascota_genero;
    }

    public String getMascota_tamano() {
        return mascota_tamano;
    }

    public void setMascota_tamano(String mascota_tamano) {
        this.mascota_tamano = mascota_tamano;
    }

    public String getMascota_color() {
        return mascota_color;
    }

    public void setMascota_color(String mascota_color) {
        this.mascota_color = mascota_color;
    }

    public Integer getMascota_estado() {
        return mascota_estado;
    }

    public void setMascota_estado(Integer mascota_estado) {
        this.mascota_estado = mascota_estado;
    }

    public String getTipoMascota_nombre() {
        return tipoMascota_nombre;
    }

    public void setTipoMascota_nombre(String tipoMascota_nombre) {
        this.tipoMascota_nombre = tipoMascota_nombre;
    }

    public String getRaza_nombre() {
        return raza_nombre;
    }

    public void setRaza_nombre(String raza_nombre) {
        this.raza_nombre = raza_nombre;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    public String getUsuario_id_facebook() {
        return usuario_id_facebook;
    }

    public void setUsuario_id_facebook(String usuario_id_facebook) {
        this.usuario_id_facebook = usuario_id_facebook;
    }

    public Integer getPkposter() {
        return pkposter;
    }

    public void setPkposter(Integer pkposter) {
        this.pkposter = pkposter;
    }

    public Integer getFkusuario() {
        return fkusuario;
    }

    public void setFkusuario(Integer fkusuario) {
        this.fkusuario = fkusuario;
    }

    public Integer getFkmascota() {
        return fkmascota;
    }

    public void setFkmascota(Integer fkmascota) {
        this.fkmascota = fkmascota;
    }

    public Integer getFktipoPoster() {
        return fktipoPoster;
    }

    public void setFktipoPoster(Integer fktipoPoster) {
        this.fktipoPoster = fktipoPoster;
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

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
