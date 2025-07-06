package com.banquito.core.clientes.controlador;

import com.banquito.core.clientes.controlador.dto.ContactoTransaccionalClienteDTO;
import com.banquito.core.clientes.enums.EstadoRegistro;
import com.banquito.core.clientes.servicio.ContactosTransaccionalesClientesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/clientes/{idCliente}/contactos-transaccionales")
@Tag(name = "Contactos Transaccionales", description = "Gestión de contactos transaccionales asociados a los clientes")
public class ContactosTransaccionalesClientesControlador {

    private final ContactosTransaccionalesClientesService contactoService;

    public ContactosTransaccionalesClientesControlador(ContactosTransaccionalesClientesService contactoService) {
        this.contactoService = contactoService;
    }

    @PostMapping
    @Operation(summary = "Crear contacto transaccional", description = "Crea un nuevo contacto transaccional para un cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contacto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ContactoTransaccionalClienteDTO> crearContacto(
            @PathVariable String idCliente,
            @RequestBody ContactoTransaccionalClienteDTO dto) {
        log.info("Creando contacto transaccional para cliente ID: {}", idCliente);
        ContactoTransaccionalClienteDTO contactoCreado = contactoService.crearContacto(idCliente, dto);
        return ResponseEntity.ok(contactoCreado);
    }

    @GetMapping("/{idContacto}")
    @Operation(summary = "Obtener contacto por ID", description = "Obtiene un contacto transaccional específico de un cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contacto encontrado"),
            @ApiResponse(responseCode = "404", description = "Contacto no encontrado")
    })
    public ResponseEntity<ContactoTransaccionalClienteDTO> obtenerContacto(
            @PathVariable String idCliente,
            @PathVariable String idContacto) {
        log.info("Obteniendo contacto transaccional ID: {} para cliente ID: {}", idContacto, idCliente);
        ContactoTransaccionalClienteDTO contacto = contactoService.obtenerContacto(idCliente);
        return ResponseEntity.ok(contacto);
    }

    @GetMapping
    @Operation(summary = "Obtener contacto actual", description = "Obtiene el contacto transaccional actual del cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contacto encontrado"),
            @ApiResponse(responseCode = "404", description = "Contacto no encontrado")
    })
    public ResponseEntity<ContactoTransaccionalClienteDTO> obtenerContactoCliente(
            @PathVariable String idCliente) {
        log.info("Obteniendo contacto transaccional para cliente ID: {}", idCliente);
        ContactoTransaccionalClienteDTO contacto = contactoService.obtenerContacto(idCliente);
        return ResponseEntity.ok(contacto);
    }

    @PutMapping("/{idContacto}")
    @Operation(summary = "Actualizar contacto transaccional", description = "Actualiza los datos de un contacto transaccional.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contacto actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Contacto no encontrado")
    })
    public ResponseEntity<ContactoTransaccionalClienteDTO> actualizarContacto(
            @PathVariable String idCliente,
            @PathVariable String idContacto,
            @RequestBody ContactoTransaccionalClienteDTO dto) {
        log.info("Actualizando contacto transaccional ID: {} para cliente ID: {}", idContacto, idCliente);
        ContactoTransaccionalClienteDTO actualizado = contactoService.actualizarContacto(idCliente, dto);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{idContacto}/estado")
    @Operation(summary = "Cambiar estado del contacto", description = "Cambia el estado de un contacto transaccional a ACTIVO o INACTIVO.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Contacto no encontrado")
    })
    public ResponseEntity<Void> cambiarEstadoContacto(
            @PathVariable String idCliente,
            @PathVariable String idContacto,
            @RequestParam EstadoRegistro estado) {
        log.info("Cambiando estado de contacto a {} para cliente ID: {}", estado, idCliente);
        contactoService.cambiarEstadoContacto(idCliente, estado);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idContacto}")
    @Operation(summary = "Eliminar contacto transaccional", description = "Elimina un contacto transaccional del cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contacto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Contacto no encontrado")
    })
    public ResponseEntity<Void> eliminarContacto(
            @PathVariable String idCliente,
            @PathVariable String idContacto) {
        log.info("Eliminando contacto transaccional ID: {} para cliente ID: {}", idContacto, idCliente);
        contactoService.eliminarContacto(idCliente);
        return ResponseEntity.noContent().build();
    }
}
