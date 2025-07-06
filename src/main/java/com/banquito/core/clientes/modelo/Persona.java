package com.banquito.core.clientes.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document(collection = "personas")
@CompoundIndex(name = "idxu_persona_tipo_numero_identificacion", 
               def = "{'tipoIdentificacion': 1, 'numeroIdentificacion': 1}", 
               unique = true)
public class Persona {
    @Id
    private String id;
    
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombre;
    private String genero;
    private LocalDate fechaNacimiento;
    private String estadoCivil;
    private String nivelEstudio;
    private String correoElectronico;
    
    // Auditoría
    private LocalDate fechaRegistro;
    private LocalDate fechaActualizacion;
    private String estado;
    private Long version;
}