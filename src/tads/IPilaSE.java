package tads;


public interface IPilaSE<T> {
    
    public boolean estaVacia();
    
    public void apilar (T dato);
    
    public T desapilar();
    
    public T getTope();
    
    public void vaciar();
    
    public int cantidadNodos();
    
    public PilaSE<T> copiarPila();
    
    public void intercambiarTope();
    
    public void concatenar(PilaSE<T> otroPila);
    
    public void invertir();
}
