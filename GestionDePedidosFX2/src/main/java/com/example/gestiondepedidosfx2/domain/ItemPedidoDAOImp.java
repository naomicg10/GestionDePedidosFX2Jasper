package com.example.gestiondepedidosfx2.domain;

import com.example.gestiondepedidosfx2.clases.ItemPedido;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 * Clase que implementa la interfaz ItemPedidoDAO para operaciones de acceso a datos relacionadas con la entidad ItemPedido.
 * Esta implementación utiliza Hibernate como proveedor de persistencia.
 */
public class ItemPedidoDAOImp implements ItemPedidoDAO<ItemPedido> {
    /**
     * Constructor de la clase que acepta una sesión de Hibernate como parámetro.
     *
     * @param session Sesión de Hibernate que se utilizará para las operaciones de base de datos.
     */
    public ItemPedidoDAOImp(Session session) {
    }
    /**
     * Constructor de la clase sin parámetros.
     */
    public ItemPedidoDAOImp() {

    }
    /**
     * Carga un objeto ItemPedido desde la base de datos según el identificador proporcionado.
     *
     * @param id Identificador del ItemPedido que se desea cargar.
     * @return Objeto ItemPedido cargado desde la base de datos.
     */
    public ItemPedido load(Integer id) {
        var salida = new ItemPedido();

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            salida = s.get(ItemPedido.class, id);
        }
        return salida;
    }
    /**
     * Elimina un objeto ItemPedido de la base de datos según el identificador proporcionado.
     *
     * @param data Identificador del ItemPedido que se desea eliminar.
     */
    public void delete(Integer data) {
        HibernateUtil.getSessionFactory().inTransaction( (session) -> {
            ItemPedido g = session.get(ItemPedido.class,data);
            session.remove(g);
        });
    }
    /**
     * Guarda un objeto ItemPedido en la base de datos.
     *
     * @param data Objeto ItemPedido que se desea guardar.
     * @return Objeto ItemPedido después de guardar en la base de datos.
     */
    public ItemPedido save(ItemPedido data) {
        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.persist(data);
            t.commit();
        }
        return data;
    }
}