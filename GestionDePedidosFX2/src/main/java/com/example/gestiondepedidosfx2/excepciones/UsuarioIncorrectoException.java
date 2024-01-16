package com.example.gestiondepedidosfx2.excepciones;

/**
 * Excepción personalizada para indicar que el usuario proporcionado es incorrecto.
 */
public class UsuarioIncorrectoException extends Exception {
    /**
     * Crea una nueva instancia de la excepción con un mensaje personalizado.
     *
     * @param mensaje El mensaje que describe la razón de la excepción.
     */
    public UsuarioIncorrectoException(String mensaje) {
        super(mensaje);
    }
}

