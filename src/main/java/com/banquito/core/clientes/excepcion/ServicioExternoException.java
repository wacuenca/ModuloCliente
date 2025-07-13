package com.banquito.core.clientes.excepcion;

public class ServicioExternoException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final int statusCode;

    public ServicioExternoException(String message) {
        super(message);
        this.statusCode = 0; // Default status code
    }

    public ServicioExternoException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 0; // Default status code
    }

    public ServicioExternoException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
