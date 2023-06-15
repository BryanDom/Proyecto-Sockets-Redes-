package pruebas;

import GUIS.Controladores.Controlador_Cliente1;
import GUIS.GUI_Cliente1;
import GUIS.Modelos.Modelo_Camara;
import GUIS.Modelos.Modelo_Cliente1;

/**
 * Clase encargada de hacer la prueba del cliente 1.
 * @author Brayan Domínguez Saucedo.
 * @author Narda Viktoria Gómez Aguilera.
 */
public class PruebaCliente1 {
    public static void main(String[] args) throws Exception {
        GUI_Cliente1 gui = new GUI_Cliente1();
        Modelo_Camara modeloC = new Modelo_Camara();
        Modelo_Cliente1 modelo = new Modelo_Cliente1(
                "192.168.56.1",
                55002,
                55001,
                55006,
                55005,
                55004,
                55003,
                55008,
                55007,
                gui,
                modeloC);

        Controlador_Cliente1 controlador = new Controlador_Cliente1(gui, modelo);
        gui.setControlador(controlador);
        gui.setCliente(modelo);
        modelo.setControlador(controlador);
        controlador.iniciar();
    }
}
