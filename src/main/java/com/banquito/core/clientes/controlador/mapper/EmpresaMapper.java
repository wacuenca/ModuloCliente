package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.EmpresasDTO;
import com.banquito.core.clientes.modelo.Empresas;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    EmpresasDTO toEmpresaDTO(Empresas empresa);

    Empresas toEmpresa(EmpresasDTO empresaDTO);
}
