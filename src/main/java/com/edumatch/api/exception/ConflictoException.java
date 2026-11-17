package com.edumatch.api.exception;

/** Se lanza cuando la petición choca con datos existentes, por ejemplo un correo repetido (responde 409). */
public class ConflictoException extends RuntimeException {

    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
