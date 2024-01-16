package com.example.gestiondepedidosfx2.domain;

import com.example.gestiondepedidosfx2.clases.ItemPedido;

import java.sql.SQLException;
import java.util.List;
/**
 * La interfaz ItemPedidoDAO define métodos para acceder y manipular datos de la entidad ItemPedido en una base de datos.
 */
public interface ItemPedidoDAO<T> {

    public T load(Integer id) throws SQLException;
}