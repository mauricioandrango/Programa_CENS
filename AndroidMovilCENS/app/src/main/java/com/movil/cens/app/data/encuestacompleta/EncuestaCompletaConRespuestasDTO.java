package com.movil.cens.app.data.encuestacompleta;

import com.movil.cens.app.data.respuestas.RespuestaDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/*
*  {
            "id": 1000,
            "nombre": "encuesta1",
            "descripcion": "encuesta1"
        }
* */
public class EncuestaCompletaConRespuestasDTO implements Serializable {
    private Integer id;
    private String nombre;
    private String descripcion;
    private List<PreguntaEncuestaViewDTO> preguntas;
    private List<RespuestaDTO> respuestas;


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

    public List<PreguntaEncuestaViewDTO> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaEncuestaViewDTO> preguntas) {
        this.preguntas = preguntas;
    }

    public List<RespuestaDTO> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaDTO> respuestas) {
        this.respuestas = respuestas;
    }

    @Override
    public String toString() {
        return id + "\t"+nombre + "\n" + descripcion;
    }
}
