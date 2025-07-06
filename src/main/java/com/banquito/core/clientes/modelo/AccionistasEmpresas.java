package com.banquito.core.clientes.modelo;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class AccionistasEmpresas {
    @Field("id_empresa") 
    private String idEmpresa;

    @Field("id_participe") 
    private String idParticipe; 
    
    private String tipoEntidadParticipe; 
    private BigDecimal participacion;
    private String estado;
    private Long version;
}