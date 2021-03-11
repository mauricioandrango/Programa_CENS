package com.cens.backend.censbackend.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pregunta")
public class Pregunta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7069475279983592127L;

	@Id
	@GeneratedValue(generator = "pregunta_generator")
	@SequenceGenerator(name = "pregunta_generator", sequenceName = "pregunta_generator", initialValue = 1)
	private Integer id;

	@Column
	private String tipo;

	@Column
	private String titulo;

	@Column
	private String opciones;

	//Relacion a la encuesta que la contiene
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "encuesta_id", nullable = false)
	//@JsonIgnore /*Sirve para que los response no tengan anidacion infinita */
	private Encuesta encuesta;
	
	//Relacion a las preguntas que tiene la encuesta
	@OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore 
	private Set<Respuesta> respuestas;
	
	

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

	public Set<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(Set<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

}
