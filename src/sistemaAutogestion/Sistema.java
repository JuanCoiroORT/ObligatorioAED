package sistemaAutogestion;

import java.time.LocalDate;
import dominio.*;
import tads.*;

public class Sistema implements IObligatorio {

    private static Sistema sistema;
    private ListaSalaDE salas;
    private ListaEventoSE eventos;
    private ListaClienteSE clientes;
    
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
        return Retorno.noImplementada();
    }

    @Override
    public Retorno eliminarEvento(String codigo) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno devolverEntrada(String cedula, String codigoEvento) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario) {
        return Retorno.noImplementada();
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
    public Retorno listarClientesDeEvento(String código, int n) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarEsperaEvento() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno deshacerUtimasCompras(int n) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno eventoMejorPuntuado() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno comprasDeCliente(String cedula) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno comprasXDia(int mes) {
        return Retorno.noImplementada();
    }

}
