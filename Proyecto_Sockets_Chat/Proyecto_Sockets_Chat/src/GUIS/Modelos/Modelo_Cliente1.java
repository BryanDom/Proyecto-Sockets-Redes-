package GUIS.Modelos;

import GUIS.Controladores.Controlador_Cliente1;
import GUIS.GUI_Cliente1;
import cliente.tcp.ClienteEnviaTCP;
import cliente.udp.ClienteEnviaUDP;
import servidor.tcp.ServidorEscuchaTCP;
import servidor.udp.ServidorEscuchaUDP;

import javax.swing.*;
import java.io.File;

/**
 * Clase que implementa atributos y métodos que ayudan a definir un modelo de Cliente1.
 */
public class Modelo_Cliente1{
    private Controlador_Cliente1 controlador; // Instancia del controlador del cliente1.
    private  ClienteEnviaUDP clienteEnviaUDP; // Instancia del cliente que envía paquetes UDP.
    private  ClienteEnviaTCP clienteEnviaTCP; // Instancia del cliente que envía paquetes TCP.
    private String ip; // Valor de la dirección IP del servidor.
    private File archivo; // Instancia de un objeto de tipo FILE (archivos).
    private JLabel latenciaDef; // Etiqueta para conocer el valor de la Latencia.
    private JLabel tiempoTransDef; // Etiqueta para conocer el valor del tiempo transcurrido (TT).
    private JLabel tiempoRestDef; // Etiqueta para conocer el valor del tiempo restante.
    private JLabel velocidadEnvioDef; // Etiqueta para conocer el valor de la velocidad actual.
    private JTextArea areaMensaje; // Área para escribir mensajes entre cliente-servidor.
    private int puerto_mensajes; // Valor del puerto del servidor de mensajes.
    private int puerto_cliente_mensajes; // Valor del puerto de mensajes del cliente.
    private int puerto_video; // Valor del puerto del servidor de videos.
    private int puerto_cliente_video; // Valor del puerto de video del cliente.
    private int puerto_archivos; // Valor del puerto del servidor de archivos.
    private int puerto_audio; // Valor del puerto del servidor de audio.
    private int puerto_cliente_audio; // Valor del puerto de audio del cliente.
    private ServidorEscuchaUDP servidorEscuchaUDP; // Instancia de un objeto de tipo ServidorEscuchaUDP.
    private int puerto_cliente_archivos; // Valor del puerto de archivos del cliente.

    /**
     * Constructor de la clase, el cual inicializa algunas variables que ya fueron definidas.
     * @param ip - Dirección IP del servidor.
     * @param puerto_mensajes - Número del puerto del servidor de mensajes.
     * @param puerto_cliente_mensajes - Número del puerto de mensajes del cliente.
     * @param puerto_video - Número del puerto del servidor de video.
     * @param puerto_cliente_video - Número del puerto de video del cliente.
     * @param puerto_archivos - Número del puerto del servidor de archivos.
     * @param puerto_audio - Número del puerto del servidor de audio.
     * @param puerto_cliente_audio - Número del puerto de audio del cliente.
     * @param gui - Interfaz gráfica.
     * @param modelo - Modelo del Cliente 1.
     * @throws Exception - Excepción relacionada con alguna interrupción dentro del flujo normal de instrucciones.
     */
    public Modelo_Cliente1(String ip, int puerto_mensajes, int puerto_cliente_mensajes,
                           int puerto_video, int puerto_cliente_video,
                           int puerto_archivos, int puerto_cliente_archivos,
                           int puerto_audio, int puerto_cliente_audio,
                           GUI_Cliente1 gui, Modelo_Camara modelo) throws Exception {
        this.ip = ip;
        this.puerto_mensajes = puerto_mensajes;
        this.puerto_cliente_mensajes = puerto_cliente_mensajes;
        this.puerto_video = puerto_video;
        this.puerto_cliente_video = puerto_cliente_video;
        this.puerto_archivos = puerto_archivos;
        this.puerto_cliente_archivos = puerto_cliente_archivos;
        this.puerto_audio = puerto_audio;
        this.puerto_cliente_audio = puerto_cliente_audio;

        gui.setCamara(modelo);
        modelo.setPuertoServerVideo(puerto_video);
        modelo.setPuertoClienteVideo(puerto_cliente_video);
        modelo.setPuertoServerAudio(puerto_audio);
        modelo.setPuertoClienteAudio(puerto_cliente_audio);

        servidorEscuchaUDP = new ServidorEscuchaUDP(puerto_cliente_mensajes, gui.getMensajesArea());
        servidorEscuchaUDP.start();

    }

    /**
     * Método SET que se encarga de modificar el valor del controlador del cliente1.
     * @param c - Controlador.
     */
    public void setControlador(Controlador_Cliente1 c){
        this.controlador = c;
    }

    /**
     * Método GET que se encarga de obtener los valores que se envían dentro de un paquete TCP.
     * @return - Retorna un paquete TCP.
     * @throws Exception - Excepción relacionada con alguna interrupción dentro del flujo normal de instrucciones.
     */
    public ClienteEnviaTCP getClienteEnviaTCP() throws Exception {
        return clienteEnviaTCP=new ClienteEnviaTCP(ip,puerto_archivos,archivo, velocidadEnvioDef,
                tiempoRestDef,areaMensaje,
                latenciaDef, tiempoTransDef);
    }

    /**
     * Método SET que se encarga de modificar el valor de los archivos enviados..
     * @param archivo - Valor del archivo.
     */
    public void setarchivoSeleccionado(File archivo){
        this.archivo = archivo;
    }

    /**
     * Método SET que se encarga de modificar el valor de la velocidad actual.
     * @param velocidadEnvioDef - Velocidad actual.
     */
    public void setvelocidadEnvioDef(JLabel velocidadEnvioDef) {
        this.velocidadEnvioDef = velocidadEnvioDef;
    }

    /**
     * Método SET que se encarga de modificar el valor del tiempo restante.
     * @param tiempoRestDef - Tiempo restante.
     */
    public void settiempoRestDef(JLabel tiempoRestDef) {
        this.tiempoRestDef = tiempoRestDef;
    }

    /**
     * Método SET que se encarga de modificar el valor de la latencia.
     * @param latenciaDef - Latencia.
     */
    public void setlatenciaDef(JLabel latenciaDef) {
        this.latenciaDef = latenciaDef;
    }

    /**
     * Método SET que se encarga de modificar el valor del tiempo transcurrido.
     * @param tiempoTransDef - Tiempo transcurrido.
     */
    public void settiempoTransDef(JLabel tiempoTransDef) {
        this.tiempoTransDef = tiempoTransDef;
    }

    /**
     * Método SET que se encarga de modificar el valor de los mensajes enviados.
     * @param areaMensaje - Mensaje(s).
     */
    public void setAreaMensajes(JTextArea areaMensaje){
        this.areaMensaje = areaMensaje;
    }

    /**
     * Método GET que se encarga de obtener los valores que se envían dentro de un paquete UDP.
     * @return - Retorna un objeto de tipo ClienteEnviaUDP.
     */
    public ClienteEnviaUDP getClienteEnviaUDP() {
        return clienteEnviaUDP = new ClienteEnviaUDP(ip, puerto_mensajes);
    }

    /**
     * Método para iniciar el servidor TCP.
     */
    public void iniciaServidorTCP(){
        try {
            ServidorEscuchaTCP servidorEscuchaTCP = new ServidorEscuchaTCP(puerto_cliente_archivos, areaMensaje);
            servidorEscuchaTCP.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
