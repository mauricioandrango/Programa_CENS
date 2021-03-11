package com.movil.cens.app.data.encuestacompleta;

import java.io.Serializable;

public class PreguntaEncuestaViewDTO implements Serializable {
    private PreguntaDTO preguntaDTO;
    private Integer idAndroidView;

    public PreguntaDTO getPreguntaDTO() {
        return preguntaDTO;
    }

    public void setPreguntaDTO(PreguntaDTO preguntaDTO) {
        this.preguntaDTO = preguntaDTO;
    }

    public Integer getIdAndroidView() {
        return idAndroidView;
    }

    public void setIdAndroidView(Integer idAndroidView) {
        this.idAndroidView = idAndroidView;
    }
}
