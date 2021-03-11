package com.cens.backend.censbackend.dto.respuesta;

import java.io.Serializable;

public class RespuestaRequestDTO implements Serializable{

	private static final long serialVersionUID = 756419461196532217L;
	
	
	private Integer cabeceraId;
	
	private Integer preguntaId;
	
	private String respuesta;
	
	private Integer id;

	public Integer getCabeceraId() {
		return cabeceraId;
	}

	public void setCabeceraId(Integer cabeceraId) {
		this.cabeceraId = cabeceraId;
	}

	public Integer getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(Integer preguntaId) {
		this.preguntaId = preguntaId;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

	
}
