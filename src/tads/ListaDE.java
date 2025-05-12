package tads;

public class ListaDE<T extends Comparable<T>> implements ILista<T> {

    protected NodoDE<T> cabeza;
    protected int longitud;

    public ListaDE() {
        cabeza = null;
        longitud = 0;
    }

    @Override
    public void adicionarFinal(T x) {
        NodoDE<T> nodo = new NodoDE<T>(x);
        if (vacia()) {
            cabeza = nodo;
        } else {
            NodoDE<T> cursor = cabeza;
            while (cursor.getSiguiente() != null) {
                cursor = cursor.getSiguiente();
            }
            nodo.setAnterior(cursor);
            cursor.setSiguiente(nodo);
        }
        longitud++;
    }

    @Override
    public void insertar(T x, int pos) throws PosFueraDeRangoException {
        if ((pos < 0) || (pos > longitud)) {
            throw new PosFueraDeRangoException();
        }

        NodoDE<T> nodo = new NodoDE<T>(x);
        if (pos == 0) {
            nodo.setSiguiente(cabeza);
            if (cabeza != null) {
                cabeza.setAnterior(nodo);
            }
            cabeza = nodo;
        } else {
            NodoDE<T> cursor = cabeza;
            int i = 0;
            while (i < pos - 1) {
                i++;
                cursor = cursor.getSiguiente();
            }
            nodo.setSiguiente(cursor.getSiguiente());
            nodo.setAnterior(cursor);
            cursor.getSiguiente().setAnterior(nodo);
            cursor.setSiguiente(nodo);

        }
        longitud++;
    }

    @Override
    public T obtener(int pos) throws PosFueraDeRangoException {
        if ((pos < 0) || (pos >= longitud)) {
            throw new PosFueraDeRangoException();
        }

        NodoDE<T> cursor = cabeza;
        for (int i = 0; i < pos; i++) {
            cursor = cursor.getSiguiente();
        }
        return cursor.getDato();
    }

    @Override
    public void eliminar(int pos) throws PosFueraDeRangoException, ListaVaciaException {
        if (vacia()) {
            throw new ListaVaciaException();
        }
        if ((pos < 0) || (pos >= longitud)) {
            throw new PosFueraDeRangoException();
        }

        NodoDE<T> cursor = cabeza;
        if (pos == 0) {
            if (cabeza.getSiguiente() != null) {
                cabeza.getSiguiente().setAnterior(null);
            }
            cabeza = cursor.getSiguiente();
        } else {
            int i = 0;
            while (i < pos - 1) {
                i++;
                cursor = cursor.getSiguiente();
            }
            cursor.setSiguiente(cursor.getSiguiente().getSiguiente());
            if (cursor.getSiguiente() != null) {
                cursor.getSiguiente().setAnterior(cursor);
            }

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
    public void eliminarInicio() throws ListaVaciaException{
        if (vacia()) {
            throw new ListaVaciaException();
        } 
        cabeza = cabeza.getSiguiente();
        if(cabeza != null){
            cabeza.setAnterior(null);
        }
        longitud--;
    }

    @Override
    public void invertirIterativo() {
        if (cabeza == null || cabeza.getSiguiente() == null) {
            return; 
        }

        NodoDE<T> actual = cabeza;
        NodoDE<T> siguiente = null;
        NodoDE<T> anterior = null;

        while (actual != null) {
            siguiente = actual.getSiguiente();
            actual.setSiguiente(anterior);
            actual.setAnterior(siguiente);
            anterior = actual;
            actual = siguiente;
        }

        cabeza = anterior; 
    }
    
    

    @Override
    public void insertarOrdenado(T elem) {
        NodoDE<T> nuevoNodo = new NodoDE<>(elem);

        if (vacia()) {
            cabeza = nuevoNodo;
            longitud++;
            return;
        }

        NodoDE<T> actual = cabeza;
        NodoDE<T> anterior = null;

        while (actual != null && (actual.getDato().compareTo(nuevoNodo.getDato()) < 0)) {
            anterior = actual;
            actual = actual.getSiguiente();
        }

        if (anterior == null) {
            nuevoNodo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevoNodo);
            cabeza = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(actual);
            nuevoNodo.setAnterior(anterior);
            anterior.setSiguiente(nuevoNodo);
            if (actual != null) {
                actual.setAnterior(nuevoNodo);
            }
        }

        longitud++;
    }

    @Override
    public ListaDE<T> cambiar(T n, T m) {
        ListaDE<T> nuevaLista = new ListaDE<>();
        NodoDE<T> aux = cabeza;
        
        while(aux != null){
            if(aux.getDato().equals(n)){
                nuevaLista.adicionarFinal(m);
            }else{
                nuevaLista.adicionarFinal(aux.getDato());
            }
            aux = aux.getSiguiente();
        }
        return nuevaLista;
    }
    
    @Override
    public T maximo() {
        if (vacia()) {
            throw new ListaVaciaException();
        } 
        
        NodoDE<T> nodoActual = cabeza;
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
    public void ordenarLista() {
        if(vacia() || cabeza.getSiguiente() == null){
            return;
        }
        boolean intercambio = true;
        NodoDE<T> actual;
        NodoDE<T> siguiente;
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
    public void mostrar() {
        NodoDE<T> aux = cabeza;
        while(aux != null){
            System.out.println(aux.getDato());
            aux = aux.getSiguiente();
        }
    }

    @Override
    public boolean estaOrdenada() {
        NodoDE<T> nodoActual = cabeza;
        while (nodoActual != null && nodoActual.getSiguiente() != null) {
            if (nodoActual.getDato().compareTo(nodoActual.getSiguiente().getDato()) > 0) {
                return false; 
            }
            nodoActual = nodoActual.getSiguiente();
        }
        return true; 
    }

    @Override
    public int contar(T elem) {
        NodoDE<T> nodoActual = cabeza;
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
    public void eliminarFinal(){
        if(vacia()){
            throw new ListaVaciaException();
        }
        // Solo hay un elemento
        if(cabeza.getSiguiente() == null){
            cabeza = null;
        }else{
            NodoDE<T> aux = cabeza;
            //Recorrer hasta el ultimo
            while(aux.getSiguiente() != null){
                aux = aux.getSiguiente();
            }
            
            NodoDE<T> anterior = aux.getAnterior();
            anterior.setSiguiente(null);
        }
        longitud--;
    }

    @Override
    public void adicionarInicio(T elem) {
        NodoDE<T> nodo = new NodoDE<T>(elem);
        if (vacia()) {
            cabeza = nodo;
        } else {
           nodo.setSiguiente(cabeza);
           cabeza.setAnterior(nodo);
           cabeza = nodo;
        }
        longitud++;
    }

    @Override
    public boolean existeElemento(T elem) {
        if (vacia()) {
            throw new ListaVaciaException();
        } else {
           NodoDE<T> aux = cabeza;
           while (aux != null){
            if (aux.getDato().equals(elem)){
                return true;
            }
            else{
                aux = aux.getSiguiente();
            }
           }
        }
       
        return false;
    }
}


