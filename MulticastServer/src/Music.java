import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;


public class Music {
    String nome;
    String artist;

    Music(String nome,String artist){
        this.nome=nome;
        this.artist=artist;
    }

    @Override
    public String toString() {
        return "Nome : "+this.nome+" Artista : "+this.artist;
    }
}
