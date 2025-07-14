package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.EmpresasDTO;
import com.banquito.core.clientes.modelo.Empresas;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    @Mapping(target = "accionistas", ignore = true)
    Empresas toEmpresa(EmpresasDTO empresaDTO);

    EmpresasDTO toEmpresaDTO(Empresas empresa);
}
