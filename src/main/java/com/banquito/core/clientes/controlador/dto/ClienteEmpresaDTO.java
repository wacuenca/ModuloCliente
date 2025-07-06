package com.banquito.core.clientes.controlador.dto;

import java.util.List;

import lombok.Data;

@Data
public class ClienteEmpresaDTO {
    private EmpresasDTO empresa;
    private ClienteDTO cliente;
    private List<RepresentanteEmpresaDTO> representantes;

}
