package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.ContactoTransaccionalClienteDTO;
import com.banquito.core.clientes.modelo.ContactosTransaccionalesClientes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactoTransaccionalMapper {
    ContactoTransaccionalClienteDTO toContactoTransaccionalDTO(ContactosTransaccionalesClientes contacto);
    ContactosTransaccionalesClientes toContactoTransaccional(ContactoTransaccionalClienteDTO contactoDTO);
}