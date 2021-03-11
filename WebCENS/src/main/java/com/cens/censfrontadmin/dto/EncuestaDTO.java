/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cens.censfrontadmin.dto;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.json.JSONObject;

/**
 *
 * @author apaez
 */
@Entity
public class EncuestaDTO implements Serializable{
    
    private static final long serialVersionUID = 7440341529583958866L;

    @Id
    private Long id;
    
    private String nombre;
    private String descripcion;
    private Set<PreguntaDTO> preguntas;

    public EncuestaDTO() {
    }

    public EncuestaDTO(Long id, String nombre, String descripcion, Set<PreguntaDTO> preguntas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preguntas = preguntas;
    }
    
    public EncuestaDTO(String jsonString) {
        JSONObject userJson = new JSONObject(jsonString);
        this.id = userJson.getLong("id");
        this.nombre = userJson.getString("nombre");
        this.descripcion = userJson.getString("descripcion");
        //this.preguntas = userJson.getJSONArray("preguntas");
    }

    
    
    public void EncuestaDTO(){
    }
    
    
    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public Set<PreguntaDTO> getPreguntas() {
	return preguntas;
    }
    public void setPreguntas(Set<PreguntaDTO> preguntas) {
	this.preguntas = preguntas;
    }
}
