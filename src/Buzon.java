import java.util.LinkedList;

public class Buzon {
    private LinkedList<String> mensajes;
    private int size;

    /**
     * Constructor de Buzon
     * @param size Tamanio del buzon
     */
    public Buzon(int size){
        this.size = size;
        this.mensajes = new LinkedList<String>();
    }

    public void recibirMensajes(String mensaje){

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
