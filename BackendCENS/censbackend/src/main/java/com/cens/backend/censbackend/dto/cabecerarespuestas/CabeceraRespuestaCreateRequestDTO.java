package com.cens.backend.censbackend.dto.cabecerarespuestas;

import java.io.Serializable;
import java.util.Date;

public class CabeceraRespuestaCreateRequestDTO implements Serializable {

	private static final long serialVersionUID = -394466911725948806L;
	private Integer id;
	private Integer encuestaId;
	private Integer usuarioId;
	private Date fechaRespuesta;
	
	
	public CabeceraRespuestaCreateRequestDTO() {
		super();
	}


	public CabeceraRespuestaCreateRequestDTO(Integer id, Integer encuestaId, Integer usuarioId, Date fechaRespuesta) {
		super();
		this.id = id;
		this.encuestaId = encuestaId;
		this.usuarioId = usuarioId;
		this.fechaRespuesta = fechaRespuesta;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}
	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}


	public Integer getEncuestaId() {
		return encuestaId;
	}


	public void setEncuestaId(Integer encuestaId) {
		this.encuestaId = encuestaId;
	}
	

}
