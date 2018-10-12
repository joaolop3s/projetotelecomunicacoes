//package hello2;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class RMI_Client {

	public static void main(String args[]) {

		/* This might be necessary if you ever need to download classes:
		System.getProperties().put("java.security.policy", "policy.all");
		System.setSecurityManager(new RMISecurityManager());
		*/

		Scanner sc = new Scanner(System.in);
		String input;

		try {

			RMI_ServerInterface serverInterface = (RMI_ServerInterface) LocateRegistry.getRegistry(7001).lookup("benfica");
			String message = serverInterface.sayHello();
			System.out.println("RMI_Server: " + message);

			// envia mensagem para o RMI que vai enviar para a nuvem
/*
				serverInterface.sendMessage("tiago");

				message = serverInterface.receiveMessage();
				System.out.println("RMI_Server: " + message);*/

				//while (true) {
					System.out.print(">> ");
					//input = sc.nextLine();
					input = "type | InsertMusic; name | Toda a noite; artist | Toy";
					serverInterface.sendMessage(input);

					message = serverInterface.receiveMessage();
					System.out.println("RMI_Server: " + message);
				//}


		} catch (Exception e) {
			System.out.println("Exception in main: " + e);
			e.printStackTrace();
		}
	}

	public void menu() {
		System.out.println("Login:");
	}
}