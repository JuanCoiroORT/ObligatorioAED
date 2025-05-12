
package dominio;

import java.time.LocalDate;

public class Entrada {
    private Evento evento;
    private Cliente cliente;
    private LocalDate fechaCompra;
    private String numeroEntrada;
    private String estado;

    public Entrada(Evento evento, Cliente cliente, LocalDate fechaCompra, String numeroEntrada, String estado) {
        this.evento = evento;
        this.cliente = cliente;
        this.fechaCompra = fechaCompra;
        this.numeroEntrada = numeroEntrada;
        this.estado = "activa";
    }

    public Evento getEvento() {
        return evento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public String getNumeroEntrada() {
        return numeroEntrada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public void setNumeroEntrada(String numeroEntrada) {
        this.numeroEntrada = numeroEntrada;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
