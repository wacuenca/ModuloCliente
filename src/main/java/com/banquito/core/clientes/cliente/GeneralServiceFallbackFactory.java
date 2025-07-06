package com.banquito.core.clientes.cliente;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.banquito.core.clientes.excepcion.ServicioExternoException;

@Slf4j
@Component
public class GeneralServiceFallbackFactory implements FallbackFactory<GeneralServiceClient> {

    @Override
    public GeneralServiceClient create(Throwable cause) {
        return new GeneralServiceClient() {
            @Override
            public void validarSucursal(String codigoSucursal) {
                logError("validarSucursal", codigoSucursal, cause);
                throw new ServicioExternoException("Servicio de validación no disponible", 503);
            }

            @Override
            public void validarPais(String codigoPais) {
                logError("validarPais", codigoPais, cause);
                throw new ServicioExternoException("Servicio de validación no disponible", 503);
            }

            @Override
            public ResponseEntity<Boolean> validarLocacion(String codigoProvincia, String codigoCanton, String codigoParroquia) {
                logError("validarLocacion", codigoProvincia + "/" + codigoCanton + "/" + codigoParroquia, cause);
                throw new ServicioExternoException("Servicio de validación no disponible", 503);
            }

            private void logError(String method, String params, Throwable cause) {
                log.error("Fallback para {} - Parámetros: {} - Error: {}", 
                    method, params, cause.getMessage());
            }
        };
    }
}