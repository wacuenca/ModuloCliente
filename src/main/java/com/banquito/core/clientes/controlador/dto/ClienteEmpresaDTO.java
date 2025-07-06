package com.banquito.core.clientes.controlador.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEmpresaDTO {
    private EmpresasDTO empresa;
    private ClienteDTO cliente;
    private List<RepresentanteEmpresaDTO> representantes;
}
