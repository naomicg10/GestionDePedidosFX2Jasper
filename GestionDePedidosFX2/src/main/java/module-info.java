module com.example.gestiondepedidosfx2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;

    requires javafx.swing;
    requires jasperreports;

    opens com.example.gestiondepedidosfx2.domain;
    opens com.example.gestiondepedidosfx2.clases;
    opens com.example.gestiondepedidosfx2.excepciones;


    opens com.example.gestiondepedidosfx2 to javafx.fxml;
    exports com.example.gestiondepedidosfx2;
}