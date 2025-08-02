package com.banquito.core.clientes.controlador;

import com.banquito.core.clientes.excepcion.NotFoundException;
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

    // ====== PERSONAS ======

    @PostMapping("/personas")
    @Operation(summary = "Crear persona", description = "Registra una nueva persona natural.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Persona creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "La persona ya existe")
    })
    public ResponseEntity<PersonaDTO> crearPersona(@RequestBody PersonaDTO personaDTO) {
        log.info("Creando nueva persona: {} {}", personaDTO.getTipoIdentificacion(),
                personaDTO.getNumeroIdentificacion());
        PersonaDTO creado = clienteService.crearPersona(personaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/personas/{tipoIdentificacion}/{numeroIdentificacion}")
    @Operation(summary = "Obtener persona por identificación", description = "Obtiene los datos de una persona según su tipo y número de identificación.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public ResponseEntity<PersonaDTO> obtenerPersona(
            @PathVariable String tipoIdentificacion,
            @PathVariable String numeroIdentificacion) {
        log.info("Consultando persona: {} {}", tipoIdentificacion, numeroIdentificacion);
        try {
            PersonaDTO persona = clienteService.obtenerPersona(tipoIdentificacion, numeroIdentificacion);
            return ResponseEntity.ok(persona);
        } catch (NotFoundException e) {
            log.warn("Persona no encontrada: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/personas")
    @Operation(summary = "Buscar personas por nombre", description = "Busca personas por coincidencia parcial de nombre.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    public ResponseEntity<List<PersonaDTO>> buscarPersonas(
            @RequestParam(required = false, defaultValue = "") String nombre,
            @RequestParam(defaultValue = "100") int limit) {
        log.info("Buscando personas con filtro nombre: {}", nombre);
        List<PersonaDTO> lista = clienteService.buscarPersonas(nombre);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/personas/{tipoIdentificacion}/{numeroIdentificacion}")
    @Operation(summary = "Actualizar persona", description = "Actualiza los datos de una persona existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public ResponseEntity<PersonaDTO> actualizarPersona(
            @PathVariable String tipoIdentificacion,
            @PathVariable String numeroIdentificacion,
            @RequestBody PersonaDTO personaDTO) {
        log.info("Actualizando persona: {} {}", tipoIdentificacion, numeroIdentificacion);
        try {
            PersonaDTO actualizado = clienteService.actualizarPersona(tipoIdentificacion, numeroIdentificacion, personaDTO);
            return ResponseEntity.ok(actualizado);
        } catch (NotFoundException e) {
            log.warn("Persona no encontrada: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ====== EMPRESAS ======

    @PostMapping("/empresas")
    @Operation(summary = "Crear empresa", description = "Registra una nueva empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empresa creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "La empresa ya existe")
    })
    public ResponseEntity<EmpresasDTO> crearEmpresa(@RequestBody EmpresasDTO empresasDTO) {
        log.info("Creando nueva empresa: {} {}", empresasDTO.getTipoIdentificacion(),
                empresasDTO.getNumeroIdentificacion());
        EmpresasDTO creado = clienteService.crearEmpresa(empresasDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/empresas/{tipoIdentificacion}/{numeroIdentificacion}")
    @Operation(summary = "Obtener empresa por identificación", description = "Obtiene los datos de una empresa por su tipo y número de identificación.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public ResponseEntity<EmpresasDTO> obtenerEmpresa(
            @PathVariable String tipoIdentificacion,
            @PathVariable String numeroIdentificacion) {
        log.info("Consultando empresa: {} {}", tipoIdentificacion, numeroIdentificacion);
        try {
            EmpresasDTO empresa = clienteService.obtenerEmpresa(tipoIdentificacion, numeroIdentificacion);
            return ResponseEntity.ok(empresa);
        } catch (NotFoundException e) {
            log.warn("Empresa no encontrada: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/empresas")
    @Operation(summary = "Buscar empresas", description = "Busca empresas por razón social o nombre comercial.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    public ResponseEntity<List<EmpresasDTO>> buscarEmpresas(
            @RequestParam(required = false) String razonSocial,
            @RequestParam(required = false) String nombreComercial,
            @RequestParam(defaultValue = "100") int limit) {
        log.info("Buscando empresas con filtros - razonSocial: {}, nombreComercial: {}", razonSocial, nombreComercial);
        List<EmpresasDTO> lista;

        if (razonSocial != null) {
            lista = clienteService.buscarEmpresasPorRazon(razonSocial);
        } else if (nombreComercial != null) {
            lista = clienteService.buscarEmpresasPorNombre(nombreComercial);
        } else {
            throw new IllegalArgumentException("Debe proporcionar al menos un criterio de búsqueda");
        }

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/empresas/{tipoIdentificacion}/{numeroIdentificacion}")
    @Operation(summary = "Actualizar empresa", description = "Actualiza los datos de una empresa existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public ResponseEntity<EmpresasDTO> actualizarEmpresa(
            @PathVariable String tipoIdentificacion,
            @PathVariable String numeroIdentificacion,
            @RequestBody EmpresasDTO empresasDTO) {
        log.info("Actualizando empresa: {} {}", tipoIdentificacion, numeroIdentificacion);
        try {
            EmpresasDTO actualizado = clienteService.actualizarEmpresa(tipoIdentificacion, numeroIdentificacion, empresasDTO);
            return ResponseEntity.ok(actualizado);
        } catch (NotFoundException e) {
            log.warn("Empresa no encontrada: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ====== CLIENTES ======

    @PostMapping("/personas/{tipoIdentificacion}/{numeroIdentificacion}/clientes")
    @Operation(summary = "Crear cliente desde persona", description = "Crea un cliente a partir de una persona registrada.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada"),
            @ApiResponse(responseCode = "409", description = "Cliente ya existe")
    })
    public ResponseEntity<ClienteDTO> crearClienteDesdePersona(
            @PathVariable String tipoIdentificacion,
            @PathVariable String numeroIdentificacion,
            @RequestBody ClienteDTO clienteDTO) {
        log.info("Creando cliente desde persona: {} {}", tipoIdentificacion, numeroIdentificacion);
        ClienteDTO creado = clienteService.crearClientePersona(tipoIdentificacion, numeroIdentificacion, clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PostMapping("/empresas/{tipoIdentificacion}/{numeroIdentificacion}/clientes")
    @Operation(summary = "Crear cliente desde empresa", description = "Crea un cliente a partir de una empresa registrada.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada"),
            @ApiResponse(responseCode = "409", description = "Cliente ya existe")
    })
    public ResponseEntity<ClienteDTO> crearClienteDesdeEmpresa(
            @PathVariable String tipoIdentificacion,
            @PathVariable String numeroIdentificacion,
            @RequestBody ClienteDTO clienteDTO) {
        log.info("Creando cliente desde empresa: {} {}", tipoIdentificacion, numeroIdentificacion);
        ClienteDTO creado = clienteService.crearClienteEmpresa(tipoIdentificacion, numeroIdentificacion, clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/clientes/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Obtiene los datos de un cliente por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable String id) {
        log.info("Consultando cliente con ID: {}", id);
        try {
            ClienteDTO cliente = clienteService.obtenerCliente(id);
            return ResponseEntity.ok(cliente);
        } catch (NotFoundException e) {
            log.warn("Cliente no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/clientes")
    @Operation(summary = "Buscar clientes", description = "Busca clientes por nombre o identificación.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    public ResponseEntity<List<ClienteDTO>> buscarClientes(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String tipoIdentificacion,
            @RequestParam(required = false) String numeroIdentificacion,
            @RequestParam(defaultValue = "100") int limit) {

        List<ClienteDTO> lista;

        if (nombre != null) {
            log.info("Buscando clientes por nombre: {}", nombre);
            lista = clienteService.buscarClientes(nombre);
        } else if (tipoIdentificacion != null && numeroIdentificacion != null) {
            log.info("Buscando cliente por identificación: {} {}", tipoIdentificacion, numeroIdentificacion);
            lista = List.of(clienteService.obtenerClientePorIdentificacion(tipoIdentificacion, numeroIdentificacion));
        } else {
            throw new IllegalArgumentException("Debe proporcionar nombre o tipo/numero de identificación");
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/listar-por-tipo-entidad")
    @Operation(summary = "Listar clientes por tipoEntidad", description = "Lista clientes por tipoEntidad (EMPRESA o PERSONA).")
    @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    public ResponseEntity<List<ClienteDTO>> listarPorTipoEntidad(@RequestParam String tipoEntidad) {
        List<ClienteDTO> lista = clienteService.listarPorTipoEntidad(tipoEntidad);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/contar-por-tipo-entidad")
    @Operation(summary = "Contar clientes por tipoEntidad", description = "Cuenta clientes por tipoEntidad (EMPRESA o PERSONA).")
    @ApiResponse(responseCode = "200", description = "Conteo obtenido exitosamente")
    public ResponseEntity<Long> contarPorTipoEntidad(@RequestParam String tipoEntidad) {
        long conteo = clienteService.contarPorTipoEntidad(tipoEntidad);
        return ResponseEntity.ok(conteo);
    }

    @GetMapping("/listar-por-tipo-identificacion")
    @Operation(summary = "Listar clientes por tipoIdentificacion", description = "Lista clientes por tipoIdentificacion (CEDULA, PASAPORTE, RUC).")
    @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    public ResponseEntity<List<ClienteDTO>> listarPorTipoIdentificacion(@RequestParam String tipoIdentificacion) {
        List<ClienteDTO> lista = clienteService.listarPorTipoIdentificacion(tipoIdentificacion);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/contar-por-tipo-identificacion")
    @Operation(summary = "Contar clientes por tipoIdentificacion", description = "Cuenta clientes por tipoIdentificacion (CEDULA, PASAPORTE, RUC).")
    @ApiResponse(responseCode = "200", description = "Conteo obtenido exitosamente")
    public ResponseEntity<Long> contarPorTipoIdentificacion(@RequestParam String tipoIdentificacion) {
        long conteo = clienteService.contarPorTipoIdentificacion(tipoIdentificacion);
        return ResponseEntity.ok(conteo);
    }

    @PutMapping("/clientes/{tipoIdentificacion}/{numeroIdentificacion}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteDTO> actualizarCliente(
            @PathVariable String tipoIdentificacion,
            @PathVariable String numeroIdentificacion,
            @RequestBody ClienteDTO clienteDTO) {
        log.info("Actualizando cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
        try {
            ClienteDTO actualizado = clienteService.actualizarCliente(tipoIdentificacion, numeroIdentificacion, clienteDTO);
            return ResponseEntity.ok(actualizado);
        } catch (NotFoundException e) {
            log.warn("Cliente no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ====== GESTIÓN DE TELÉFONOS ======

    @PostMapping("/clientes/{tipoIdentificacion}/{numeroIdentificacion}/telefonos")
    @Operation(summary = "Agregar teléfono", description = "Agrega un número telefónico a un cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Teléfono agregado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteDTO> agregarTelefono(
            @PathVariable String tipoIdentificacion,
            @PathVariable String numeroIdentificacion,
            @RequestBody TelefonoClienteDTO telefonoDTO) {
        log.info("Agregando teléfono a cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
        ClienteDTO actualizado = clienteService.agregarTelefonoCliente(tipoIdentificacion, numeroIdentificacion, telefonoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(actualizado);
    }

    @DeleteMapping("/clientes/{tipoIdentificacion}/{numeroIdentificacion}/telefonos/{indice}")
    @Operation(summary = "Eliminar teléfono", description = "Inactiva un número telefónico del cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teléfono inactivado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente o teléfono no encontrado")
    })
    public ResponseEntity<ClienteDTO> eliminarTelefono(
            @PathVariable String tipoIdentificacion,
            @PathVariable String numeroIdentificacion,
            @PathVariable int indice) {
        log.info("Inactivando teléfono {} del cliente: {} {}", indice, tipoIdentificacion, numeroIdentificacion);
        ClienteDTO actualizado = clienteService.eliminarTelefonoCliente(tipoIdentificacion, numeroIdentificacion, indice);
        return ResponseEntity.ok(actualizado);
    }

    // ====== GESTIÓN DE DIRECCIONES ======

    @PostMapping("/clientes/{tipoIdentificacion}/{numeroIdentificacion}/direcciones")
    @Operation(summary = "Agregar dirección", description = "Agrega una nueva dirección al cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dirección agregada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteDTO> agregarDireccion(
            @PathVariable String tipoIdentificacion,
            @PathVariable String numeroIdentificacion,
            @RequestBody DireccionClienteDTO direccionDTO) {
        log.info("Agregando dirección a cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
        ClienteDTO actualizado = clienteService.agregarDireccionCliente(tipoIdentificacion, numeroIdentificacion, direccionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(actualizado);
    }

    // ====== GESTIÓN DE SUCURSALES ======

    @PostMapping("/clientes/{tipoIdentificacion}/{numeroIdentificacion}/sucursales")
    @Operation(summary = "Agregar sucursal", description = "Asocia una sucursal a un cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sucursal asociada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteDTO> agregarSucursal(
            @PathVariable String tipoIdentificacion,
            @PathVariable String numeroIdentificacion,
            @RequestBody ClienteSucursalDTO sucursalDTO) {
        log.info("Agregando sucursal a cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
        ClienteDTO actualizado = clienteService.agregarSucursalCliente(tipoIdentificacion, numeroIdentificacion, sucursalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(actualizado);
    }
}
