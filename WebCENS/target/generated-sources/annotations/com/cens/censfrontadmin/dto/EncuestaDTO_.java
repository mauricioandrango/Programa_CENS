package com.cens.censfrontadmin.dto;

import com.cens.censfrontadmin.dto.PreguntaDTO;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-02-07T23:45:41")
@StaticMetamodel(EncuestaDTO.class)
public class EncuestaDTO_ { 

    public static volatile SingularAttribute<EncuestaDTO, String> descripcion;
    public static volatile SetAttribute<EncuestaDTO, PreguntaDTO> preguntas;
    public static volatile SingularAttribute<EncuestaDTO, Long> id;
    public static volatile SingularAttribute<EncuestaDTO, String> nombre;

}