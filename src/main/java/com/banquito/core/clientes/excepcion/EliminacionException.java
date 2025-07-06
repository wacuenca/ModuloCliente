package com.banquito.core.clientes.excepcion;

public class EliminacionException extends ClienteException {
    private static final Integer CODIGO_ERROR_DEFAULT = 4000;
    
    public EliminacionException(String mensaje) {
        super(mensaje, CODIGO_ERROR_DEFAULT);
    }
    
    public EliminacionException(String mensaje, Integer codigoError) {
        super(mensaje, codigoError);
    }
}
