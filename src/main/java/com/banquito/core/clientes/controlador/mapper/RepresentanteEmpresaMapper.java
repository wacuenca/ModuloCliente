package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.RepresentanteEmpresaDTO;
import com.banquito.core.clientes.modelo.RepresentantesEmpresas;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepresentanteEmpresaMapper {
    RepresentanteEmpresaDTO toDto(RepresentantesEmpresas representante);
    RepresentantesEmpresas toEntity(RepresentanteEmpresaDTO dto);
}