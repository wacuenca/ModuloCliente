package com.banquito.core.clientes.controlador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteConCuentaDTO {
    
    private ClienteDTO cliente;
    
    private CuentaClienteInfo cuenta;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CuentaClienteInfo {
        private Integer id;
        private String numeroCuenta;
        private BigDecimal saldoDisponible;
        private BigDecimal saldoContable;
        private Instant fechaApertura;
        private String estado;
        private String nombreTipoCuenta;
        private String codigoTipoCuenta;
    }
}
