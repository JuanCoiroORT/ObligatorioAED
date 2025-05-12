
package dominio;

import java.time.LocalDate;

public class Evento implements Comparable<Evento>{
    private String codigo;
    private String descripcion;
    private int aforoNecesario;
    private LocalDate fecha;
    private Sala sala;
    private int entradasDisponibles;
    private int entradasVendidas;
    
    public Evento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha, Sala sala, int entradasDisponibles, int entradasVendidas){
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.aforoNecesario = aforoNecesario;
        this.fecha = fecha;
        this.sala = sala;
        this.entradasDisponibles = entradasDisponibles;
        this.entradasVendidas = entradasVendidas;
    }
    
    // Getters
    public String getCodigo(){
        return codigo;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public int getAforoNecesario(){
        return aforoNecesario;
    }
    public LocalDate getFecha(){
        return fecha;
    }
    public Sala getSala(){
        return sala;
    }
    public int getEntradasDisponibles(){
        return entradasDisponibles;
    }
    public int getEntradasVendidas(){
        return entradasVendidas;
    }
    
    // Setters
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }
    public void setDescripcion(String desc){
        this.descripcion = desc;
    }
    public void setAforoNecesario(int aforo){
        this.aforoNecesario = aforo;
    }
    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }
    public void setSala(Sala sala){
        this.sala = sala;
    }
    public void setEntradasDisponiobles(int disponibles){
        this.entradasDisponibles = disponibles;
    }
    public void setEntradasVenididas(int vendidas){
        this.entradasVendidas = vendidas;
    }

    @Override
    public int compareTo(Evento o) {
        return this.codigo.compareTo(o.codigo);
    }
    
    public String toString(){
        return "Codigo: " + codigo + ", descripcion: " + descripcion + 
                ", sala: " + sala.getNombre() + ", entradas disponibles: " + 
                entradasDisponibles + ", entradas vendidas:" + entradasVendidas;
    }
}
