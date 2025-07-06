package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class EmpresasDTO {
    private String id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombreComercial;
    private String razonSocial;
    private String tipo;
    private LocalDate fechaConstitucion;
    private String correoElectronico;
    private String sectorEconomico;
    private String estado;
    private List<AccionistasEmpresasDTO> accionistas;
}