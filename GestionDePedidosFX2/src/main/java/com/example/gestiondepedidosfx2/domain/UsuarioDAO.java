package com.example.gestiondepedidosfx2.domain;

import com.example.gestiondepedidosfx2.clases.Usuario;
//import com.example.gestiondepedidosfx2.excepciones.ContraseñaIncorrectaException;
import com.example.gestiondepedidosfx2.excepciones.ContrasenhaIncorrectaException;
import com.example.gestiondepedidosfx2.excepciones.UsuarioIncorrectoException;

import java.sql.SQLException;
/**
 * La interfaz UsuarioDAO define métodos para acceder y manipular datos de la entidad Usuario en una base de datos.
 */
public interface UsuarioDAO<T> {
    public T validateUser(String nombre, String contraseña) throws SQLException, UsuarioIncorrectoException, ContrasenhaIncorrectaException;

    public T get(Integer id) throws SQLException;
}