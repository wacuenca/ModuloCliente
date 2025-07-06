package com.banquito.core.clientes.modelo;

import lombok.*;

import java.time.LocalDate;

@Data
public class ClientesSucursales {
    private String codigoSucursal;
    private String estado;
    private Long version;
    private LocalDate fechaCreacion;
    private LocalDate fechaUltimaActualizacion;
}