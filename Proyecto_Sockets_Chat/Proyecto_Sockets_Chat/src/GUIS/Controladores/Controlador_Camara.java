package GUIS.Controladores;

import GUIS.GUI_Camara;
import GUIS.Modelos.Modelo_Camara;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Clase que implementa métodos para poder controlar las acciones de la webcam.
 */
public class Controlador_Camara implements ActionListener {
    protected GUI_Camara gui; // Instancia de la interfaz gráfica de la cámara.
    protected Modelo_Camara c; // Instancia del modelo de la cámara.

    /**
     * Constructor de la clase, el cual inicializa las variables declaradas.
     * @param g - Valor de la interfaz gráfica.
     * @param c - Valor del modelo.
     */
    public Controlador_Camara(GUI_Camara g, Modelo_Camara c){
        this.gui = g;
        this.c = c;
    }

    /**
     * Método para poder iniciar la interfaz gráfica de la webcam.
     */
    public void iniciar(){
        gui.setCamara(c);
        gui.inicializar();
    }

    /**
     * Método para poder utilizar los eventos sobre algún elemento a la GUI de video.
     * @param e - Valor del evento.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ACTIVAR")){
            try {
                gui.activar_camara();
            } catch (SocketException ex) {
                throw new RuntimeException(ex);
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getActionCommand().equals("DESACTIVAR")){
            try {
                gui.desactivar_camara();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getActionCommand().equals("DESACTIVAR_M")){
            try {
                gui.activar_microfono();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        else{
            gui.desactivar_microfono();
        }
    }
}