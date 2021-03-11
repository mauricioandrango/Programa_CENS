package com.movil.cens.app.data.respuestas;

import java.io.Serializable;
import java.util.List;

public class EnviarRespuestaRequestDTO implements Serializable {
    private static final long serialVersionUID = 7497465040153017953L;

    private Integer encuestaId;

    private Integer usuarioId;

    private List<RespuestaDTO> respuestas;

    public Integer getEncuestaId() {
        return encuestaId;
    }
    public void setEncuestaId(Integer encuestaId) {
        this.encuestaId = encuestaId;
    }
    public List<RespuestaDTO> getRespuestas() {
        return respuestas;
    }
    public void setRespuestas(List<RespuestaDTO> respuestas) {
        this.respuestas = respuestas;
    }
    public Integer getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

}
