/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author renzo
 */
public class Cliente implements Comparable<Cliente>{
    private String cedula;
    private String nombre; 
    
    public Cliente(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    // Orden ascendente
    @Override
    public int compareTo(Cliente o) {
        return this.cedula.compareTo(o.cedula);
    }

    public String toString(){
        return "CI: " + cedula + ", nombre: " + nombre;
    }
   
}
