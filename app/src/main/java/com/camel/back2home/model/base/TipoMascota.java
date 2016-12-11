package com.camel.back2home.model.base;

import com.camel.back2home.model.Entity;

/**
 * Created by hp on 11/10/2016.
 */
public class TipoMascota extends Entity {

    private Integer pktipoMascota;
    private String nombre;

    public TipoMascota() {

    }

    public Integer getPktipoMascota() {
        return pktipoMascota;
    }

    public void setPktipoMascota(Integer pktipoMascota) {
        this.pktipoMascota = pktipoMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
