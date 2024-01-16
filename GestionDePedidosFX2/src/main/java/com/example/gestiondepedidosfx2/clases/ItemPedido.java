package com.example.gestiondepedidosfx2.clases;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name="itemspedido")
public class ItemPedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Identificador único del elemento de pedido
    @Transient
    private String pedido;   // Descripción del pedido
    @Column(name="cantidad")
    private Integer cantidad;    // Cantidad del producto solicitado
    @ManyToOne
    @JoinColumn(name= "codigo_pedido", referencedColumnName = "codigo_pedido")
    private Pedido cpedido; // Relación muchos a uno con la entidad Pedido
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto; // Producto relacionado con este elemento de pedido

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getCpedido() {
        return cpedido;
    }
    public void setCpedido(Pedido cpedido) {
        this.cpedido = cpedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "id=" + id +
                ", pedido='" + pedido + '\'' +
                ", cantidad=" + cantidad +
                ", producto=" + producto +
                '}';
    }
}