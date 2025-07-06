package com.banquito.core.clientes.repositorio;

import com.banquito.core.clientes.modelo.Persona;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepositorio extends MongoRepository<Persona, String> {
    Optional<Persona> findByTipoIdentificacionAndNumeroIdentificacion(String tipo, String numero);

    List<Persona> findByNombreLikeOrderByNombreAsc(String nombre);

    boolean existsByTipoIdentificacionAndNumeroIdentificacion(String tipo, String numero);
}