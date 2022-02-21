import java.util.LinkedList;

public class Buzon {
	private String id;
    private LinkedList<String> mensajes;
    private int size;

    /**
     * Constructor de Buzon
     * @param size Tamanio del buzon
     */
    public Buzon(String id, int size){
    	this.id = id;
        this.size = size;
        this.mensajes = new LinkedList<String>();
    }

    public void recibirMensajes(String mensaje){
        mensajes.add(mensaje);
    }

    public String darMensaje(){
        return mensajes.removeFirst();
    }

	public LinkedList<String> getMensajes() {
		return mensajes;
	}

	public int getSize() {
		return size;
	}
    
    
}
