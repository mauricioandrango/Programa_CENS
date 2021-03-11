/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cens.censfrontadmin.responseDTO;

import com.cens.censfrontadmin.dto.UsuarioDTO;
import java.io.Serializable;

/**
 *
 * @author apaez
 */
public class LoginResponseDTO implements Serializable{
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
