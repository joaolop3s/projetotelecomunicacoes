//package hello2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_ServerInterface extends Remote {
	public String sayHello() throws RemoteException;
	public void sendMessage(String message) throws RemoteException;
	public String receiveMessage() throws RemoteException;
}