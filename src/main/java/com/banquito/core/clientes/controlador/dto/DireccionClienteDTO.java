package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DireccionClienteDTO {
    private String tipo;
    private String linea1;
    private String linea2;
    private String codigoPostal;
    private String codigoGeografico;
    private String codigoProvincia;
    private String codigoCanton;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private String estado;
}