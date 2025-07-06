package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.ContactoTransaccionalClienteDTO;
import com.banquito.core.clientes.modelo.ContactosTransaccionalesClientes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactoTransaccionalMapper {
    ContactoTransaccionalClienteDTO toContactoTransaccionalDTO(ContactosTransaccionalesClientes contacto);
    ContactosTransaccionalesClientes toContactoTransaccional(ContactoTransaccionalClienteDTO contactoDTO);
    List<ContactoTransaccionalClienteDTO> toContactoTransaccionalDTOList(List<ContactosTransaccionalesClientes> contactos);
    List<ContactosTransaccionalesClientes> toContactoTransaccionalList(List<ContactoTransaccionalClienteDTO> contactoDTOs);
}