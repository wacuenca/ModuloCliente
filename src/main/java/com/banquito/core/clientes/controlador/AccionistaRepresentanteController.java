package com.banquito.core.clientes.controlador;

import com.banquito.core.clientes.controlador.dto.AccionistasEmpresasDTO;
import com.banquito.core.clientes.controlador.dto.RepresentanteEmpresaDTO;
import com.banquito.core.clientes.enums.EstadoRegistro;
import com.banquito.core.clientes.excepcion.NotFoundException;
import com.banquito.core.clientes.servicio.AccionistaRepresentanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/empresas/{idEmpresa}")
@Tag(name = "Accionistas y Representantes", description = "Gestiona los accionistas y representantes legales de empresas")
public class AccionistaRepresentanteController {

    private final AccionistaRepresentanteService servicio;

    public AccionistaRepresentanteController(AccionistaRepresentanteService servicio) {
        this.servicio = servicio;
    }

    // --- ACCIONISTAS ---

    @PostMapping("/accionistas")
    @Operation(summary = "Crear accionista", description = "Crea un nuevo accionista para la empresa indicada.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Accionista creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o empresa no encontrada")
    })
    public ResponseEntity<AccionistasEmpresasDTO> crearAccionista(
            @PathVariable String idEmpresa,
            @RequestBody AccionistasEmpresasDTO accionistaDTO) {
        log.info("Creando nuevo accionista para empresa ID: {}", idEmpresa);
        AccionistasEmpresasDTO creado = servicio.agregarAccionista(idEmpresa, accionistaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/accionistas/{idParticipe}")
    @Operation(summary = "Actualizar accionista", description = "Actualiza los datos de un accionista existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accionista actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Accionista o empresa no encontrados")
    })
    public ResponseEntity<AccionistasEmpresasDTO> actualizarAccionista(
            @PathVariable String idEmpresa,
            @PathVariable String idParticipe,
            @RequestBody AccionistasEmpresasDTO accionistaDTO) {
        log.info("Actualizando accionista {} en empresa {}", idParticipe, idEmpresa);
        AccionistasEmpresasDTO actualizado = servicio.actualizarAccionista(idEmpresa, idParticipe, accionistaDTO);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/accionistas/{idParticipe}/estado")
    @Operation(summary = "Cambiar estado de accionista", description = "Activa o inactiva un accionista asociado a una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Accionista o empresa no encontrados")
    })
    public ResponseEntity<Void> cambiarEstadoAccionista(
            @PathVariable String idEmpresa,
            @PathVariable String idParticipe,
            @RequestParam EstadoRegistro estado) {
        log.info("Cambiando estado de accionista {} en empresa {} a {}", idParticipe, idEmpresa, estado);
        servicio.cambiarEstadoAccionista(idEmpresa, idParticipe, estado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/accionistas")
    @Operation(summary = "Listar accionistas activos", description = "Obtiene los accionistas activos asociados a una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public ResponseEntity<List<AccionistasEmpresasDTO>> listarAccionistasActivos(@PathVariable String idEmpresa) {
        log.info("Listando accionistas activos para empresa ID: {}", idEmpresa);
        List<AccionistasEmpresasDTO> lista = servicio.listarAccionistasActivos(idEmpresa);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/accionistas/{idParticipe}")
    @Operation(summary = "Obtener un accionista específico", description = "Obtiene los datos de un accionista específico asociado a una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accionista encontrado"),
            @ApiResponse(responseCode = "404", description = "Accionista no encontrado")
    })
    public ResponseEntity<AccionistasEmpresasDTO> obtenerAccionista(
            @PathVariable String idEmpresa,
            @PathVariable String idParticipe) {
        log.info("Obteniendo accionista {} de empresa {}", idParticipe, idEmpresa);
        try {
            AccionistasEmpresasDTO accionista = servicio.obtenerAccionista(idEmpresa, idParticipe);
            return ResponseEntity.ok(accionista);
        } catch (NotFoundException e) {
            log.warn("Accionista no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // --- REPRESENTANTES ---

    @PostMapping("/representantes")
    @Operation(summary = "Crear representante", description = "Crea un nuevo representante legal para una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Representante creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o empresa no encontrada")
    })
    public ResponseEntity<RepresentanteEmpresaDTO> crearRepresentante(
            @PathVariable String idEmpresa,
            @RequestBody RepresentanteEmpresaDTO representanteDTO) {
        log.info("Creando nuevo representante para empresa ID: {}", idEmpresa);
        RepresentanteEmpresaDTO creado = servicio.agregarRepresentante(idEmpresa, representanteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/representantes/{idCliente}")
    @Operation(summary = "Actualizar representante", description = "Actualiza los datos de un representante legal existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Representante actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Representante o empresa no encontrados")
    })
    public ResponseEntity<RepresentanteEmpresaDTO> actualizarRepresentante(
            @PathVariable String idEmpresa,
            @PathVariable String idCliente,
            @RequestBody RepresentanteEmpresaDTO representanteDTO) {
        log.info("Actualizando representante {} en empresa {}", idCliente, idEmpresa);
        RepresentanteEmpresaDTO actualizado = servicio.actualizarRepresentante(idEmpresa, idCliente, representanteDTO);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/representantes/{idCliente}/estado")
    @Operation(summary = "Cambiar estado de representante", description = "Activa o inactiva a un representante legal.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Representante o empresa no encontrados")
    })
    public ResponseEntity<Void> cambiarEstadoRepresentante(
            @PathVariable String idEmpresa,
            @PathVariable String idCliente,
            @RequestParam EstadoRegistro estado) {
        log.info("Cambiando estado de representante {} en empresa {} a {}", idCliente, idEmpresa, estado);
        servicio.cambiarEstadoRepresentante(idEmpresa, idCliente, estado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/representantes")
    @Operation(summary = "Listar representantes activos", description = "Obtiene los representantes legales activos de una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public ResponseEntity<List<RepresentanteEmpresaDTO>> listarRepresentantesActivos(@PathVariable String idEmpresa) {
        log.info("Listando representantes activos para empresa ID: {}", idEmpresa);
        List<RepresentanteEmpresaDTO> lista = servicio.listarRepresentantesActivos(idEmpresa);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/representantes/{idCliente}")
    @Operation(summary = "Obtener un representante específico", description = "Obtiene los datos de un representante específico asociado a una empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Representante encontrado"),
            @ApiResponse(responseCode = "404", description = "Representante no encontrado")
    })
    public ResponseEntity<RepresentanteEmpresaDTO> obtenerRepresentante(
            @PathVariable String idEmpresa,
            @PathVariable String idCliente) {
        log.info("Obteniendo representante {} de empresa {}", idCliente, idEmpresa);
        try {
            RepresentanteEmpresaDTO representante = servicio.obtenerRepresentante(idEmpresa, idCliente);
            return ResponseEntity.ok(representante);
        } catch (NotFoundException e) {
            log.warn("Representante no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
