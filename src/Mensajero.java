import java.util.LinkedList;

public class Mensajero extends Thread {

    private boolean estiloEnvio;
    private boolean estiloRecibido;
    private int id;
    private Buzon buzonEntrada;
    private Buzon buzonSalida;
    private int tiempoTransformacion;
    private LinkedList<String> mensajesEnEspera;
    private boolean end;

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
        this.end = false;
    }

    /**
     * Guarda los mensajes existentes en la lista de espera de envíos del mensajero
     */
    public void transmitir(LinkedList<String> mensajesATransmitir) {
        this.mensajesEnEspera = mensajesATransmitir;
    }

    public void revisarMensajeEnEspera(){
        if (mensajesEnEspera.getFirst().length()>3){
            end = mensajesEnEspera.getFirst().substring(0, 3).equals("FIN");
        }
    }

    public void revisarMensajeEnEsperaFinal(){
        if (mensajesEnEspera.getLast().length()>3){
            end = mensajesEnEspera.getLast().substring(0, 3).equals("FIN");
        }
    }

    public void run(){
        if (id == 1){
            if (estiloEnvio){
                enviarActivo();
            }else{
                enviarPasivo();
            }
            enviarFin();
            while (!end){
                if (estiloRecibido){
                    recibirActivo();
                }else{
                    recibirPasivo();
                }
                revisarMensajeEnEsperaFinal();
            }
            System.out.print("Todos los threads han terminado con exito. Imprimiendo mensajes:");
            System.out.println(mensajesEnEspera);
        }else{
            while(!end){
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
        revisarMensajeEnEspera();
    	String mensaje = this.mensajesEnEspera.removeFirst()+"/"+String.valueOf(this.id)+sub;
        return mensaje;
    }

    public void enviarFin(){
        mensajesEnEspera.add("FIN");
        if (estiloEnvio){
            enviarActivo();
        }else{
            enviarPasivo();
        }
    }

    public void enviar(){
        String mensaje = modificarMensaje();
        try {
            sleep(this.tiempoTransformacion);
        } catch (InterruptedException e) {}
        buzonSalida.recibirMensajes(mensaje);
        buzonSalida.notify();
    }

    public void enviarPasivo(){
    	while (mensajesEnEspera.size()>0){
            synchronized(buzonSalida){
                int capacidad = buzonSalida.getSize();
                while(buzonSalida.getMensajes().size()==capacidad) {
                    try {
                        buzonSalida.wait();
                    }catch(InterruptedException e) {}
                }
                enviar();
            }
        }
    }
    
    public void enviarActivo(){
        while (mensajesEnEspera.size()>0){
            int capacidad = buzonSalida.getSize();
            while(buzonSalida.getMensajes().size()==capacidad) {
                Thread.yield();
            }
            synchronized(buzonSalida){
                enviar();
            }
        }
    }

    public void recibir(){
        while(buzonEntrada.getMensajes().size()>0){
            String mensaje = buzonEntrada.darMensaje();
            mensajesEnEspera.addLast(mensaje);
            buzonEntrada.notify();
        }   
    }

    public void recibirActivo(){
    	while(buzonEntrada.getMensajes().size()==0){
            Thread.yield();
        }
        synchronized(buzonEntrada){
            recibir();
        }
    }
    
    public void recibirPasivo(){
        synchronized(buzonEntrada){
            while(buzonEntrada.getMensajes().size()==0) {
                try {
                    buzonEntrada.wait();
                }catch(InterruptedException e) {}
            }
            recibir();
        }
    }
}