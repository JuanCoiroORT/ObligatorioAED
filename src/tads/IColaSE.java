
package tads;


public interface IColaSE<T> {
    
    public void encolar (T dato);
    
    public T desencolar () throws Exception;
    
    public T getFrente ();
    
    public boolean estaVacia();
    
    public int cantidadNodos();
    
    public T[] datos();
    
    public void eliminarRepetidos() throws Exception;
    
    public ColaSE<T> interaccionColas(ColaSE<T> cola2);
}
