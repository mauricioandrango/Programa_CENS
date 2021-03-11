/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cens.censfrontadmin.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.json.JSONObject;

/**
 *
 * @author apaez
 */
@Entity
public class RespuestaDTO implements Serializable{

    @Id
    private Integer id;
    
    
    private String encuestaId;
	
    private String preguntaId;
	
    private String respuesta;

    public RespuestaDTO() {
    }
    public RespuestaDTO(String jsonString) {
        JSONObject userJson = new JSONObject(jsonString);    
        this.id = userJson.getInt("id");
        this.encuestaId = userJson.getString("encuesta");
        
        this.preguntaId = userJson.getString("pregunta");
        //this.encuestaId = userJson.getInt("encuestaId");
        //this.preguntaId = userJson.getInt("preguntaId");
        this.respuesta = userJson.getString("respuesta");
        //this.preguntas = userJson.getJSONArray("preguntas");
    }
    
    public RespuestaDTO(Integer id, String cabeceraId, String preguntaId, String respuesta) {
        this.id = id;
        this.encuestaId = cabeceraId;
        this.preguntaId = preguntaId;
        this.respuesta = respuesta;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEncuestaId() {
        return encuestaId;
    }

    public void setEncuestaId(String encuestaId) {
        this.encuestaId = encuestaId;
    }
    public String getPreguntaId() {
	return preguntaId;
    }

    public void setPreguntaId(String preguntaId) {
	this.preguntaId = preguntaId;
    }

    public String getRespuesta() {
	return respuesta;
    }

    public void setRespuesta(String respuesta) {
    	this.respuesta = respuesta;
    }
}
