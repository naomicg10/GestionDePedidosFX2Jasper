package com.example.gestiondepedidosfx2;

import com.example.gestiondepedidosfx2.clases.Pedido;
import com.example.gestiondepedidosfx2.clases.Sesion;
import com.example.gestiondepedidosfx2.domain.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;
import org.hibernate.Session;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Clase que representa la ventana de gestión de pedidos en una interfaz gráfica JavaFX.
 * Permite realizar operaciones como añadir, borrar y editar pedidos, interactuando con la base de datos a través de la clase PedidoDAO.
 */
public class VentanaPedidos implements Initializable {
    public Button btnAñadir;
    public Button btnBorrar;
    public Spinner<Double> spinnerTotal;
    public TextField txtUsuario;
    public DatePicker dpFecha;
    public TextField txtCodigo;
    public TextField txtId;
    @javafx.fxml.FXML
    private BorderPane ventana2;
    @javafx.fxml.FXML
    private Menu menu;
    @javafx.fxml.FXML
    private MenuItem menuLogout;
    @javafx.fxml.FXML
    private TableView<Pedido> tablaPedido;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cIdPedido;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cCodigoPedido;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cFechaPedido;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cUsuarioIdPedido;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cTotalPedido;
    private ObservableList<Pedido> obs;
    private Pedido pedido;
    private final PedidoDAOImp pedidoDAO = new PedidoDAOImp();
    @javafx.fxml.FXML
    private Button btnEditar;
    @javafx.fxml.FXML
    private Button btnPdf;

