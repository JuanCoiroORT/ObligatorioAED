package tads;

public class ColaSE<T> implements IColaSE<T>{
    private NodoSE<T> frente;
    private NodoSE<T> fondo;

    public ColaSE() {
        frente = null;
        fondo = null;
    }

    //PUSH
    @Override
    public void encolar(T dato) {
        NodoSE<T> nuevoNodo = new NodoSE<T>(dato);
        // Si esta vacia fondo y frente son iguales
        if(estaVacia()){
            frente = nuevoNodo;
        }
        else{
            //Agrega el nodo al final
            fondo.setSiguiente(nuevoNodo);
        }
        //Actualiza el fondo al elemento nuevo
        fondo = nuevoNodo;
    }

    //POP
    @Override
    public T desencolar() throws Exception {
       if(estaVacia()){
           throw new Exception("Lista vacia");
       }
       //Tomar dato a desencolar
       T dato = frente.getDato();
       // Mover frente al siguiente nodo
       frente = frente.getSiguiente();
       // Si la cola queda vacia, tambien se actualiza el fondo
       if(frente == null) {
           fondo = null;
       }
       return dato;
    }

    @Override
    public boolean estaVacia() {
        return frente == null;
    }

    @Override
    public T getFrente() {
        // Si frente no es null hace frente.getdato
        // Si frente es null devuelve null
       /*return  frente != null ? frente.getDato() : null;*/
       if(frente != null){
           return frente.getDato();
       }
       else{
           return null;
        }
    }
    
    public T getFondo() {
        // Si fondo no es null hace fondo.getDato
        // Si fondo es null devuelve null
        return  fondo != null ? fondo.getDato() : null;
    }
    
    @Override
    public int cantidadNodos() {
        NodoSE<T> aux = frente;
        int cantidad = 0;
        
        // Recorre los elementos de la cola y los cuenta
        while(aux != null){
            aux = aux.getSiguiente();
            cantidad++;
        }
        
        return cantidad;
    }

    @Override
    public T[] datos() {
        int cantidad = cantidadNodos();
        T[] resultado = (T[]) new Object[cantidad];
        
        NodoSE<T> actual = frente;
        int i = 0;
        
        while(actual != null)
        {
            resultado[i++] = actual.getDato();
            actual = actual.getSiguiente();
        }
        return resultado;
    }

   @Override
    public void eliminarRepetidos() throws Exception{
        ColaSE<T> colaSinRepetidos = new ColaSE<>();

        while (!this.estaVacia()) {
            T actual = this.getFrente();
            this.desencolar();

             boolean repetido = false;

            // Verificamos si ya está en la colaSinRepetidos
            ColaSE<T> aux = new ColaSE<>();

            while (!colaSinRepetidos.estaVacia()) {
                T dato = colaSinRepetidos.getFrente();
                colaSinRepetidos.desencolar();
                if (dato.equals(actual)) {
                    repetido = true;
                }
                aux.encolar(dato);
            }

            // Restauramos colaSinRepetidos
            while (!aux.estaVacia()) {
                colaSinRepetidos.encolar(aux.getFrente());
                aux.desencolar();
            }

            if (!repetido) {
                colaSinRepetidos.encolar(actual);
            }
        }

        // Restauramos la cola original
        while (!colaSinRepetidos.estaVacia()) {
            this.encolar(colaSinRepetidos.getFrente());
            colaSinRepetidos.desencolar();
        }
    }


    @Override
    public ColaSE<T> interaccionColas(ColaSE<T> cola2) {
        ColaSE<T> resultado = new ColaSE<>();
        ColaSE<T> copiaCola2 = new ColaSE<>();

        NodoSE<T> aux1 = this.frente;

        while (aux1 != null) {
            T dato1 = aux1.getDato();

            NodoSE<T> aux2 = cola2.frente;
            boolean encontrado = false;

            // Buscamos dato1 en cola2
            while (aux2 != null && !encontrado) {
                if (dato1.equals(aux2.getDato())) {
                    encontrado = true;
                }
                aux2 = aux2.getSiguiente();
            }

            if (encontrado) {
                resultado.encolar(dato1);
            }

            aux1 = aux1.getSiguiente();
        }

        return resultado;
    }
}


