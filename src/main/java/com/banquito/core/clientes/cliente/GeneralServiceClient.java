package com.banquito.core.clientes.cliente;

import com.banquito.core.clientes.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "general-service",
    url = "${general.service.url}",
    configuration = FeignConfig.class,
    fallbackFactory = GeneralServiceFallbackFactory.class
)
public interface GeneralServiceClient {
    
    @GetMapping("/api/externo/sucursales/{codigoSucursal}")
    void validarSucursal(@PathVariable String codigoSucursal);
    
    @GetMapping("/api/externo/locaciones/{codigoLocacion}")
    void validarLocacion(@PathVariable String codigoLocacion);
    
    @GetMapping("/api/externo/paises/{codigoPais}")
    void validarPais(@PathVariable String codigoPais);
    
    @GetMapping("/api/externo/provincias/{codigoProvincia}/cantones/{codigoCanton}")
    void validarProvinciaYCanton(
        @PathVariable String codigoProvincia,
        @PathVariable String codigoCanton
    );
}
