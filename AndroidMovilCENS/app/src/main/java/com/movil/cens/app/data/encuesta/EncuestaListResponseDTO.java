package com.movil.cens.app.data.encuesta;

import java.io.Serializable;
import java.util.List;

public class EncuestaListResponseDTO implements Serializable {

	private String code;
	private String message;
	private List<Encuesta> data;



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

	public List<Encuesta> getData() {
		return data;
	}

	public void setData(List<Encuesta> data) {
		this.data = data;
	}
}
