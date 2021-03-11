package com.cens.backend.censbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cens.backend.censbackend.entities.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>  {
	
	
	@Query(value = "SELECT u FROM Usuario u WHERE u.username  = ?1 AND u.password = ?2 ")
	public Optional<Usuario> findByLogin(String username, String password);
	
	@Query(value = "SELECT u FROM Usuario u WHERE u.username  = ?1 ")
	public Optional<Usuario> findByUsername(String username);
	
	 
}
