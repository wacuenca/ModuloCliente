package com.banquito.core.clientes.controlador;

import com.banquito.core.clientes.servicio.ClienteService;
import com.banquito.core.clientes.controlador.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // ========== ENDPOINTS PARA PERSONAS ==========

    @PostMapping("/personas")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonaDTO crearPersona(@RequestBody PersonaDTO personaDTO) {
        log.info("Creando nueva persona con identificación: {} {}", 
            personaDTO.getTipoIdentificacion(), personaDTO.getNumeroIdentificacion());
        return clienteService.crearPersona(personaDTO);
    }

    @GetMapping("/personas/{tipo}/{numero}")
    public PersonaDTO obtenerPersona(
            @PathVariable String tipo,
            @PathVariable String numero) {
        log.info("Obteniendo persona con identificación: {} {}", tipo, numero);
        return clienteService.obtenerPersona(tipo, numero);
    }

    @GetMapping("/personas/buscar")
    public List<PersonaDTO> buscarPersonas(
            @RequestParam String nombre) {
        log.info("Buscando personas con nombre similar a: {}", nombre);
        return clienteService.buscarPersonas(nombre);
    }

    @PutMapping("/personas/{id}")
    public PersonaDTO actualizarPersona(
            @PathVariable String id,
            @RequestBody PersonaDTO personaDTO) {
        log.info("Actualizando persona con ID: {}", id);
        return clienteService.actualizarPersona(id, personaDTO);
    }

    // ========== ENDPOINTS PARA EMPRESAS ==========

    @PostMapping("/empresas")
    @ResponseStatus(HttpStatus.CREATED)
    public EmpresasDTO crearEmpresa(@RequestBody EmpresasDTO empresasDTO) {
        log.info("Creando nueva empresa con identificación: {} {}", 
            empresasDTO.getTipoIdentificacion(), empresasDTO.getNumeroIdentificacion());
        return clienteService.crearEmpresa(empresasDTO);
    }

    @GetMapping("/empresas/{tipo}/{numero}")
    public EmpresasDTO obtenerEmpresa(
            @PathVariable String tipo,
            @PathVariable String numero) {
        log.info("Obteniendo empresa con identificación: {} {}", tipo, numero);
        return clienteService.obtenerEmpresa(tipo, numero);
    }

    @GetMapping("/empresas/buscar/razon")
    public List<EmpresasDTO> buscarEmpresasPorRazon(
            @RequestParam String razonSocial) {
        log.info("Buscando empresas por razón social similar a: {}", razonSocial);
        return clienteService.buscarEmpresasPorRazon(razonSocial);
    }

    @GetMapping("/empresas/buscar/nombre")
    public List<EmpresasDTO> buscarEmpresasPorNombre(
            @RequestParam String nombreComercial) {
        log.info("Buscando empresas por nombre comercial similar a: {}", nombreComercial);
        return clienteService.buscarEmpresasPorNombre(nombreComercial);
    }

    @PutMapping("/empresas/{id}")
    public EmpresasDTO actualizarEmpresa(
            @PathVariable String id,
            @RequestBody EmpresasDTO empresasDTO) {
        log.info("Actualizando empresa con ID: {}", id);
        return clienteService.actualizarEmpresa(id, empresasDTO);
    }

    // ========== ENDPOINTS PARA CLIENTES ==========

    @PostMapping("/persona/{idPersona}")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO crearClientePersona(
            @PathVariable String idPersona,
            @RequestBody ClienteDTO clienteDTO) {
        log.info("Creando cliente desde persona con ID: {}", idPersona);
        return clienteService.crearClientePersona(idPersona, clienteDTO);
    }

    @PostMapping("/empresa/{idEmpresa}")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO crearClienteEmpresa(
            @PathVariable String idEmpresa,
            @RequestBody ClienteDTO clienteDTO) {
        log.info("Creando cliente desde empresa con ID: {}", idEmpresa);
        return clienteService.crearClienteEmpresa(idEmpresa, clienteDTO);
    }

    @GetMapping("/{id}")
    public ClienteDTO obtenerClientePorId(
            @PathVariable String id) {
        log.info("Obteniendo cliente con ID: {}", id);
        return clienteService.obtenerCliente(id);
    }

    @GetMapping("/{tipo}/{numero}")
    public ClienteDTO obtenerClientePorIdentificacion(
            @PathVariable String tipo,
            @PathVariable String numero) {
        log.info("Obteniendo cliente con identificación: {} {}", tipo, numero);
        return clienteService.obtenerCliente(tipo, numero);
    }

    @GetMapping("/buscar")
    public List<ClienteDTO> buscarClientes(
            @RequestParam String nombre) {
        log.info("Buscando clientes con nombre similar a: {}", nombre);
        return clienteService.buscarClientes(nombre);
    }

    @PutMapping("/{id}")
    public ClienteDTO actualizarCliente(
            @PathVariable String id,
            @RequestBody ClienteDTO clienteDTO) {
        log.info("Actualizando cliente con ID: {}", id);
        return clienteService.actualizarCliente(id, clienteDTO);
    }

    @PostMapping("/{idCliente}/telefonos")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO agregarTelefonoCliente(
            @PathVariable String idCliente,
            @RequestBody TelefonoClienteDTO telefonoDTO) {
        log.info("Agregando teléfono al cliente con ID: {}", idCliente);
        return clienteService.agregarTelefonoCliente(idCliente, telefonoDTO);
    }

    @DeleteMapping("/{idCliente}/telefonos/{indice}")
    public ResponseEntity<ClienteDTO> eliminarTelefonoCliente(
            @PathVariable String idCliente,
            @PathVariable int indice) {
        log.info("Eliminando telefono al cliente con ID: {}", idCliente);
        ClienteDTO clienteActualizado = clienteService.eliminarTelefonoCliente(idCliente, indice);
        return ResponseEntity.ok(clienteActualizado);
    }

    @PostMapping("/{idCliente}/direcciones")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO agregarDireccionCliente(
            @PathVariable String idCliente,
            @RequestBody DireccionClienteDTO direccionDTO) {
        log.info("Agregando dirección al cliente con ID: {}", idCliente);
        return clienteService.agregarDireccionCliente(idCliente, direccionDTO);
    }

    @PostMapping("/{idCliente}/sucursales")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO agregarSucursalCliente(
            @PathVariable String idCliente,
            @RequestBody ClienteSucursalDTO sucursalDTO) {
        log.info("Agregando sucursal al cliente con ID: {}", idCliente);
        return clienteService.agregarSucursalCliente(idCliente, sucursalDTO);
    }


}