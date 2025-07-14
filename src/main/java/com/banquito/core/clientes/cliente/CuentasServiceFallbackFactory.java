package com.banquito.core.clientes.cliente;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CuentasServiceFallbackFactory implements FallbackFactory<CuentasServiceClient> {

    @Override
    public CuentasServiceClient create(Throwable cause) {
        return new CuentasServiceClient() {
            
            @Override
            public ResponseEntity<CuentasClientesRespuestaDTO> crearCuentaAutomatica(CuentasClientesSolicitudDTO request) {
                log.error("Error al crear cuenta automática para cliente: {}. Causa: {}", 
                         request != null ? request.getIdCliente() : "null", cause.getMessage());
                throw new RuntimeException("Servicio de cuentas no disponible: " + cause.getMessage(), cause);
            }
            
            @Override
            public ResponseEntity<CuentaRespuestaDTO> obtenerCuenta(Integer id) {
                log.error("Error al obtener cuenta con ID: {}. Causa: {}", id, cause.getMessage());
                throw new RuntimeException("Servicio de cuentas no disponible: " + cause.getMessage(), cause);
            }
            
            @Override
            public ResponseEntity<CuentasClientesRespuestaDTO> obtenerCuentaCliente(Integer id) {
                log.error("Error al obtener cuenta cliente con ID: {}. Causa: {}", id, cause.getMessage());
                throw new RuntimeException("Servicio de cuentas no disponible: " + cause.getMessage(), cause);
            }
        };
    }
}
