package com.banquito.core.clientes.cliente;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.openfeign.FallbackFactory;
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
            public void validarLocacion(String codigoLocacion) {
                logError("validarLocacion", codigoLocacion, cause);
                throw new ServicioExternoException("Servicio de validación no disponible", 503);
            }

            @Override
            public void validarPais(String codigoPais) {
                logError("validarPais", codigoPais, cause);
                throw new ServicioExternoException("Servicio de validación no disponible", 503);
            }

            @Override
            public void validarProvinciaYCanton(String codigoProvincia, String codigoCanton) {
                logError("validarProvinciaYCanton", codigoProvincia + "/" + codigoCanton, cause);
                throw new ServicioExternoException("Servicio de validación no disponible", 503);
            }

            private void logError(String method, String params, Throwable cause) {
                log.error("Fallback para {} - Parámetros: {} - Error: {}", 
                    method, params, cause.getMessage());
            }
        };
    }
}