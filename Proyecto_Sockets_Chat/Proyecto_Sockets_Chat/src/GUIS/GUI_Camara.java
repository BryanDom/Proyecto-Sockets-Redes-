package GUIS;


import GUIS.Controladores.Controlador_Camara;
import GUIS.Modelos.Modelo_Camara;
import cliente.udp.ClienteEnviaAudioUDP;
import cliente.udp.ClienteEnviaVideoUDP;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Clase que implementa métodos para crear la visualización de la interfaz gráfica para la webcam.
 */
public class GUI_Camara extends JFrame {
    private JButton activa_camara; // Botón para encender la cámara.
    private JButton activa_micro; // Botón para encender el micrófono.
    private JButton desactiva_camara; // Botón para apagar la cámara.
    private JButton desactiva_micro; // Botón para apagar el micrófono.
    private JLabel video_usuario; // Label para mostrar el video del usuario.
    protected Controlador_Camara controlador; // Controlador de la cámara.
    private String ip_servidor; // Valor de la dirección IP del servidor.
    private int puerto_video; // Puerto del video.
    private int puerto_audio; // Puerto del audio.
    private JLabel servidor; // Label para el servidor.
    private JPanel contenedor;
    private JPanel contenedor2;
    private JPanel contenedorFinal;
    private ClienteEnviaVideoUDP clienteEnviaVideoUDP; // Objeto para enviar el video por paquete UDP.
    private ClienteEnviaAudioUDP clienteEnviaAudioUDP; // Objeto para enviar audio por paquete UDP.
    private Modelo_Camara camara; // Modelo del cámara.

    /**
     * Constructor de la clase que manda llamar el método Iniciar().
     */
    public GUI_Camara()  {
        Iniciar();
    }

    /**
     * Método para iniciar la interfaz gráfica.
     */
    private void Iniciar() {
        this.setSize(1300, 450);
        this.setResizable(false);

        contenedor = new JPanel();
        contenedor2 = new JPanel();
        video_usuario = new JLabel();
        contenedorFinal = new JPanel();
        servidor = new JLabel();
        activa_camara = new JButton();
        desactiva_camara = new JButton();
        activa_micro = new JButton();
        desactiva_micro = new JButton();

        //ventana, configuración
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        //panel principal
        contenedor.setLayout(new BorderLayout());
        add(contenedor, BorderLayout.CENTER);

        contenedor2.setLayout(new BorderLayout());
        contenedor.add(contenedor2, BorderLayout.CENTER);
        contenedor2.add(video_usuario, BorderLayout.CENTER);

        contenedorFinal.setLayout(new FlowLayout());
        contenedor.add(contenedorFinal, BorderLayout.SOUTH);
        contenedorFinal.add(servidor);
        contenedorFinal.add(activa_camara);
        contenedorFinal.add(desactiva_camara);
        contenedorFinal.add(activa_micro);
        contenedorFinal.add(desactiva_micro);

        ImageIcon iconoActivar = new ImageIcon(getClass().getResource("/archivos/imagenes/Camara_on.png"));
        Image imagenRedimensionadaA = iconoActivar.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon iconoRedimensionadoA = new ImageIcon(imagenRedimensionadaA);
        activa_camara.setIcon(iconoRedimensionadoA);

        ImageIcon iconoDesactivar = new ImageIcon(getClass().getResource("/archivos/imagenes/Camara_off.png"));
        Image imagenRedimensionadaD = iconoDesactivar.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon iconoRedimensionadoD = new ImageIcon(imagenRedimensionadaD);
        desactiva_camara.setIcon(iconoRedimensionadoD);

        ImageIcon iconoActivarM = new ImageIcon(getClass().getResource("/archivos/imagenes/Micro_on.png"));
        Image imagenRedimensionadaAM = iconoActivarM.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon iconoRedimensionadoAM = new ImageIcon(imagenRedimensionadaAM);
        activa_micro.setIcon(iconoRedimensionadoAM);

        ImageIcon iconoDesactivarM = new ImageIcon(getClass().getResource("/archivos/imagenes/Micro_off.png"));
        Image imagenRedimensionadaDM = iconoDesactivarM.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon iconoRedimensionadoDM = new ImageIcon(imagenRedimensionadaDM);
        desactiva_micro.setIcon(iconoRedimensionadoDM);
    }

