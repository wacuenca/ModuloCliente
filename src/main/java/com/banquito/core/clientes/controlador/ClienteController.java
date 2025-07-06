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
@RequestMapping("/api/v1/clientes")
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
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "La persona ya existe")
    })
    public PersonaDTO crearPersona(@RequestBody PersonaDTO personaDTO) {
        log.info("Creando nueva persona: {} {}", personaDTO.getTipoIdentificacion(), personaDTO.getNumeroIdentificacion());
        return clienteService.crearPersona(personaDTO);
    }

    @GetMapping("/personas/{tipoIdentificacion}/{numeroIdentificacion}")
    @Operation(summary = "Obtener persona por identificación", 
              description = "Obtiene los datos de una persona según su tipo y número de identificación.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Persona encontrada"),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public PersonaDTO obtenerPersona(
            @PathVariable String tipoIdentificacion, 
            @PathVariable String numeroIdentificacion) {
        log.info("Consultando persona: {} {}", tipoIdentificacion, numeroIdentificacion);
        return clienteService.obtenerPersona(tipoIdentificacion, numeroIdentificacion);
    }

    @GetMapping("/personas")
    @Operation(summary = "Buscar personas por nombre", 
              description = "Busca personas por coincidencia parcial de nombre.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    })
    public List<PersonaDTO> buscarPersonas(
            @RequestParam(required = false) String nombre,
            @RequestParam(defaultValue = "100") int limit) {
        log.info("Buscando personas con filtro nombre: {}", nombre);
        return clienteService.buscarPersonas(nombre != null ? nombre : "");
    }

    @PutMapping("/personas/{tipoIdentificacion}/{numeroIdentificacion}")
    @Operation(summary = "Actualizar persona", 
              description = "Actualiza los datos de una persona existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Persona actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public PersonaDTO actualizarPersona(
            @PathVariable String tipoIdentificacion, 
            @PathVariable String numeroIdentificacion,
            @RequestBody PersonaDTO personaDTO) {
        log.info("Actualizando persona: {} {}", tipoIdentificacion, numeroIdentificacion);
        return clienteService.actualizarPersona(tipoIdentificacion, numeroIdentificacion, personaDTO);
    }

    // ========== EMPRESAS ==========
    @PostMapping("/empresas")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear empresa", description = "Registra una nueva empresa.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Empresa creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "La empresa ya existe")
    })
    public EmpresasDTO crearEmpresa(@RequestBody EmpresasDTO empresasDTO) {
        log.info("Creando nueva empresa: {} {}", empresasDTO.getTipoIdentificacion(), empresasDTO.getNumeroIdentificacion());
        return clienteService.crearEmpresa(empresasDTO);
    }

    @GetMapping("/empresas/{tipoIdentificacion}/{numeroIdentificacion}")
    @Operation(summary = "Obtener empresa por identificación", 
              description = "Obtiene los datos de una empresa por su tipo y número de identificación.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
        @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public EmpresasDTO obtenerEmpresa(
            @PathVariable String tipoIdentificacion, 
            @PathVariable String numeroIdentificacion) {
        log.info("Consultando empresa: {} {}", tipoIdentificacion, numeroIdentificacion);
        return clienteService.obtenerEmpresa(tipoIdentificacion, numeroIdentificacion);
    }

    @GetMapping("/empresas")
    @Operation(summary = "Buscar empresas", 
              description = "Busca empresas por razón social o nombre comercial.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    })
    public List<EmpresasDTO> buscarEmpresas(
            @RequestParam(required = false) String razonSocial,
            @RequestParam(required = false) String nombreComercial,
            @RequestParam(defaultValue = "100") int limit) {
        log.info("Buscando empresas con filtros - razonSocial: {}, nombreComercial: {}", razonSocial, nombreComercial);
        
        if (razonSocial != null) {
            return clienteService.buscarEmpresasPorRazon(razonSocial);
        } else if (nombreComercial != null) {
            return clienteService.buscarEmpresasPorNombre(nombreComercial);
        } else {
            throw new IllegalArgumentException("Debe proporcionar al menos un criterio de búsqueda");
        }
    }

    @PutMapping("/empresas/{tipoIdentificacion}/{numeroIdentificacion}")
    @Operation(summary = "Actualizar empresa", 
              description = "Actualiza los datos de una empresa existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empresa actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public EmpresasDTO actualizarEmpresa(
            @PathVariable String tipoIdentificacion, 
            @PathVariable String numeroIdentificacion,
            @RequestBody EmpresasDTO empresasDTO) {
        log.info("Actualizando empresa: {} {}", tipoIdentificacion, numeroIdentificacion);
        return clienteService.actualizarEmpresa(tipoIdentificacion, numeroIdentificacion, empresasDTO);
    }

    // ========== CLIENTES ==========
    @PostMapping("/personas/{tipoIdentificacion}/{numeroIdentificacion}/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear cliente desde persona", 
              description = "Crea un cliente a partir de una persona registrada.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada"),
        @ApiResponse(responseCode = "409", description = "Cliente ya existe")
    })
    public ClienteDTO crearClienteDesdePersona(
            @PathVariable String tipoIdentificacion, 
            @PathVariable String numeroIdentificacion,
            @RequestBody ClienteDTO clienteDTO) {
        log.info("Creando cliente desde persona: {} {}", tipoIdentificacion, numeroIdentificacion);
        return clienteService.crearClientePersona(tipoIdentificacion, numeroIdentificacion, clienteDTO);
    }

    @PostMapping("/empresas/{tipoIdentificacion}/{numeroIdentificacion}/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear cliente desde empresa", 
              description = "Crea un cliente a partir de una empresa registrada.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Empresa no encontrada"),
        @ApiResponse(responseCode = "409", description = "Cliente ya existe")
    })
    public ClienteDTO crearClienteDesdeEmpresa(
            @PathVariable String tipoIdentificacion, 
            @PathVariable String numeroIdentificacion,
            @RequestBody ClienteDTO clienteDTO) {
        log.info("Creando cliente desde empresa: {} {}", tipoIdentificacion, numeroIdentificacion);
        return clienteService.crearClienteEmpresa(tipoIdentificacion, numeroIdentificacion, clienteDTO);
    }

    @GetMapping("/clientes/{id}")
    @Operation(summary = "Obtener cliente por ID", 
              description = "Obtiene los datos de un cliente por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ClienteDTO obtenerClientePorId(@PathVariable String id) {
        log.info("Consultando cliente con ID: {}", id);
        return clienteService.obtenerCliente(id);
    }

    @GetMapping("/clientes")
    @Operation(summary = "Buscar clientes", 
              description = "Busca clientes por nombre o identificación.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    })
    public List<ClienteDTO> buscarClientes(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String tipoIdentificacion,
            @RequestParam(required = false) String numeroIdentificacion,
            @RequestParam(defaultValue = "100") int limit) {
        
        if (nombre != null) {
            log.info("Buscando clientes por nombre: {}", nombre);
            return clienteService.buscarClientes(nombre);
        } else if (tipoIdentificacion != null && numeroIdentificacion != null) {
            log.info("Buscando cliente por identificación: {} {}", tipoIdentificacion, numeroIdentificacion);
            return List.of(clienteService.obtenerClientePorIdentificacion(tipoIdentificacion, numeroIdentificacion));
        } else {
            throw new IllegalArgumentException("Debe proporcionar nombre o tipo/numero de identificación");
        }
    }

    @PutMapping("/clientes/{tipoIdentificacion}/{numeroIdentificacion}")
    @Operation(summary = "Actualizar cliente", 
              description = "Actualiza los datos de un cliente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ClienteDTO actualizarCliente(
            @PathVariable String tipoIdentificacion, 
            @PathVariable String numeroIdentificacion,
            @RequestBody ClienteDTO clienteDTO) {
        log.info("Actualizando cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
        return clienteService.actualizarCliente(tipoIdentificacion, numeroIdentificacion, clienteDTO);
    }

    // ========== GESTIÓN DE TELÉFONOS ==========
    @PostMapping("/clientes/{tipoIdentificacion}/{numeroIdentificacion}/telefonos")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Agregar teléfono", 
              description = "Agrega un número telefónico a un cliente.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Teléfono agregado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ClienteDTO agregarTelefono(
            @PathVariable String tipoIdentificacion, 
            @PathVariable String numeroIdentificacion,
            @RequestBody TelefonoClienteDTO telefonoDTO) {
        log.info("Agregando teléfono a cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
        return clienteService.agregarTelefonoCliente(tipoIdentificacion, numeroIdentificacion, telefonoDTO);
    }

    @DeleteMapping("/clientes/{tipoIdentificacion}/{numeroIdentificacion}/telefonos/{indice}")
    @Operation(summary = "Eliminar teléfono", 
              description = "Inactiva un número telefónico del cliente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Teléfono inactivado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente o teléfono no encontrado")
    })
    public ResponseEntity<ClienteDTO> eliminarTelefono(
            @PathVariable String tipoIdentificacion, 
            @PathVariable String numeroIdentificacion,
            @PathVariable int indice) {
        log.info("Inactivando teléfono {} del cliente: {} {}", indice, tipoIdentificacion, numeroIdentificacion);
        ClienteDTO clienteActualizado = clienteService.eliminarTelefonoCliente(tipoIdentificacion, numeroIdentificacion, indice);
        return ResponseEntity.ok(clienteActualizado);
    }

    // ========== GESTIÓN DE DIRECCIONES ==========
    @PostMapping("/clientes/{tipoIdentificacion}/{numeroIdentificacion}/direcciones")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Agregar dirección", 
              description = "Agrega una nueva dirección al cliente.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Dirección agregada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ClienteDTO agregarDireccion(
            @PathVariable String tipoIdentificacion, 
            @PathVariable String numeroIdentificacion,
            @RequestBody DireccionClienteDTO direccionDTO) {
        log.info("Agregando dirección a cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
        return clienteService.agregarDireccionCliente(tipoIdentificacion, numeroIdentificacion, direccionDTO);
    }

    // ========== GESTIÓN DE SUCURSALES ==========
    @PostMapping("/clientes/{tipoIdentificacion}/{numeroIdentificacion}/sucursales")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Agregar sucursal", 
              description = "Asocia una sucursal a un cliente.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Sucursal asociada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ClienteDTO agregarSucursal(
            @PathVariable String tipoIdentificacion, 
            @PathVariable String numeroIdentificacion,
            @RequestBody ClienteSucursalDTO sucursalDTO) {
        log.info("Agregando sucursal a cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
        return clienteService.agregarSucursalCliente(tipoIdentificacion, numeroIdentificacion, sucursalDTO);
    }
}