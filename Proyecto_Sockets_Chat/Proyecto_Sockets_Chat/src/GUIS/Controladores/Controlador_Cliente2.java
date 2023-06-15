package GUIS.Controladores;

import GUIS.GUI_Cliente2;
import GUIS.Modelos.Modelo_Cliente2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Clase que se encarga del algunos métodos para administrar el controlador del cliente2.
 */
public class Controlador_Cliente2 implements ActionListener {
    private GUI_Cliente2 gui; // Instancia de la interfaz gráfica del cliente2.
    private Modelo_Cliente2 modelo; // Instancia del modelo del cliente2.

    /**
     * Constructor de la clase, el cual da un valor a las variables declaradas.
     * @param g - Valor de la GUI del cliente2.
     * @param m - Valor del modelo del cliente2.
     */
    public Controlador_Cliente2(GUI_Cliente2 g, Modelo_Cliente2 m){
        this.gui = g;
        this.modelo = m;
    }

    /**
     * Método para poder hacer uso de las funcionalidades establecidas del cliente2.
     * @throws Exception - Excepción relacionada con alguna interrupción dentro del flujo normal de instrucciones.
     */
    public void iniciar() throws Exception {
        gui.argumentos();
        gui.inicializar();
        modelo.iniciaServidorTCP();
    }

    /**
     * Método para poder utilizar los eventos sobre algún elemento a la GUI del cliente2.
     * @param e - Valor del evento.e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("SELECCIONAR")){
            gui.seleccionarArchivo();
        }
        else if (e.getActionCommand().equals("ACTIVAR")){
            try {
                gui.activarCamara();
            } catch (SocketException ex) {
                throw new RuntimeException(ex);
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getActionCommand().equals("ENVIAR_A")){
            try {
                gui.enviarArchivo();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        else{
            gui.enviarMensaje();
        }
    }
}