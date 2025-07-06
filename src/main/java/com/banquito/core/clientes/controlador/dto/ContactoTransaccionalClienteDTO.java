package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContactoTransaccionalClienteDTO {


    private String idCliente;
    private String telefono;
    private String correoElectronico;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private String estado;
}