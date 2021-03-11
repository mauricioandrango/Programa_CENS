/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cens.censfrontadmin.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.json.JSONObject;

/**
 *
 * @author apaez
 */
@Entity
public class PreguntaDTO implements Serializable{

    @Id
    private Long id;
    private String tipo;
    private String  titulo;
    private String opciones;
    private Integer encuesta_id;

    public PreguntaDTO() {
    }
    
       
    public PreguntaDTO(String jsonString) {
        JSONObject preguntaJson = new JSONObject(jsonString);
        this.id = preguntaJson.getLong("id");
        this.tipo = preguntaJson.getString("tipo");
        this.titulo = preguntaJson.getString("titulo");
        if(this.tipo=="OPCIONES"){
        this.opciones = preguntaJson.getString("opciones");
        }
        this.encuesta_id  = preguntaJson.getJSONObject("encuesta").getInt("id");
    }

    public PreguntaDTO(Long id, String tipo, String titulo, String opciones, Integer encuesta_id) {
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
        this.opciones = opciones;
        this.encuesta_id = encuesta_id;
    }
    
    
	
    public String getTipo() {
	return tipo;
    }
    public void setTipo(String tipo) {
	this.tipo = tipo;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
	this.titulo = titulo;
    }
    public String getOpciones() {
	return opciones;
    }
    public void setOpciones(String opciones) {
	this.opciones = opciones;
    }
    public Integer getEncuesta_id() {
	return encuesta_id;
    }
    public void setEncuesta_id(Integer encuesta_id) {
	this.encuesta_id = encuesta_id;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
