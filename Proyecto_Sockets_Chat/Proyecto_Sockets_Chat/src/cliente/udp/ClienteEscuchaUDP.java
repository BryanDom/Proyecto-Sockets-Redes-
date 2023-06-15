package cliente.udp;

import java.net.*;
import java.io.*;

/**
 * Clase que se encarga de implementar métodos para escuchar tanto videos como audios por medio de un paquete UDP.
 * Declaramos la clase udp escucha; el que escucha hereda de hilo.
 */
public class ClienteEscuchaUDP extends Thread{
    protected BufferedReader in;
    // Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER=256;
    protected final int PUERTO_CLIENTE;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket servPaquete;
    //protected String SERVER;

    /**
     * Constructor de la clase, el cual inicializa las variables declaradas.
     * Solo se necesita el socket para referencia.
     * @param socketNuevo - Socket de referencia.
     */
    public  ClienteEscuchaUDP(DatagramSocket socketNuevo){
        socket = socketNuevo;
        // SERVER=servidor;
        // Puerto que fue pasado como referencia.
        // Obtener el número de puerto que se esta utilizando (el cual fue definido al crear el socket).
        PUERTO_CLIENTE = socket.getLocalPort();
    }


    public void run() {
        byte[] mensaje_bytes;
        String mensaje="";
        mensaje_bytes=mensaje.getBytes();
        
        String cadenaMensaje="";

        byte[] recogerServidor_bytes;

        try {
            // Ciclo infinito  hasta que el mensaje que reciba sea una cadena.
            do {
                // Arreglo de bytes donde a través el método recibir voy a obtener el socket que tuvimos: punto de comunicación.
                // Un paquete de tipo Datagram, de cierto tamaño, será guardado en un arreglo.
                recogerServidor_bytes = new byte[MAX_BUFFER];

                // Esperamos a recibir un paquete.
                servPaquete = new DatagramPacket(recogerServidor_bytes,MAX_BUFFER);
                // Trae un mensaje que fue recibido del servidor.
                socket.receive(servPaquete);

                // Converción de el mensaje recibido en un string.
                // lo guardamos como cadena, para poderlo imprimir.
                cadenaMensaje = new String(recogerServidor_bytes).trim();

                // Imprimimos el paquete recibido.
                // sacamos su dirección IP y el puerto, para saber de dónde vienen.
                System.out.println("Mensaje recibido \""+cadenaMensaje +"\" de "+
                        servPaquete.getAddress()+"#"+servPaquete.getPort());
            } while (!cadenaMensaje.startsWith("fin"));
        }
        catch (Exception e) {
            // Excepciones correspondientes.
            e.printStackTrace();
            System.err.println("Excepcion C: "+e.getMessage());
        }
    }
}
