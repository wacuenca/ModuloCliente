package com.banquito.core.clientes.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@FeignClient(name = "cuentas-service", url = "${feign.cuentas-service.url}")
public interface CuentasServiceClient {
    
    @PostMapping("/api/v1/cuentas-clientes")
    ResponseEntity<CuentasClientesRespuestaDTO> crearCuentaAutomatica(@RequestBody CuentasClientesSolicitudDTO request);
    
    @GetMapping("/api/v1/cuentas/{id}")
    ResponseEntity<CuentaRespuestaDTO> obtenerCuenta(@PathVariable("id") Integer id);
    
    // DTO que coincide exactamente con CuentasClientesSolicitudDTO del microservicio
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class CuentasClientesSolicitudDTO {
        private Integer idCuenta;  // ID de la cuenta maestra (requerido)
        private String idCliente;  // ID del cliente (requerido)
    }
    
    // DTO de respuesta
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class CuentasClientesRespuestaDTO {
        private Integer id;
        private CuentaRespuestaDTO_Min2 idCuenta;
        private String idCliente;
        private String numeroCuenta;
        private java.math.BigDecimal saldoDisponible;
        private java.math.BigDecimal saldoContable;
        private java.time.Instant fechaApertura;
        private String estado;
        private Long version;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class CuentaRespuestaDTO_Min2 {
        private Integer id;
        private String codigoCuenta;
        private String nombre;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class CuentaRespuestaDTO {
        private Integer id;
        private String tipoCuentaId;
        private String tasaInteresId;
        private String codigoCuenta;
        private String nombre;
        private String descripcion;
        private java.time.Instant fechaCreacion;
        private java.time.Instant fechaModificacion;
        private String estado;
        private Long version;
    }
}
