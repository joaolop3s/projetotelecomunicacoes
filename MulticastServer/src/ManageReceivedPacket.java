import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

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
                switch (toDo) {
                    case "login":
                        System.out.println("é para fazer login");
                        message = login(parts[3], parts[5], message);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operation: "+toDo);
                }

                message = message.replaceAll("\\bs\\b", "r");
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(this.address), this.port);
                System.out.println("mensagem enviada: " + message);
                socket.send(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //System.out.println("Ignorar");
        }
    }

    public String login(String username, String password, String message) {
        // ver à bd
        ArrayList<User> users = new ArrayList<>();
        User user1 = new User("tintin", "unicorn", true);
        users.add(user1);

        User getUser = verifyUsernameDataBase(users, username);
        if (getUser != null) {
            // getPassoword
            if (getUser.getPassword().equals(password)) {
                System.out.println("username e pass corretas");
                // mandar ao RMI server que está tudo ok
                // não altera a message
            }
            else {
                // mandar ao RMI server que a pass está incorreta
                message = message.replaceAll(password, "null");
                System.out.println("pass incorreta");
                return message;
            }
        }
        else {
            // retornar que não está na BD
            System.out.println("user nao existe");
            message = message.replaceAll(username, "null");
            message = message.replaceAll(password, "null");
            return message;
        }

        return message;
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
}

