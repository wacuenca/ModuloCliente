package com.banquito.core.clientes.controlador;

import com.banquito.core.clientes.servicio.AccionistaRepresentanteService;
import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.enums.EstadoRegistro;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/accionistas-representantes")
public class AccionistaRepresentanteController {

    private final AccionistaRepresentanteService servicio;

    public AccionistaRepresentanteController(AccionistaRepresentanteService servicio) {
        this.servicio = servicio;
    }

    // ========== ENDPOINTS PARA ACCIONISTAS ==========

    @PostMapping("/empresas/{idEmpresa}/accionistas")
    @ResponseStatus(HttpStatus.CREATED)
    public AccionistasEmpresasDTO crearAccionista(
            @PathVariable String idEmpresa,
            @RequestBody AccionistasEmpresasDTO accionistaDTO) {
        log.info("Creando nuevo accionista para empresa ID: {}", idEmpresa);
        return servicio.agregarAccionista(idEmpresa, accionistaDTO);
    }

    @PutMapping("/empresas/{idEmpresa}/accionistas/{idParticipe}")
    public AccionistasEmpresasDTO actualizarAccionista(
            @PathVariable String idEmpresa,
            @PathVariable String idParticipe, 
            @RequestBody AccionistasEmpresasDTO accionistaDTO) {
        log.info("Actualizando accionista {} en empresa {}", idParticipe, idEmpresa);
        return servicio.actualizarAccionista(idEmpresa, idParticipe, accionistaDTO);
    }

    @PatchMapping("/empresas/{idEmpresa}/accionistas/{idParticipe}/estado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cambiarEstadoAccionista(
            @PathVariable String idEmpresa,
            @PathVariable String idParticipe,
            @RequestParam EstadoRegistro estado) {
        log.info("Cambiando estado de accionista {} en empresa {} a {}", idParticipe, idEmpresa, estado);
        servicio.cambiarEstadoAccionista(idEmpresa, idParticipe, estado);
    }

    @GetMapping("/empresas/{idEmpresa}/accionistas")
    public List<AccionistasEmpresasDTO> listarAccionistasActivos(
            @PathVariable String idEmpresa) {
        log.info("Listando accionistas activos para empresa ID: {}", idEmpresa);
        return servicio.listarAccionistasActivos(idEmpresa);
    }

    // ========== ENDPOINTS PARA REPRESENTANTES ==========

    @PostMapping("/empresas/{idEmpresa}/representantes")
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentanteEmpresaDTO crearRepresentante(
            @PathVariable String idEmpresa,
            @RequestBody RepresentanteEmpresaDTO representanteDTO) {
        log.info("Creando nuevo representante para empresa ID: {}", idEmpresa);
        return servicio.agregarRepresentante(idEmpresa, representanteDTO);
    }

    @PutMapping("/empresas/{idEmpresa}/representantes/{idCliente}")
    public RepresentanteEmpresaDTO actualizarRepresentante(
            @PathVariable String idEmpresa,
            @PathVariable String idCliente,
            @RequestBody RepresentanteEmpresaDTO representanteDTO) {
        log.info("Actualizando representante {} en empresa {}", idCliente, idEmpresa);
        return servicio.actualizarRepresentante(idEmpresa, idCliente, representanteDTO);
    }

    @PatchMapping("/empresas/{idEmpresa}/representantes/{idCliente}/estado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cambiarEstadoRepresentante(
            @PathVariable String idEmpresa,
            @PathVariable String idCliente,
            @RequestParam EstadoRegistro estado) {
        log.info("Cambiando estado de representante {} en empresa {} a {}", idCliente, idEmpresa, estado);
        servicio.cambiarEstadoRepresentante(idEmpresa, idCliente, estado);
    }

    @GetMapping("/empresas/{idEmpresa}/representantes")
    public List<RepresentanteEmpresaDTO> listarRepresentantesActivos(
            @PathVariable String idEmpresa) {
        log.info("Listando representantes activos para empresa ID: {}", idEmpresa);
        return servicio.listarRepresentantesActivos(idEmpresa);
    }
}