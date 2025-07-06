package com.banquito.core.clientes.controlador;

import com.banquito.core.clientes.excepcion.NotFoundException;
import com.banquito.core.clientes.servicio.AccionistaRepresentanteService;
import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.enums.EstadoRegistro;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/accionistas-representantes")
@Tag(name = "Accionistas y Representantes", description = "Gestiona los accionistas y representantes legales de empresas")
public class AccionistaRepresentanteController {

    private final AccionistaRepresentanteService servicio;

    public AccionistaRepresentanteController(AccionistaRepresentanteService servicio) {
        this.servicio = servicio;
    }

    // ========== ENDPOINTS PARA ACCIONISTAS ==========

    @PostMapping("/empresas/{idEmpresa}/accionistas")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear accionista", description = "Crea un nuevo accionista para la empresa indicada.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Accionista creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o empresa no encontrada")
    })
    public AccionistasEmpresasDTO crearAccionista(
            @PathVariable String idEmpresa,
            @RequestBody AccionistasEmpresasDTO accionistaDTO) {
        log.info("Creando nuevo accionista para empresa ID: {}", idEmpresa);
        return servicio.agregarAccionista(idEmpresa, accionistaDTO);
    }

    @PutMapping("/empresas/{idEmpresa}/accionistas/{idParticipe}")
    @Operation(summary = "Actualizar accionista", description = "Actualiza los datos de un accionista existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accionista actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Accionista o empresa no encontrados")
    })
    public AccionistasEmpresasDTO actualizarAccionista(
            @PathVariable String idEmpresa,
            @PathVariable String idParticipe,
            @RequestBody AccionistasEmpresasDTO accionistaDTO) {
        log.info("Actualizando accionista {} en empresa {}", idParticipe, idEmpresa);
        return servicio.actualizarAccionista(idEmpresa, idParticipe, accionistaDTO);
    }

    @PatchMapping("/empresas/{idEmpresa}/accionistas/{idParticipe}/estado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Cambiar estado de accionista", description = "Activa o inactiva un accionista asociado a una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Accionista o empresa no encontrados")
    })
    public void cambiarEstadoAccionista(
            @PathVariable String idEmpresa,
            @PathVariable String idParticipe,
            @RequestParam EstadoRegistro estado) {
        log.info("Cambiando estado de accionista {} en empresa {} a {}", idParticipe, idEmpresa, estado);
        servicio.cambiarEstadoAccionista(idEmpresa, idParticipe, estado);
    }

    @GetMapping("/empresas/{idEmpresa}/accionistas")
    @Operation(summary = "Listar accionistas activos", description = "Obtiene los accionistas activos asociados a una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public List<AccionistasEmpresasDTO> listarAccionistasActivos(@PathVariable String idEmpresa) {
        log.info("Listando accionistas activos para empresa ID: {}", idEmpresa);
        return servicio.listarAccionistasActivos(idEmpresa);
    }

    @GetMapping("/empresas/{idEmpresa}/accionistas/{idParticipe}")
    @Operation(summary = "Obtener un accionista específico", description = "Obtiene los datos de un accionista específico asociado a una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accionista encontrado"),
            @ApiResponse(responseCode = "404", description = "Accionista no encontrado")
    })
    public ResponseEntity<AccionistasEmpresasDTO> obtenerAccionista(
            @PathVariable String idEmpresa,
            @PathVariable String idParticipe) {
        try {
            AccionistasEmpresasDTO accionista = servicio
                    .listarAccionistasActivos(idEmpresa).stream()
                    .filter(a -> a.getIdParticipe().equals(idParticipe))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Accionista no encontrado"));

            return ResponseEntity.ok(accionista);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // ========== ENDPOINTS PARA REPRESENTANTES ==========

    @PostMapping("/empresas/{idEmpresa}/representantes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear representante", description = "Crea un nuevo representante legal para una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Representante creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o empresa no encontrada")
    })
    public RepresentanteEmpresaDTO crearRepresentante(
            @PathVariable String idEmpresa,
            @RequestBody RepresentanteEmpresaDTO representanteDTO) {
        log.info("Creando nuevo representante para empresa ID: {}", idEmpresa);
        return servicio.agregarRepresentante(idEmpresa, representanteDTO);
    }

    @PutMapping("/empresas/{idEmpresa}/representantes/{idCliente}")
    @Operation(summary = "Actualizar representante", description = "Actualiza los datos de un representante legal existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Representante actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Representante o empresa no encontrados")
    })
    public RepresentanteEmpresaDTO actualizarRepresentante(
            @PathVariable String idEmpresa,
            @PathVariable String idCliente,
            @RequestBody RepresentanteEmpresaDTO representanteDTO) {
        log.info("Actualizando representante {} en empresa {}", idCliente, idEmpresa);
        return servicio.actualizarRepresentante(idEmpresa, idCliente, representanteDTO);
    }

    @PatchMapping("/empresas/{idEmpresa}/representantes/{idCliente}/estado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Cambiar estado de representante", description = "Activa o inactiva a un representante legal.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Representante o empresa no encontrados")
    })
    public void cambiarEstadoRepresentante(
            @PathVariable String idEmpresa,
            @PathVariable String idCliente,
            @RequestParam EstadoRegistro estado) {
        log.info("Cambiando estado de representante {} en empresa {} a {}", idCliente, idEmpresa, estado);
        servicio.cambiarEstadoRepresentante(idEmpresa, idCliente, estado);
    }

    @GetMapping("/empresas/{idEmpresa}/representantes")
    @Operation(summary = "Listar representantes activos", description = "Obtiene los representantes legales activos de una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public List<RepresentanteEmpresaDTO> listarRepresentantesActivos(@PathVariable String idEmpresa) {
        log.info("Listando representantes activos para empresa ID: {}", idEmpresa);
        return servicio.listarRepresentantesActivos(idEmpresa);
    }

    @GetMapping("/empresas/{idEmpresa}/representantes/{idCliente}")
    @Operation(summary = "Obtener un representante específico", description = "Obtiene los datos de un representante específico asociado a una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Representante encontrado"),
            @ApiResponse(responseCode = "404", description = "Representante no encontrado")
    })
    public ResponseEntity<RepresentanteEmpresaDTO> obtenerRepresentante(
            @PathVariable String idEmpresa,
            @PathVariable String idCliente) {
        try {
            RepresentanteEmpresaDTO representante = servicio
                    .listarRepresentantesActivos(idEmpresa).stream()
                    .filter(r -> r.getIdCliente().equals(idCliente))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Representante no encontrado"));

            return ResponseEntity.ok(representante);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
