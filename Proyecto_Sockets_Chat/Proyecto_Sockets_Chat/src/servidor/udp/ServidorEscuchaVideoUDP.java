package servidor.udp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;

//declaramos la clase udp envia
public class ServidorEscuchaVideoUDP extends Thread {
    // Atributos de la clase
    protected DatagramSocket socket; // Socket para recibir paquetes
    protected DatagramPacket paquete; // Paquete para recibir datos
    private ImageIcon imagen_capturada; // Imagen para mostrar en la interfaz gráfica
    private JLabel videoServidor; // Etiqueta para mostrar la imagen en la interfaz gráfica

    // Constructor de la clase
    public ServidorEscuchaVideoUDP(int puertoServidor, JLabel webcamServidor) throws SocketException {
        // Creamos un socket en el puerto especificado
        socket = new DatagramSocket(puertoServidor);
        // Asignamos la etiqueta para mostrar la imagen
        videoServidor=webcamServidor;
    }

    // Método run que se ejecuta cuando se inicia el hilo
    public void run() {
        // Bucle infinito para recibir paquetes
        while (true) {
            try {
                // Creamos un array de bytes para almacenar los datos recibidos
                byte[] bytes = new byte[64000];

                // Creamos un paquete para recibir los datos
                paquete = new DatagramPacket(bytes,64000);
                // Recibimos el paquete del socket
                socket.receive(paquete);
                // Convertimos los datos del paquete a un String y lo recortamos
                String mensaje = new String(paquete.getData(),0,paquete.getLength()).trim();
                // Si el mensaje es igual a "-1"
                if(mensaje.equalsIgnoreCase("detener")){
                    // Borramos la imagen de la etiqueta
                    videoServidor.setIcon(null);
                }else{
                    // Si el mensaje no es igual a "detener"
                    // Obtenemos los datos del paquete en forma de array de bytes
                    byte[] paqueteBytes=paquete.getData();
                    // Creamos un ByteArrayInputStream con los datos del paquete
                    ByteArrayInputStream bain=new ByteArrayInputStream(paqueteBytes);
                    // Leemos una imagen desde el ByteArrayInputStream
                    BufferedImage bIma=ImageIO.read(bain);
                    // Creamos un ImageIcon con la imagen leída
                    imagen_capturada= new ImageIcon(bIma);
                    // Asignamos el ImageIcon a la etiqueta para mostrar la imagen en la interfaz gráfica
                    videoServidor.setIcon(imagen_capturada);
                }
            } catch (Exception e) {
                // Si ocurre un error al recibir el paquete, mostramos el error completo
                e.printStackTrace();
            }
        }
    }
}