package com.example.gestiondepedidosfx2.domain;

import com.example.gestiondepedidosfx2.clases.Usuario;
import com.example.gestiondepedidosfx2.excepciones.ContrasenhaIncorrectaException;
import com.example.gestiondepedidosfx2.excepciones.UsuarioIncorrectoException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
/**
 * Clase que implementa la interfaz UsuarioDAO para operaciones de acceso a datos relacionadas con la entidad Usuario.
 * Esta implementación utiliza Hibernate como proveedor de persistencia.
 */
public class UsuarioDAOImp implements UsuarioDAO<Usuario> {
    /**
     * Valida un usuario en la base de datos en función del nombre de usuario y la contraseña proporcionados.
     *
     * @param nombre      Nombre de usuario a validar.
     * @param contraseña  Contraseña asociada al nombre de usuario.
     * @return Usuario validado si las credenciales son correctas.
     * @throws UsuarioIncorrectoException     Excepción lanzada si el nombre de usuario no es válido.
     * @throws ContrasenhaIncorrectaException Excepción lanzada si la contraseña no es válida.
     */
    public Usuario validateUser(String nombre, String contraseña) throws UsuarioIncorrectoException, ContrasenhaIncorrectaException {
        Usuario result = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> q = s.createQuery("from Usuario where nombre=:nombre and contraseña=:contraseña", Usuario.class);
            q.setParameter("nombre", nombre);
            q.setParameter("contraseña", contraseña);

            try {
                result = q.getSingleResult();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return result;
        }
    }
    /**
     * Obtiene un objeto Usuario desde la base de datos según su identificador.
     *
     * @param id Identificador del Usuario que se desea obtener.
     * @return Objeto Usuario obtenido desde la base de datos.
     */
    public Usuario get (Integer id) {
        var salida = new Usuario();

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            salida = s.get(Usuario.class, id);
        }
        return salida;
    }
}