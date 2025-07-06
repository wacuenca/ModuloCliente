package com.banquito.core.clientes.modelo;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class RepresentantesEmpresas {
    @Field("empresa_id")
    private String empresaId;
    private String clienteId;
    private String rol;
    private LocalDate fechaAsignacion;
    private String estado;
    private Long version;
}