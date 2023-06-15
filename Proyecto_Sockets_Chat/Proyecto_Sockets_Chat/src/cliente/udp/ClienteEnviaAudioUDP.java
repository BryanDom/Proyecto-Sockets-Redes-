package cliente.udp;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.net.*;

/**
 * Clase que se encarga de implementar los métodos necesarios para enviar audio entre cliente-servidor.
 */
public class ClienteEnviaAudioUDP extends Thread {
    private DatagramSocket socket;  // Socket UDP para enviar los paquetes de audio.
    private InetAddress address;  // Dirección IP del servidor al que se enviará el audio.
    private TargetDataLine micro;  // Línea de datos de destino para capturar el audio del micrófono.
    private String ipServidor;  // Dirección IP del servidor.
    private AudioFormat formatoAudio;  // Formato de audio utilizado.
    private DataLine.Info info;  // Información sobre la línea de datos.
    private byte[] datos;  // Datos de audio capturados.
    private boolean bandera_audio;  // Bandera que indica si se está enviando audio o no.
    private int puertoServidor;  // Número de puerto del servidor.
    private int dsize;  // Tamaño de los datos de audio capturados.
    private DatagramPacket paquete;  // Paquete UDP que contiene los datos de audio.

    /**
     * Método que se encarga de enviar audio por un UDP.
     * @param ipServer - Guarda la dirección IP del servidor.
     * @param puertoServer - Guarda el número de puerto del servidor.
     * @throws SocketException - Excepción que se lanza en caso de que haya un error con la red.
     * @throws UnknownHostException - Excepción que se lanza para indicar que no se pudo determinar la dirección IP de un host.
     */
    public ClienteEnviaAudioUDP(String ipServer, int puertoServer) throws SocketException, UnknownHostException {
        this.ipServidor = ipServer;
        this.puertoServidor = puertoServer;
        bandera_audio = true;  // Establece la bandera de audio en verdadero, lo que indica que se está enviando audio.
        address = InetAddress.getByName(ipServidor);  // Obtiene la instancia de InetAddress correspondiente a la dirección IP del servidor.
        socket = new DatagramSocket();  // Crea un nuevo socket UDP para enviar paquetes de audio.
        formatoAudio = new AudioFormat(48000.0f, 16, 2, true, false);  // Crea un nuevo objeto AudioFormat con los parámetros especificados (frecuencia de muestreo, tamaño de muestra, canales, etc.).
        info = new DataLine.Info(TargetDataLine.class, formatoAudio);  // Crea un nuevo objeto DataLine.Info para el tipo de línea TargetDataLine con el formato de audio especificado.
    }

    /**
     * Método para la captura de audio de un micrófono.
     */
    @Override
    public void run() {
        try {
            micro = (TargetDataLine) AudioSystem.getLine(info);  // Obtiene la línea de datos de destino del sistema de audio.
            micro.open(formatoAudio);  // Abre la línea de datos con el formato de audio especificado.
            micro.start();  // Inicia la captura de audio desde el micrófono.
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (bandera_audio) {
            try {
                datos = new byte[64000];  // Crea un nuevo arreglo de bytes para almacenar los datos de audio capturados.
                dsize = micro.read(datos, 0, datos.length);  // Lee los datos de audio capturados y obtiene la cantidad de bytes leídos.
                paquete = new DatagramPacket(datos, datos.length, address, puertoServidor);  // Crea un nuevo paquete UDP con los datos de audio, la dirección IP y el puerto del servidor.
                socket.send(paquete);  // Envía el paquete UDP al servidor.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para poder detener el envío del audio.
     */
    public void detener() {
        if (bandera_audio) {
            bandera_audio = false;  // Establece la bandera de audio en falso para detener el envío de audio.
            micro.close();  // Cierra la línea de datos de destino.
            socket.close();  // Cierra el socket UDP.
            stop();  // Detiene la ejecución del hilo.
        }
    }
}
