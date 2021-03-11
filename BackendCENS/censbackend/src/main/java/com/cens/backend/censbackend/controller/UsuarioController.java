package com.cens.backend.censbackend.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cens.backend.censbackend.dto.GenericResponseDTO;
import com.cens.backend.censbackend.dto.user.CreateUserRequestDTO;
import com.cens.backend.censbackend.dto.user.LoginRequestDTO;
import com.cens.backend.censbackend.entities.Usuario;
import com.cens.backend.censbackend.repository.UsuarioRepository;
import com.cens.backend.censbackend.utils.ConstantsUtils;

@RestController

@Service
@Transactional

public class UsuarioController {

	@Autowired // This means to get the bean called userRepository// Which is auto-generated
				// by// Spring, we will use it to handle the data
	private UsuarioRepository usuarioRepository;

	@PostMapping(path = "/createUsuario") // Map ONLY POST Requests
	public @ResponseBody GenericResponseDTO createUser(@RequestBody CreateUserRequestDTO usuarioDTO) {
		try {

			Usuario usuario = new Usuario();
			usuario.setUsername(usuarioDTO.getUsername());
			usuario.setNombres(usuarioDTO.getNombres());
			usuario.setApellidos(usuarioDTO.getApellidos());
			usuario.setMail(usuarioDTO.getMail());
			usuario.setPassword(usuarioDTO.getPassword());

			usuarioRepository.save(usuario);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Creado correctamente", usuario);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"No se pudo crear el usuario: " + e.getMessage(), null);
		}
	}
	
	@PostMapping(path = "/registrarUsuarioMovil") // Map ONLY POST Requests
	public @ResponseBody GenericResponseDTO registrarUsuarioMovil(@RequestBody CreateUserRequestDTO usuarioDTO) {
		try {

			Usuario usuario = new Usuario();
			usuario.setUsername(usuarioDTO.getUsername());
			usuario.setNombres(usuarioDTO.getNombres());
			usuario.setApellidos(usuarioDTO.getApellidos());
			usuario.setMail(usuarioDTO.getMail());
			usuario.setPassword(usuarioDTO.getPassword());

			usuarioRepository.save(usuario);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Creado correctamente", usuario);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"No se pudo crear el usuario: " + e.getMessage(), null);
		}
	}
	
	@PostMapping(path = "/login") 
	public @ResponseBody GenericResponseDTO login(@RequestBody LoginRequestDTO usuarioDTO) {
		try {
			
			Optional<Usuario> usuariooptional = usuarioRepository.findByLogin(usuarioDTO.getUsername(), usuarioDTO.getPassword());
			if(usuariooptional.isPresent()) {
				return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Login correcto", usuariooptional.get());
				
			}else {
				return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR, "Usuario o contraseña incorrectos", null);
			}

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"No se pudo encontrar el usuario: " + e.getMessage(), null);
		}
	}

	@GetMapping(path = "/allUsuarios")
	public @ResponseBody GenericResponseDTO getAllUsers() {
		try {

			// This returns a JSON or XML with the elements
			Iterable<Usuario> iterableUsuarios = usuarioRepository.findAll();

			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Creado correctamente", iterableUsuarios);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"Error obteniendo la lista de usuarios: " + e.getMessage(), null);
		}

	}

	@GetMapping("findUsuarioById/{id}")

	public @ResponseBody GenericResponseDTO findUsuarioById(@PathVariable int id) {
		try {

			Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Usuario encontrado", usuarioOptional.get());

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"No se pudo encontrar el usuario: " + e.getMessage(), null);
		}
	}

	@DeleteMapping(path = "/deleteUsuario/{id}") // Map ONLY POST Requests
	public @ResponseBody GenericResponseDTO delete(@PathVariable int id) {
		try {

			usuarioRepository.deleteById(id);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Eliminado correctamente", null);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR, e.getMessage(), null);
		}
	}

	@PostMapping(path = "/updateUsuario") // Map ONLY POST Requests
	public @ResponseBody GenericResponseDTO updateUser(@RequestBody CreateUserRequestDTO usuarioDTO) {
		try {

			Usuario usuario = usuarioRepository.findById(usuarioDTO.getId()).get();
			usuario.setNombres(usuarioDTO.getNombres());
			usuario.setApellidos(usuarioDTO.getApellidos());
			usuario.setMail(usuarioDTO.getMail());
			usuario.setPassword(usuarioDTO.getPassword());

			usuarioRepository.save(usuario);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Actualizado correctamente", usuario);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"No se pudo crear el usuario: " + e.getMessage(), null);
		}
	}

	@RequestMapping(path = "/crearDummyUsuario") // Map ONLY POST Requests
	public @ResponseBody Object crearSimple() {
		try {
			Usuario obj = new Usuario();
			// obj.setId(1);
			obj.setUsername("asanchez");
			obj.setNombres("Andres");
			obj.setApellidos("Sanchez");
			obj.setMail("asanchez@gmail.com");
			obj.setPassword("123");
			usuarioRepository.save(obj);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Creado correctamente", obj);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR, "Error creando usuario: " + e.getMessage(),
					null);
		}
	}
	
	@GetMapping(path = "/resetPassword/{username}") // Map ONLY POST Requests
	public @ResponseBody GenericResponseDTO resetPassword(@PathVariable String username) {
		try {

			Usuario usuario = usuarioRepository.findByUsername(username).get();
			if(usuario!=null) {
				String generatedPassword = (usuario.getUsername()+ ConstantsUtils.DEFAULT_PASSWORD_SUFFIX);
			
				usuario.setPassword(generatedPassword);
				
				usuarioRepository.save(usuario);
				return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Password reseteado correctamente", usuario.getUsername());
				
			}else {
				return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR, "No se encontro el usuario", usuario.getUsername());

			}


		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"No se pudo crear actualizar el password del usuario: " + e.getMessage(), null);
		}
	}
	
	
}
