package pruebas;

import GUIS.Controladores.Controlador_Cliente2;
import GUIS.GUI_Cliente2;
import GUIS.Modelos.Modelo_Camara;
import GUIS.Modelos.Modelo_Cliente2;

/**
 * Clase encargada de hacer la prueba del cliente 2.
 * @author Brayan Domínguez Saucedo.
 * @author Narda Viktoria Gómez Aguilera.
 */
public class PruebaCliente2 {
    public static void main(String[] args) throws Exception {
        GUI_Cliente2 gui = new GUI_Cliente2();
        Modelo_Camara modeloC =new Modelo_Camara();
        Modelo_Cliente2 modelo = new Modelo_Cliente2(
                "192.168.56.1",
                55001,
                55002,
                55005,
                55006,
                55003,
                55004,
                55007,
                55008,
                gui,
                modeloC);

        Controlador_Cliente2 controlador = new Controlador_Cliente2(gui, modelo);
        gui.setControlador(controlador);
        gui.setCliente(modelo);
        modelo.setControlador(controlador);
        controlador.iniciar();
    }
}
