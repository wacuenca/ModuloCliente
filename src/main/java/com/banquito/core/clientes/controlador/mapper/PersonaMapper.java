package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.PersonaDTO;
import com.banquito.core.clientes.modelo.Personas;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

    PersonaDTO toPersonaDTO(Personas persona);

    Personas toPersona(PersonaDTO personaDTO);
}
