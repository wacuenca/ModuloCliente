package com.banquito.core.clientes.controlador.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccionistasEmpresasDTO {
    private String idParticipe;
    private String tipoEntidadParticipe;
    private BigDecimal participacion;
    private String estado;
}