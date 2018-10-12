import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;
import java.util.List;

public class ManageReceivedPacket extends Thread{
    MulticastSocket socket;
    String address;
    int port;
    String message;

    public ManageReceivedPacket(MulticastSocket socket, String address, int port, String message) {
        this.socket = socket;
        this.address = address;
        this.port = port;
        this.message = message;

        this.start();
    }

    public void run() {

        message = message.replaceAll(" ", "");
        String[] parts = message.split("[|;]");



        if (parts[6].equals("s")) {
            try {
                String toDo = parts[1];
                System.out.println(toDo);
                switch (toDo) {
                    case "login":
                        System.out.println("é para fazer login");
                        login(parts[2], parts[4]);
                        break;
                    case "InsertMusic":
                        adicionamusica(parts[3],parts[5]);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operation: "+toDo);
                }
                message = message.replaceAll("\\bs\\b", "r");
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(this.address), this.port);
                //System.out.println("mensagem enviada: " + message);
                socket.send(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //System.out.println("Ignorar");
        }
    }

    public void login(String username, String password) {
        // ver à bd
        ArrayList<String> users = new ArrayList<>();
        users.add("tintin");


    }

    public User verifyUsernameDataBase(ArrayList<User> users, String username) {
        for (User u :users) {
            System.out.println(u.getName() +" = "+username);
            if (u.getName().equals(username)) {
                return u;
            }
        }
        return null;
    }




    public void adicionamusica(String nome,String artista){
        List<Music> musicas = new ArrayList<>();
        Music musica = new Music(nome,artista);
        musicas.add(musica);

        Iterator<Music> it = musicas.iterator();

        while(it.hasNext()){
            System.out.println("list is:"+musicas);
            Music str = it.next();

        }
    }

}

