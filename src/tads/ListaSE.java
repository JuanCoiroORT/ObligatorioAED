package tads;

public class ListaSE<T extends Comparable<? super T> > implements ILista<T> {

    protected NodoSE<T> cabeza;
    protected int longitud;

    public ListaSE() {
        cabeza = null;
        longitud = 0;
    }

    @Override
    public void adicionarFinal(T x) {
        NodoSE<T> elem = new NodoSE<T>(x);
        if (vacia()) 
            cabeza = elem;
        else
        {
          NodoSE<T> aux = cabeza;
          while (aux.getSiguiente() != null){
            aux = aux.getSiguiente();             
          }
          aux.setSiguiente(elem);  
        }
        
        longitud++;
    }
   

    @Override
    public void insertar(T x, int pos) throws PosFueraDeRangoException {
        if ((pos < 0) || (pos > longitud)) {
             throw new PosFueraDeRangoException();
        }

        NodoSE<T> nodo = new NodoSE<T>(x, null);
        if (pos == 0) {
            nodo.setSiguiente(cabeza);
            cabeza = nodo;
        } else {
            NodoSE<T> cursor = cabeza;
            int i = 0;
            while (i < pos - 1) {
                i++;
                cursor = cursor.getSiguiente();
            }
            nodo.setSiguiente(cursor.getSiguiente());
            cursor.setSiguiente(nodo);
        }
        longitud++;
   
    }

    @Override
    public T obtener(int pos) throws PosFueraDeRangoException{
        if(pos < 0 || pos >= longitud){
           throw new PosFueraDeRangoException();
        }
        NodoSE<T> aux = cabeza;
        for (int i = 0; i < pos; i++) {
            aux = aux.getSiguiente();
        }
        return aux.getDato();
    }

    @Override
    public void eliminar(int pos) throws PosFueraDeRangoException, ListaVaciaException {
         if (vacia()) {
            throw new ListaVaciaException();
        }
        if ((pos < 0) || (pos >= longitud)) {
            throw new PosFueraDeRangoException();
        }

        NodoSE<T> cursor = cabeza;
        if (pos == 0) {
            cabeza = cursor.getSiguiente();
        } else {
            int i = 0;
            while (i < pos - 1) {
                i++;
                cursor = cursor.getSiguiente();
            }            
            cursor.setSiguiente(cursor.getSiguiente().getSiguiente());
        }
        longitud--;

    }

    @Override
    public int longitud() {
        return longitud;
    }

    @Override
    public boolean vacia() {
        return (longitud == 0);
    }
    
    
    @Override
    public void adicionarInicio(T elem){
        NodoSE<T> nodo = new NodoSE<T>(elem, null);
        if (vacia()) {
            cabeza = nodo;
        } else {
           nodo.setSiguiente(cabeza);
           cabeza = nodo;
        }
        longitud++;  
    }
   
    
    @Override
    public boolean existeElemento(T elem){
        NodoSE<T> aux = cabeza;
        while(aux != null){
            if(aux.getDato().equals(elem)){
                return true;
            }
            aux = aux.getSiguiente();
        }
        return false;
    }
    
    @Override
    public void eliminarInicio(){
        if (vacia()) {
            throw new ListaVaciaException();
        } 
        else {
          cabeza = cabeza.getSiguiente();
          longitud--;
        }

    }
    
    @Override
    public void eliminarFinal(){
        if (vacia()) {
            throw new ListaVaciaException();
        } 
        if(cabeza.getSiguiente() == null){
            cabeza = null;
        }else{
            NodoSE<T> aux = cabeza;
            while(aux.getSiguiente().getSiguiente() != null){
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(null);
        }
        longitud--;
    }
    
    
    @Override
    public void invertirIterativo(){
        if (vacia()) {
            throw new ListaVaciaException();
        } 
        NodoSE<T> nodoActual = cabeza;
        NodoSE<T> nodoAnterior = null;
        NodoSE<T> nodoSiguiente;

        while (nodoActual != null) {
            nodoSiguiente = nodoActual.getSiguiente();
            nodoActual.setSiguiente(nodoAnterior);
            nodoAnterior = nodoActual;
            nodoActual = nodoSiguiente;
        }

        cabeza = nodoAnterior; 
    }
    
    
    @Override
    public boolean estaOrdenada() {
        NodoSE<T> nodoActual = cabeza;
        while (nodoActual != null && nodoActual.getSiguiente() != null) {
            if (nodoActual.getDato().compareTo(nodoActual.getSiguiente().getDato()) > 0) {
                return false; 
            }
            nodoActual = nodoActual.getSiguiente();
        }
        return true; 
    }
    
    @Override
     public void insertarOrdenado(T elem) {
        NodoSE<T> nuevoNodo = new NodoSE<T>(elem, null);

        if (cabeza == null || cabeza.getDato().compareTo(elem) > 0) {
            nuevoNodo.setSiguiente(cabeza);
            cabeza = nuevoNodo;
        } else {
            NodoSE<T> nodoActual = cabeza;
            while (nodoActual.getSiguiente() != null && nodoActual.getSiguiente().getDato().compareTo(elem) < 0) {

                nodoActual = nodoActual.getSiguiente();
            }
            nuevoNodo.setSiguiente(nodoActual.getSiguiente());
            nodoActual.setSiguiente(nuevoNodo);
        }

        longitud++;
    }
     
    @Override
    public int contar(T elem) {
        NodoSE<T> nodoActual = cabeza;
        int contador = 0;

        while (nodoActual != null) {
            if (nodoActual.getDato().compareTo(elem) == 0) {
                contador++;
            }
            nodoActual = nodoActual.getSiguiente();
        }

        return contador;
    }
    
    @Override
    public T maximo() {
        if (vacia()) {
            throw new ListaVaciaException();
        } 
        
        NodoSE<T> nodoActual = cabeza;
        T maximo = cabeza.getDato();

        while (nodoActual != null) {
            if (nodoActual.getDato().compareTo(maximo) > 0) {
                maximo = nodoActual.getDato();
            }
            nodoActual = nodoActual.getSiguiente();
        }

        return maximo;
    }

    @Override
    public ListaSE<T> cambiar(T n, T m) {
        if (existeElemento(n)) {
            ListaSE<T> listaResultado = new ListaSE<>();
            NodoSE<T> nodoActual = cabeza;

            while (nodoActual != null) {
                if (nodoActual.getDato().equals(n)) {
                    listaResultado.adicionarFinal(m); 
                } else {
                    listaResultado.adicionarFinal(nodoActual.getDato());
                }
                nodoActual = nodoActual.getSiguiente();
            }

            return listaResultado;
        }
        else return this;
    }
     
    @Override
    public void ordenarLista(){
        if(vacia() || cabeza.getSiguiente() == null){
            return;
        }
        boolean intercambio = true;
        NodoSE<T> actual;
        NodoSE<T> siguiente;
        while(intercambio){
            intercambio = false;
            actual = cabeza;
            //Recorrer lista
            while(actual != null && actual.getSiguiente() != null){
                siguiente = actual.getSiguiente();
                if(actual.getDato().compareTo(siguiente.getDato()) > 0){
                    // Intercambiar datos
                    T temp = actual.getDato();
                    actual.setDato(siguiente.getDato());
                    siguiente.setDato(temp);
                    intercambio = true;
                }
                actual = actual.getSiguiente();
            }
        }
    }
    
    @Override
    public void mostrar(){
        NodoSE<T> aux = cabeza;
        while(aux != null){
            System.out.println(aux.getDato());
            aux = aux.getSiguiente();
        }
    }

      
}
