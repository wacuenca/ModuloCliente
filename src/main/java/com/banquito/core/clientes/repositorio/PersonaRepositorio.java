package com.banquito.core.clientes.repositorio;

import com.banquito.core.clientes.modelo.Personas;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepositorio extends MongoRepository<Personas, String> {
    //Optional<Personas> findByTipoIdentificacionAndNumeroIdentificacion(String tipo, String numero);

    List<Personas> findByNombreLikeOrderByNombreAsc(String nombre);

    boolean existsByTipoIdentificacionAndNumeroIdentificacion(String tipo, String numero);

    Optional<Personas> findByTipoIdentificacionAndNumeroIdentificacion(String tipoIdentificacion, String numeroIdentificacion);

}