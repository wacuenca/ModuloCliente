package com.banquito.core.clientes.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document(collection = "clientes")
@CompoundIndex(name = "idxu_cliente_tipo_numero_identificacion", 
               def = "{'tipoIdentificacion': 1, 'numeroIdentificacion': 1}", 
               unique = true)
public class Clientes {
    @Id
    private String id;

    @Indexed
    private String tipoEntidad; 
    private String idEntidad; 
    
    // Información básica
    private String nombre;
    private String nacionalidad;
    private String tipoIdentificacion;
    
    @Indexed
    private String numeroIdentificacion;
    
    private String tipoCliente; 
    private String segmento;
    private String canalAfiliacion;
    private String comentarios;
    private String estado;
    private Long version;

    // Información de contacto embebida
    private List<TelefonosClientes> telefonos;
    private List<DireccionesClientes> direcciones;
    private ContactosTransaccionalesClientes contactoTransaccional;

    // Auditoría
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
}