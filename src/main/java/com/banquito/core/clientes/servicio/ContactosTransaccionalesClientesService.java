package com.banquito.core.clientes.servicio;

import com.banquito.core.clientes.controlador.dto.ContactoTransaccionalClienteDTO;
import com.banquito.core.clientes.controlador.mapper.ContactoTransaccionalMapper;
import com.banquito.core.clientes.enums.EstadoRegistro;
import com.banquito.core.clientes.excepcion.*;
import com.banquito.core.clientes.modelo.Clientes;
import com.banquito.core.clientes.modelo.ContactosTransaccionalesClientes;
import com.banquito.core.clientes.repositorio.ClientesRepositorio;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class ContactosTransaccionalesClientesService {

    private final ClientesRepositorio clienteRepo;
    private final ContactoTransaccionalMapper mapper;

    public ContactosTransaccionalesClientesService(
            ClientesRepositorio clienteRepo,
            @Qualifier("contactoTransaccionalMapperImpl")ContactoTransaccionalMapper mapper) {
        this.clienteRepo = clienteRepo;
        this.mapper = mapper;
    }

    @Transactional
    public ContactoTransaccionalClienteDTO crearContacto(String idCliente, ContactoTransaccionalClienteDTO dto) {
        try {
            log.info("Creando contacto transaccional para cliente ID: {}", idCliente);
            
            Clientes cliente = clienteRepo.findById(idCliente)
                    .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 4001));

            if (cliente.getContactoTransaccional() != null) {
                throw new CreacionException("El cliente ya tiene un contacto transaccional", 4101);
            }

            ContactosTransaccionalesClientes contacto = mapper.toContactoTransaccional(dto);
            contacto.setEstado(EstadoRegistro.ACTIVO.name());
            contacto.setFechaCreacion(LocalDate.now());
            contacto.setFechaActualizacion(LocalDate.now());

            cliente.setContactoTransaccional(contacto);
            cliente.setFechaActualizacion(LocalDate.now());
            
            Clientes guardado = clienteRepo.save(cliente);
            return mapper.toContactoTransaccionalDTO(guardado.getContactoTransaccional());
            
        } catch (NotFoundException | CreacionException e) {
            log.error("Error al crear contacto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear contacto", e);
            throw new CreacionException("Error al crear contacto transaccional", 4199);
        }
    }

    public ContactoTransaccionalClienteDTO obtenerContacto(String idCliente) {
        try {
            log.info("Obteniendo contacto transaccional para cliente ID: {}", idCliente);
            
            Clientes cliente = clienteRepo.findById(idCliente)
                    .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 4002));
            
            if (cliente.getContactoTransaccional() == null) {
                throw new NotFoundException("El cliente no tiene contacto transaccional", 4003);
            }
            
            return mapper.toContactoTransaccionalDTO(cliente.getContactoTransaccional());
            
        } catch (NotFoundException e) {
            log.error("Error al obtener contacto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al obtener contacto", e);
            throw new ConsultaException("Error al obtener contacto transaccional", 4299);
        }
    }

    @Transactional
    public ContactoTransaccionalClienteDTO actualizarContacto(String idCliente, ContactoTransaccionalClienteDTO dto) {
        try {
            log.info("Actualizando contacto transaccional para cliente ID: {}", idCliente);
            
            Clientes cliente = clienteRepo.findById(idCliente)
                    .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 4005));

            if (cliente.getContactoTransaccional() == null) {
                throw new NotFoundException("El cliente no tiene contacto transaccional", 4006);
            }

            ContactosTransaccionalesClientes contacto = cliente.getContactoTransaccional();
            contacto.setTelefono(dto.getTelefono());
            contacto.setCorreoElectronico(dto.getCorreoElectronico());
            contacto.setFechaActualizacion(LocalDate.now());

            cliente.setFechaActualizacion(LocalDate.now());
            
            Clientes actualizado = clienteRepo.save(cliente);
            return mapper.toContactoTransaccionalDTO(actualizado.getContactoTransaccional());
            
        } catch (NotFoundException e) {
            log.error("Error al actualizar contacto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar contacto", e);
            throw new ActualizacionException("Error al actualizar contacto transaccional", 4499);
        }
    }

    @Transactional
    public void cambiarEstadoContacto(String idCliente, EstadoRegistro nuevoEstado) {
        try {
            log.info("Cambiando estado del contacto para cliente ID: {} a {}", idCliente, nuevoEstado);
            
            if (nuevoEstado == null) {
                throw new ValidacionException("Estado no puede ser nulo", 4401);
            }
            
            Clientes cliente = clienteRepo.findById(idCliente)
                    .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 4007));

            if (cliente.getContactoTransaccional() == null) {
                throw new NotFoundException("El cliente no tiene contacto transaccional", 4008);
            }

            cliente.getContactoTransaccional().setEstado(nuevoEstado.name());
            cliente.setFechaActualizacion(LocalDate.now());

            clienteRepo.save(cliente);
            
        } catch (NotFoundException | ValidacionException e) {
            log.error("Error al cambiar estado contacto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al cambiar estado contacto", e);
            throw new ActualizacionException("Error al cambiar estado de contacto", 4599);
        }
    }

    @Transactional
    public void eliminarContacto(String idCliente) {
        try {
            log.info("Eliminando contacto transaccional para cliente ID: {}", idCliente);
            
            Clientes cliente = clienteRepo.findById(idCliente)
                    .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 4009));

            if (cliente.getContactoTransaccional() == null) {
                throw new NotFoundException("El cliente no tiene contacto transaccional", 4010);
            }

            cliente.setContactoTransaccional(null);
            cliente.setFechaActualizacion(LocalDate.now());

            clienteRepo.save(cliente);
            
        } catch (NotFoundException e) {
            log.error("Error al eliminar contacto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al eliminar contacto", e);
            throw new EliminacionException("Error al eliminar contacto transaccional", 4699);
        }
    }
}