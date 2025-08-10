package com.banquito.core.clientes.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.clientes.cliente.CuentasServiceClient.CuentasClientesRespuestaDTO;
import com.banquito.core.clientes.servicio.CuentasClientesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/clientes/v1")
@Tag(name = "Cuentas Clientes", description = "Gestión de cuentas de clientes a través del microservicio de cuentas")
public class CuentasClientesController {

    private final CuentasClientesService cuentasClientesService;

    public CuentasClientesController(CuentasClientesService cuentasClientesService) {
        this.cuentasClientesService = cuentasClientesService;
    }

    @PostMapping("/clientes/{cedulaCliente}/cuentas/ahorros")
    @Operation(summary = "Crear cuenta de ahorros", description = "Crea una cuenta de ahorros para un cliente usando la cuenta maestra predeterminada")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o cliente no encontrado"),
            @ApiResponse(responseCode = "409", description = "Ya existe una cuenta para este cliente"),
            @ApiResponse(responseCode = "503", description = "Servicio de cuentas no disponible")
    })
    public ResponseEntity<CuentasClientesRespuestaDTO> crearCuentaAhorros(
            @PathVariable String cedulaCliente) {
        log.info("Solicitud para crear cuenta de ahorros para cliente: {}", cedulaCliente);
        CuentasClientesRespuestaDTO cuenta = cuentasClientesService.crearCuentaAhorros(cedulaCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuenta);
    }

    @PostMapping("/clientes/{cedulaCliente}/cuentas/{idCuentaMaestra}")
    @Operation(summary = "Crear cuenta específica", description = "Crea una cuenta para un cliente con un tipo específico de cuenta maestra")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente o cuenta maestra no encontrada"),
            @ApiResponse(responseCode = "503", description = "Servicio de cuentas no disponible")
    })
    public ResponseEntity<CuentasClientesRespuestaDTO> crearCuentaCliente(
            @PathVariable String cedulaCliente,
            @PathVariable Integer idCuentaMaestra) {
        log.info("Solicitud para crear cuenta tipo {} para cliente: {}", idCuentaMaestra, cedulaCliente);
        CuentasClientesRespuestaDTO cuenta = cuentasClientesService.crearCuentaCliente(idCuentaMaestra, cedulaCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuenta);
    }

    @GetMapping("/cuentas/{idCuentaCliente}")
    @Operation(summary = "Obtener cuenta cliente", description = "Obtiene los datos de una cuenta cliente por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada"),
            @ApiResponse(responseCode = "503", description = "Servicio de cuentas no disponible")
    })
    public ResponseEntity<CuentasClientesRespuestaDTO> obtenerCuentaCliente(
            @PathVariable Integer idCuentaCliente) {
        log.info("Solicitud para obtener cuenta cliente con ID: {}", idCuentaCliente);
        CuentasClientesRespuestaDTO cuenta = cuentasClientesService.obtenerCuentaCliente(idCuentaCliente);
        return ResponseEntity.ok(cuenta);
    }
}
