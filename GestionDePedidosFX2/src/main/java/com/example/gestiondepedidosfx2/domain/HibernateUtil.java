package com.example.gestiondepedidosfx2.domain;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Logger;

/**
 * Clase de utilidad para la configuración de Hibernate y la creación de la SessionFactory.
 * Esta clase proporciona un método estático para obtener la SessionFactory de Hibernate.
 *
 * @author Naomi
 * @version 1.0
 */
public class HibernateUtil {
    /**
     * La clase {@code HibernateUtil} es responsable de configurar Hibernate y crear una SessionFactory.
     * Utiliza la clase {@link Configuration} para cargar la configuración de Hibernate desde el archivo predeterminado (hibernate.cfg.xml).
     * El bloque de inicialización estático intenta construir la SessionFactory y, si tiene éxito, registra un mensaje de éxito.
     * Si ocurre una excepción durante el proceso, se registra un mensaje de error junto con la pila de excepciones.
     */
    private static SessionFactory sf;
    private static Logger log=Logger.getLogger(HibernateUtil.class.getName());

    static{
        try {
            Configuration cfg = new Configuration();
            cfg.configure();
            sf = cfg.buildSessionFactory();
            log.severe("SessionFactory creada con exito!");
        } catch ( Exception ex){
            ex.printStackTrace();
            log.severe("Error al crear SessionFactory");
        }
    }

    public static SessionFactory getSessionFactory(){
        return sf;
    }
}
