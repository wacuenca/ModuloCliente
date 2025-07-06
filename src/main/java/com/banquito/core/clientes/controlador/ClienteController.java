package com.banquito.core.clientes.controlador;

import com.banquito.core.clientes.servicio.ClienteService;
import com.banquito.core.clientes.controlador.dto.*;
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
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gestión de personas, empresas y clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // ========== PERSONAS ==========

    @PostMapping("/personas")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear persona", description = "Registra una nueva persona natural.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Persona creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public PersonaDTO crearPersona(@RequestBody PersonaDTO personaDTO) {
        log.info("Creando nueva persona con identificación: {} {}",
                personaDTO.getTipoIdentificacion(), personaDTO.getNumeroIdentificacion());
        return clienteService.crearPersona(personaDTO);
    }

    @GetMapping("/personas/{tipo}/{numero}")
    @Operation(summary = "Obtener persona por identificación", description = "Obtiene los datos de una persona según su tipo y número de identificación.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public PersonaDTO obtenerPersona(@PathVariable String tipo, @PathVariable String numero) {
        log.info("Obteniendo persona con identificación: {} {}", tipo, numero);
        return clienteService.obtenerPersona(tipo, numero);
    }

    @GetMapping("/personas/buscar")
    @Operation(summary = "Buscar personas por nombre", description = "Busca personas por coincidencia parcial de nombre.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    })
    public List<PersonaDTO> buscarPersonas(@RequestParam String nombre) {
        log.info("Buscando personas con nombre similar a: {}", nombre);
        return clienteService.buscarPersonas(nombre);
    }

    @PutMapping("/personas/{id}")
    @Operation(summary = "Actualizar persona", description = "Actualiza los datos de una persona existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public PersonaDTO actualizarPersona(@PathVariable String id, @RequestBody PersonaDTO personaDTO) {
        log.info("Actualizando persona con ID: {}", id);
        return clienteService.actualizarPersona(id, personaDTO);
    }

    // ========== EMPRESAS ==========

    @PostMapping("/empresas")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear empresa", description = "Registra una nueva empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empresa creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public EmpresasDTO crearEmpresa(@RequestBody EmpresasDTO empresasDTO) {
        log.info("Creando nueva empresa con identificación: {} {}",
                empresasDTO.getTipoIdentificacion(), empresasDTO.getNumeroIdentificacion());
        return clienteService.crearEmpresa(empresasDTO);
    }

    @GetMapping("/empresas/{tipo}/{numero}")
    @Operation(summary = "Obtener empresa por identificación", description = "Obtiene los datos de una empresa por su tipo y número de identificación.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public EmpresasDTO obtenerEmpresa(@PathVariable String tipo, @PathVariable String numero) {
        log.info("Obteniendo empresa con identificación: {} {}", tipo, numero);
        return clienteService.obtenerEmpresa(tipo, numero);
    }

    @GetMapping("/empresas/buscar/razon")
    @Operation(summary = "Buscar empresas por razón social", description = "Busca empresas por coincidencia parcial de razón social.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    })
    public List<EmpresasDTO> buscarEmpresasPorRazon(@RequestParam String razonSocial) {
        log.info("Buscando empresas por razón social similar a: {}", razonSocial);
        return clienteService.buscarEmpresasPorRazon(razonSocial);
    }

    @GetMapping("/empresas/buscar/nombre")
    @Operation(summary = "Buscar empresas por nombre comercial", description = "Busca empresas por coincidencia parcial de nombre comercial.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    })
    public List<EmpresasDTO> buscarEmpresasPorNombre(@RequestParam String nombreComercial) {
        log.info("Buscando empresas por nombre comercial similar a: {}", nombreComercial);
        return clienteService.buscarEmpresasPorNombre(nombreComercial);
    }

    @PutMapping("/empresas/{id}")
    @Operation(summary = "Actualizar empresa", description = "Actualiza los datos de una empresa existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public EmpresasDTO actualizarEmpresa(@PathVariable String id, @RequestBody EmpresasDTO empresasDTO) {
        log.info("Actualizando empresa con ID: {}", id);
        return clienteService.actualizarEmpresa(id, empresasDTO);
    }

    // ========== CLIENTES ==========

    @PostMapping("/persona/{idPersona}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear cliente desde persona", description = "Crea un cliente a partir de una persona registrada.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public ClienteDTO crearClientePersona(@PathVariable String idPersona, @RequestBody ClienteDTO clienteDTO) {
        log.info("Creando cliente desde persona con ID: {}", idPersona);
        return clienteService.crearClientePersona(idPersona, clienteDTO);
    }

    @PostMapping("/empresa/{idEmpresa}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear cliente desde empresa", description = "Crea un cliente a partir de una empresa registrada.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public ClienteDTO crearClienteEmpresa(@PathVariable String idEmpresa, @RequestBody ClienteDTO clienteDTO) {
        log.info("Creando cliente desde empresa con ID: {}", idEmpresa);
        return clienteService.crearClienteEmpresa(idEmpresa, clienteDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Obtiene los datos de un cliente por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ClienteDTO obtenerClientePorId(@PathVariable String id) {
        log.info("Obteniendo cliente con ID: {}", id);
        return clienteService.obtenerCliente(id);
    }

    @GetMapping("/{tipo}/{numero}")
    @Operation(summary = "Obtener cliente por identificación", description = "Obtiene los datos de un cliente por tipo y número de identificación.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ClienteDTO obtenerClientePorIdentificacion(@PathVariable String tipo, @PathVariable String numero) {
        log.info("Obteniendo cliente con identificación: {} {}", tipo, numero);
        return clienteService.obtenerCliente(tipo, numero);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar clientes por nombre", description = "Busca clientes por coincidencia parcial del nombre.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    })
    public List<ClienteDTO> buscarClientes(@RequestParam String nombre) {
        log.info("Buscando clientes con nombre similar a: {}", nombre);
        return clienteService.buscarClientes(nombre);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ClienteDTO actualizarCliente(@PathVariable String id, @RequestBody ClienteDTO clienteDTO) {
        log.info("Actualizando cliente con ID: {}", id);
        return clienteService.actualizarCliente(id, clienteDTO);
    }

    // ========== GESTIÓN DE TELÉFONOS, DIRECCIONES Y SUCURSALES ==========

    @PostMapping("/{idCliente}/telefonos")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Agregar teléfono al cliente", description = "Agrega un número telefónico a un cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Teléfono agregado exitosamente")
    })
    public ClienteDTO agregarTelefonoCliente(@PathVariable String idCliente, @RequestBody TelefonoClienteDTO telefonoDTO) {
        log.info("Agregando teléfono al cliente con ID: {}", idCliente);
        return clienteService.agregarTelefonoCliente(idCliente, telefonoDTO);
    }

    @DeleteMapping("/{idCliente}/telefonos/{indice}")
    @Operation(summary = "Eliminar teléfono del cliente", description = "Elimina un número telefónico del cliente según su índice en la lista.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teléfono eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente o teléfono no encontrado")
    })
    public ResponseEntity<ClienteDTO> eliminarTelefonoCliente(@PathVariable String idCliente, @PathVariable int indice) {
        log.info("Eliminando telefono al cliente con ID: {}", idCliente);
        ClienteDTO clienteActualizado = clienteService.eliminarTelefonoCliente(idCliente, indice);
        return ResponseEntity.ok(clienteActualizado);
    }

    @PostMapping("/{idCliente}/direcciones")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Agregar dirección al cliente", description = "Agrega una nueva dirección al cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dirección agregada exitosamente")
    })
    public ClienteDTO agregarDireccionCliente(@PathVariable String idCliente, @RequestBody DireccionClienteDTO direccionDTO) {
        log.info("Agregando dirección al cliente con ID: {}", idCliente);
        return clienteService.agregarDireccionCliente(idCliente, direccionDTO);
    }

    @PostMapping("/{idCliente}/sucursales")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Agregar sucursal al cliente", description = "Asocia una sucursal a un cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sucursal asociada exitosamente")
    })
    public ClienteDTO agregarSucursalCliente(@PathVariable String idCliente, @RequestBody ClienteSucursalDTO sucursalDTO) {
        log.info("Agregando sucursal al cliente con ID: {}", idCliente);
        return clienteService.agregarSucursalCliente(idCliente, sucursalDTO);
    }
}
