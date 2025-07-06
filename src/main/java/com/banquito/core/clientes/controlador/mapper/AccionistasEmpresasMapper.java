package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.AccionistasEmpresasDTO;
import com.banquito.core.clientes.modelo.AccionistasEmpresas;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccionistasEmpresasMapper {
    AccionistasEmpresasDTO toAccionistaDTO(AccionistasEmpresas accionista);
    AccionistasEmpresas toAccionista(AccionistasEmpresasDTO accionistaDTO);
}