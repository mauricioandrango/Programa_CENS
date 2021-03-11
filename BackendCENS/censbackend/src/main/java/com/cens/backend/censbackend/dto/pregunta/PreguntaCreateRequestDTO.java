package com.cens.backend.censbackend.dto.pregunta;

import java.io.Serializable;

/*
 * Clase para mapear DTOs de tipo Pregunta provenientes de los servicios rest
 * 
 * */
public class PreguntaCreateRequestDTO implements Serializable {

	private static final long serialVersionUID = -6605345659464702714L;
	
	private int id;
	private String tipo;
	private String  titulo;
	private String opciones;
	private int encuesta_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getEncuesta_id() {
		return encuesta_id;
	}
	public void setEncuesta_id(int encuesta_id) {
		this.encuesta_id = encuesta_id;
	}

}
