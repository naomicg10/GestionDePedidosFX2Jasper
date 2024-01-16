package com.example.gestiondepedidosfx2.domain;

import com.example.gestiondepedidosfx2.clases.Pedido;
import org.hibernate.Transaction;

/**
 * Clase que implementa la interfaz PedidoDAO para operaciones de acceso a datos relacionadas con la entidad Pedido.
 * Esta implementación utiliza Hibernate como proveedor de persistencia.
 */
public class PedidoDAOImp implements PedidoDAO<Pedido> {
    /**
     * Elimina un objeto Pedido de la base de datos.
     *
     * @param data Objeto Pedido que se desea eliminar. Se utiliza el identificador (ID) del pedido para la operación.
     */
    public void delete(Pedido data) {
        HibernateUtil.getSessionFactory().inTransaction( (session) -> {
            Pedido g = session.get(Pedido.class,data.getId());
            session.remove(g);
        });
    }
    /**
     * Guarda un objeto Pedido en la base de datos.
     *
     * @param data Objeto Pedido que se desea guardar.
     * @return Objeto Pedido después de guardar en la base de datos.
     */
    public Pedido save(Pedido data) {
        try(org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.persist(data);
            t.commit();
        }
        return data;
    }
    /**
     * Actualiza un objeto Pedido en la base de datos.
     *
     * @param data Objeto Pedido con los nuevos datos que se desean actualizar.
     */
    public void update(Pedido data) {
        try(org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            Pedido p = s.get(Pedido.class, data.getId());
            Pedido.merge(data, p);
            t.commit();
        }
    }
}