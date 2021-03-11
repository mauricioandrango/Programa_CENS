package com.cens.backend.censbackend.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;



@Entity
@Table(name = "encuesta")
public class Encuesta {
	
	@Id
    @GeneratedValue(generator = "answer_generator")
    @SequenceGenerator(
            name = "answer_generator",
            sequenceName = "answer_sequence",
            initialValue = 1000
    )	
	private Integer id;
	
	@Column
	private String nombre;
	@Column
	private String descripcion;
	
	@Column
	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@Column
	//@DateTimeFormat(pattern = "YYYY-MM-dd")
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;
	
	//Relacion a las preguntas que tiene la encuesta
	@OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Pregunta> preguntas;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(Set<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}



	



}
