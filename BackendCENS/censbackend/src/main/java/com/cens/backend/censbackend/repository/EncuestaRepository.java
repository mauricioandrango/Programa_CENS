package com.cens.backend.censbackend.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cens.backend.censbackend.entities.Encuesta;
import com.cens.backend.censbackend.entities.Usuario;

@Repository
public interface EncuestaRepository extends CrudRepository<Encuesta, Integer>  {
	@Query(value = "SELECT u FROM Encuesta u WHERE :actualDate BETWEEN u.fechaInicio AND u.fechaFin ")
	public List<Encuesta> findEncuestasActivas(@Param("actualDate")Date actualDate);

}
