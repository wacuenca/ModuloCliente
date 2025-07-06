package com.banquito.core.clientes.modelo;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class AccionistasEmpresas {
    @Field("id_empresa") // Si en MongoDB el campo es 'id_empresa'
    private String idEmpresa;

    @Field("id_participe") // Si en MongoDB el campo es 'id_participe'
    private String idParticipe; 
    
    private String tipoEntidadParticipe; 
    private BigDecimal participacion;
    private String estado;
    private Long version;
}