package com.banquito.core.clientes.repositorio;

import com.banquito.core.clientes.modelo.Empresas;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresasRepositorio extends MongoRepository<Empresas, String> {

    List<Empresas> findByRazonSocialLikeOrderByRazonSocialAsc(String razonSocial);

    List<Empresas> findByNombreComercialLikeOrderByNombreComercialAsc(String nombreComercial);

    boolean existsByTipoIdentificacionAndNumeroIdentificacion(String tipo, String numero);

    Optional<Empresas> findByTipoIdentificacionAndNumeroIdentificacion(String tipoIdentificacion, String numeroIdentificacion);
}