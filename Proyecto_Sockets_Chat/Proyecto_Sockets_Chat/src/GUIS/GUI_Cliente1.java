package GUIS;
import GUIS.Controladores.Controlador_Camara;
import GUIS.Controladores.Controlador_Cliente1;
import GUIS.Modelos.Modelo_Camara;
import GUIS.Modelos.Modelo_Cliente1;
import cliente.tcp.ClienteEnviaTCP;
import cliente.udp.ClienteEnviaUDP;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Clase que implementa los métodos para construir la interfaz gráfica del cliente1.
 */
public class GUI_Cliente1 extends JFrame{
    private GUI_Camara cam; // Objeto que manda llamar la clase GUI_Camara (interfaz).
    private File archivoSeleccionado; // Archivo que es seleccionado y el cual será enviado.
    private Modelo_Cliente1 cliente; // Objeto del modelo del cliente 1.
    private JTextArea Mensajeria; // Mensajes recibidos.
    private JButton activar_camara; // Botón para activar la cámara.
    private JButton enviar_archivo; // Botón para enviar un archivo.
    private JButton enviar_mensaje; // Botón para enviar un mensaje.
    private JButton seleccionar_archivo; // Botón para seleccionar un archivo de nuestra computadora.
    private JLabel latenciaDef; //donde se actualizara el valor de la latencia
    private JLabel tiempoTransDef; //donde se actualizara el valor del tiempo transcurrido
    private JLabel tiempoRestDef; //donde se actualizara el valor de tiempo restante
    private JLabel velocidadEnvioDef; ////donde se actualizara el valor de velocidad de envio
    private JTextPane textoAingresar; // Mensaje que será ingresado en el campo de texto.
    protected Controlador_Cliente1 controlador; // Objeto del controlador del cliente1.
    private Modelo_Camara camara; //Objeto del modelo para controlar la cámara.

    /**
     * Constructor de la clase. Manda llamar a un método que inicializa los componentes.
     */
    public GUI_Cliente1(){
        iniciarComponentes();
    }

    /**
     * Método para inicializar los componentes y así construir la GUI del cliente1.
     */
    private void iniciarComponentes() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        JScrollPane scrollTextoIngresado = new JScrollPane();
        textoAingresar = new JTextPane();
        seleccionar_archivo = new JButton();
        activar_camara = new JButton();
        enviar_mensaje = new JButton();
        JScrollPane scrollMensajeria = new JScrollPane();
        Mensajeria = new JTextArea();
        enviar_archivo = new JButton();
        JLabel velocidadEnvio = new JLabel();
        JLabel tiempoRest = new JLabel();
        velocidadEnvioDef = new JLabel();
        tiempoRestDef = new JLabel();
        JLabel tiempoTrans = new JLabel();
        tiempoTransDef = new JLabel();
        JLabel latencia = new JLabel();
        latenciaDef = new JLabel();

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        scrollTextoIngresado.setViewportView(textoAingresar);

        // Cargar la imagen del archivo "carpeta-archivo" ubicado en el director "/archivos/imagenes/"
        ImageIcon iconoArchivo = new ImageIcon(getClass().getResource("/archivos/imagenes/carpeta-archivo.png"));

        // Redimensionar la imagen al tamaño que ingrese (30,30)
        Image imagenRedimensionadaA = iconoArchivo.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        // Crear un nuevo ImageIcon con la imagen redimensionada
        ImageIcon iconoRedimensionadoA = new ImageIcon(imagenRedimensionadaA);

        // Asignar el nuevo ImageIcon al botón
        seleccionar_archivo.setIcon(iconoRedimensionadoA);


        // Cargar la imagen del archivo "camara-web.png" ubicado en el director "/archivos/imagenes/"
        ImageIcon iconoCamara = new ImageIcon(getClass().getResource("/archivos/imagenes/camara-de-video.png"));

