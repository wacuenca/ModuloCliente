package com.banquito.core.clientes.controlador.mapper;

import org.mapstruct.Mapper;

import com.banquito.core.clientes.controlador.dto.DireccionClienteDTO;
import com.banquito.core.clientes.modelo.DireccionesClientes;

@Mapper(componentModel = "spring")
public interface DireccionesClientesMapper {
    DireccionClienteDTO toDireccionDTO(DireccionesClientes direccion);
    DireccionesClientes toDireccion(DireccionClienteDTO direccionDTO);
}
