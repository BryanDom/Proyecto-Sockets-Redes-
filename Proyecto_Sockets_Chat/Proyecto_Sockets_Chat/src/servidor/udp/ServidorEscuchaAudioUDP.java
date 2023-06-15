package servidor.udp;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServidorEscuchaAudioUDP extends Thread{
    private DatagramSocket socket;
    private boolean activo;
    private DataLine.Info data_line;
    private AudioFormat formato_audio;
    private SourceDataLine bocinas_audio;
    private DatagramPacket paquete;
    private byte[] datos;
    public ServidorEscuchaAudioUDP(int puerto) throws SocketException {
        socket=new DatagramSocket(puerto);
        activo=true;
        formato_audio = new AudioFormat(48000.0f, 16, 2, true, false);
        data_line = new DataLine.Info(SourceDataLine.class, formato_audio);
        datos=new byte[64000];
    }

    @Override
    public void run(){
        try{
            bocinas_audio= (SourceDataLine) AudioSystem.getLine(data_line);
            bocinas_audio.open(formato_audio);
            bocinas_audio.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            paquete=new DatagramPacket(datos,datos.length);
            while(activo){
                socket.receive(paquete);
                String mensaje = new String(paquete.getData(),0,paquete.getLength()).trim();
                if(mensaje.equalsIgnoreCase("-1")){
                    bocinas_audio.drain();
                }else {
                    capturar_el_audio(paquete.getData());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void capturar_el_audio(byte soundbytes[]) {
        try
        {
            bocinas_audio.write(soundbytes, 0, soundbytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
