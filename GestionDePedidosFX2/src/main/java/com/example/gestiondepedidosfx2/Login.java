package com.example.gestiondepedidosfx2;

import com.example.gestiondepedidosfx2.clases.Sesion;
import com.example.gestiondepedidosfx2.clases.Usuario;
import com.example.gestiondepedidosfx2.domain.HibernateUtil;
import com.example.gestiondepedidosfx2.domain.UsuarioDAOImp;
//import com.example.gestiondepedidosfx2.excepciones.ContraseñaIncorrectaException;
import com.example.gestiondepedidosfx2.excepciones.ContrasenhaIncorrectaException;
import com.example.gestiondepedidosfx2.excepciones.UsuarioIncorrectoException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * La clase Login es un controlador de eventos para la ventana de inicio de sesión de la aplicación.
 */
public class Login implements Initializable {
    @javafx.fxml.FXML
    private TextField txtUsuario;
    @javafx.fxml.FXML
    private PasswordField txtContraseña;
    @javafx.fxml.FXML
    private Button buttonLogin;

    /**
     * Inicializa el controlador de eventos.
     *
     * @param url             La ubicación relativa del archivo FXML.
     * @param resourceBundle  Los recursos específicos del idioma.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void button(ActionEvent actionEvent) {
        String usuario = txtUsuario.getText();
        String contraseña = txtContraseña.getText();
        try {
            Usuario dao = (new UsuarioDAOImp()).validateUser(usuario,contraseña);
            Sesion.setUsuario(dao);
            System.out.println(dao);
        } catch (UsuarioIncorrectoException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Mensaje de error");
            alerta.setContentText("El usuario es incorrecto");
            alerta.showAndWait();
            throw new RuntimeException(e);
        }catch (ContrasenhaIncorrectaException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Mensaje de error");
            alerta.setContentText("La contraseña es incorrecta");
            alerta.showAndWait();
            throw new RuntimeException(e);
        }
        HelloApplication.loadFXML("ventanaPedidos.fxml");
    }
}