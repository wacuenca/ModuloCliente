package com.banquito.core.clientes.repositorio;

import com.banquito.core.clientes.modelo.RepresentantesEmpresas;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepresentantesEmpresasRepositorio extends MongoRepository<RepresentantesEmpresas, String> {
    Optional<RepresentantesEmpresas> findById(String id);

    List<RepresentantesEmpresas> findByEmpresaIdAndEstado(String idEmpresa, String estado);

    boolean existsByEmpresaIdAndClienteId(String idEmpresa, String idCliente);

    boolean existsById(String id);
}