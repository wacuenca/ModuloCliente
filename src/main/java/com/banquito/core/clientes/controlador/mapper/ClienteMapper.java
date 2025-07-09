package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.modelo.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {TelefonoClienteMapper.class, DireccionesClientesMapper.class,
                ContactoTransaccionalMapper.class, ClienteSucursalMapper.class,
                PersonaMapper.class, EmpresaMapper.class, RepresentanteEmpresaMapper.class})
public interface ClienteMapper {
    // Mapeos para Persona
    PersonaDTO toPersonaDTO(Personas persona);
    Personas toPersona(PersonaDTO personaDTO);

    // Mapeos para Empresa
    EmpresasDTO toEmpresaDTO(Empresas empresa);
    Empresas toEmpresa(EmpresasDTO empresaDTO);

    // Mapeos para Cliente
    @Mapping(target = "scoreInterno", source = "scoreInterno") 
    ClienteDTO toClienteDTO(Clientes cliente);

    @Mapping(target = "scoreInterno", source = "scoreInterno") 
    Clientes toCliente(ClienteDTO clienteDTO);
}
