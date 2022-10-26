package server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import server.controller.Dispatcher;

/**
 * Server launcher
 * @author stephane
 */
public class RunServer {
	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		@SuppressWarnings("unused")
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			String hostaddress = ip.getHostAddress();
			System.out.println("Host address: " + hostaddress);
			System.setProperty("java.rmi.server.hostname", hostaddress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dispatcher dispatcher = new Dispatcher();
	}
}
