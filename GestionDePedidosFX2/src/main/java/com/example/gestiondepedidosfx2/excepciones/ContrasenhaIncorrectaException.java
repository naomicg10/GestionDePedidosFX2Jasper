package com.example.gestiondepedidosfx2.excepciones;

/**
 * Excepción personalizada para indicar que la contraseña proporcionada es incorrecta.
 */
public class ContrasenhaIncorrectaException extends Exception {
    /**
     * Crea una nueva instancia de la excepción con un mensaje personalizado.
     *
     * @param mensaje El mensaje que describe la razón de la excepción.
    **/
    public ContrasenhaIncorrectaException(String mensaje) {
        super(mensaje);
    }
}