import java.util.LinkedList;

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
        if (this.id == 1){
            
        }else{
            String mensaje = mensajesEnEspera.getFirst();
            while(!mensaje.equals("FIN")){
                if (estiloRecibido){
                    recibirActivo();
                }else{
                    recibirPasivo();
                }
                if (estiloEnvio){
                    enviarActivo();
                }else{
                    enviarPasivo();
                }
            }
        }
    }

    public String modificarMensaje(){
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
    	String mensaje = this.mensajesEnEspera.removeFirst()+"/"+String.valueOf(this.id)+sub;
        return mensaje;
    }

    public void enviarPasivo(){
    	String mensaje = modificarMensaje();
    	synchronized(buzonSalida){
            int capacidad = buzonSalida.getSize();
            while(buzonSalida.getMensajes().size()==capacidad) {
                try {
                    wait();
                }catch(InterruptedException e) {}
            }
            LinkedList<String> mensajesNuevos = buzonSalida.getMensajes();
            mensajesNuevos.addLast(mensaje);
            try {
                sleep(this.tiempoTransformacion);
            } catch (InterruptedException e) {}
            buzonSalida.recibirMensajes(mensaje);
            notify();
        }
    }
    
    public void enviarActivo(){
    	int capacidad = buzonSalida.getSize();
    	while(buzonSalida.getMensajes().size()==capacidad) {
    		Thread.yield();
    	}
    	String mensaje = modificarMensaje();
        synchronized(buzonSalida){
            try {
                sleep(this.tiempoTransformacion);
            } catch (InterruptedException e) {}
            buzonSalida.recibirMensajes(mensaje);
            notify();
        }
    }

    public void recibirActivo(){
    	while(buzonEntrada.getMensajes().size()==0){
            Thread.yield();
        }
        synchronized(buzonEntrada){
            String mensaje = buzonEntrada.darMensaje();
            mensajesEnEspera.addLast(mensaje);
            notify();
        }
    }
    
    public void recibirPasivo(){
        synchronized(buzonEntrada){
            while(buzonEntrada.getMensajes().size()==0) {
                try {
                    wait();
                }catch(InterruptedException e) {}
            }
            String mensaje = buzonEntrada.darMensaje();
            mensajesEnEspera.addLast(mensaje);
            notify();
        }
    }
}