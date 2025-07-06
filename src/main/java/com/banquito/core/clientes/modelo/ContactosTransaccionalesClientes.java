package com.banquito.core.clientes.modelo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ContactosTransaccionalesClientes {
    private String idCliente;
    private String telefono;
    private String correoElectronico;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private String estado;
    private Long version;
}