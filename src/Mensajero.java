public class Mensajero extends Thread {

    private boolean estiloEnvio;
    private boolean estiloRecibido;
    private int id;
    private Buzon buzonEntrada;
    private Buzon buzonSalida;
    private int tiempoTransformacion;

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
    }
}
