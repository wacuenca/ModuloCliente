package com.banquito.core.clientes.repositorio;

import com.banquito.core.clientes.modelo.AccionistasEmpresas;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccionistasEmpresasRepositorio extends MongoRepository<AccionistasEmpresas, String> {
    Optional<AccionistasEmpresas> findById(String id);

    List<AccionistasEmpresas> findByIdEmpresaAndEstado(String idEmpresa, String estado);

    @Query("{'id_empresa': ?0, 'id_participe': ?1}")
    boolean existsByIdEmpresaAndIdParticipe(String idEmpresa, String idParticipe);

    boolean existsById(String id);

}