package com.banquito.core.clientes.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document(collection = "empresas")
@CompoundIndex(name = "idxu_empresa_tipo_numero_identificacion", 
               def = "{'tipoIdentificacion': 1, 'numeroIdentificacion': 1}", 
               unique = true)
public class Empresas {
    @Id
    private String id;
    
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombreComercial;
    private String razonSocial;
    private String tipo;
    private LocalDate fechaConstitucion;
    private String correoElectronico;
    private String sectorEconomico;
    
    // Accionistas 
    private List<AccionistasEmpresas> accionistas;

    // Representantes 
    private List<RepresentantesEmpresas> representantes;

    // Auditoría
    private LocalDate fechaRegistro;
    private LocalDate fechaActualizacion;
    private String estado;
    private Long version;
}