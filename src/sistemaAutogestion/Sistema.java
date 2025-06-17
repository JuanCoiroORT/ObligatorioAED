package sistemaAutogestion;

import java.time.LocalDate;
import dominio.*;
import tads.*;

public class Sistema implements IObligatorio {

    private static Sistema sistema;
    private ListaSalaDE salas;
    private ListaEventoSE eventos;
    private ListaClienteSE clientes;
    private PilaEntradaSE entradas;
    
    
    public Sistema(){
        this.salas = new ListaSalaDE();
        this.eventos = new ListaEventoSE();
        this.clientes = new ListaClienteSE();
    }
    
    @Override
    public Retorno crearSistemaDeGestion() {
        salas = new ListaSalaDE();
        eventos = new ListaEventoSE();
        clientes = new ListaClienteSE();
        return Retorno.ok();
    }

    @Override
    public Retorno registrarSala(String nombre, int capacidad) {
        // Validar capacidad
         if (capacidad <= 0) return Retorno.error2();
        // Validar si la sala ya existe
        for (int i = 0; i < salas.longitud(); i++) {
            Sala actual = (Sala) salas.obtener(i);
            if(actual.getNombre().equalsIgnoreCase(nombre)){
                return Retorno.error1();
            }
        }
        Sala nuevaSala = new Sala(nombre, capacidad);
        salas.adicionarFinal(nuevaSala);
        return Retorno.ok();
    }

    @Override
    public Retorno eliminarSala(String nombre) {
        if(salas.vacia()){
            return Retorno.error1();
        }
        for (int i = 0; i < salas.longitud(); i++) {
            Sala salaActual = (Sala) salas.obtener(i);
            if(salaActual.getNombre().equals(nombre)){
                salas.eliminar(i);
                return Retorno.ok();
            }
        }
        return Retorno.error2();
    }

