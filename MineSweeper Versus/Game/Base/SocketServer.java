package pt.tecnico.sockets;

import java.io.*;
import java.net.*;

public class SocketServer {

	public static void main(String[] args) throws IOException {
		// Check arguments
		if (args.length < 1) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s port%n", SocketServer.class.getName());
			return;
		}

		// Convert port from String to int
		final int port = Integer.parseInt(args[0]);

		// Create server socket
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.printf("Server accepting connections on port %d %n", port);

		// wait for and then accept client connection
		// a socket is created to handle the created connection
		Socket clientSocket = serverSocket.accept();
		final String clientAddress = clientSocket.getInetAddress().getHostAddress();
		final int clientPort = clientSocket.getPort();
		System.out.printf("Connected to client %s on port %d %n", clientAddress, clientPort);

		// Create buffered stream to receive data from client, one line at a time
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		// !!Create stream to send data to client
		DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

		// Receive data until client closes the connection
		String response;
		while (true) {
			// Read a line of text.
			// A line ends with a line feed ('\n').
			response = in.readLine();
			if (response == null) {
				break;
			}
			System.out.printf("Received message with content: '%s'%n", response);
			break;
		}

		// !! Read and update number to send
		String[] sendParts = response.split(" ");
		int nrResponse = Integer.parseInt(sendParts[1]) + 1;
		// !! Send response to client
		out.writeBytes("" + nrResponse);
		System.out.println("Sent text: " + nrResponse);

		// Close connection to current client
		clientSocket.close();
		System.out.println("Closed connection with client");

		// Close server socket
		serverSocket.close();
		System.out.println("Closed server socket");
	}

}
