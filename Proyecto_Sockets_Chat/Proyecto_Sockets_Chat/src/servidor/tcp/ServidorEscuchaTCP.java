package servidor.tcp;

import javax.swing.*;
import java.net.*;
//importar la libreria java.net
 
import java.io.*;
//importar la libreria java.io
// declaramos la clase servidortcp

// declaramos la clase ServidorEscuchaTCP
public class ServidorEscuchaTCP extends Thread {
    // declaramos variables miembro
    private ServerSocket socket;
    private final int PUERTO_SERVER;

    private JTextArea areaMensajes;

    // constructor de la clase
    public ServidorEscuchaTCP(int puertoS, JTextArea areaMensajes) throws Exception {
        PUERTO_SERVER = puertoS;
        // Instanciamos un ServerSocket con la dirección del destino y el
        // puerto que vamos a utilizar para la comunicación
        socket = new ServerSocket(PUERTO_SERVER);

        this.areaMensajes = areaMensajes;
    }
    // método run() que se ejecuta en un hilo separado
    public void run() {
        try {
            while (true) {
                // Aceptamos una conexión de cliente entrante
                Socket cliente_socket = socket.accept();
                InputStream is = cliente_socket.getInputStream();

                // Creamos un DataInputStream para recibir datos del cliente
                DataInputStream in = new DataInputStream(cliente_socket.getInputStream());

                // Leemos el nombre del archivo enviado por el cliente
                String nombreArchivo = in.readUTF();

                // Especificamos la ubicación de guardado del archivo
                String rutaArchivo = "Proyecto_Sockets_Chat/src/archivos/" + nombreArchivo;
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(rutaArchivo));

                byte[] buffer = new byte[8192];
                // Leemos los datos del archivo enviados por el cliente y los escribimos en el archivo local
                int contador;
                while ((contador = is.read(buffer)) != -1) {
                    out.write(buffer, 0, contador);
                }
                out.close();


                // Agregamos el mensaje de recepción del archivo a la JTextArea de mensajes recibidos
                String mensajesRecibidos = areaMensajes.getText();
                mensajesRecibidos += "Archivo recibido: \"" + nombreArchivo + "\n";
                areaMensajes.setText(mensajesRecibidos);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