    /**
     * Método SET que se encarga de modificar el valor del controlador de la cámara.
     * @param c - Nuevo valor del controlador.
     */
    public void setControlador(Controlador_Camara c){
        this.controlador = c;
    }

    /**
     * Método SET que se encarga de modificar el valor del modelo de la cámara.
     * @param c - Nuevo valor que tomará el valor del modelo.
     */
    public void setCamara(Modelo_Camara c){
        this.camara = c;
    }

    /**
     * Método GET que obtiene el valor que tiene el label del servidor.
     * @return - Retorna el valor del servidor.
     */
    public JLabel getServidor() {
        return servidor;
    }

    /**
     * Método SET que se encarga de modificar el valor de la dirección IP del servidor.
     * @param ipServer - Nuevo valor de la IP.
     */
    public void setip_servidor(String ipServer) {
        this.ip_servidor = ipServer;
    }

    /**
     * Método SET que se encarga de modificar el valor del puerto del servidor de video.
     * @param puerto_video - Nuevo valor del puerto.
     */
    public void setPuerto_Video(int puerto_video) {
        this.puerto_video = puerto_video;
    }

    /**
     * Método SET que se encarga de modificar el valor del puerto del servidor de audio.
     * @param puerto_audio - Nuevo valor del servidor.
     */
    public void setPuertoAudio(int puerto_audio) {
        this.puerto_audio = puerto_audio;
    }

    /**
     * Método para asignarle un string a los jButton's y de este modo el ActionListener lo escuche y haga lo que se indica.
     */
    public void inicializar(){
        activa_camara.setActionCommand("ACTIVAR");
        desactiva_camara.setActionCommand("DESACTIVAR");
        activa_micro.setActionCommand("ACTIVAR_M");
        desactiva_micro.setActionCommand("DESACTIVAR_M");

        activa_camara.addActionListener(controlador);
        desactiva_camara.addActionListener(controlador);
        activa_micro.addActionListener(controlador);
        desactiva_micro.addActionListener(controlador);
    }

    /**
     * Método para activar el video de la cámara.
     * @throws SocketException - Excepción que se lanza en caso de que haya un error con la red.
     * @throws UnknownHostException - Excepción que se lanza para indicar que no se pudo determinar la dirección IP de un host.
     */
    public void activar_camara() throws SocketException, UnknownHostException {
        clienteEnviaVideoUDP= new ClienteEnviaVideoUDP(ip_servidor,puerto_video,video_usuario);
        clienteEnviaVideoUDP.start();
    }

    /**
     * Método para desactivar el video de la cámara.
     * @throws IOException - Excepción de entrada/salida de datos.
     */
    public void desactivar_camara() throws IOException {
        if(clienteEnviaVideoUDP!=null) {
            clienteEnviaVideoUDP.detener();
        }
    }

    /**
     * Método para activar el paso de audio del micrófono.
     * @throws SocketException - Excepción que se lanza en caso de que haya un error con la red.
     * @throws UnknownHostException - Excepción que se lanza para indicar que no se pudo determinar la dirección IP de un host.
     */
    public void activar_microfono() throws SocketException, UnknownHostException {
        clienteEnviaAudioUDP=new ClienteEnviaAudioUDP(ip_servidor,puerto_audio);
        clienteEnviaAudioUDP.start();
    }

    /**
     * Método para desactivar el paso de audio del micrófono.
     */
    public void desactivar_microfono() {
        if(clienteEnviaAudioUDP!=null){
            clienteEnviaAudioUDP.detener();
        }
    }
}

