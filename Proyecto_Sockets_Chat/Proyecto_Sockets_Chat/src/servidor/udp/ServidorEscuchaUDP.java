package servidor.udp;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class ServidorEscuchaUDP extends Thread{
    protected DatagramSocket socket;
    protected final int PUERTO_SERVER;
    protected int puertoCliente=0;
    
    protected InetAddress addressCliente;
    protected final int MAX_BUFFER=256;
    protected JTextArea areaMensajes;
    
    public ServidorEscuchaUDP(int puertoS, JTextArea areaDeMensajes) throws Exception{
        //Creamos el socket
        PUERTO_SERVER=puertoS;
        //datagramSocket hace referencia a UDP.
        socket = new DatagramSocket(puertoS);

        areaMensajes=areaDeMensajes;
    }
    /**
     * Método run() que se ejecuta en un hilo para recibir mensajes del cliente.
     */
    public void run() {
        try {
            String mensaje = "";

            while (true) {
                // Crear un array de bytes para almacenar el mensaje recibido
                byte[] mensaje_bytes = new byte[MAX_BUFFER];

                // Crear un DatagramPacket para recibir el mensaje del cliente
                DatagramPacket paquete = new DatagramPacket(mensaje_bytes, MAX_BUFFER);

                // Recibir el mensaje del cliente
                socket.receive(paquete);

                // Obtener los bytes recibidos del DatagramPacket
                mensaje_bytes = new byte[paquete.getLength()];
                mensaje_bytes = paquete.getData();

                // Convertir los bytes a una cadena de texto, eliminando los espacios en blanco
                mensaje = new String(mensaje_bytes, 0, paquete.getLength()).trim();

                // Mostrar el mensaje recibido en un área de texto
                String mensajesRecibidos = areaMensajes.getText();
                mensajesRecibidos += "Mensaje recibido \"" + mensaje + "\" del cliente " + paquete.getAddress() + "\n";
                areaMensajes.setText(mensajesRecibidos);

                // Guardar el puerto y la dirección IP del cliente
                puertoCliente = paquete.getPort();
                addressCliente = paquete.getAddress();
            }
        } catch (Exception e) {
            // En caso de producirse una excepción, mostrar el mensaje de error en la consola y salir del programa
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
