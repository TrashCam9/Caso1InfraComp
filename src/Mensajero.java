import java.util.LinkedList;
import java.util.Queue;

public class Mensajero extends Thread {

    private boolean estiloEnvio;
    private boolean estiloRecibido;
    private int id;
    private Buzon buzonEntrada;
    private Buzon buzonSalida;
    private int tiempoTransformacion;
    private LinkedList<String> mensajesEnEspera;

    /**
     * Constructor para la clase Mensajero
     * @param estiloEnvio true si el envio es activo, false si el envio es pasivo
     * @param estiloRecibido true si el recibido es activo, false si el envio es pasivo
     * @param id Numero entre el 1 y el 4 que describe la identificación del mensajero
     * @param buzonEntrada Referencia al buzon que tendrá los mensajes que va a recibir
     * @param buzonSalida Referencia al buzon que tendrá los mensajes que va a enviar
     * @param tiempoTransformacion Tiempo en milisegundos de demora de envío del mensajero
     */
    public Mensajero(boolean estiloEnvio, boolean estiloRecibido, int id, Buzon buzonEntrada, Buzon buzonSalida,
            int tiempoTransformacion) {
        this.estiloEnvio = estiloEnvio;
        this.estiloRecibido = estiloRecibido;
        this.id = id;
        this.buzonEntrada = buzonEntrada;
        this.buzonSalida = buzonSalida;
        this.tiempoTransformacion = tiempoTransformacion;
        this.mensajesEnEspera = new LinkedList<String>();
    }

    /**
     * Guarda los mensajes existentes en la lista de espera de envíos del mensajero
     */
    public void transmitir(LinkedList<String> mensajesATransmitir) {
        this.mensajesEnEspera = mensajesATransmitir;
    }

    public void run(){
        
    }

    public synchronized void enviarActivo(Buzon buzon){
    	int capacidad = buzon.getSize();
    	if(buzon.getMensajes().size()==capacidad) {
    		try {
    			wait();
    		}catch(InterruptedException e) {}
    	}
    	
    	String sub = "PP";
    	
    	if(this.estiloEnvio && this.estiloRecibido) {
    		sub = "AA";
    	}
    	else if(this.estiloEnvio && !this.estiloRecibido) {
    		sub = "AP";
    	}
    	else if(!this.estiloEnvio && this.estiloRecibido) {
    		sub = "PA";
    	}
    	
    	
    	String mensaje = this.mensajesEnEspera.getFirst()+"/"+String.valueOf(this.id)+sub;
		LinkedList<String> mensajesNuevos = buzon.getMensajes();
		mensajesNuevos.addLast(mensaje);
		try {
			sleep(this.tiempoTransformacion);
		} catch (InterruptedException e) {}
		buzon.setMensajes(mensajesNuevos);
    }
    
    
    public synchronized void enviarPasivo(Buzon buzon){
    	int capacidad = buzon.getSize();
    	while(buzon.getMensajes().size()==capacidad) {
    		try {
    			wait();
    		}catch(InterruptedException e) {}
    	}
    	
    	String sub = "PP";
    	
    	if(this.estiloEnvio && this.estiloRecibido) {
    		sub = "AA";
    	}
    	else if(this.estiloEnvio && !this.estiloRecibido) {
    		sub = "AP";
    	}
    	else if(!this.estiloEnvio && this.estiloRecibido) {
    		sub = "PA";
    	}
    	
    	
    	String mensaje = this.mensajesEnEspera.getFirst()+"/"+String.valueOf(this.id)+sub;
		LinkedList<String> mensajesNuevos = buzon.getMensajes();
		mensajesNuevos.addLast(mensaje);
		try {
			sleep(this.tiempoTransformacion);
		} catch (InterruptedException e) {}
		buzon.setMensajes(mensajesNuevos);
    }

    public synchronized void recibirActivo(Buzon buzon){
    	
    }
    
    public synchronized void recibirPasivo(Buzon buzon){
    	while(buzon.getMensajes().size()==0) {
    		try {
    			wait();
    		}catch(InterruptedException e) {}
    	}
    	this.mensajesEnEspera.addLast(buzon.getMensajes().getFirst());
    	LinkedList<String> mensajesNuevos = buzon.getMensajes();
		mensajesNuevos.removeFirst();
		buzon.setMensajes(mensajesNuevos);
    }
}
