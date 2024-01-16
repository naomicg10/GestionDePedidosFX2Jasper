package com.example.gestiondepedidosfx2.clases;

import lombok.Getter;
import lombok.Setter;

/**
 * La clase Sesion se utiliza para mantener información de usuario, producto, pedido y posición en un contexto de sesión.
 */
public class Sesion {
    private static Usuario usuario;    // Usuario actual en sesión
    private static Producto producto;  // Producto seleccionado en sesión
    private static Pedido pedido;      // Pedido en sesión
    private static ItemPedido ItemPedido;      // Item pedido en sesión
    private static Integer pos = null; // Posición en una lista o arreglo en sesión

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Sesion.usuario = usuario;
    }

    public static Producto getProducto() {
        return producto;
    }

    public static void setProducto(Producto producto) {
        Sesion.producto = producto;
    }

    public static Pedido getPedido() {
        return pedido;
    }

    public static void setPedido(Pedido pedido) {
        Sesion.pedido = pedido;
    }

    public static Integer getPos() {
        return pos;
    }

    public static void setPos(Integer pos) {
        Sesion.pos = pos;
    }

    public static ItemPedido getItemPedido() {
        return ItemPedido;
    }

    public static void setItemPedido(ItemPedido itemPedido) {
        ItemPedido = itemPedido;
    }
}