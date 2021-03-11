package com.movil.cens.app.data.encuesta;

import java.io.Serializable;
/*
*  {
            "id": 1000,
            "nombre": "encuesta1",
            "descripcion": "encuesta1"
        }
* */
public class Encuesta implements Serializable {
    private Integer id;
    private String nombre;
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return id + "\t"+nombre + "\n" + descripcion;
    }
}
