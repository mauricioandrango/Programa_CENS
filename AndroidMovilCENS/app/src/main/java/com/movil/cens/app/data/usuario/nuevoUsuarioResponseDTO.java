package com.movil.cens.app.data.usuario;

import java.io.Serializable;

public class nuevoUsuarioResponseDTO implements Serializable {

    private String code;
    private String message;
    private String data;



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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
