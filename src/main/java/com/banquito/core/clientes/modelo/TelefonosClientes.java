package com.banquito.core.clientes.modelo;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TelefonosClientes {
    
    @Indexed
    private String tipo; 
    private String numero;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private String estado;
    private Long version;
}