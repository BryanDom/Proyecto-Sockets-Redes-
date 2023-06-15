package cliente.udp;

import java.net.*;
import java.io.*;
 
//declaramos la clase udp
public class ClienteUDP{
    /*protected final int PUERTO_SERVER;
    protected final String SERVER;


    //define una variable para dirección ip o host y puerto.
    public ClienteUDP(String servidor, int puertoS){
        PUERTO_SERVER=puertoS;
        SERVER=servidor;
    }

    //redireccionar el error
    public void inicia()throws Exception{
        //define un solo socket de tipo DatagramSocket, utilizando UDP.
        DatagramSocket socket=new DatagramSocket();

        //el cliente solo recibe y envia datagramas

        //enviar mensajes y escuchar mensajes UDP
        //enviar y recibi y los dos reciben los mismos socket, el punto de comunicación
        ClienteEscuchaUDP clienteEnvUDP=new ClienteEscuchaUDP(socket);
        ClienteEnviaUDP clienteEscUDP=new ClienteEnviaUDP(socket, SERVER, PUERTO_SERVER);


        //inicio los dos hilos.
        clienteEnvUDP.start();
        clienteEscUDP.start();
    }*/
}
