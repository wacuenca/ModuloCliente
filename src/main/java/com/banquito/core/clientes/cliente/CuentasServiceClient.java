package com.banquito.core.clientes.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.banquito.core.clientes.config.FlexibleInstantDeserializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@FeignClient(
    name = "cuentas-service", 
    url = "${feign.cuentas-service.url}", 
    fallbackFactory = CuentasServiceFallbackFactory.class
)
public interface CuentasServiceClient {
    
    @PostMapping("/v1/cuentas-clientes")
    ResponseEntity<CuentasClientesRespuestaDTO> crearCuentaAutomatica(@RequestBody CuentasClientesSolicitudDTO request);
    
    @GetMapping("/api/v1/cuentas/{id}")
    ResponseEntity<CuentaRespuestaDTO> obtenerCuenta(@PathVariable("id") Integer id);
    
    @GetMapping("/api/v1/cuentas-clientes/{id}")
    ResponseEntity<CuentasClientesRespuestaDTO> obtenerCuentaCliente(@PathVariable("id") Integer id);
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class CuentasClientesSolicitudDTO {
        private Integer idCuenta; 
        private String idCliente; 
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class CuentasClientesRespuestaDTO {
        private Integer id;
        private CuentaRespuestaDTOMin idCuenta;
        private String idCliente;
        private String numeroCuenta;
        private java.math.BigDecimal saldoDisponible;
        private java.math.BigDecimal saldoContable;
        @JsonDeserialize(using = FlexibleInstantDeserializer.class)
        private java.time.Instant fechaApertura;
        private String estado;
        private Long version;
    }
    
    // DTO minimizado para la cuenta maestra
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class CuentaRespuestaDTOMin {
        private Integer id;
        private String codigoCuenta;
        private String nombre;
    }
    
    // DTO completo de cuenta
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
        @JsonDeserialize(using = FlexibleInstantDeserializer.class)
        private java.time.Instant fechaCreacion;
        @JsonDeserialize(using = FlexibleInstantDeserializer.class)
        private java.time.Instant fechaModificacion;
        private String estado;
        private Long version;
    }
    
    // Enum para estado de cuenta cliente
    enum EstadoCuentaClienteEnum {
        ACTIVO, INACTIVO
    }
}