        // Redimensionar la imagen al tamaño deseado
        Image imagenRedimensionada = iconoCamara.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        // Crear un nuevo ImageIcon con la imagen redimensionada
        ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);

        // Asignar el nuevo ImageIcon al botón
        activar_camara.setIcon(iconoRedimensionado);


        // Cargar la imagen del archivo "camara-de-video.png" ubicado en el director "/archivos/imagenes/"
        ImageIcon iconoEnvia = new ImageIcon(getClass().getResource("/archivos/imagenes/enviar.png"));

        // Redimensionar la imagen al tamaño deseado
        Image imagenRedimensionadaE = iconoEnvia.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        // Crear un nuevo ImageIcon con la imagen redimensionada
        ImageIcon iconoRedimensionadoE = new ImageIcon(imagenRedimensionadaE);

        // Asignar el nuevo ImageIcon al botón
        enviar_mensaje.setIcon(iconoRedimensionadoE);
        enviar_mensaje.setEnabled(false);

        // Agregar el listener al campo de texto
        textoAingresar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                validarTexoIngresado();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                validarTexoIngresado();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                validarTexoIngresado();
            }
        });

        Mensajeria.setEditable(false);
        Mensajeria.setColumns(20);
        Mensajeria.setRows(5);
        Mensajeria.setEnabled(false);
        Mensajeria.setText("");
        scrollMensajeria.setViewportView(Mensajeria);
        //modificar el tamaño del scroll
        scrollMensajeria.setPreferredSize(new java.awt.Dimension(650,50));

        enviar_archivo.setText("Enviar Archivo");
        enviar_archivo.setEnabled(false);

        velocidadEnvio.setText("Tasa de Transferencia:");

        tiempoTrans.setText("Tiempo transcurrido:");

        latencia.setText("Latencia (Tiempo Total):");

        tiempoRest.setText("Tiempo Restante:");

        velocidadEnvioDef.setText("---");

        tiempoRestDef.setText("---");

        tiempoTransDef.setText("---");

        latenciaDef.setText("---");

        agregarComponentes(tiempoTrans, latencia, velocidadEnvio, tiempoRest,scrollMensajeria, scrollTextoIngresado);

        this.setTitle("Cliente Brayan");
        this.setVisible(true);
    }

    /**
     * Método para agregar nuevos componentes a la GUI.
     * @param tiempoTrans - Tiempo transcurrido del envío.
     * @param latencia - Latencia del envío.
     * @param velocidadEnvio - Velocidad actual del envío.
     * @param tiempoRest - Tiempo restante del envío.
     * @param scrollMensajeria - Deslizar el panel de mensajes recibidos.
     * @param scrollTextoIngresado - Deslizar el panel del texto ingresado.
     */
    private void agregarComponentes(JLabel tiempoTrans, JLabel latencia, JLabel velocidadEnvio, JLabel tiempoRest,
                                    JScrollPane scrollMensajeria, JScrollPane scrollTextoIngresado) {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(tiempoTrans)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(tiempoTransDef, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(seleccionar_archivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(enviar_archivo, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(latencia)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(latenciaDef, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(velocidadEnvio)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(velocidadEnvioDef, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(tiempoRest)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(tiempoRestDef, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(scrollMensajeria)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(activar_camara, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(scrollTextoIngresado, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(enviar_mensaje, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollMensajeria)
                                        .addComponent(activar_camara, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(scrollTextoIngresado, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(enviar_mensaje, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(seleccionar_archivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(enviar_archivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(tiempoTrans)
                                                        .addComponent(tiempoTransDef))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(latencia)
                                                        .addComponent(latenciaDef))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(velocidadEnvio)
                                                        .addComponent(velocidadEnvioDef))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(tiempoRest)
                                                        .addComponent(tiempoRestDef))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addContainerGap(14, Short.MAX_VALUE))
        );
        pack();
    }

    /**
     * Método para validar si hay un mensaje ingresado dentro del TextArea.
     */
    private void validarTexoIngresado() {
        // Verificar si hay texto en el campo de texto
        if (textoAingresar.getText().trim().isEmpty()) {
            enviar_mensaje.setEnabled(false);
        } else {
            enviar_mensaje.setEnabled(true);
        }
    }

    /**
     * Método para obtener los mensajes enviados dentro del TextArea.
     * @return - Retorna los mensajes.
     */
    public JTextArea getMensajesArea() {
        return Mensajeria;
    }

    /**
     * Método SET que se encarga de modificar el valor del modelo de la cámara.
     * @param c - Nuevo valor del modelo.
     */
    public void setCamara(Modelo_Camara c){
        this.camara = c;
    }


    /**
     * Método SET que se encarga de modificar el valor del controlador del cliente1.
     * @param c - Nuevo valor.
     */
    public void setControlador(Controlador_Cliente1 c){
        this.controlador = c;
    }

    /**
     * Método SET que se encarga de modificar el valor del modelo del cliente1.
     * @param c - Nuevo valor.
     */
    public void setCliente(Modelo_Cliente1 c){
        this.cliente = c;
    }

    /**
     * Método que se encarga de modificar el área de mensajería.
     */
    public void argumentos(){
        cliente.setAreaMensajes(Mensajeria);
    }

    /**
     * Método para inicializar las acciones de los eventos de botones.
     */
    public void inicializar(){
        seleccionar_archivo.setActionCommand("SELECCIONAR");
        activar_camara.setActionCommand("ACTIVAR");
        enviar_mensaje.setActionCommand("ENVIAR");
        enviar_archivo.setActionCommand("ENVIAR_A");

        seleccionar_archivo.addActionListener(controlador);
        activar_camara.addActionListener(controlador);
        enviar_mensaje.addActionListener(controlador);
        enviar_archivo.addActionListener(controlador);
    }

    /**
     * Método de acción para el botón "Seleccionar Archivo".
     * Abre un cuadro de diálogo para seleccionar un archivo y realiza las acciones correspondientes.
     */
    public void seleccionarArchivo() {
        JFileChooser archivoElegido = new JFileChooser();

        // Mostrar el cuadro de diálogo para seleccionar un archivo
        int valor = archivoElegido.showOpenDialog(this);

        if (valor == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado
            archivoSeleccionado = archivoElegido.getSelectedFile();

            // Verificar si el archivo existe
            if (archivoSeleccionado.exists()) {
                String mensajesEnviados=Mensajeria.getText();
                mensajesEnviados+="                     Tu seleccionaste el archivo: "+archivoSeleccionado.getAbsolutePath() + "\n";
                Mensajeria.setText(mensajesEnviados);

                // Habilitar el botón "Enviar Archivo"
                enviar_archivo.setEnabled(true);
            }
        }
    }

    /**
     * Método de acción para el botón "Enviar Archivo".
     * Realiza las acciones correspondientes al enviar un archivo.
     * @throws Exception si se produce un error durante el envío del archivo.
     */
    public void enviarArchivo() throws Exception {
        if (archivoSeleccionado != null) {
            // Agregar mensaje de envío del archivo a la sección de mensajes enviados
            String mensajesEnviados=Mensajeria.getText();
            mensajesEnviados+="                     Enviando..... ";
            Mensajeria.setText(mensajesEnviados);
            Mensajeria.append("\n");

            // Configurar los componentes de la interfaz en el cliente, es decir, le mandamos lo que requiere.
            cliente.setarchivoSeleccionado(archivoSeleccionado);
            cliente.setvelocidadEnvioDef(velocidadEnvioDef);
            cliente.settiempoRestDef(tiempoRestDef);
            cliente.setlatenciaDef(latenciaDef);
            cliente.settiempoTransDef(tiempoTransDef);

            // Obtener la instancia del cliente que envía el archivo utilizando TCP
            ClienteEnviaTCP clienteEnviaTCP = cliente.getClienteEnviaTCP();
            clienteEnviaTCP.start();

            archivoSeleccionado = null;

            enviar_archivo.setEnabled(false);
        }
    }


    /**
     * Método de acción para el botón "Enviar Mensaje".
     * Obtiene el mensaje del campo de texto y realiza las acciones correspondientes.
     */
    public void enviarMensaje() {
        // Obtener el mensaje del campo de texto
        String mensaje = textoAingresar.getText();

        // Verificar si el mensaje está vacío
        if (mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tu mensaje está vacío");
            return;
        }

        // Agregar el mensaje a la sección de mensajes enviados
        String mensajesEnviados = Mensajeria.getText();
        mensajesEnviados+="Tu: " +mensaje+"\n";
        Mensajeria.setText(mensajesEnviados);

        // Limpiar el campo de texto del mensaje
        limpiarEntradaTexto();

        // Obtener la instancia del cliente que envía mensajes UDP
        ClienteEnviaUDP clienteEnviaUDP = cliente.getClienteEnviaUDP();

        // Enviar el mensaje utilizando el cliente UDP
        clienteEnviaUDP.enviarMensaje(mensaje);
        enviar_mensaje.setEnabled(false);
    }

    // se limpia en la entrada de texto
    public void limpiarEntradaTexto() {
        textoAingresar.setText("");
    }

    /**
     * Acción ejecutada al presionar el botón "Activar Cámara".
     * Crea una instancia de la clase GUI_Camara y asigna un controlador.
     * Ejecuta la cámara y muestra la interfaz gráfica correspondiente.
     */
    public void activarCamara() throws SocketException, UnknownHostException {
        cam = new GUI_Camara();
        Controlador_Camara controlador = new Controlador_Camara(cam, camara);
        camara.ejecutar(cam);
        cam.setControlador(controlador);
        cam.setTitle("Camara del -Cliente Viktoria-");
        cam.setVisible(true);
        controlador.iniciar();
    }

}
