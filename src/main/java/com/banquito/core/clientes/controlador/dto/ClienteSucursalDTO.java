package com.banquito.core.clientes.controlador.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteSucursalDTO {
    private String codigoSucursal;
    private String estado;
    private LocalDate fechaCreacion;
    private LocalDate fechaUltimaActualizacion;
}