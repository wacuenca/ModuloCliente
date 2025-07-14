package com.banquito.core.clientes.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Deserializador personalizado para Instant que maneja múltiples formatos de fecha
 */
public class FlexibleInstantDeserializer extends JsonDeserializer<Instant> {

    private static final DateTimeFormatter[] FORMATTERS = {
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"), // Microsegundos (6 dígitos)
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),    // Milisegundos (3 dígitos)
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"),        // Sin fracciones
        DateTimeFormatter.ISO_INSTANT                                    // Formato ISO estándar
    };

    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        
        // Intentar parsear con diferentes formatos
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return Instant.from(formatter.parse(text));
            } catch (DateTimeParseException e) {
    
            }
        }
        
        try {
            return Instant.parse(text);
        } catch (DateTimeParseException e) {
            throw new IOException("Unable to parse date: " + text, e);
        }
    }
}
