package com.movil.cens.app.data.encuestacompleta;

import com.movil.cens.app.data.encuesta.Encuesta;

import java.io.Serializable;
import java.util.List;

public class EncuestaCompletaResponseDTO implements Serializable {

	private String code;
	private String message;
	private EncuestaCompletaDTO data;



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EncuestaCompletaDTO getData() {
		return data;
	}

	public void setData(EncuestaCompletaDTO data) {
		this.data = data;
	}
}
