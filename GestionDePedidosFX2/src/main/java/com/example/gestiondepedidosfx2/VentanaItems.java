package com.example.gestiondepedidosfx2;

import com.example.gestiondepedidosfx2.clases.ItemPedido;
import com.example.gestiondepedidosfx2.clases.Pedido;
import com.example.gestiondepedidosfx2.clases.Producto;
import com.example.gestiondepedidosfx2.clases.Sesion;
import com.example.gestiondepedidosfx2.domain.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.hibernate.Session;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Clase que representa la ventana de gestión de ítems de un pedido en una interfaz gráfica JavaFX.
 * Permite realizar operaciones como añadir y borrar ítems, interactuando con la base de datos a través de la clase ItemPedidoDAO.
 */
public class VentanaItems implements Initializable {
    @javafx.fxml.FXML
    private TableColumn<ItemPedido, String> cIdItem;
    @javafx.fxml.FXML
    private TableColumn<ItemPedido, String> cCodigoItem;
    @javafx.fxml.FXML
    private TableColumn<ItemPedido, String> cCantidadItem;
    @javafx.fxml.FXML
    private TableColumn<ItemPedido, String> cProductoId;
    @javafx.fxml.FXML
    private TableView tablaItem;
    private ObservableList<ItemPedido> obs;
    private ItemPedido itemPedido;
    private Producto producto;
    private Pedido pedido;
    @javafx.fxml.FXML
    private TableColumn<ItemPedido, String> cNombreProducto;
    @javafx.fxml.FXML
    private TableColumn<ItemPedido, String> cPrecio;
    @javafx.fxml.FXML
    private TableColumn<ItemPedido, String> cCantidadDisponible;

    private final ItemPedidoDAOImp ItemPedidoDAO = new ItemPedidoDAOImp();
    private final ProductoDAOImp ProductoDAO = new ProductoDAOImp();
    private final PedidoDAOImp pedidoDAO = new PedidoDAOImp();
    @javafx.fxml.FXML
    private TextField txtCantidad;
    @javafx.fxml.FXML
    private ComboBox<Producto> comboProducto;
    @javafx.fxml.FXML
    private Button btnañadirItem;
    @javafx.fxml.FXML
    private Button btnborrarItem;
    private ItemPedido pedidoSeleccionado;

    /**
     * Inicializa el controlador de eventos y configura las columnas de la tabla.
     *
     * @param url             La ubicación relativa del archivo FXML.
     * @param resourceBundle  Los recursos específicos del idioma.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(Sesion.getPedido().toString());
        comboProducto.setConverter(new StringConverter<Producto>() {
            @Override
            public String toString(Producto producto) {
                if(producto!=null){
                    return producto.getNombre();
                }else{
                    return null;
                }

            }

            @Override
            public Producto fromString(String s) {
                return null;
            }
        });
        comboProducto.getItems().addAll(new ProductoDAOImp().loadAll());
        // Configuración de las columnas de la tabla
        cIdItem.setCellValueFactory((fila) -> {
            String id = String.valueOf(fila.getValue().getId());
            return new SimpleStringProperty(id);
        });
        cCodigoItem.setCellValueFactory((fila) -> {
            String codigo = String.valueOf(fila.getValue().getCpedido().getCodigo());
            return new SimpleStringProperty(codigo);
        });
        cCantidadItem.setCellValueFactory((fila) -> {
            String cantidad = String.valueOf(fila.getValue().getCantidad());
            return new SimpleStringProperty(cantidad);
        });
        cProductoId.setCellValueFactory((fila) -> {
            String productoId = String.valueOf(fila.getValue().getProducto().getId());
            return new SimpleStringProperty(productoId);
        });
        cNombreProducto.setCellValueFactory((fila) -> {
            String nombre = String.valueOf(fila.getValue().getProducto().getNombre());
            return new SimpleStringProperty(nombre);
        });
        cPrecio.setCellValueFactory((fila) -> {
            String precio = String.valueOf(fila.getValue().getProducto().getPrecio() +" €");
            return new SimpleStringProperty(precio);
        });
        cCantidadDisponible.setCellValueFactory((fila) -> {
            String cantidadDisponible = String.valueOf(fila.getValue().getProducto().getCantidadDisponible());
            return new SimpleStringProperty(cantidadDisponible);
        });

        obs = FXCollections.observableArrayList();
        if(!(Sesion.getPedido().getItems()==null)){
            obs.addAll(Sesion.getPedido().getItems());
        }
        tablaItem.setItems(obs);

        tablaItem.getSelectionModel().selectedItemProperty().addListener(
                (observable, vOld, vNew) -> {
                     pedidoSeleccionado = (ItemPedido) vNew;
                });
    }
    /**
     * Añade un nuevo ítem al pedido actual con los datos proporcionados en la interfaz gráfica.
     * El nuevo ítem se guarda en la base de datos y se agrega a la lista observable.
     * @param actionEvent Evento de acción que desencadena la llamada al método.
     */
    public void añadirItem(ActionEvent actionEvent) {
        String cItemPedido=Sesion.getPedido().getCodigo();
        Integer cantidad = Integer.valueOf(txtCantidad.getText());

        ItemPedido nuevoItemPedido = new ItemPedido();
        nuevoItemPedido.setCantidad(cantidad);
        nuevoItemPedido.setProducto(comboProducto.getSelectionModel().getSelectedItem());
        nuevoItemPedido.setCpedido(Sesion.getPedido());
        if(Sesion.getPedido().getItems()==null){
            Sesion.getPedido().setItems(new ArrayList<>());
        }
        Sesion.getPedido().getItems().add(nuevoItemPedido);
        System.out.println(Sesion.getPedido().getItems());

        try {
           nuevoItemPedido= ItemPedidoDAO.save(nuevoItemPedido);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        obs.add(nuevoItemPedido);
    }
    /**
     * Borra el ítem seleccionado del pedido actual en la interfaz gráfica y la base de datos.
     * Se muestra una alerta de confirmación antes de realizar la eliminación.
     * @param actionEvent Evento de acción que desencadena la llamada al método.
     */
    public void borrarItem(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿Deseas borrar" + Sesion.getPedido().getCodigo() + " del listado?");
        var result = alert.showAndWait().get();
        if (result == ButtonType.OK) {
            ItemPedidoDAO.delete(Integer.valueOf(pedidoSeleccionado.getId()));
            obs.remove(pedidoSeleccionado);

        }
    }
}