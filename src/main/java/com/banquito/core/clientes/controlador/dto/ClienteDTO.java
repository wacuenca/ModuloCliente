package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ClienteDTO {
    private String id;
    private String tipoEntidad; 
    private String idEntidad;
    private String nombre;
    private String nacionalidad;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String tipoCliente;
    private String segmento;
    private String canalAfiliacion;
    private String comentarios;
    private String estado;
    private LocalDate fechaCreacion;
    private List<TelefonoClienteDTO> telefonos;
    private List<DireccionClienteDTO> direcciones;
    private ContactoTransaccionalClienteDTO contactoTransaccional;

}