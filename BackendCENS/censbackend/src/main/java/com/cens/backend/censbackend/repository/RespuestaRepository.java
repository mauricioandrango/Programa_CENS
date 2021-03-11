package com.cens.backend.censbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cens.backend.censbackend.entities.Respuesta;
import com.cens.backend.censbackend.entities.Usuario;

@Repository
public interface RespuestaRepository extends CrudRepository<Respuesta, Integer>  {
	
	@Query(value = "SELECT u FROM Respuesta  u WHERE u.pregunta.id  = ?1 ")
	public List<Respuesta> findByPreguntaaId(Integer id);

}
