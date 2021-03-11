package com.cens.backend.censbackend.dto;

import java.io.Serializable;

public class EstadisticasEncuestaDTO  implements Serializable {

	private static final long serialVersionUID = 2415336925455211424L;
	
	private String pregunta;
	private Integer numeroRespuestas;
	public String getPregunta() {
		return pregunta;
	}
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	public Integer getNumeroRespuestas() {
		return numeroRespuestas;
	}
	public void setNumeroRespuestas(Integer numeroRespuestas) {
		this.numeroRespuestas = numeroRespuestas;
	}
	

}
