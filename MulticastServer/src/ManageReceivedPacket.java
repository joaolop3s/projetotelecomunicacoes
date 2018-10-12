import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;
import java.util.List;

public class ManageReceivedPacket extends Thread {
    private MulticastSocket socket;
    private String address;
    private int port;
    private String message;
    private CopyOnWriteArrayList<User> users;


    public ManageReceivedPacket(MulticastSocket socket, String address, int port, String message, CopyOnWriteArrayList<User> users) {
        this.socket = socket;
        this.address = address;
        this.port = port;
        this.message = message;
        this.users = users;


        User user1 = new User("tintin", "unicorn", true);
        users.add(user1);


        this.start();
    }

    public CopyOnWriteArrayList<User> getUsers() {
        return users;
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
                        message = login(parts[3], parts[5], message);
                        break;
                    case "signUp":
                        System.out.println("Registando user");
                        // verificar se o input contem carateres ilegais
                        // se sim, pedir input novamente
                        signUp(parts[3], parts[5], users);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operation: " + toDo);
                }
                message = message.replaceAll("\\bs\\b", "r");
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(this.address), this.port);
                //System.out.println("mensagem enviada: " + message);
                socket.send(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //System.out.println("Ignorar");
        }
    }


    /********** LOGIN ***********/

    public String login(String username, String password, String message) {

        // ver à bd
        ArrayList<String> users = new ArrayList<>();
        users.add("tintin");

        User getUser = verifyUsernameDataBase(getUsers(), username);
        if (getUser != null) {
            // getPassoword
            if (getUser.getPassword().equals(password)) {
                System.out.println("username e pass corretas");
                // mandar ao RMI server que está tudo ok
                // não altera a message
            } else {
                // mandar ao RMI server que a pass está incorreta
                message = message.replaceAll(password, "null");
                System.out.println("pass incorreta");

            }
        } else {
            // retornar que não está na BD
            System.out.println("user nao existe");
            message = message.replaceAll(username, "null");
            message = message.replaceAll(password, "null");
        }
        return message;
    }





    public User verifyUsernameDataBase(CopyOnWriteArrayList<User> users, String username) {
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



    /*****************************/


    /********** SIGN UP **********/

    public void signUp(String username, String password, CopyOnWriteArrayList<User> users) {
        // adiciona à BD
        User user;

        if (users.isEmpty()) {
            user = new User(username, password, true);
        } else {
            user = new User(username, password, false);
        }
        users.add(user);

    }

    /*****************************/
 }

