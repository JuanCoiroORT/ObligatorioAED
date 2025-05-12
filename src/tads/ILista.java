package tads;

public interface ILista<T> {

    void adicionarFinal(T elem);
    
    void adicionarInicio(T elem);

    void insertar(T x, int pos) throws Exception;

    T obtener(int pos) throws Exception;

    void eliminar(int pos) throws Exception;

    int longitud();

    boolean vacia();
    
    void eliminarInicio();
    
    boolean estaOrdenada();
    
    void invertirIterativo();
    
    void insertarOrdenado(T elem);
    
    int contar(T elem);
    
    T maximo();
    
    void eliminarFinal();
    
    ILista<T> cambiar(T n, T m);
    
    void ordenarLista();
    
    void mostrar();
    
    boolean existeElemento(T elem);
}