    /**
     * Inicializa el controlador de eventos y configura las columnas de la tabla de pedidos.
     *
     * @param url             La ubicación relativa del archivo FXML.
     * @param resourceBundle  Los recursos específicos del idioma.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configuración de las columnas de la tabla de pedidos
        cIdPedido.setCellValueFactory((fila) -> {
            String id = String.valueOf(fila.getValue().getId());
            return new SimpleStringProperty(id);
        });
        cCodigoPedido.setCellValueFactory((fila) -> {
            String codigo = String.valueOf(fila.getValue().getCodigo());
            return new SimpleStringProperty(codigo);
        });
        cFechaPedido.setCellValueFactory((fila) -> {
            String fecha = String.valueOf(fila.getValue().getFecha());
            return new SimpleStringProperty(fecha);
        });
        cUsuarioIdPedido.setCellValueFactory((fila) -> {
            String usuarioId = String.valueOf(fila.getValue().getUsuario().getNombre());
            return new SimpleStringProperty(usuarioId);
        });
        cTotalPedido.setCellValueFactory((fila) -> {
            String total = String.valueOf(fila.getValue().getTotal());
            return new SimpleStringProperty(total);
        });

        obs = FXCollections.observableArrayList();
        System.out.println(Sesion.getUsuario());
        obs.addAll(Sesion.getUsuario().getPedidos());
        tablaPedido.setItems(obs);

        SpinnerValueFactory.DoubleSpinnerValueFactory spinnerFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE, 0.0, 0.1);

        spinnerTotal.setValueFactory(spinnerFactory);

        dpFecha.setEditable(false);
        dpFecha.setValue(LocalDate.now());

        tablaPedido.getSelectionModel().selectedItemProperty().addListener(
                (observable, vOld, vNew) -> {
                    Sesion.setPedido(vNew);
                });

        tablaPedido.setOnMouseClicked(event -> {
            if(event.getClickCount()==2&&Sesion.getPedido()!=null){
                Stage stage = new Stage();
                Image imagen = new Image(HelloApplication.class.getClassLoader().getResource("icon.png").toExternalForm());
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ventanaItems.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 900, 500);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.getIcons().add(imagen);
                stage.setTitle("Gestión de Pedidos");
                stage.setScene(scene);
                stage.show();
            }
        });


    }

    /**
     * Maneja el evento de cierre de sesión (logout) y carga la ventana de inicio de sesión.
     *
     * @param actionEvent El evento de acción generado por el clic en el botón de cierre de sesión.
     */
    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        Sesion.setUsuario(null);
        HelloApplication.loadFXML("login.fxml");
    }
    /**
     * Añade un nuevo pedido con los datos proporcionados en la interfaz gráfica.
     * El nuevo pedido se guarda en la base de datos y se agrega a la lista observable.
     * @param actionEvent Evento de acción que desencadena la llamada al método.
     */
    public void añadir(ActionEvent actionEvent) {
        String codigo = txtCodigo.getText();
        Double total = spinnerTotal.getValue();

        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setCodigo(codigo);
        nuevoPedido.setFecha(Date.valueOf(LocalDate.now()));
        nuevoPedido.setUsuario(Sesion.getUsuario());
        nuevoPedido.setItems(null);
        nuevoPedido.setTotal(total);
        Sesion.getUsuario().getPedidos().add(nuevoPedido);

        try {
            pedidoDAO.save(nuevoPedido);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        obs.add(nuevoPedido);
    }
    /**
     * Borra el pedido seleccionado de la interfaz gráfica y la base de datos.
     * Se muestra una alerta de confirmación antes de realizar la eliminación.
     * @param actionEvent Evento de acción que desencadena la llamada al método.
     */
    public void borrar(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿Deseas borrar" + Sesion.getPedido().getCodigo() + " del listado?");
        var result = alert.showAndWait().get();
        if (result == ButtonType.OK) {
            pedidoDAO.delete(Sesion.getPedido());
            obs.remove(Sesion.getPedido());
            tablaPedido.setItems(obs);

            tablaPedido.getSelectionModel().clearSelection();
        }
    }
    /**
     * Edita el pedido seleccionado con los datos proporcionados en la interfaz gráfica.
     * Los cambios se actualizan en la base de datos y se reflejan en la interfaz gráfica.
     * @param actionEvent Evento de acción que desencadena la llamada al método.
     */
    public void editar(ActionEvent actionEvent) {
        Pedido pedidoSeleccionado = tablaPedido.getSelectionModel().getSelectedItem();
        pedidoSeleccionado.setCodigo(txtCodigo.getText());
        pedidoSeleccionado.setFecha(Date.valueOf(dpFecha.getValue()));
        pedidoSeleccionado.setTotal(spinnerTotal.getValue());

        try {
            pedidoDAO.update(pedidoSeleccionado);
            tablaPedido.refresh();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método descarga un informe en formato PDF del pedido actual utilizando JasperReports.
     * Se conecta a una base de datos MySQL local, recopila información relevante del pedido
     * y del usuario actual, llena un informe Jasper con estos datos y lo exporta a un archivo PDF.
     *
     * @param actionEvent El evento que desencadena la descarga del PDF (puede ser un clic de botón, por ejemplo).
     *
     * @throws RuntimeException Si se produce un error al establecer la conexión con la base de datos,
     *                           al llenar el informe Jasper o al exportar el informe a PDF.
     */
    @javafx.fxml.FXML
    public void descargarPdf(ActionEvent actionEvent) {
        // Establecer la conexión a la base de datos MySQL local
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost/gestionpedidos", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Crear un mapa para almacenar los parámetros necesarios para el informe Jasper
        HashMap hm = new HashMap<>();
        hm.put("codigo", Sesion.getPedido().getCodigo());
        hm.put("nombreUsuario", Sesion.getUsuario().getNombre());
        hm.put("correoUsuario", Sesion.getUsuario().getEmail());
        hm.put("fecha", Sesion.getPedido().getFecha());
        hm.put("total", Sesion.getPedido().getTotal());

        // Llenar el informe Jasper con los datos y la conexión a la base de datos
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport("Pedidos.jasper", hm, c);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        // Crear un visor Jasper para visualizar el informe
        JRViewer viewer = new JRViewer(jasperPrint);

        // Configurar y mostrar un JFrame para visualizar el informe
        JFrame frame = new JFrame("Listado de Pedidos");
        frame.getContentPane().add(viewer);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);

        // Imprimir mensaje indicando que la visualización del informe ha concluido
        System.out.println("Visualización del informe completa.");

        // Exportar el informe Jasper a un archivo PDF
        JRPdfExporter exp = new JRPdfExporter();
        exp.setExporterInput(new SimpleExporterInput(jasperPrint));
        exp.setExporterOutput(new SimpleOutputStreamExporterOutput("pedidos.pdf"));
        exp.setConfiguration(new SimplePdfExporterConfiguration());
        try {
            exp.exportReport();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        // Imprimir mensaje indicando que la exportación del informe a PDF ha concluido
        System.out.println("Exportación del informe a PDF completa.");
    }
}