package com.cens.backend.censbackend.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "respuesta")
public class Respuesta {
	@Id
    @GeneratedValue(generator = "respuesta_generator")
    @SequenceGenerator(
            name = "respuesta_generator",
            sequenceName = "respuesta_generator",
            initialValue = 1000
    )	
	private Integer id;
	
	
	//Relacion a la encuesta que la contiene
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pregunta_id", nullable = false)
	@JsonIgnore /*Sirve para que los response no tengan anidacion infinita */
	private Pregunta pregunta;
	
	
	//Relacion al usuario que repsondio
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	@JsonIgnore /*Sirve para que los response no tengan anidacion infinita */
	private Usuario usuario;
	
	
	private String respuesta;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Pregunta getPregunta() {
		return pregunta;
	}


	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}


	public String getRespuesta() {
		return respuesta;
	}


	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}




}
