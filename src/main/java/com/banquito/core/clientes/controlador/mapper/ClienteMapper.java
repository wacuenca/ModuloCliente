package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.modelo.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", 
        uses = {TelefonoClienteMapper.class, DireccionesClientesMapper.class, 
                ContactoTransaccionalMapper.class, ClienteSucursalMapper.class})
public interface ClienteMapper {

    // Mapeos para Persona
    PersonaDTO toPersonaDTO(Persona persona);
    Persona toPersona(PersonaDTO personaDTO);

    // Mapeos para Empresa
    EmpresasDTO toEmpresaDTO(Empresas empresa);
    Empresas toEmpresa(EmpresasDTO empresaDTO);

    // Mapeos para Cliente
    ClienteDTO toClienteDTO(Clientes cliente);
    Clientes toCliente(ClienteDTO clienteDTO);

    // Mapeos compuestos simples
    @Mapping(target = "persona", source = "persona")
    @Mapping(target = "cliente", source = "cliente")
    ClientePersonaDTO toClientePersonaDTO(Persona persona, Clientes cliente);

    @Mapping(target = "empresa", source = "empresa")
    @Mapping(target = "cliente", source = "cliente")
    ClienteEmpresaDTO toClienteEmpresaDTO(Empresas empresa, Clientes cliente);
}