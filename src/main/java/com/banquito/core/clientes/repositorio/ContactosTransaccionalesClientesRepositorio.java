package com.banquito.core.clientes.repositorio;

import com.banquito.core.clientes.modelo.ContactosTransaccionalesClientes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactosTransaccionalesClientesRepositorio
        extends MongoRepository<ContactosTransaccionalesClientes, String> {
    Optional<ContactosTransaccionalesClientes> findById(String id);

    List<ContactosTransaccionalesClientes> findByIdCliente(String idCliente);

    boolean existsByIdClienteAndTelefono(String idCliente, String telefono);

    boolean existsByIdClienteAndCorreoElectronico(String idCliente, String correo);
}