    @Override
    public Retorno registrarEvento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha) {
        // Validar aforo ingresado
        if(aforoNecesario <= 0){
            return Retorno.error2();
        }
        // Validar que el codigo no exista
        for (int i = 0; i < eventos.longitud(); i++) {
            Evento e = (Evento) eventos.obtener(i);
            if(e.getCodigo().equals(codigo)){
                return Retorno.error1();
            }
        }
        // Buscar sala para el evento
        for(int i = 0; i < salas.longitud(); i++){
            Sala sala = (Sala) salas.obtener(i);
            if(sala.getCapacidad() >= aforoNecesario && !sala.estaOcupada(fecha)){
                // Se crea el evento y se lo asocia a la sala
                Evento e = new Evento(codigo, descripcion, aforoNecesario, fecha, sala, aforoNecesario, 0);
                eventos.adicionarFinal(e);
                sala.agendarEvento(fecha);
                return Retorno.ok();
            }
        }
        return Retorno.error3();
    }

    @Override
    public Retorno registrarCliente(String cedula, String nombre) {
        // Validar CI
        if(cedula.length() != 8){
            return Retorno.error1();
        }
        // Buscar si ya existe el cliente
        for (int i = 0; i < clientes.longitud(); i++) {
            Cliente c = (Cliente) clientes.obtener(i);
            if(c.getCedula().equals(cedula)){
                return Retorno.error2();
            }
        }
        // Agregar cliente a lista en sistema
        Cliente nuevo = new Cliente(cedula, nombre); 
        clientes.adicionarFinal(nuevo);
        return Retorno.ok();
    }

    @Override
    public Retorno comprarEntrada(String cedula, String codigoEvento) {
        Cliente clienteBuscado = null;
        // Buscar el cliente
        for (int i = 0; i < clientes.longitud(); i++) {
            Cliente c = (Cliente) clientes.obtener(i);
            if(c.getCedula().equals(cedula)){
                clienteBuscado = c;
                break;
            }
        }
        // Si no existe el cliente
        if(clienteBuscado == null){
            return Retorno.error1();
        }
        
        Evento eventoBuscado = null;
        //Buscar evento
        for(int i = 0; i < eventos.longitud(); i++){
            Evento e = (Evento) eventos.obtener(i);
            if(e.getCodigo().equals(codigoEvento)){
                eventoBuscado = e;
                break;
            }
        }
        // Si no existe el evento
        if(eventoBuscado == null){
            return Retorno.error2();
        }
        
        // Comprar la entrada o asignar a la cola de espera
        if(eventoBuscado.getEntradasDisponibles() > 0){
            int numeroEntrada = eventoBuscado.getEntradasVendidas() + 1;
            Entrada entrada = new Entrada(eventoBuscado, clienteBuscado, LocalDate.now(), numeroEntrada);
            // Modificar entradas del evento
            eventoBuscado.setEntradasDisponiobles(eventoBuscado.getEntradasDisponibles() - 1);
            eventoBuscado.setEntradasVenididas(eventoBuscado.getEntradasVendidas() + 1);
            
            //Apilar entrada
            entradas.apilar(entrada);
            
            return Retorno.ok();
        }
        else{
            eventoBuscado.getClientesEnEspera().encolar(clienteBuscado);
            return Retorno.ok();
        }
        
    }

    @Override
    public Retorno eliminarEvento(String codigo) {
        Evento eventoBuscado = null;
        int pos = -1;
        
        // Buscar evento y su posicion en la lista
        for (int i = 0; i < eventos.longitud(); i++) {
            Evento e = (Evento) eventos.obtener(i);
            if(e.getCodigo().equals(codigo)){
                eventoBuscado = e;
                pos = i;
                break;
            }
        }
        
        // Si no existe el evento
        if(eventoBuscado == null){
            return Retorno.error1();
        }
        
        // Si ya tiene entradas vendidad
        if(eventoBuscado.getEntradasVendidas() > 0){
            return Retorno.error2();
        }
        
        //Liberar sala
        //eventoBuscado.getSala()
        
        // Eliminar evento de la lista en sistema
        eventos.eliminar(pos);
        
        return Retorno.ok();
    }

    @Override
    public Retorno devolverEntrada(String cedula, String codigoEvento) {
        Cliente clienteBuscado = null;
        // Buscar el cliente
        for (int i = 0; i < clientes.longitud(); i++) {
            Cliente c = (Cliente) clientes.obtener(i);
            if(c.getCedula().equals(cedula)){
                clienteBuscado = c;
                break;
            }
        }
        // Si no existe el cliente
        if(clienteBuscado == null){
            return Retorno.error1();
        }
        
        Evento eventoBuscado = null;
        // Buscar evento
        for(int i = 0; i < eventos.longitud(); i++){
            Evento e = (Evento) eventos.obtener(i);
            if(e.getCodigo().equals(codigoEvento)){
                eventoBuscado = e;
                break;
            }
        }
        // Si no existe el evento
        if(eventoBuscado == null){
            return Retorno.error2();
        }
        
        // Buscar la entrada y pasarla a estado="devuelta"
        PilaEntradaSE aux = new PilaEntradaSE();
        boolean encontrado = false;
        
        while(!entradas.estaVacia()){
            Entrada e = (Entrada) entradas.desapilar();
            
            if(!encontrado &&
                e.getCliente().getCedula().equals(cedula) &&
                e.getEvento().getCodigo().equals(codigoEvento) &&
                !e.getEstado().equals("devuelta")){
                
                e.setEstado("devuelta");
                encontrado = true;
                
                //Actualizar contadores
                eventoBuscado.setEntradasDisponiobles(eventoBuscado.getEntradasDisponibles() + 1);
                eventoBuscado.setEntradasVenididas(eventoBuscado.getEntradasVendidas() - 1);
            }
            
            aux.apilar(e);
        }
        
        //Restaurar pila original
        while(!aux.estaVacia()){
            entradas.apilar(aux.desapilar());
        }
        
        // Asignar entrada a cliente en cola de espera
        if(!eventoBuscado.getClientesEnEspera().estaVacia()){
            try{
                Cliente c = (Cliente) eventoBuscado.getClientesEnEspera().desencolar();
                int nuevoNumero = eventoBuscado.getEntradasVendidas() + 1;
                Entrada nuevaEntrada = new Entrada(eventoBuscado, c, LocalDate.now(), nuevoNumero);
                entradas.apilar(nuevaEntrada);
                
                 //Actualizar contadores
                eventoBuscado.setEntradasDisponiobles(eventoBuscado.getEntradasDisponibles() - 1);
                eventoBuscado.setEntradasVenididas(eventoBuscado.getEntradasVendidas() + 1);
            }catch(Exception ex){
                System.out.println("Error al reasignar entrada: " + ex.getMessage());
            }
        }
       
        return Retorno.ok();
    }

    @Override
    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario) {
        Cliente clienteBuscado = null;
        // Buscar el cliente
        for (int i = 0; i < clientes.longitud(); i++) {
            Cliente c = (Cliente) clientes.obtener(i);
            if(c.getCedula().equals(cedula)){
                clienteBuscado = c;
                break;
            }
        }
        // Si no existe el cliente
        if(clienteBuscado == null){
            return Retorno.error1();
        }
        
        Evento eventoBuscado = null;
        // Buscar evento
        for(int i = 0; i < eventos.longitud(); i++){
            Evento e = (Evento) eventos.obtener(i);
            if(e.getCodigo().equals(codigoEvento)){
                eventoBuscado = e;
                break;
            }
        }
        // Si no existe el evento
        if(eventoBuscado == null){
            return Retorno.error2();
        }
        
        // Si el puntaje no es valido
        if(puntaje < 1 || puntaje > 10){
            return Retorno.error3();
        }
        
        //Si el cliente ya califico el evento
        ListaCalificacionesSE calificaciones = eventoBuscado.getCalificaciones();
        for (int i = 0; i < calificaciones.longitud(); i++) {
            Calificacion c = (Calificacion) calificaciones.obtener(i);
            if(c.getCliente().getCedula().equals(cedula)){
                return Retorno.error4();
            }
        }
        
        // Registrar calificacion
        Calificacion nueva = new Calificacion(clienteBuscado, puntaje, comentario);
        eventoBuscado.getCalificaciones().adicionarFinal(nueva);
        
        return Retorno.ok();
    }

    @Override
    public Retorno listarSalas() {
        if(salas.vacia()){
            return Retorno.error1();
        }
        // Invertir Lista
        salas.invertirIterativo();
        // Mostrar lista
        salas.mostrar();
        // Volver lista a estado original
        salas.invertirIterativo();
        return Retorno.ok();
    }

    @Override
    public Retorno listarEventos() {
        if(eventos.vacia()){
            System.out.println("Lista de eventos vacia.");
        }
        if(!eventos.estaOrdenada()){
            eventos.ordenarLista();
        }
        eventos.mostrar();
        return Retorno.ok();
    }

    @Override
    public Retorno listarClientes() {
        if(clientes.vacia()){
            System.out.println("Lista de clientes vacia.");
        }
        if(!clientes.estaOrdenada()){
            clientes.ordenarLista();
        }
        clientes.mostrar();
        return Retorno.ok();
    }

    @Override
    public Retorno esSalaOptima(String[][] vistaSala) {
        int filas = vistaSala.length;
        int columnas = vistaSala[0].length;
        int columnasOptimas = 0;
        
        // Se recorre cada columna
        for(int col = 0; col < columnas; col++){
            int libres = 0;
            int ocupadosConsecutivos = 0;
            int maxOcupadosConsecutivos = 0;
            
            // Se recorre cada fila de esa columna
            for(int fila = 0; fila < filas; fila++){
                String valor = vistaSala[fila][col];
                switch (valor) {
                    // Si esta libre se suma a la variable y se cortan los OcupadosConsecutivos
                    case "X":
                        libres++;
                        ocupadosConsecutivos = 0;
                        break;
                        //Si esta ocupado se suma a ocupadosConsecutivos
                    case "O":
                        ocupadosConsecutivos++;
                        // Se compara para establecer el maximo
                        if(ocupadosConsecutivos > maxOcupadosConsecutivos){
                            maxOcupadosConsecutivos = ocupadosConsecutivos;
                        }
                        break;
                        // Si es "#" se cortan los ocupadosConsecutivos
                    default:
                        ocupadosConsecutivos = 0;
                        break;
                }
            }
            // Se verifica que la columna sea optima
            if(maxOcupadosConsecutivos > libres){
                columnasOptimas++;
            }
        }
        String mensaje = "";
        if(columnasOptimas >= 2){
            mensaje += "Es optimo.";
        }else{
            mensaje += "No es optimo.";
        }
        return Retorno.ok(mensaje);
    }

    @Override
    public Retorno listarClientesDeEvento(String codigo, int n) {
        // Verificar si existe el evento
        Evento eventoBuscado = null;
        for (int i = 0; i < eventos.longitud(); i++) {
            Evento e = (Evento) eventos.obtener(i);
            if(e.getCodigo().equals(codigo)){
                eventoBuscado = e;
                break;
            }
        }
        if(eventoBuscado == null){
            return Retorno.error1();
        }
        
        // Verificar n
        if(n < 1){
            return Retorno.error2();
        }
        
        //Crear pila auxiliar para recorrer sin perder contenido
        PilaEntradaSE aux = new PilaEntradaSE();
        ListaClienteSE clientesPorEvento = new ListaClienteSE();
        
        int cantidad = 0;
        while(!entradas.estaVacia() && cantidad < n){
            Entrada entrada = (Entrada) entradas.desapilar();
            
            if(entrada.getEvento().getCodigo().equals(codigo)){
                clientesPorEvento.adicionarFinal(entrada.getCliente());
                cantidad++;
            }
            aux.apilar(entrada);
        }
        
        //Restaurar pila original
        while(!aux.estaVacia()){
            entradas.apilar(aux.desapilar());
        }
        
        //Listar clientes
        for (int i = 0; i < clientesPorEvento.longitud(); i++) {
            Cliente c = (Cliente) clientesPorEvento.obtener(i);
            System.out.println("Nombre: " + c.getNombre() + " CI: " + c.getCedula());
        }
        return Retorno.ok();
    }

    @Override
    public Retorno listarEsperaEvento() {
        // Lista de eventos con lista de espera
        ListaEventoSE eventosConEspera = new ListaEventoSE();
        for (int i = 0; i < eventos.longitud(); i++) {
            Evento e = (Evento) eventos.obtener(i);
            if(!e.getClientesEnEspera().estaVacia()){
                eventosConEspera.adicionarFinal(e);
            }
        }
        
        //Ordenar eventos por codigo
        eventosConEspera.ordenarLista();
        
        // Lista de clientes de esos eventos
        for (int i = 0; i < eventosConEspera.longitud(); i++) {
            Evento e = (Evento) eventosConEspera.obtener(i);
            ColaClienteSE colaOriginal = e.getClientesEnEspera();
            
            ListaClienteSE clientesEnEspera = new ListaClienteSE();
            NodoSE actual = (NodoSE) colaOriginal.getFrente();
            while (actual != null) {
                Cliente cliente = (Cliente) actual.getDato();
                clientesEnEspera.adicionarFinal(cliente);
                actual = actual.getSiguiente();
            }
            //Ordenar los clientes por cedula
            clientesEnEspera.ordenarLista();
            
            //Listar los clientes
            for (int j = 0; j < clientesEnEspera.longitud(); j++) {
                Cliente c = (Cliente) clientesEnEspera.obtener(j);
                System.out.println("Nombre: " + c.getNombre() + " CI: " + c.getCedula());
            }
        }
        return Retorno.ok();
    }

    @Override
    public Retorno deshacerUtimasCompras(int n) {
        if(n <= 0 || n > entradas.cantidadNodos()){
            return Retorno.error1();
        }
        
        PilaEntradaSE aux = new PilaEntradaSE();
        ListaEntradaSE entradasDesechas = new ListaEntradaSE();
        
        // Sacar ultimas n entradas y almacenarlas
        for (int i = 0; i < n; i++) {
            Entrada e = (Entrada) entradas.desapilar();
            
            //Marcar como devuelta
            e.setEstado("devuelta");
            
            //Actualizar contadores
            Evento evento = (Evento) e.getEvento();
            evento.setEntradasDisponiobles(evento.getEntradasDisponibles() + 1);
            evento.setEntradasVenididas(evento.getEntradasVendidas() - 1);
            
            //Guardar en lista para imprimir en pantalla
            entradasDesechas.adicionarFinal(e);
            
            // Guardar en la pila auxiliar para restaurar
            aux.apilar(e);
        }
        
        // Restaurar la pila original con las entradas devueltas en el tope
        while(aux.estaVacia()){
            entradas.apilar(aux.desapilar());
        }
        
        //Ordenal lista a imprimir
        entradasDesechas.ordenarLista();
        
        //Imprimir
        System.out.println("Lista de entradas eliminadas:");
        for (int i = 0; i < entradasDesechas.longitud(); i++) {
            Entrada e = (Entrada) entradasDesechas.obtener(i);
            System.out.println("Codigo del evento: " + e.getEvento().getCodigo()
                                + " CI: " + e.getCliente().getCedula());
        }
        
        return Retorno.ok();
    }

    @Override
    public Retorno eventoMejorPuntuado() {

        ListaEventoSE eventosConPromedio = new ListaEventoSE();
        double mejorPromedio = -1;
        
        ListaEventoSE mejoresEventos = new ListaEventoSE();
        
        for (int i = 0; i < eventos.longitud(); i++) {
            double suma = 0;
            int cantidad = 0;
            Evento e = (Evento) eventos.obtener(i);
            
            ListaCalificacionesSE calificaciones = e.getCalificaciones();
            for (int j = 0; j < calificaciones.longitud(); j++) {
                Calificacion c = (Calificacion) calificaciones.obtener(j);
                suma += c.getPuntaje();
                cantidad++;
            }
            
            if(cantidad > 0){
                double promedio = suma / cantidad;
                
                
                if(promedio > mejorPromedio){
                    mejorPromedio = promedio;
                    mejoresEventos = new ListaEventoSE();
                    
                    mejoresEventos.adicionarFinal(e);
                } else if(promedio == mejorPromedio){
                    mejoresEventos.adicionarFinal(e);
                }
            }
        }
        // Si no hay eventos o los eventos no tienen calificaciones
        if(mejoresEventos.vacia()){
            System.out.println("No hubo eventos con calificaciones.");
        }
        
        // Si hay mas de un evento con el mejor promedio se ordena
        if(mejoresEventos.longitud() > 1){
            mejoresEventos.ordenarLista();
        }
        
        System.out.println("Evento mejor puntuado.");
        for (int i = 0; i < mejoresEventos.longitud(); i++) {
            Evento e = (Evento) mejoresEventos.obtener(i);
            System.out.println("Codigo del evento: " + e.getCodigo()
            + " Promedio: " + mejorPromedio);
        }
        return Retorno.ok();
    }

    @Override
    public Retorno comprasDeCliente(String cedula) {
        Cliente clienteBuscado = null;
        for (int i = 0; i < clientes.longitud(); i++) {
            Cliente c = (Cliente) clientes.obtener(i);
            if(c.getCedula().equals(cedula)){
                clienteBuscado = c;
                break;
            }
        }
        if(clienteBuscado == null){
            return Retorno.error1();
        }
        //Recorrer pila sin perder datos
        PilaEntradaSE aux = new PilaEntradaSE();
        PilaEntradaSE reverso = new PilaEntradaSE();
        
        // Pasar todo a reverso para mantener orden original
        while(!entradas.estaVacia()){
            Entrada entrada = (Entrada) entradas.desapilar();
            reverso.apilar(entrada);
        }
        
        //Procesar desde la mas antigua
        while(!reverso.estaVacia()){
            Entrada entrada = (Entrada) reverso.desapilar();
            
            if(entrada.getCliente().getCedula().equals(cedula)){
                String letraEstado = entrada.getEstado().equalsIgnoreCase("activa") ? "N" : "D";
                System.out.println(entrada.getEvento().getCodigo() + " - " + letraEstado);
            }
            //Restaurar en aux
            aux.apilar(entrada);
        }
        
        //Restaurar original
        while(!aux.estaVacia()){
            entradas.apilar(aux.desapilar());
        }
        return Retorno.ok();
    }

    @Override
    public Retorno comprasXDia(int mes) {
        if(mes < 1 || mes > 12){
            return Retorno.error1();
        }
        
        ListaSE<Integer> dias = new ListaSE<>();
        ListaSE<Integer> cantidades = new ListaSE<>();
        
        PilaEntradaSE aux = new PilaEntradaSE();
        PilaEntradaSE reverso = new PilaEntradaSE();
        
        //Invertir pila original para procesar mas antigua
        while(!entradas.estaVacia()){
            Entrada e = (Entrada) entradas.desapilar();
            reverso.apilar(e);
        }
        
        //Procesar entradas
        while(!reverso.estaVacia()){
            Entrada e = (Entrada) reverso.desapilar();
            LocalDate fecha = e.getFechaCompra();
            
            if(fecha.getMonthValue() == mes){
                int dia = fecha.getDayOfMonth();
                boolean encontrado = false;
                
                for (int i = 0; i < dias.longitud(); i++) {
                    if(dias.obtener(i) == dia){
                        int nuevaCantidad = cantidades.obtener(i) + 1;
                        cantidades.eliminar(i);
                        cantidades.insertar(i, nuevaCantidad);
                        encontrado = true;
                        break;
                    }
                }
                if(!encontrado){
                    dias.adicionarFinal(dia);
                    cantidades.adicionarFinal(1);
                }
            }
            
            aux.apilar(e);
        }
        // Restaurar pila original
        while(!aux.estaVacia()){
            entradas.apilar(aux.desapilar());
        }
        
        //Mostrar resultados
        for (int i = 0; i < dias.longitud(); i++) {
            System.out.println("Dia: " + dias.obtener(i) + 
                               " - Compras: " + cantidades.obtener(i));
        }
       
        return Retorno.ok();
    }

}
