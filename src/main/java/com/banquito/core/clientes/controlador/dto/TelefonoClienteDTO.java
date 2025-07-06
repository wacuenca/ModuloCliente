package com.banquito.core.clientes.controlador.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TelefonoClienteDTO {
    private String tipo;
    private String numero;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private String estado;
}