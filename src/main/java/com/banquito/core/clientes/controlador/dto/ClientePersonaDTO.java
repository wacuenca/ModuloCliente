package com.banquito.core.clientes.controlador.dto;

import lombok.Data;

@Data
public class ClientePersonaDTO {
    private PersonaDTO persona;
    private ClienteDTO cliente;   
}
