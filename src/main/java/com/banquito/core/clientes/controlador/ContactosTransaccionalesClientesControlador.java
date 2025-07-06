package com.banquito.core.clientes.controlador;

import com.banquito.core.clientes.controlador.dto.ContactoTransaccionalClienteDTO;
import com.banquito.core.clientes.enums.EstadoRegistro;
import com.banquito.core.clientes.servicio.ContactosTransaccionalesClientesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/clientes/{idCliente}/contactos-transaccionales")
public class ContactosTransaccionalesClientesControlador {

    private final ContactosTransaccionalesClientesService contactoService;

    public ContactosTransaccionalesClientesControlador(ContactosTransaccionalesClientesService contactoService) {
        this.contactoService = contactoService;
    }

    @PostMapping
    public ResponseEntity<ContactoTransaccionalClienteDTO> crearContacto(
            @PathVariable String idCliente,
            @RequestBody ContactoTransaccionalClienteDTO dto) {
        log.info("Creando contacto transaccional para cliente ID: {}", idCliente);
        ContactoTransaccionalClienteDTO contactoCreado = contactoService.crearContacto(idCliente, dto);
        return ResponseEntity.ok(contactoCreado);
    }

    @GetMapping("/{idContacto}")
    public ResponseEntity<ContactoTransaccionalClienteDTO> obtenerContacto(
            @PathVariable String idCliente,
            @PathVariable String idContacto) {
        log.info("Obteniendo contacto transaccional para cliente ID: {}", idCliente);
        ContactoTransaccionalClienteDTO contacto = contactoService.obtenerContacto(idCliente);
        return ResponseEntity.ok(contacto);
    }

    @GetMapping
    public ResponseEntity<ContactoTransaccionalClienteDTO> obtenerContactoCliente(
            @PathVariable String idCliente) {
        log.info("Obteniendo contacto transaccional para cliente ID: {}", idCliente);
        ContactoTransaccionalClienteDTO contacto = contactoService.obtenerContacto(idCliente);
        return ResponseEntity.ok(contacto);
    }

    @PutMapping("/{idContacto}")
    public ResponseEntity<ContactoTransaccionalClienteDTO> actualizarContacto(
            @PathVariable String idCliente,
            @PathVariable String idContacto,
            @RequestBody ContactoTransaccionalClienteDTO dto) {
        log.info("Actualizando contacto transaccional para cliente ID: {}", idCliente);
        ContactoTransaccionalClienteDTO actualizado = contactoService.actualizarContacto(idCliente, dto);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{idContacto}/estado")
    public ResponseEntity<Void> cambiarEstadoContacto(
            @PathVariable String idCliente,
            @PathVariable String idContacto,
            @RequestParam EstadoRegistro estado) {
        log.info("Cambiando estado de contacto a {} para cliente ID: {}", estado, idCliente);
        contactoService.cambiarEstadoContacto(idCliente, estado);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idContacto}")
    public ResponseEntity<Void> eliminarContacto(
            @PathVariable String idCliente,
            @PathVariable String idContacto) {
        log.info("Eliminando contacto transaccional para cliente ID: {}", idCliente);
        contactoService.eliminarContacto(idCliente);
        return ResponseEntity.noContent().build();
    }
}