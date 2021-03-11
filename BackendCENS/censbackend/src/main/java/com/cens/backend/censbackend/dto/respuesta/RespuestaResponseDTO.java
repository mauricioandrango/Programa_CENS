package com.cens.backend.censbackend.dto.respuesta;

import java.io.Serializable;

public class RespuestaResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6240350492899365906L;
	
	private Integer id;
	
	private String encuesta;

	private String respuesta;

	private String pregunta;

	public RespuestaResponseDTO(Integer id, String encuesta, String respuesta, String pregunta) {
		super();
		this.id=id;
		this.encuesta = encuesta;
		this.respuesta = respuesta;
		this.pregunta = pregunta;
	}
	
	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(String encuesta) {
		this.encuesta = encuesta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

}
