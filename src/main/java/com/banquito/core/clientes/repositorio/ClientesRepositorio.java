package com.banquito.core.clientes.repositorio;

import com.banquito.core.clientes.modelo.Clientes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientesRepositorio extends MongoRepository<Clientes, String> {
    Optional<Clientes> findByTipoIdentificacionAndNumeroIdentificacion(String tipo, String numero);

    List<Clientes> findByNombreLikeOrderByNombreAsc(String nombre);

    boolean existsByTipoIdentificacionAndNumeroIdentificacion(String tipo, String numero);

    boolean existsByIdEntidadAndTipoEntidad(String idEntidad, String tipoEntidad);

    boolean existsByIdAndTipoEntidad(String id, String tipoEntidad);
}
