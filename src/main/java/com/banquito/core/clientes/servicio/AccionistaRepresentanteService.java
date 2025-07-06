package com.banquito.core.clientes.servicio;

import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.controlador.mapper.*;
import com.banquito.core.clientes.enums.EstadoRegistro;
import com.banquito.core.clientes.enums.TipoEntidadParticipe;
import com.banquito.core.clientes.excepcion.*;
import com.banquito.core.clientes.modelo.*;
import com.banquito.core.clientes.repositorio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccionistaRepresentanteService {

    private final AccionistasEmpresasMapper accionistasEmpresasMapper;
    private final EmpresasRepositorio empresaRepo;
    private final ClientesRepositorio clienteRepo;
    private final RepresentanteEmpresaMapper representanteMapper;

    public AccionistaRepresentanteService(AccionistasEmpresasMapper accionistasEmpresasMapper,
                                          EmpresasRepositorio empresaRepo,
                                          ClientesRepositorio clienteRepo,
                                          @Qualifier("representanteEmpresaMapperImpl")RepresentanteEmpresaMapper representanteMapper) {
        this.accionistasEmpresasMapper = accionistasEmpresasMapper;
        this.empresaRepo = empresaRepo;
        this.clienteRepo = clienteRepo;
        this.representanteMapper = representanteMapper;
    }

    // ========== MÉTODOS PARA ACCIONISTAS ==========

    @Transactional
    public AccionistasEmpresasDTO agregarAccionista(String idEmpresa, AccionistasEmpresasDTO accionistaDTO) {
        try {
            log.info("Agregando accionista a empresa: {}", idEmpresa);
            
            Empresas empresa = empresaRepo.findById(idEmpresa)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3206));
            
            if (TipoEntidadParticipe.PERSONA.name().equals(accionistaDTO.getTipoEntidadParticipe())) {
                if (!clienteRepo.existsByIdAndTipoEntidad(accionistaDTO.getIdParticipe(), "PERSONA")) {
                    throw new CreacionException("Persona debe ser cliente", 1401);
                }
            } else if (TipoEntidadParticipe.EMPRESA.name().equals(accionistaDTO.getTipoEntidadParticipe())) {
                if (!clienteRepo.existsByIdAndTipoEntidad(accionistaDTO.getIdParticipe(), "EMPRESA")) {
                    throw new CreacionException("Empresa debe ser cliente", 1402);
                }
            }
            
            // Verificar si ya existe el accionista
            if (empresa.getAccionistas() != null) {
                boolean existe = empresa.getAccionistas().stream()
                    .anyMatch(a -> a.getIdParticipe().equals(accionistaDTO.getIdParticipe()));
                if (existe) {
                    throw new CreacionException("Accionista ya existe", 1404);
                }
            } else {
                // Inicializar la lista si es null
                empresa.setAccionistas(new ArrayList<>());
            }
            
            AccionistasEmpresas accionista = accionistasEmpresasMapper.toAccionista(accionistaDTO);
            accionista.setEstado(EstadoRegistro.ACTIVO.name());
            
            empresa.getAccionistas().add(accionista);
            empresa.setFechaActualizacion(LocalDate.now());
            
            Empresas empresaGuardada = empresaRepo.save(empresa);
            
            // Buscar el accionista recién agregado
            AccionistasEmpresas accionistaGuardado = empresaGuardada.getAccionistas().stream()
                .filter(a -> a.getIdParticipe().equals(accionistaDTO.getIdParticipe()))
                .findFirst()
                .orElseThrow(() -> new CreacionException("Error al guardar accionista", 1499));
            
            return accionistasEmpresasMapper.toAccionistaDTO(accionistaGuardado);
            
        } catch (CreacionException | NotFoundException e) {
            log.error("Error al agregar accionista: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al agregar accionista", e);
            throw new CreacionException("Error al agregar accionista", 1499);
        }
    }

    @Transactional
    public AccionistasEmpresasDTO actualizarAccionista(String idEmpresa, String idParticipe, AccionistasEmpresasDTO accionistaDTO) {
        try {
            log.info("Actualizando accionista {} en empresa {}", idParticipe, idEmpresa);
            
            Empresas empresa = empresaRepo.findById(idEmpresa)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3401));
            
            if (empresa.getAccionistas() == null) {
                throw new NotFoundException("Accionista no encontrado", 3402);
            }
            
            AccionistasEmpresas accionista = empresa.getAccionistas().stream()
                .filter(a -> a.getIdParticipe().equals(idParticipe))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Accionista no encontrado", 3402));
            
            accionista.setParticipacion(accionistaDTO.getParticipacion());
            empresa.setFechaActualizacion(LocalDate.now());
            
            empresaRepo.save(empresa);
            return accionistasEmpresasMapper.toAccionistaDTO(accionista);
            
        } catch (NotFoundException e) {
            log.error("Error al actualizar accionista: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar accionista", e);
            throw new ActualizacionException("Error al actualizar accionista", 2499);
        }
    }

    @Transactional
    public void cambiarEstadoAccionista(String idEmpresa, String idParticipe, EstadoRegistro estado) {
        try {
            log.info("Cambiando estado del accionista {} en empresa {} a {}", idParticipe, idEmpresa, estado);
            
            Empresas empresa = empresaRepo.findById(idEmpresa)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3403));
            
            if (empresa.getAccionistas() == null) {
                throw new NotFoundException("Accionista no encontrado", 3404);
            }
            
            AccionistasEmpresas accionista = empresa.getAccionistas().stream()
                .filter(a -> a.getIdParticipe().equals(idParticipe))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Accionista no encontrado", 3404));
            
            accionista.setEstado(estado.name());
            empresa.setFechaActualizacion(LocalDate.now());
            
            empresaRepo.save(empresa);
            
        } catch (NotFoundException e) {
            log.error("Error al cambiar estado accionista: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al cambiar estado accionista", e);
            throw new ActualizacionException("Error al cambiar estado accionista", 2498);
        }
    }

    public List<AccionistasEmpresasDTO> listarAccionistasActivos(String idEmpresa) {
        try {
            log.info("Listando accionistas activos de empresa: {}", idEmpresa);
            
            Empresas empresa = empresaRepo.findById(idEmpresa)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3208));
            
            if (empresa.getAccionistas() == null) {
                return List.of();
            }
            
            return empresa.getAccionistas().stream()
                    .filter(a -> EstadoRegistro.ACTIVO.name().equals(a.getEstado()))
                    .map(accionistasEmpresasMapper::toAccionistaDTO)
                    .collect(Collectors.toList());
            
        } catch (NotFoundException e) {
            log.error("Error al listar accionistas: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al listar accionistas", e);
            throw new ConsultaException("Error al listar accionistas", 3497);
        }
    }

    public AccionistasEmpresasDTO obtenerAccionista(String idEmpresa, String idParticipe) {
        log.info("Obteniendo accionista {} de empresa {}", idParticipe, idEmpresa);

        Empresas empresa = empresaRepo.findById(idEmpresa)
                .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3601));

        if (empresa.getAccionistas() == null) {
            throw new NotFoundException("Accionista no encontrado", 3602);
        }

        AccionistasEmpresas accionista = empresa.getAccionistas().stream()
                .filter(a -> a.getIdParticipe().equals(idParticipe))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Accionista no encontrado", 3602));

        return accionistasEmpresasMapper.toAccionistaDTO(accionista);
    }

    // ========== MÉTODOS PARA REPRESENTANTES ==========

    @Transactional
    public RepresentanteEmpresaDTO agregarRepresentante(String idEmpresa, RepresentanteEmpresaDTO representanteDTO) {
        try {
            log.info("Agregando representante a empresa: {}", idEmpresa);
            
            Empresas empresa = empresaRepo.findById(idEmpresa)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3209));
            
            Clientes cliente = clienteRepo.findById(representanteDTO.getIdCliente())
                    .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3305));

            if (!"PERSONA".equals(cliente.getTipoEntidad())) {
                throw new CreacionException("Representante debe ser persona", 1501);
            }
            
            // Verificar si ya existe el representante
            if (empresa.getRepresentantes() != null) {
                boolean existe = empresa.getRepresentantes().stream()
                    .anyMatch(r -> r.getClienteId().equals(representanteDTO.getIdCliente()));
                if (existe) {
                    throw new CreacionException("Representante ya existe", 1502);
                }
            } else {
                // Inicializar la lista si es null
                empresa.setRepresentantes(new ArrayList<>());
            }
            
            RepresentantesEmpresas representante = representanteMapper.toEntity(representanteDTO);
            representante.setEstado(EstadoRegistro.ACTIVO.name());
            representante.setFechaAsignacion(LocalDate.now());
            
            empresa.getRepresentantes().add(representante);
            empresa.setFechaActualizacion(LocalDate.now());
            
            Empresas empresaGuardada = empresaRepo.save(empresa);
            
            // Buscar el representante recién agregado
            RepresentantesEmpresas representanteGuardado = empresaGuardada.getRepresentantes().stream()
                .filter(r -> r.getClienteId().equals(representanteDTO.getIdCliente()))
                .findFirst()
                .orElseThrow(() -> new CreacionException("Error al guardar representante", 1599));
            
            return representanteMapper.toDto(representanteGuardado);
            
        } catch (CreacionException | NotFoundException e) {
            log.error("Error al agregar representante: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al agregar representante", e);
            throw new CreacionException("Error al agregar representante", 1599);
        }
    }

    @Transactional
    public RepresentanteEmpresaDTO actualizarRepresentante(String idEmpresa, String idCliente, RepresentanteEmpresaDTO representanteDTO) {
        try {
            log.info("Actualizando representante {} en empresa {}", idCliente, idEmpresa);
            
            Empresas empresa = empresaRepo.findById(idEmpresa)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3501));
            
            if (empresa.getRepresentantes() == null) {
                throw new NotFoundException("Representante no encontrado", 3502);
            }
            
            RepresentantesEmpresas representante = empresa.getRepresentantes().stream()
                .filter(r -> r.getClienteId().equals(idCliente))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Representante no encontrado", 3502));
            
            representante.setRol(representanteDTO.getRol());
            empresa.setFechaActualizacion(LocalDate.now());
            
            empresaRepo.save(empresa);
            return representanteMapper.toDto(representante);
            
        } catch (NotFoundException e) {
            log.error("Error al actualizar representante: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar representante", e);
            throw new ActualizacionException("Error al actualizar representante", 2599);
        }
    }

    @Transactional
    public void cambiarEstadoRepresentante(String idEmpresa, String idCliente, EstadoRegistro estado) {
        try {
            log.info("Cambiando estado del representante {} en empresa {} a {}", idCliente, idEmpresa, estado);
            
            Empresas empresa = empresaRepo.findById(idEmpresa)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3503));
            
            if (empresa.getRepresentantes() == null) {
                throw new NotFoundException("Representante no encontrado", 3504);
            }
            
            RepresentantesEmpresas representante = empresa.getRepresentantes().stream()
                .filter(r -> r.getClienteId().equals(idCliente))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Representante no encontrado", 3504));
            
            representante.setEstado(estado.name());
            empresa.setFechaActualizacion(LocalDate.now());
            
            empresaRepo.save(empresa);
            
        } catch (NotFoundException e) {
            log.error("Error al cambiar estado representante: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al cambiar estado representante", e);
            throw new ActualizacionException("Error al cambiar estado representante", 2598);
        }
    }

    public List<RepresentanteEmpresaDTO> listarRepresentantesActivos(String idEmpresa) {
        try {
            log.info("Listando representantes activos de empresa: {}", idEmpresa);
            
            Empresas empresa = empresaRepo.findById(idEmpresa)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3210));
            
            if (empresa.getRepresentantes() == null) {
                return List.of();
            }
            
            return empresa.getRepresentantes().stream()
                    .filter(r -> EstadoRegistro.ACTIVO.name().equals(r.getEstado()))
                    .map(representanteMapper::toDto)
                    .collect(Collectors.toList());
            
        } catch (NotFoundException e) {
            log.error("Error al listar representantes: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al listar representantes", e);
            throw new ConsultaException("Error al listar representantes", 3597);
        }
    }

    public RepresentanteEmpresaDTO obtenerRepresentante(String idEmpresa, String idCliente) {
        log.info("Obteniendo representante {} de empresa {}", idCliente, idEmpresa);

        Empresas empresa = empresaRepo.findById(idEmpresa)
                .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3605));

        if (empresa.getRepresentantes() == null) {
            throw new NotFoundException("Representante no encontrado", 3606);
        }

        RepresentantesEmpresas representante = empresa.getRepresentantes().stream()
                .filter(r -> r.getClienteId().equals(idCliente))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Representante no encontrado", 3606));

        return representanteMapper.toDto(representante);
    }

}