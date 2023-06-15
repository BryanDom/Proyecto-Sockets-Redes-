package cliente.udp;


import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Clase desarrollada para enviar video por medio de un paquete UDP.
 */
public class ClienteEnviaVideoUDP extends Thread {
    private int puerto_servidor; // Número de puerto del servidor.
    private DatagramSocket socket; // Socket UDP para enviar los paquetes de audio.
    private InetAddress ip; // Dirección IP del servidor al que se enviará el audio.
    private DatagramPacket paquete; // Paquete UDP que contiene los datos de audio.
    private Webcam camara; // API que permite el uso de una camara interna o externa.
    private BufferedImage bufferedImg; // Manipulación de datos de una imagen.
    private ImageIcon drawImg; // Trabajar con distintos tipos de imagen.
    private JLabel video_usuario; // Etiqueta para el video del usuario.
    private boolean bandera_camara; // Estado actual de la bandera de la camara.
    private String servidor; // Valor del servidor.

    /**
     * Constructor de la clase, el cual nos ayuda a poder enviar el video.
     * Inicialización de variables declaradas.
     * @param servidor - Nombre del servidor.
     * @param puertoServidor - Valor del puerto del servidor.
     * @param video_usuario - Etiqueta del usuario.
     * @throws UnknownHostException - Excepción que se lanza para indicar que no se pudo determinar la dirección IP de un host.
     * @throws SocketException - Excepción que se lanza en caso de que haya un error con la red.
     */
    public ClienteEnviaVideoUDP(String servidor, int puertoServidor, JLabel video_usuario) throws UnknownHostException, SocketException {
        this.servidor = servidor;
        this.puerto_servidor = puertoServidor;
        this.ip = InetAddress.getByName(servidor);
        this.video_usuario = video_usuario;
        bandera_camara = true;
        socket = new DatagramSocket();
    }

    /**
     * Método para poder iniciar con el vídeo entre cliente-servidor.
     */
    public void run() {
        try {
            camara = Webcam.getDefault();
            camara.setViewSize(new Dimension(320, 240));
            camara.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (bandera_camara) {
            try {
                bufferedImg = camara.getImage();
                drawImg = new ImageIcon(bufferedImg);
                video_usuario.setIcon(drawImg);


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImg, "jpg", baos);
                byte[] imageByte = baos.toByteArray();

                paquete = new DatagramPacket(imageByte, imageByte.length, ip, puerto_servidor);
                socket.send(paquete);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para detener el envío de video entre cliente-servidor.
     * @throws IOException - Excepción de entrada y salida de datos.
     */
    public void detener() throws IOException {
        if (bandera_camara) {
            bandera_camara = false;
            camara.close();
            String valorSalida = "detener";
            byte[] bytes = valorSalida.getBytes(StandardCharsets.UTF_8);
            DatagramPacket paqueteSalida = new DatagramPacket(bytes, bytes.length, ip, puerto_servidor);
            socket.send(paqueteSalida);
            video_usuario.setIcon(null);
            socket.close();
            stop();
        }
    }
}

