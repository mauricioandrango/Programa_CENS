package com.cens.backend.censbackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cens.backend.censbackend.dto.GenericResponseDTO;
import com.cens.backend.censbackend.dto.respuesta.RespuestaRequestDTO;
import com.cens.backend.censbackend.dto.respuesta.RespuestaResponseDTO;
import com.cens.backend.censbackend.dto.respuesta.enviarrespuestas.EnviarRespuestasRequestDTO;
import com.cens.backend.censbackend.dto.respuesta.enviarrespuestas.RespuestaDTO;
import com.cens.backend.censbackend.entities.Encuesta;
import com.cens.backend.censbackend.entities.Pregunta;
import com.cens.backend.censbackend.entities.Respuesta;
import com.cens.backend.censbackend.entities.Usuario;
import com.cens.backend.censbackend.repository.RespuestaRepository;
import com.cens.backend.censbackend.utils.ConstantsUtils;

@RestController
public class RespuestaController {
	@Autowired // This means to get the bean called userRepository// Which is auto-generated by// Spring, we will use it to handle the data
	private RespuestaRepository respuestaRepository;

	
	@PostMapping(path = "/createRespuesta") // Map ONLY POST Requests
	public @ResponseBody GenericResponseDTO create(@RequestBody RespuestaRequestDTO requestDto) {
		try {

			Respuesta respuesta = new Respuesta();
			respuesta.setRespuesta(requestDto.getRespuesta());
			//agregar el pregunta
			Pregunta  pregunta= new Pregunta();
			pregunta.setId(requestDto.getPreguntaId());
			respuesta.setPregunta(pregunta);
			
			respuestaRepository.save(respuesta);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Creado correctamente", respuesta);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"No se pudo crear el usuario: " + e.getMessage(), null);
		}
	}

	@GetMapping(path = "/allRespuesta")
	public @ResponseBody GenericResponseDTO getAll() {
		try {
			List<RespuestaResponseDTO> response = new ArrayList<>();
			List<Respuesta> respuestas = (List<Respuesta>) respuestaRepository.findAll();
			
			for(Respuesta respuesta:respuestas) {
				
				String respuestaString = respuesta.getRespuesta();
				String encuesta = respuesta.getPregunta().getEncuesta().getNombre();
				String pregunta= respuesta.getPregunta().getTitulo();
				
				response.add(new RespuestaResponseDTO(respuesta.getId(),encuesta, respuestaString, pregunta));
			}
			
			// This returns a JSON or XML with the elements
			//Iterable<Respuesta> iterableRespuestas = respuestaRepository.findAll();

			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "OK", response);
		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"Error obteniendo la lista de usuarios: " + e.getMessage(), null);
		}

	}
	//TODO: falta crear findbyID, update, delete
	
	@GetMapping("findRespuestaById/{id}")

	public @ResponseBody GenericResponseDTO findRespuestaById(@PathVariable int id) {
		try {

			Optional<Respuesta> respuestaOptional = respuestaRepository.findById(id);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Respuesta encontrado", respuestaOptional.get());

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"Respuesta no Encontrada: " + e.getMessage(), null);
		}
	}
	
	@DeleteMapping(path = "/deleteRespuesta") 
	public @ResponseBody GenericResponseDTO delete(@RequestParam int id) {
		try {

			respuestaRepository.deleteById(id);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Eliminado correctamente", null);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR, e.getMessage(), null);
		}
	}
	
	
	@PostMapping(path = "/updateRespuesta") // Map ONLY POST Requests
	public @ResponseBody GenericResponseDTO updateRespuesta(@RequestBody RespuestaRequestDTO RespuestarequestDTO) {
		try {

			Respuesta respuesta = respuestaRepository.findById(RespuestarequestDTO.getId()).get();
			
			//Update Pregunta
			Pregunta pregunta = new Pregunta();
			pregunta.setId(RespuestarequestDTO.getPreguntaId());
			respuesta.setPregunta(pregunta);
			//respuesta
			respuesta.setRespuesta(RespuestarequestDTO.getRespuesta());
		

			respuestaRepository.save(respuesta);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Actualizado correctamente", respuesta);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"No se pudo actualizar: " + e.getMessage(), null);
		}
	}
	
	@PostMapping(path = "/enviarRespuestasEncuesta") // Map ONLY POST Requests
	public @ResponseBody GenericResponseDTO enviarRespuestasEncuesta(@RequestBody EnviarRespuestasRequestDTO requestDTO) {
		try {

			//Guardar Cabecera 
			// 	agregar encuesta 
			Encuesta encuesta = new Encuesta();
			encuesta.setId(requestDTO.getEncuestaId());
			//	agregar usuario 
			Usuario usuario = new Usuario();
			usuario.setId(requestDTO.getUsuarioId());
			//fecha
			//guardar en base de datos la cabecera
						
			//Guardar Respuestas
			for(RespuestaDTO respuestaDTO: requestDTO.getRespuestas()) {
				Respuesta respuesta = new Respuesta();
			
				
				//Update Pregunta
				Pregunta pregunta = new Pregunta();
				pregunta.setId(respuestaDTO.getPreguntaId());
				respuesta.setPregunta(pregunta);
				//agregar usuario 
				
				respuesta.setUsuario(usuario);
				//respuesta
				respuesta.setRespuesta(respuestaDTO.getRespuesta());

				respuestaRepository.save(respuesta);
				
			}
			
		

			
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Respuestas Almacenadas correctamente",encuesta);

		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"No se pudo almacenar: " + e.getMessage(), null);
		}
	}
	
}