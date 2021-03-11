package com.movil.cens.app.data.usuario;

import com.movil.cens.app.data.encuesta.Encuesta;

import java.io.Serializable;
import java.util.List;

public class LoginResponseDTO implements Serializable {

	private String code;
	private String message;
	private UsuarioDTO data;



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

	public UsuarioDTO getData() {
		return data;
	}

	public void setData(UsuarioDTO data) {
		this.data = data;
	}
}
