//package hello2;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

// do multicast
import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.IOException;


public class RMI_Server extends UnicastRemoteObject implements RMI_ServerInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ENTRAR NA NUVEM
    static String MULTICAST_ADDRESS = "224.0.224.0";
	static int PORT = 4321;
	//

	public RMI_Server() throws RemoteException {
		super();
	}

	public String sayHello() throws RemoteException {
		System.out.println("print do lado do servidor...!.");

		return "HELLOOO, World!";
	}

	public void sendMessage(String message) throws RemoteException {
		// envia mensagem para a nuvem
		// mandar datagram para a nuvem
		MulticastSocket socket = null;

		try {
			socket = new MulticastSocket();  // create socket without binding it (only for sending)
			message += " | s";
			System.out.println(">> "+message+" enviada");



			byte[] buffer = message.getBytes();
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);


			socket.send(packet);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}
	}

	public String receiveMessage() {
		MulticastSocket socket = null;
		try {
			// estar sempre a ler
			socket = new MulticastSocket(PORT);  // create socket and bind it
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			socket.joinGroup(group);
			byte[] buffer = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			socket.receive(packet);
			String message = new String(packet.getData(), 0, packet.getLength());
			System.out.println("<< "+message+" recebida");

			// tratar mensagem
			message = message.replaceAll(" ", "");
			String[] parts = message.split("[|;]");

			String toDo = parts[1];
			switch (toDo) {
				case "login":
					message = login(parts[3], parts[5]);
					break;
				default:
					throw new IllegalArgumentException("Invalid operation: "+toDo);
			}


			return message;

		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}
		return null;
	}

	public String login(String username, String password) {
		String message;

		if (username.equals("null") && password.equals("null")) {
			message = "User e Password não encontrados\ncriar conta...";
		} else if (password.equals("null")) {
			message = "Password incorreta\nInsira novamente...";
		}
		else {
			message = "Logged in";
		}

		return message;
	}





	// =========================================================
	public static void main(String args[]) {

		try {
			RMI_Server h = new RMI_Server();
			Registry r = LocateRegistry.createRegistry(7001);
			r.rebind("benfica", h);
			System.out.println("RMI_Server Server ready.");



		} catch (RemoteException re) {
			System.out.println("Exception in HelloImpl.main: " + re);
		}
	}

}