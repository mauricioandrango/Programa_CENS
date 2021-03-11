package com.movil.cens.app.data.respuestas;

import java.io.Serializable;

public class RespuestaDTO implements Serializable {
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
