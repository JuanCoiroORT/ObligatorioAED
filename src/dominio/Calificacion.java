
package dominio;


public class Calificacion implements Comparable<Calificacion>{
    private Cliente cliente;
    private int puntaje;
    private String comentario;
    
    public Calificacion(Cliente cliente, int puntaje, String comentario){
        this.cliente = cliente;
        this.puntaje = puntaje;
        this.comentario = comentario;
    }
    
    //Getters
    public Cliente getCliente(){
        return cliente;
    }
    public int getPuntaje(){
        return puntaje;
    }
    public String getComentario(){
        return comentario;
    }
    
    //Setters
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    public void setPuntaje(int puntaje){
        this.puntaje = puntaje;
    }
    public void setComentario(String comentario){
        this.comentario = comentario;
    }

    @Override
    public int compareTo(Calificacion o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
