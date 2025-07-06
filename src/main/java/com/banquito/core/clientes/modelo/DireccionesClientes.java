package com.banquito.core.clientes.modelo;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Data;

@Data
public class DireccionesClientes {
    private String tipo;
    private String linea1;
    private String linea2;
    private String codigoPostal;
    
    @Indexed
    private String codigoGeografico;
    
    // @CompoundIndex(name = "idx_locacion_geografica_provincia_canton", 
    //               def = "{'codigoProvincia': 1, 'codigoCanton': 1}")
    private String codigoProvincia;
    private String codigoCanton;
    
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private String estado;
    private Long version;
}