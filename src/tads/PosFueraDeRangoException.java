/*
 * PosFueraDeRangoException.java
 *
 * Created on 25 de marzo de 2024, 0:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tads;

/**
 *
 * @author yanete
 */
public class PosFueraDeRangoException extends RuntimeException {
    
    /** Creates a new instance of ExceptionPosFueraDeRango */
    public PosFueraDeRangoException() {
        super("Posicion fuera de rango");
    }
    
}
