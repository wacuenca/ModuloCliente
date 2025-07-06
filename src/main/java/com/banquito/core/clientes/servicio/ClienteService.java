package com.banquito.core.clientes.servicio;

import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.controlador.mapper.*;
import com.banquito.core.clientes.excepcion.*;
import com.banquito.core.clientes.modelo.*;
import com.banquito.core.clientes.repositorio.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClienteService {

    private final PersonaRepositorio personaRepo;
    private final EmpresasRepositorio empresaRepo;
    private final ClientesRepositorio clienteRepo;
    private final ClienteMapper clienteMapper;
    private final TelefonoClienteMapper telefonoMapper;
    private final DireccionesClientesMapper direccionMapper;
    private final ClienteSucursalMapper sucursalMapper;

    public ClienteService(PersonaRepositorio personaRepo,
                         EmpresasRepositorio empresaRepo,
                         ClientesRepositorio clienteRepo,
                         ClienteMapper clienteMapper,
                         @Qualifier("telefonoClienteMapperImpl") TelefonoClienteMapper telefonoMapper,
                         @Qualifier("direccionesClientesMapperImpl") DireccionesClientesMapper direccionMapper,
                         @Qualifier("clienteSucursalMapperImpl")ClienteSucursalMapper sucursalMapper) {
        this.personaRepo = personaRepo;
        this.empresaRepo = empresaRepo;
        this.clienteRepo = clienteRepo;
        this.clienteMapper = clienteMapper;
        this.telefonoMapper = telefonoMapper;
        this.direccionMapper = direccionMapper;
        this.sucursalMapper = sucursalMapper;
    }

    // ========== MÉTODOS PARA PERSONAS ==========

    @Transactional
    public PersonaDTO crearPersona(PersonaDTO personaDTO) {
        try {
            log.info("Creando persona: {} {}", personaDTO.getTipoIdentificacion(), personaDTO.getNumeroIdentificacion());

            if (personaRepo.existsByTipoIdentificacionAndNumeroIdentificacion(
                    personaDTO.getTipoIdentificacion(),
                    personaDTO.getNumeroIdentificacion())) {
                throw new CreacionException("Persona ya existe", 1101);
            }

            Personas persona = clienteMapper.toPersona(personaDTO);
            persona.setFechaRegistro(LocalDate.now());
            persona.setFechaActualizacion(LocalDate.now());
            persona.setEstado("ACTIVO");
            persona = personaRepo.save(persona);
            return clienteMapper.toPersonaDTO(persona);
        } catch (CreacionException e) {
            log.error("Error al crear persona: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear persona", e);
            throw new CreacionException("Error al crear persona", 1199);
        }
    }

    public PersonaDTO obtenerPersona(String tipoIdentificacion, String numeroIdentificacion) {
        log.info("Obteniendo persona: {} {}", tipoIdentificacion, numeroIdentificacion);
        Personas persona = personaRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)
                .orElseThrow(() -> new NotFoundException("Persona no encontrada", 3101));
        return clienteMapper.toPersonaDTO(persona);
    }

    public List<PersonaDTO> buscarPersonas(String nombre) {
        log.info("Buscando personas: {}", nombre);
        List<Personas> personas = personaRepo.findByNombreLikeOrderByNombreAsc("%" + nombre + "%");

        if (personas.isEmpty()) {
            throw new NotFoundException("No se encontraron personas", 3102);
        }

        return personas.stream()
                .limit(100)
                .map(clienteMapper::toPersonaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PersonaDTO actualizarPersona(String tipoIdentificacion, String numeroIdentificacion, PersonaDTO personaDTO) {
        try {
            log.info("Actualizando persona: {} {}", tipoIdentificacion, numeroIdentificacion);
            Personas persona = personaRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)
                    .orElseThrow(() -> new NotFoundException("Persona no encontrada", 3103));

            persona.setNombre(personaDTO.getNombre());
            persona.setGenero(personaDTO.getGenero());
            persona.setFechaNacimiento(personaDTO.getFechaNacimiento());
            persona.setEstadoCivil(personaDTO.getEstadoCivil());
            persona.setNivelEstudio(personaDTO.getNivelEstudio());
            persona.setCorreoElectronico(personaDTO.getCorreoElectronico());
            persona.setFechaActualizacion(LocalDate.now());

            persona = personaRepo.save(persona);
            return clienteMapper.toPersonaDTO(persona);
        } catch (NotFoundException e) {
            log.error("Error al actualizar persona: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar persona", e);
            throw new ActualizacionException("Error al actualizar persona", 2199);
        }
    }

    // ========== MÉTODOS PARA EMPRESAS ==========

    @Transactional
    public EmpresasDTO crearEmpresa(EmpresasDTO empresasDTO) {
        try {
            log.info("Creando empresa: {} {}", empresasDTO.getTipoIdentificacion(), empresasDTO.getNumeroIdentificacion());

            if (empresaRepo.existsByTipoIdentificacionAndNumeroIdentificacion(
                    empresasDTO.getTipoIdentificacion(),
                    empresasDTO.getNumeroIdentificacion())) {
                throw new CreacionException("Empresa ya existe", 1201);
            }

            Empresas empresa = clienteMapper.toEmpresa(empresasDTO);
            empresa.setFechaRegistro(LocalDate.now());
            empresa.setFechaActualizacion(LocalDate.now());
            empresa.setEstado("ACTIVO");
            empresa = empresaRepo.save(empresa);
            return clienteMapper.toEmpresaDTO(empresa);
        } catch (CreacionException e) {
            log.error("Error al crear empresa: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear empresa", e);
            throw new CreacionException("Error al crear empresa", 1299);
        }
    }

    public EmpresasDTO obtenerEmpresa(String tipo, String numero) {
        log.info("Obteniendo empresa: {} {}", tipo, numero);
        Empresas empresa = empresaRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipo, numero)
                .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3201));
        return clienteMapper.toEmpresaDTO(empresa);
    }

    public List<EmpresasDTO> buscarEmpresasPorRazon(String razonSocial) {
        log.info("Buscando empresas por razón social: {}", razonSocial);
        List<Empresas> empresas = empresaRepo.findByRazonSocialLikeOrderByRazonSocialAsc("%" + razonSocial + "%");

        if (empresas.isEmpty()) {
            throw new NotFoundException("No se encontraron empresas", 3202);
        }

        return empresas.stream()
                .limit(100)
                .map(clienteMapper::toEmpresaDTO)
                .collect(Collectors.toList());
    }

    public List<EmpresasDTO> buscarEmpresasPorNombre(String nombreComercial) {
        log.info("Buscando empresas por nombre comercial: {}", nombreComercial);
        List<Empresas> empresas = empresaRepo.findByNombreComercialLikeOrderByNombreComercialAsc("%" + nombreComercial + "%");

        if (empresas.isEmpty()) {
            throw new NotFoundException("No se encontraron empresas", 3203);
        }

        return empresas.stream()
                .limit(100)
                .map(clienteMapper::toEmpresaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmpresasDTO actualizarEmpresa(String tipoIdentificacion, String numeroIdentificacion, EmpresasDTO empresasDTO) {
        try {
            log.info("Actualizando empresa: {} {}", tipoIdentificacion, numeroIdentificacion);
            Empresas empresa = empresaRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3204));

            empresa.setNombreComercial(empresasDTO.getNombreComercial());
            empresa.setRazonSocial(empresasDTO.getRazonSocial());
            empresa.setTipo(empresasDTO.getTipo());
            empresa.setCorreoElectronico(empresasDTO.getCorreoElectronico());
            empresa.setSectorEconomico(empresasDTO.getSectorEconomico());
            empresa.setFechaActualizacion(LocalDate.now());

            empresa = empresaRepo.save(empresa);
            return clienteMapper.toEmpresaDTO(empresa);
        } catch (NotFoundException e) {
            log.error("Error al actualizar empresa: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar empresa", e);
            throw new ActualizacionException("Error al actualizar empresa", 2299);
        }
    }

    // ========== MÉTODOS PARA CLIENTES ==========

    @Transactional
    public ClienteDTO crearClientePersona(String tipoIdentificacion, String numeroIdentificacion, ClienteDTO clienteDTO) {
        try {
            log.info("Creando cliente desde persona: {} {}", tipoIdentificacion, numeroIdentificacion);
            
            // Verificar si ya existe un cliente con esta identificación
            if (clienteRepo.existsByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)) {
                throw new CreacionException("Ya existe un cliente con esta identificación", 1301);
            }

            // Obtener la persona
            Personas persona = personaRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)
                    .orElseThrow(() -> new NotFoundException("Persona no encontrada", 3104));

            clienteDTO.setTipoEntidad("PERSONA");
            clienteDTO.setIdEntidad(persona.getId());
            clienteDTO.setNombre(persona.getNombre());
            clienteDTO.setTipoIdentificacion(persona.getTipoIdentificacion());
            clienteDTO.setNumeroIdentificacion(persona.getNumeroIdentificacion());
            clienteDTO.setFechaCreacion(LocalDate.now());

            Clientes cliente = clienteMapper.toCliente(clienteDTO);
            cliente.setFechaActualizacion(LocalDate.now());
            cliente.setEstado("ACTIVO");
            cliente = clienteRepo.save(cliente);
            return clienteMapper.toClienteDTO(cliente);
        } catch (NotFoundException | CreacionException e) {
            log.error("Error al crear cliente persona: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear cliente persona", e);
            throw new CreacionException("Error al crear cliente persona", 1399);
        }
    }

    @Transactional
    public ClienteDTO crearClienteEmpresa(String tipoIdentificacion, String numeroIdentificacion, ClienteDTO clienteDTO) {
        try {
            log.info("Creando cliente desde empresa: {} {}", tipoIdentificacion, numeroIdentificacion);
            
            // Verificar si ya existe un cliente con esta identificación
            if (clienteRepo.existsByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)) {
                throw new CreacionException("Ya existe un cliente con esta identificación", 1302);
            }

            // Obtener la empresa
            Empresas empresa = empresaRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3205));

            clienteDTO.setTipoEntidad("EMPRESA");
            clienteDTO.setIdEntidad(empresa.getId());
            clienteDTO.setNombre(empresa.getRazonSocial());
            clienteDTO.setTipoIdentificacion(empresa.getTipoIdentificacion());
            clienteDTO.setNumeroIdentificacion(empresa.getNumeroIdentificacion());
            clienteDTO.setFechaCreacion(LocalDate.now());

            Clientes cliente = clienteMapper.toCliente(clienteDTO);
            cliente.setFechaActualizacion(LocalDate.now());
            cliente.setEstado("ACTIVO");
            cliente = clienteRepo.save(cliente);
            return clienteMapper.toClienteDTO(cliente);
        } catch (NotFoundException | CreacionException e) {
            log.error("Error al crear cliente empresa: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear cliente empresa", e);
            throw new CreacionException("Error al crear cliente empresa", 1398);
        }
    }

    public ClienteDTO obtenerCliente(String id) {
        log.info("Obteniendo cliente ID: {}", id);
        Clientes cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3301));
        return clienteMapper.toClienteDTO(cliente);
    }

    public ClienteDTO obtenerClientePorIdentificacion(String tipo, String numero) {
        log.info("Obteniendo cliente: {} {}", tipo, numero);
        Clientes cliente = clienteRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipo, numero)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3302));
        return clienteMapper.toClienteDTO(cliente);
    }

    public List<ClienteDTO> buscarClientes(String nombre) {
        log.info("Buscando clientes: {}", nombre);
        List<Clientes> clientes = clienteRepo.findByNombreLikeOrderByNombreAsc("%" + nombre + "%");

        if (clientes.isEmpty()) {
            throw new NotFoundException("No se encontraron clientes", 3303);
        }

        return clientes.stream()
                .limit(100)
                .map(clienteMapper::toClienteDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClienteDTO actualizarCliente(String tipoIdentificacion, String numeroIdentificacion, ClienteDTO clienteDTO) {
        try {
            log.info("Actualizando cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
            Clientes cliente = clienteRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)
                    .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3304));

            cliente.setTipoCliente(clienteDTO.getTipoCliente());
            cliente.setSegmento(clienteDTO.getSegmento());
            cliente.setCanalAfiliacion(clienteDTO.getCanalAfiliacion());
            cliente.setComentarios(clienteDTO.getComentarios());
            cliente.setEstado(clienteDTO.getEstado());
            cliente.setFechaActualizacion(LocalDate.now());

            cliente = clienteRepo.save(cliente);
            return clienteMapper.toClienteDTO(cliente);
        } catch (NotFoundException e) {
            log.error("Error al actualizar cliente: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar cliente", e);
            throw new ActualizacionException("Error al actualizar cliente", 2399);
        }
    }

    @Transactional
    public ClienteDTO agregarTelefonoCliente(String tipoIdentificacion, String numeroIdentificacion, TelefonoClienteDTO telefonoDTO) {
        log.info("Agregando teléfono a cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
        Clientes cliente = clienteRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3305));

        TelefonosClientes telefono = telefonoMapper.toTelefono(telefonoDTO);
        telefono.setFechaCreacion(LocalDate.now());
        telefono.setFechaActualizacion(LocalDate.now());
        telefono.setEstado("ACTIVO");

        if (cliente.getTelefonos() == null) {
            cliente.setTelefonos(new ArrayList<>());
        }
        cliente.getTelefonos().add(telefono);

        cliente.setFechaActualizacion(LocalDate.now());
        cliente = clienteRepo.save(cliente);
        return clienteMapper.toClienteDTO(cliente);
    }

    @Transactional
    public ClienteDTO eliminarTelefonoCliente(String tipoIdentificacion, String numeroIdentificacion, int indiceTelefono) {
        Clientes cliente = clienteRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3308));

        cliente.getTelefonos().get(indiceTelefono).setEstado("INACTIVO");
        cliente.setFechaActualizacion(LocalDate.now());
        cliente = clienteRepo.save(cliente);
        return clienteMapper.toClienteDTO(cliente);
    }

    @Transactional
    public ClienteDTO agregarDireccionCliente(String tipoIdentificacion, String numeroIdentificacion, DireccionClienteDTO direccionDTO) {
        log.info("Agregando dirección a cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
        Clientes cliente = clienteRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3306));

        DireccionesClientes direccion = direccionMapper.toDireccion(direccionDTO);
        direccion.setFechaCreacion(LocalDate.now());
        direccion.setFechaActualizacion(LocalDate.now());
        direccion.setEstado("ACTIVO");

        if (cliente.getDirecciones() == null) {
            cliente.setDirecciones(List.of(direccion));
        } else {
            cliente.getDirecciones().add(direccion);
        }

        cliente.setFechaActualizacion(LocalDate.now());
        cliente = clienteRepo.save(cliente);
        return clienteMapper.toClienteDTO(cliente);
    }

    @Transactional
    public ClienteDTO agregarSucursalCliente(String tipoIdentificacion, String numeroIdentificacion, ClienteSucursalDTO sucursalDTO) {
        log.info("Agregando sucursal a cliente: {} {}", tipoIdentificacion, numeroIdentificacion);
        Clientes cliente = clienteRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3307));

        ClientesSucursales sucursal = sucursalMapper.toClienteSucursal(sucursalDTO);
        sucursal.setFechaCreacion(LocalDate.now());
        sucursal.setFechaUltimaActualizacion(LocalDate.now());
        sucursal.setEstado("ACTIVO");

        cliente.setFechaActualizacion(LocalDate.now());
        cliente = clienteRepo.save(cliente);
        return clienteMapper.toClienteDTO(cliente);
    }
}