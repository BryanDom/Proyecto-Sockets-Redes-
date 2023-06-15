package cliente.tcp;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
// importar la libreria java.io

/**
 * Clase que brinda los métodos para poder enviar un paquete TCP.
 * Hereda de un hilo, de Thread.
 */

public class ClienteEnviaTCP extends Thread{
    private Socket socket;     // Declaramos un objeto socket para realizar la comunicación.
    private int puerto_archivos; // Valor del puerto del servidor.
    private String ip; // ip del servidor.
    private File archivo; // Archivo enviado.
    private JLabel velocidadEnvioDef; // Etiqueta para mostrar el ancho de banda.
    private JLabel tiempoRestDef; // Etiqueta para mostrar el tiempo restante para terminar de enviar un archivo.
    private JLabel latenciaDef; // Etiqueta para mostrar el tiempo que va a tardar un mensaje de llegar de un lugar a otro.
    private JLabel tiempoTransDef; // Etiqueta para mostrar el tiempo que ha avanzado desde que se envío el archivo.
    private JTextArea areaMensaje; // Contenedor que recibe los mensajes enviados.

    /**
     * Constructor de la clase.
     * Redireccion de las exepciones.
     * @param ip - la ip del servidor.
     * @param puerto_archivos - Número de puerto del servidor.
     * @param archivo - Archivo enviado.
     * @param velocidadEnvioDef - Ancho de banda.
     * @param tiempoRestDef - Tiempo restante del envío del mensaje.
     * @param areaMensaje - Mensajes enviados.
     * @param latenciaDef - Latencia del mensaje enviado.
     * @param tiempoTransDef - Tiempo transcurrido  partir del envío del mensaje.
     * @throws Exception - Excepción relacionada con alguna interrupción dentro del flujo normal de instrucciones.
     */
    public ClienteEnviaTCP(String ip, int puerto_archivos, File archivo, JLabel velocidadEnvioDef,
                           JLabel tiempoRestDef, JTextArea areaMensaje,
                           JLabel latenciaDef, JLabel tiempoTransDef)throws Exception{

        this.puerto_archivos=puerto_archivos;
        this.ip=ip;
        this.archivo=archivo;
        this.velocidadEnvioDef=velocidadEnvioDef;
        this.tiempoRestDef=tiempoRestDef;
        this.areaMensaje=areaMensaje;
        this.latenciaDef=latenciaDef;
        this.tiempoTransDef=tiempoTransDef;
        
        // Instanciamos un socket con la dirección del destino y el
        // puerto que vamos a utilizar para la comunicación.
        socket = new Socket(ip,puerto_archivos);
    }

    /**
     * Método para poder enviar el paquete TCP.
     */
    public void run() {
        // Declaramos un bloque try y catch para controlar la ejecución del subprograma.
        try {
            // Creamos un BufferedInputStream para leer el archivo.
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(archivo));

            // Creamos un DataOutputStream para enviar datos al servidor destino a través del socket.
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Enviamos el nombre del archivo al servidor.
            // codificado en UTF
            out.writeUTF(archivo.getName());
            out.flush();

            // Inicialización de variables.
            int contador_enviados = 0;


            long inicio = System.currentTimeMillis();

            // Creamos un array de bytes para almacenar los datos leídos del archivo.
            byte[] buffer = new byte[8192];

            int contador; //
            // Leemos los datos del archivo y los enviamos al servidor.
            while ((contador = in.read(buffer)) != -1) {
                out.write(buffer, 0, contador);
                out.flush();
                contador_enviados++;

                // Calculamos el tiempo transcurrido en segundos
                long tiempo_transcurrido = (System.currentTimeMillis() - inicio) / 1000;

                // Calculamos la cantidad de datos leídos y restantes
                long cantidad_leidos = (long) contador_enviados * buffer.length;
                long cantidad_restante = archivo.length() - cantidad_leidos;

                // división sin cero
                if (tiempo_transcurrido != 0 && cantidad_leidos != 0) {
                    // Calculate the transfer rate (bandwidth), total transmission time
                    // (transmissionTime) and remaining time (tiempoRestante)
                    long ancho_banda = calcularAnchoDeBanda(cantidad_leidos, tiempo_transcurrido);
                    long transmissionTime = calcularTiempoDeTransmision(archivo.length(), ancho_banda);
                    long tiempoRestante = calcularTiempoRestante(cantidad_restante, ancho_banda);

                    latenciaDef.setText(transmissionTime + "s");
                    tiempoTransDef.setText(tiempo_transcurrido + "s");
                    velocidadEnvioDef.setText(ancho_banda + "bps");
                    tiempoRestDef.setText(tiempoRestante + "s");
                }
            }

            // Actualizamos las etiquetas para indicar que el envío se ha completado.
            latenciaDef.setText("LISTO");
            tiempoTransDef.setText("LISTO");
            velocidadEnvioDef.setText("LISTO");
            tiempoRestDef.setText("LISTO");


            String mensajesEnviadosString = areaMensaje.getText();
            mensajesEnviadosString += "Enviado: " + archivo.getName() + "\n";
            areaMensaje.setText(mensajesEnviadosString);

            // Cerramos los flujos y el socket.
            in.close();
            out.close();
            socket.close();

            // detenemos el hilo actual.
            stop();
        }
        // Utilizamos el catch para capturar los errores que puedan surgir.
        catch (Exception e) {
            // Mostramos los errores en la consola y salimos del programa.
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    // Método para calcular el ancho de banda
    private long calcularAnchoDeBanda(long cantidad_leidos, long tiempo_transcurrido) {
        // Calculamos el ancho de banda dividiendo la cantidad de datos leídos por el tiempo transcurrido
        long ancho_banda = cantidad_leidos * 8 / 1024 / 1024 / tiempo_transcurrido; // bps
        return ancho_banda;
    }

    // Método para calcular el tiempo de transmisión
    private long calcularTiempoDeTransmision(long tamanoArchivo, long ancho_banda) {
        // Inicializamos el tiempo de transmisión en cero
        long tiempo_transmisión = 0;
        // Si el ancho de banda es diferente de cero
        if (ancho_banda != 0) {
            // Calculamos el tiempo de transmisión dividiendo el tamaño total del archivo por el ancho de banda
            tiempo_transmisión = (tamanoArchivo * 8 / 1024 / 1024) / ancho_banda; // seconds
        } else {
            // Si el ancho de banda es cero, asignamos un valor predeterminado al tiempo de transmisión
            tiempo_transmisión = 1; // seconds
        }
        return tiempo_transmisión;
    }

    // Método para calcular el tiempo restante
    private long calcularTiempoRestante(long cantidad_restante, long ancho_banda) {
        // Inicializamos el tiempo restante en cero
        long tiempoRestante = 0;
        // Si el ancho de banda es diferente de cero
        if (ancho_banda != 0) {
            // Calculamos el tiempo restante dividiendo los datos restantes por el ancho de banda
            tiempoRestante = (cantidad_restante * 8 / 1024 / 1024) / ancho_banda; // seconds
        } else {
            // Si el ancho de banda es cero, asignamos un valor predeterminado al tiempo restante
            tiempoRestante = 1; // seconds
        }
        return tiempoRestante;
    }
}
