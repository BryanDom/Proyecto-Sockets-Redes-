package GUIS.Controladores;

import GUIS.GUI_Cliente1;
import GUIS.Modelos.Modelo_Cliente1;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Clase que se encarga del algunos métodos para administrar el controlador del cliente1.
 */
public class Controlador_Cliente1 implements ActionListener {
    private GUI_Cliente1 gui; // Instancia de la interfaz gráfica del cliente1.
    private Modelo_Cliente1 modelo; // Instancia del modelo del cliente1.

    /**
     * Constructor de la clase, el cual da un valor a las variables declaradas.
     * @param g - Valor de la GUI del cliente1.
     * @param m - Valor del modelo del cliente1.
     */
    public Controlador_Cliente1(GUI_Cliente1 g, Modelo_Cliente1 m){
        this.gui = g;
        this.modelo = m;
    }

    /**
     * Método para poder hacer uso de las funcionalidades establecidas del cliente1.
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