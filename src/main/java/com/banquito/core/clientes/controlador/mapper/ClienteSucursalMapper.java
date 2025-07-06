package com.banquito.core.clientes.controlador.mapper;

import org.mapstruct.Mapper;

import com.banquito.core.clientes.controlador.dto.ClienteSucursalDTO;
import com.banquito.core.clientes.modelo.ClientesSucursales;

@Mapper(componentModel = "spring")
public interface ClienteSucursalMapper {
    ClienteSucursalDTO toClienteSucursalDTO(ClientesSucursales sucursal);
    ClientesSucursales toClienteSucursal(ClienteSucursalDTO sucursalDTO);
}
