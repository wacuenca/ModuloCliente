package com.banquito.core.clientes.cliente;

import com.banquito.core.clientes.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "general-service",
    url = "${general.service.url}",
    configuration = FeignConfig.class,
    fallbackFactory = GeneralServiceFallbackFactory.class
)
public interface GeneralServiceClient {
    
    @GetMapping("/api/externo/sucursales/{codigoSucursal}")
    void validarSucursal(@PathVariable String codigoSucursal);
    
    @GetMapping("/api/externo/paises/{codigoPais}")
    void validarPais(@PathVariable String codigoPais);
    
    // ================= VALIDACIÓN DE LOCACIONES JERÁRQUICAS =================
    @GetMapping("/locacion/validar")
    ResponseEntity<Boolean> validarLocacion(
        @RequestParam String codigoProvincia,
        @RequestParam(required = false) String codigoCanton,
        @RequestParam(required = false) String codigoParroquia
    );
}
