package com.cens.backend.censbackend.dto.respuesta.enviarrespuestas;

import java.io.Serializable;

public class RespuestaDTO implements Serializable {

	private static final long serialVersionUID = -2996467242213141802L;
	private Integer preguntaId;
	private String respuesta;
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
	
}
