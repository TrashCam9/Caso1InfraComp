import java.util.ArrayList;

public class Buzon {
    private ArrayList<String> mensajes;
    private int size;

    /**
     * Constructor de Buzon
     * @param size Tamaño del buzon
     */
    public Buzon(int size){
        this.size = size;
        this.mensajes = new ArrayList<String>(size);
    }
}
