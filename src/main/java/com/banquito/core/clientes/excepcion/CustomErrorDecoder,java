package com.banquito.core.clientes.excepcion;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CustomErrorDecoder implements ErrorDecoder {
    
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        // 1. Extraer información básica del error
        String requestUrl = response.request().url();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        String responseBody = getResponseBody(response);

        // 2. Crear mensaje de error descriptivo
        String errorMessage = String.format(
            "Error en llamado a servicio externo. Método: %s | URL: %s | Status: %d | Cuerpo: %s",
            methodKey,
            requestUrl,
            responseStatus.value(),
            responseBody
        );

        // 3. Clasificar el tipo de error
        if (responseStatus.is4xxClientError()) {
            return new ServicioExternoException(
                "Error del cliente en servicio externo: " + errorMessage,
                responseStatus.value()
            );
        } else if (responseStatus.is5xxServerError()) {
            return new ServicioExternoException(
                "Error del servidor en servicio externo: " + errorMessage,
                responseStatus.value()
            );
        }

        // 4. Para otros códigos de estado, usar el decodificador por defecto
        return defaultErrorDecoder.decode(methodKey, response);
    }

    private String getResponseBody(Response response) {
        try (InputStream bodyStream = response.body().asInputStream()) {
            if (bodyStream != null) {
                byte[] bodyBytes = bodyStream.readAllBytes();
                return new String(bodyBytes, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            return "No se pudo leer el cuerpo de la respuesta";
        }
        return "Cuerpo de respuesta vacío";
    }
}