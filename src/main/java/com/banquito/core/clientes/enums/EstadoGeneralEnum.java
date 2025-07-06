package com.banquito.core.clientes.enums;

public enum EstadoGeneralEnum {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO");

    private final String valor;

    EstadoGeneralEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
