/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.time.LocalDate;
import tads.ListaSE;

/**
 *
 * @author renzo
 */
public class Sala implements Comparable<Sala>{

    private String nombre;
    private int capacidad;
    private ListaSE<LocalDate> fechasOcupadas;

    public Sala(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.fechasOcupadas = new ListaSE<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setCapacidad(int capacidad){
        this.capacidad = capacidad;
    }
    
    public boolean estaOcupada(LocalDate fecha){
        for (int i = 0; i < fechasOcupadas.longitud(); i++) {
            if(fechasOcupadas.obtener(i).equals(fecha)){
                return true;
            }
        }
        return false;
    }
    
    public void agendarEvento(LocalDate fecha){
        if(!estaOcupada(fecha)){
            fechasOcupadas.adicionarFinal(fecha);
        }
    }

    @Override
    public int compareTo(Sala o) {
        return this.nombre.compareTo(o.nombre);
    }
    
    public String toString(){
        return "Nombre: " + nombre + ", capacidad: " + capacidad;
    }
}
