package com.banquito.core.clientes.controlador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientePersonaDTO {
    private PersonaDTO persona;
    private ClienteDTO cliente;   
}
