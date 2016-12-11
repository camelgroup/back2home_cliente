package com.camel.back2home.model.base;

import com.camel.back2home.model.Entity;

/**
 * Created by hp on 11/10/2016.
 */
public class Raza extends Entity {

    private Integer pkraza;
    private String nombre;
    private Integer fktipoMascota;

    public Raza() {

    }

    public Integer getPkraza() {
        return pkraza;
    }

    public void setPkraza(Integer pkraza) {
        this.pkraza = pkraza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFktipoMascota() {
        return fktipoMascota;
    }

    public void setFktipoMascota(Integer fktipoMascota) {
        this.fktipoMascota = fktipoMascota;
    }
}
