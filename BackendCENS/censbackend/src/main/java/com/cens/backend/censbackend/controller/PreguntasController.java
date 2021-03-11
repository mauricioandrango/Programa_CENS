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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cens.backend.censbackend.dto.GenericResponseDTO;
import com.cens.backend.censbackend.dto.PreguntaDTO;
import com.cens.backend.censbackend.dto.pregunta.PreguntaCreateRequestDTO;
import com.cens.backend.censbackend.entities.Encuesta;
import com.cens.backend.censbackend.entities.Pregunta;
import com.cens.backend.censbackend.repository.EncuestaRepository;
import com.cens.backend.censbackend.repository.PreguntaRepository;
import com.cens.backend.censbackend.utils.ConstantsUtils;

@RestController
public class PreguntasController {
	@Autowired // This means to get the bean called userRepository// Which is auto-generated
				// by// Spring, we will use it to handle the data
	private PreguntaRepository preguntaRepository;

	@Autowired // This means to get the bean called userRepository// Which is auto-generated
				// by// Spring, we will use it to handle the data
	private EncuestaRepository encuestaRepository;

	@PostMapping(path = "/createPregunta") // Map ONLY POST Requests
	public @ResponseBody Pregunta createEncuesta(@RequestBody PreguntaCreateRequestDTO preguntaDTO) {
		try {
			Pregunta pregunta = new Pregunta();
			pregunta.setTipo(preguntaDTO.getTipo());
			pregunta.setTitulo(preguntaDTO.getTitulo());
			pregunta.setOpciones(preguntaDTO.getOpciones());
			// Buscar y Agregar la encuesta
			Optional<Encuesta> encuestaOptional = encuestaRepository.findById(preguntaDTO.getEncuesta_id());
			if (encuestaOptional.isPresent()) {
				pregunta.setEncuesta(encuestaOptional.get());
			} else {
				System.out.print("No existe la encuesta con id: " + preguntaDTO.getEncuesta_id());
				return null;
			}
			preguntaRepository.save(pregunta);
			return pregunta; // new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Creado correctamente",
								// obj);

		} catch (Exception e) {
			return null;// new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR, e.getMessage(), null);
		}
	}

	@GetMapping(path = "/allPreguntas")
	public @ResponseBody GenericResponseDTO getAllUsers() {
		
		try {
			// This returns a JSON or XML with the elements
			Iterable<Pregunta> iterablePreguntas = preguntaRepository.findAll();
			List<PreguntaDTO> preguntasDTOList = new ArrayList<>();
			for(Pregunta pregunta: iterablePreguntas) {
				PreguntaDTO preguntaDTO = new PreguntaDTO();
				preguntaDTO.setId(pregunta.getId());
				preguntaDTO.setOpciones(pregunta.getOpciones());
				preguntaDTO.setTipo(pregunta.getTipo());
				preguntaDTO.setTitulo(pregunta.getTitulo());
				//encuesta
				Encuesta encuesta = new Encuesta();
				encuesta.setId(pregunta.getEncuesta().getId());
				encuesta.setDescripcion(pregunta.getEncuesta().getDescripcion());
				encuesta.setNombre(pregunta.getEncuesta().getNombre());
				preguntaDTO.setEncuesta(encuesta);
				//add to dto list
				preguntasDTOList.add(preguntaDTO);
			}
			
			//Hibernate.initialize(iterableUsuarios);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Creado correctamente", preguntasDTOList);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"Error obteniendo la lista de usuarios: " + e.getMessage(), null);
		}
	}

	@RequestMapping(path = "/crearPreguntaDummy") // Map ONLY POST Requests
	public @ResponseBody Object crearSimple() {
		try {
			Pregunta pregunta = new Pregunta();
			// obj.setId(1);
			pregunta.setTitulo("En que barrio vive?");
			pregunta.setTipo(ConstantsUtils.TIPO_PREGUNTA_ABIERTA);

			// Agregar Encuesta a la relacion
			Optional<Encuesta> encuestaOpt = encuestaRepository.findById(1000);
			if (encuestaOpt.isPresent()) {
				Encuesta encuesta = encuestaOpt.get();
				pregunta.setEncuesta(encuesta);
			}

			preguntaRepository.save(pregunta);
			return pregunta; // new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Creado correctamente",
								// obj);

		} catch (Exception e) {
			e.printStackTrace();

			return null;// new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR, e.getMessage(), null);
		}
	}
	
	@PostMapping(path = "/updatePregunta") // Map ONLY POST Requests
	public @ResponseBody GenericResponseDTO updateEncuesta(@RequestBody PreguntaDTO pregunta) {
		try {

			Pregunta updatedPregunta = preguntaRepository.findById(pregunta.getId()).get();
				updatedPregunta.setTipo(pregunta.getTipo());
			updatedPregunta.setTitulo(pregunta.getTitulo());
			updatedPregunta.setOpciones(pregunta.getOpciones());
			// Buscar y Agregar la encuesta
			Optional<Encuesta> encuestaOptional = encuestaRepository.findById(pregunta.getEncuesta_id());
			if (encuestaOptional.isPresent()) {
				updatedPregunta.setEncuesta(encuestaOptional.get());
			} else {
				System.out.print("No existe la encuesta con id: " + pregunta.getEncuesta().getId());
				return null;
			}
			preguntaRepository.save(updatedPregunta);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Actualizado correctamente", updatedPregunta);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR,
					"No se pudo crear el usuario: " + e.getMessage(), null);
		}
	}
	
	@DeleteMapping(path = "/deletePregunta/{id}") // Map ONLY POST Requests
	public @ResponseBody GenericResponseDTO delete(@PathVariable int id) {
		try {

			preguntaRepository.deleteById(id);
			return new GenericResponseDTO(ConstantsUtils.CODE_200_OK, "Eliminado correctamente", null);

		} catch (Exception e) {
			return new GenericResponseDTO(ConstantsUtils.CODE_500_ERROR, e.getMessage(), null);
		}
	}

}
