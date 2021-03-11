package com.cens.backend.censbackend.dto;

import java.io.Serializable;
import java.util.Set;

import com.cens.backend.censbackend.entities.Pregunta;

public class EncuestaDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7440341529583958866L;
	private Integer id;
	private String nombre;
	private String descripcion;
	private Set<Pregunta> preguntas;
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
	public Set<Pregunta> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(Set<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}
	

}
