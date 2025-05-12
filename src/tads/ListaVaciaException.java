/*
 * ListaVaciaException.java
 *
 * Created on 25 de marzo de 2024 0:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tads;

/**
 *
 * @author yanete
 */
public class ListaVaciaException extends RuntimeException {
    
    /** Creates a new instance of ExceptionListaVacia */
    public ListaVaciaException() {
        super("Lista Vacia");
    }
}
