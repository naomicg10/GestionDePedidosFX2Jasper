package com.example.gestiondepedidosfx2.domain;

import com.example.gestiondepedidosfx2.clases.ItemPedido;
import com.example.gestiondepedidosfx2.clases.Producto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Clase que implementa operaciones de acceso a datos relacionadas con la entidad Producto.
 * Esta implementación utiliza Hibernate como proveedor de persistencia.
 */
public class ProductoDAOImp{
    /**
     * Carga todos los productos desde la base de datos.
     *
     * @return ArrayList de Producto que contiene todos los productos almacenados en la base de datos.
     */
    public ArrayList<Producto> loadAll() {
        ArrayList<Producto>listaP=new ArrayList<>();
        String hql = "FROM Producto";
        try(Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Producto> query = s.createQuery(hql, Producto.class);
            listaP= (ArrayList<Producto>) query.getResultList();

            return listaP;
        }
    }
}