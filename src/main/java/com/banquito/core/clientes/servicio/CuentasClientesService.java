package com.banquito.core.clientes.servicio;

import org.springframework.stereotype.Service;

import com.banquito.core.clientes.cliente.CuentasServiceClient;
import com.banquito.core.clientes.cliente.CuentasServiceClient.CuentasClientesRespuestaDTO;
import com.banquito.core.clientes.cliente.CuentasServiceClient.CuentasClientesSolicitudDTO;
import com.banquito.core.clientes.excepcion.ServicioExternoException;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CuentasClientesService {

    private final CuentasServiceClient cuentasServiceClient;
    
    private static final Integer ID_CUENTA_MAESTRA_AHORROS = 27;

    public CuentasClientesService(CuentasServiceClient cuentasServiceClient) {
        this.cuentasServiceClient = cuentasServiceClient;
    }

    public CuentasClientesRespuestaDTO crearCuentaAhorros(String cedulaCliente) {
        return crearCuentaCliente(ID_CUENTA_MAESTRA_AHORROS, cedulaCliente);
    }

    public CuentasClientesRespuestaDTO crearCuentaCliente(Integer idCuentaMaestra, String cedulaCliente) {
        try {
            log.info("Creando cuenta para cliente cédula: {} con cuenta maestra ID: {}", 
                    cedulaCliente, idCuentaMaestra);

            // Validaciones de entrada
            if (cedulaCliente == null || cedulaCliente.trim().isEmpty()) {
                throw new IllegalArgumentException("La cédula del cliente es requerida");
            }
            
            if (idCuentaMaestra == null) {
                throw new IllegalArgumentException("El ID de cuenta maestra es requerido");
            }

            // Construir la solicitud
            CuentasClientesSolicitudDTO request = CuentasClientesSolicitudDTO.builder()
                .idCuenta(idCuentaMaestra)
                .idCliente(cedulaCliente)
                .build();

            // Llamar al servicio externo
            var response = cuentasServiceClient.crearCuentaAutomatica(request);

            if (response != null && response.getBody() != null) {
                CuentasClientesRespuestaDTO cuenta = response.getBody();
                log.info("Cuenta creada exitosamente - Número: {}, Cliente: {}, Saldo inicial: {}", 
                        cuenta.getNumeroCuenta(), cuenta.getIdCliente(), cuenta.getSaldoDisponible());
                return cuenta;
            } else {
                throw new ServicioExternoException("Respuesta vacía del servicio de cuentas", null);
            }

        } catch (FeignException.BadRequest e) {
            log.error("Error de validación al crear cuenta - Cliente: {}, Error: {}", cedulaCliente, e.getMessage());
            throw new ServicioExternoException("Datos inválidos para crear cuenta: " + extractErrorMessage(e), e);
        } catch (FeignException.NotFound e) {
            log.error("Cliente o cuenta maestra no encontrada - Cliente: {}, Cuenta maestra: {}", 
                     cedulaCliente, idCuentaMaestra);
            throw new ServicioExternoException("Cliente no encontrado o cuenta maestra inexistente", e);
        } catch (FeignException e) {
            log.error("Error de comunicación con servicio de cuentas - Status: {}, Cliente: {}", 
                     e.status(), cedulaCliente);
            throw new ServicioExternoException("Error al comunicarse con servicio de cuentas: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.error("Error de validación - Cliente: {}, Error: {}", cedulaCliente, e.getMessage());
            throw new ServicioExternoException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error inesperado al crear cuenta para cliente: {}", cedulaCliente, e);
            throw new ServicioExternoException("Error inesperado al crear cuenta", e);
        }
    }

    public CuentasClientesRespuestaDTO obtenerCuentaCliente(Integer idCuentaCliente) {
        try {
            log.info("Obteniendo cuenta cliente con ID: {}", idCuentaCliente);

            var response = cuentasServiceClient.obtenerCuentaCliente(idCuentaCliente);
            
            if (response != null && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new ServicioExternoException("Cuenta cliente no encontrada", null);
            }

        } catch (FeignException.NotFound e) {
            log.error("Cuenta cliente no encontrada - ID: {}", idCuentaCliente);
            throw new ServicioExternoException("Cuenta cliente no encontrada", e);
        } catch (FeignException e) {
            log.error("Error al obtener cuenta cliente - ID: {}, Status: {}", idCuentaCliente, e.status());
            throw new ServicioExternoException("Error al comunicarse con servicio de cuentas", e);
        } catch (Exception e) {
            log.error("Error inesperado al obtener cuenta cliente: {}", idCuentaCliente, e);
            throw new ServicioExternoException("Error inesperado al obtener cuenta cliente", e);
        }
    }

    /**
     * Extrae el mensaje de error de una excepción Feign
     */
    private String extractErrorMessage(FeignException e) {
        String message = e.getMessage();
        if (message != null && message.contains("Cliente no encontrado")) {
            return "Cliente no encontrado";
        } else if (message != null && message.contains("cuenta maestra")) {
            return "Cuenta maestra no válida";
        } else if (message != null && message.contains("ya existe")) {
            return "Ya existe una cuenta para este cliente";
        }
        return "Error en la validación de datos";
    }
}
