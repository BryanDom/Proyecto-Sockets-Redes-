package GUIS.Modelos;

import GUIS.GUI_Camara;
import servidor.udp.ServidorEscuchaAudioUDP;
import servidor.udp.ServidorEscuchaVideoUDP;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Clase que implementa atributos y métodos que ayudan a definir un modelo de la Cámara.
 */
public class Modelo_Camara {
    private ServidorEscuchaVideoUDP servidorEscuchaVideoUDP; // Instancia del servidor de video para escuchar a través de un paquete UDP.
    private ServidorEscuchaAudioUDP servidorEscuchaAudioUDP; // Instancia del servidor de audio para escuchar a través de un paquete UDP.
    private GUI_Camara camara; // Instancia de la interfaz gráfica de la cámara.
    private String ipServidor; // Valor de la dirección IP del servidor.
    private int puertoServidorVideo; // Valor del puerto del servidor de video.
    private int puertoServidorAudio; // Valor del puerto del servidor de audio.
    private int puertoClienteAudio; // Valor del puerto de audio del cliente.
    private int puertoClienteVideo; // Valor del puerto de video del cliente.

    /**
     * Constructor de la clase.
     */
    public Modelo_Camara(){
        //no hace nada
    }

    /**
     * Método que se encarga de ejecutar la interfaz gráfica de la webcam.
     * @param camara - Valor del objeto de tipo "GUI_Camara".
     * @throws SocketException -  Excepción que se lanza en caso de que haya un error con la red.
     * @throws UnknownHostException - Excepción que se lanza para indicar que no se pudo determinar la dirección IP de un host.
     */
    public void ejecutar(GUI_Camara camara) throws SocketException {
        this.camara = camara;

        camara.setip_servidor(ipServidor);
        camara.setPuerto_Video(puertoServidorVideo);
        camara.setPuertoAudio(puertoServidorAudio);

        servidorEscuchaAudioUDP=new ServidorEscuchaAudioUDP(puertoClienteAudio);
        servidorEscuchaAudioUDP.start();
        servidorEscuchaVideoUDP=new ServidorEscuchaVideoUDP(puertoClienteVideo, camara.getServidor());
        servidorEscuchaVideoUDP.start();
    }


    /**
     * Método SET que se encarga de modificar el valor del puerto del servidor de video.
     * @param puertoServerVideo - Valor del puerto.
     */
    public void setPuertoServerVideo(int puertoServerVideo) {
        this.puertoServidorVideo = puertoServerVideo;
    }

    /**
     * Método SET que se encarga de modificar el valor del puerto de video de un cliente.
     * @param puertoClienteVideo - Valor del puerto.
     */
    public void setPuertoClienteVideo(int puertoClienteVideo) {
        this.puertoClienteVideo = puertoClienteVideo;
    }

    /**
     * Método SET que se encarga de modificar el valor del puerto del servidor de audio.
     * @param puertoServerAudio - Valor del puerto.
     */
    public void setPuertoServerAudio(int puertoServerAudio) {
        this.puertoServidorAudio = puertoServerAudio;
    }

    /**
     * Método SET que se encarga de modificar el valor del puerto de audio de un cliente.
     * @param puertoClienteAudio - Valor del puerto.
     */
    public void setPuertoClienteAudio(int puertoClienteAudio) {
        this.puertoClienteAudio = puertoClienteAudio;
    }

}
