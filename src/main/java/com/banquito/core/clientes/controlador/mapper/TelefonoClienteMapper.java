package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.modelo.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TelefonoClienteMapper {
    TelefonoClienteDTO toTelefonoDTO(TelefonosClientes telefono);
    TelefonosClientes toTelefono(TelefonoClienteDTO telefonoDTO);
}