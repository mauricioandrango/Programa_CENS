package com.cens.backend.censbackend.dto;

import java.io.Serializable;

import com.cens.backend.censbackend.entities.Encuesta;

public class PreguntaDTO implements Serializable {

	private static final long serialVersionUID = -1943252894118085980L;


	private Integer id;

	private String tipo;

	private String titulo;

	private String opciones;
	
	private Integer encuesta_id;

	//Relacion a la encuesta que la contiene

	private Encuesta encuesta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Encuesta getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(Encuesta encuesta) {
		this.encuesta = encuesta;
	}

	public Integer getEncuesta_id() {
		return encuesta_id;
	}

	public void setEncuesta_id(Integer encuesta_id) {
		this.encuesta_id = encuesta_id;
	}
	
	
	

}
