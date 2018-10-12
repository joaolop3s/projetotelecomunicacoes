import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;


public class Album {
    String nome;
    String artist;


    Album(String nome,String artist){
        this.nome=nome;
        this.artist=artist;
    }
}
