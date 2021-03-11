package com.cens.backend.censbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cens.backend.censbackend.entities.Pregunta;

@Repository
public interface PreguntaRepository extends CrudRepository<Pregunta, Integer>  {

}
