/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package sistemaAutogestion;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pesce
 */
public class IObligatorioTest {

    private Sistema miSistema;
    


    @Before
    public void setUp() {
        miSistema = new Sistema();
    }

    @Test
    public void testCrearSistemaDeGestion() {
        Retorno r = miSistema.crearSistemaDeGestion();
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testRegistrarSalaOK() {
        Retorno r = miSistema.registrarSala("Sala Principal", 600);
        assertEquals(Retorno.ok().resultado, r.resultado);
    }
//    
    @Test
    public void testRegistrarSalaError1(){
        Retorno r = miSistema.registrarSala("Sala Principal", 600);
        assertEquals(Retorno.ok().resultado, r.resultado);
        
        Retorno r2 = miSistema.registrarSala("Sala Principal", 600);
        assertEquals(Retorno.error1().resultado, r2.resultado);
    }
    
     @Test
    public void testRegistrarSalaError2(){
        Retorno r = miSistema.registrarSala("Sala Principal", -600);
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testEliminarSala() {
         miSistema.registrarSala("Sala niños", 100);
         
         Retorno r = miSistema.eliminarSala("Sala niños");
         assertEquals(Retorno.ok().resultado, r.resultado);
    }
//    
     @Test
    public void testEliminarSalaError1() {
         Retorno r = miSistema.eliminarSala("Sala que no existe");
         assertEquals(Retorno.error1().resultado, r.resultado);
    }

    @Test
    public void testRegistrarEventoOk() {
        //Crear sala con la capacidad requerida
        miSistema.registrarSala("Sala Principal", 600);
        //Crear fecha
        LocalDate fecha = LocalDate.of(2025, 1, 11);
        Retorno r = miSistema.registrarEvento("E110", "Nuevo evento", 200, fecha);
        assertEquals(Retorno.ok().resultado, r.resultado);
    }
    //Si ya existe un evento con el mismo codigo
    @Test
    public void testRegistrarEventoError1() {
        //Crear sala con la capacidad requerida
        miSistema.registrarSala("Sala Principal", 600);
        //Crear fecha
        LocalDate fecha = LocalDate.of(2025, 1, 11);
        miSistema.registrarEvento("E110", "Nuevo evento", 200, fecha);
        Retorno r =  miSistema.registrarEvento("E110", "Nuevo evento", 200, fecha);
        assertEquals(Retorno.error1().resultado, r.resultado);
    }
//    
    //El aforo necesario es menor o igual a 0
    @Test
    public void testRegistrarEventoError2() {
        //Crear sala con la capacidad requerida
        miSistema.registrarSala("Sala Principal", 600);
        //Crear fecha
        LocalDate fecha = LocalDate.of(2025, 1, 11);
        Retorno r =  miSistema.registrarEvento("E110", "Nuevo evento", -11, fecha);
        assertEquals(Retorno.error2().resultado, r.resultado);
    }
//    
//    // No hay salas von aforo suficiente para esa fecha
    @Test
    public void testRegistrarEventoError3() {
        //Crear sala con la capacidad requerida
        miSistema.registrarSala("Sala Principal", 600);
        //Crear fecha
        LocalDate fecha = LocalDate.of(2025, 1, 11);
        miSistema.registrarEvento("E120", "Gran Evento", 500, fecha);
        Retorno r =  miSistema.registrarEvento("E110", "Nuevo evento", 300, fecha);
        assertEquals(Retorno.error3().resultado, r.resultado);
    }
//
    @Test
    public void testRegistrarClienteOk() {
        Retorno r = miSistema.registrarCliente("49957002", "Juan Coiro");
        assertEquals(Retorno.ok().resultado, r.resultado);
    }
//    
    //Cedula con formato invalido
    @Test
    public void testRegistrarClienteError1() {
        Retorno r = miSistema.registrarCliente("4995700-2", "Juan Coiro");
        assertEquals(Retorno.error1().resultado, r.resultado);
    }
    
    //Cedula ya registrada
    @Test
    public void testRegistrarClienteError2() {
        miSistema.registrarCliente("49957002", "Juan Coiro");
        Retorno r = miSistema.registrarCliente("49957002", "Juan Coiro");
        assertEquals(Retorno.error2().resultado, r.resultado);
    }
//
    @Test
    public void testListarSalas() {
        miSistema.registrarSala("Sala Principal", 600);
        miSistema.registrarSala("Sala Oeste", 200);
        miSistema.registrarSala("Sala Dorada", 300);
        Retorno r = miSistema.listarSalas();
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testListarEventos() {
        //Crear sala con la capacidad requerida
        miSistema.registrarSala("Sala Principal", 600);
        //Crear fecha
        LocalDate fecha1 = LocalDate.of(2025, 1, 11);
        LocalDate fecha2 = LocalDate.of(2025, 4, 15);
        miSistema.registrarEvento("E110", "Evento 1", 200, fecha1);
        miSistema.registrarEvento("E120", "Evento 2", 500, fecha2);
        Retorno r = miSistema.listarEventos();
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testListarClientes() {
        miSistema.registrarCliente("49957003", "Juan Coiro");
        miSistema.registrarCliente("49957006", "Pedro Perez");
        miSistema.registrarCliente("49957001", "Manuel Lema");
        Retorno r = miSistema.listarClientes();
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testEsSalaOptima() {
        String[][] vistaSalaOptima ={
            {"#", "#", "#", "#", "#", "#", "#"},
            {"#", "#", "X", "X", "X", "X", "#"},
            {"#", "O", "O", "X", "X", "X", "#"},
            {"#", "O", "O", "O", "O", "X", "#"},
            {"#", "O", "O", "X", "O", "O", "#"},
            {"#", "O", "O", "O", "O", "O", "#"},
            {"#", "X", "X", "O", "O", "O", "O"},
            {"#", "X", "X", "O", "O", "O", "X"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "#", "#", "O", "#", "#", "#"},
            {"#", "#", "#", "O", "#", "#", "#"}
        };
        String[][] vistaSalaNoOptima = {
            {"#", "#", "#", "#", "#", "#", "#"},
            {"#", "#", "X", "X", "X", "X", "#"},
            {"#", "X", "X", "X", "X", "X", "#"},
            {"#", "O", "X", "O", "X", "X", "#"},
            {"#", "O", "O", "O", "X", "X", "#"},
            {"#", "X", "X", "X", "X", "X", "#"},
            {"#", "X", "X", "X", "X", "X", "#"},
            {"#", "X", "X", "X", "X", "X", "#"},
            {"#", "X", "X", "X", "X", "X", "#"},
            {"#", "X", "X", "X", "X", "X", "#"},
            {"#", "#", "#", "#", "#", "#", "#"},
            {"#", "#", "#", "#", "#", "#", "#"}
        };      
        Retorno r = miSistema.esSalaOptima(vistaSalaOptima);
        System.out.println(r.valorString);
        Retorno r2 = miSistema.esSalaOptima(vistaSalaNoOptima);
        System.out.println(r2.valorString);
        assertEquals(Retorno.ok("Es optimo.").resultado, r.resultado);
    }

}
