package com.banquito.core.clientes.excepcion;

public class ServicioExternoException extends RuntimeException {
    private final int statusCode;

    public ServicioExternoException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
