package tads;

public class PilaSE<T> implements IPilaSE<T>{
    private NodoSE<T> tope;

    public PilaSE() {
        tope = null;
    }

    @Override
    public boolean estaVacia() {
        return tope == null;
    }
    
    @Override
    public void apilar(T dato) {
        NodoSE<T> miNodo = new NodoSE(dato);
        
        // El nuevo nodo apunta al valor de tope
        miNodo.setSiguiente(tope);
        
        //El valor de tope pasa a ser el nuevo nodo
        tope = miNodo;
    }

    @Override
    public T desapilar(){
       // tomo el dato que se va a deapilar
       T dato = tope.getDato();
       //Cambio el tope
       tope = tope.getSiguiente();
       //Devuelvo el desapilado
       return dato;
    }


    @Override
    public T getTope() {
        return tope!=null?tope.getDato():null;
    }
    
     @Override
    public void vaciar() {
        tope = null;
    }

    @Override
    public int cantidadNodos() {
        NodoSE<T> aux = tope;
        int cantidad = 0;
        
        while(aux != null){
            aux = aux.getSiguiente();
            cantidad++;
        }
        
        return cantidad;
    }

    @Override
    public PilaSE<T> copiarPila() {
        PilaSE<T> copia = new PilaSE<>();
        PilaSE<T> aux = new PilaSE<>();

        // 1. Desapilar y guerdar en auxiliar
        while(!this.estaVacia())
        {
            T dato = this.getTope();
            this.desapilar();
            aux.apilar(dato);
        }
        
        // 2. Restaurar la original y crear copia
        while(!aux.estaVacia())
        {
            T dato = aux.getTope();
            aux.desapilar();
            
            // Restauramos en original
            this.apilar(dato);
            
            //Agregar a la copia
            copia.apilar(dato);
        }
        
        return copia;
    }

    @Override
    public void intercambiarTope() {
        //Tomar y desapilar
        T primero = this.getTope();
        this.desapilar();
        T segundo = this.getTope();
        this.desapilar();
        
        //Intercambiar
        this.apilar(primero);
        this.apilar(segundo);
    }

    @Override
    public void concatenar(PilaSE<T> otraPila) {
        // 1. Copiar pila para no modificar la original
        
        PilaSE<T> copia = new PilaSE<>();
        PilaSE<T> inversa = new PilaSE<>();
        // Copiar elementos de otraPila a copia (sin modificar otraPila)
        PilaSE<T> aux = new PilaSE<>(); 
        while(!otraPila.estaVacia())
        {
            T dato = otraPila.getTope();
            otraPila.desapilar();
            copia.apilar(dato);
            aux.apilar(dato);
        }
        
        // Restaurar otraPila 
        while(!aux.estaVacia())
        {
            otraPila.apilar(aux.getTope());
            aux.desapilar();
        }
        
        // 2. Invertir la copia
        while(!copia.estaVacia())
        {
            inversa.apilar(copia.getTope());
            copia.desapilar();
        }
        
        // 3. Agregar a this
        while(!inversa.estaVacia())
        {
            this.apilar(inversa.getTope());
            inversa.desapilar();
        }
    }

    @Override
    public void invertir() {
        PilaSE<T> aux = new PilaSE<>();
        
        // 1. Mover elementos a aux
        while(!this.estaVacia())
        {
            aux.apilar(this.getTope());
            this.desapilar();
        }
        
        // 2. Mover elementos a pila original
        while(!aux.estaVacia())
        {
            this.apilar(aux.getTope());
            aux.desapilar();
        }
    }
   
}
