package com.example.gestiondepedidosfx2.domain;

import com.example.gestiondepedidosfx2.clases.Producto;

import java.sql.SQLException;

/**
 * La interfaz ProductoDAO define métodos para acceder y manipular datos de la entidad Producto en una base de datos.
 */
public interface ProductoDAO<T> {

    public T load(Integer id) throws SQLException;
}