package cliente.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Clase que envía mensajes UDP al servidor.
 */
public class ClienteEnviaUDP extends Thread {
    protected final int MAX_BUFFER = 256;
    protected final int PUERTO_SERVIDOR;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket paquete;
    protected final String SERVIDOR;

    /**
     * Constructor de la clase ClienteEnviaUDP.
     * @param servidor Dirección IP del servidor.
     * @param puertoServidor Puerto del servidor.
     */
    public ClienteEnviaUDP(String servidor, int puertoServidor) {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        SERVIDOR = servidor;
        PUERTO_SERVIDOR = puertoServidor;
    }

    /**
     * Envía un mensaje UDP al servidor.
     * @param mensaje Mensaje a enviar.
     */
    public void enviarMensaje(String mensaje) {
        byte[] mensaje_bytes;

        try {
            // Transformar la dirección IP del servidor a InetAddress.
            address = InetAddress.getByName(SERVIDOR);
            // Convertir el mensaje en un arreglo de bytes.
            mensaje_bytes = mensaje.getBytes();
            // Crear un paquete DatagramPacket con el mensaje, la dirección IP y el puerto del servidor.
            paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, PUERTO_SERVIDOR);
            // Enviar el paquete al servidor a través del socket UDP.
            socket.send(paquete);
        } catch (Exception e) {
            System.err.println("Exception " + e.getMessage());
        }
    }
}

