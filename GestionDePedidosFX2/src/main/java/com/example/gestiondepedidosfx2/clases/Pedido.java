package com.example.gestiondepedidosfx2.clases;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name="pedido")
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;          // Identificador único del pedido
    @Column(name="codigo_pedido")
    private String codigo;   // Código único del pedido
    @Column(name="fecha")
    private Date fecha;      // Fecha en la que se realizó el pedido
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;  // Usuario que realizó el pedido
    @OneToMany(mappedBy = "cpedido",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<ItemPedido> items;  // Elementos de pedido asociados a este pedido
    @Column(name="total")
    private double total;    // Total del pedido

    public static void merge(Pedido origen, Pedido destino) {
        destino.setId(origen.getId());
        destino.setCodigo(origen.getCodigo());
        destino.setFecha(origen.getFecha());
        destino.setUsuario(origen.getUsuario());
        destino.setItems(origen.getItems());
        destino.setTotal(origen.getTotal());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", fecha=" + fecha +
                ", usuario=" + getUsuario().getNombre() +
                ", total=" + total +
                "Lista de Items= "+items+
                '}';
    }
}