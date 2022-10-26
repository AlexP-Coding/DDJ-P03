package pt.tecnico.sockets;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class SocketClient {

	public static void main(String[] args) throws IOException {
		// Check arguments
		if (args.length < 3) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s host port text%n", SocketClient.class.getName());
			return;
		}

		// First argument is the server host name
		final String host = args[0];
		// Second argument is the server port
		// Convert port from String to int
		final int port = Integer.parseInt(args[1]);

		// !! Make "date" argument to send to server
		Date date = new java.util.Date();
		// Concatenate following arguments using a string builder
		StringBuilder sb = new StringBuilder();
		for (int i = 2; i < args.length; i++) {
			sb.append(args[i]);
			if (i < args.length - 1) {
				sb.append(" ");
			}
		}
		sb.append(" " + date);
		final String text = sb.toString();

		// Create client socket
		Socket socket = new Socket(host, port);
		System.out.printf("Connected to server %s on port %d %n", host, port);

		// Create stream to send data to server
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		// !!Create buffered stream to receive data from server, one line at a time
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Send text to server as bytes
		out.writeBytes(text);
		out.writeBytes("\n");
		System.out.println("Sent text: " + text);

		// !! Receive server response
		String nrResponse;
		while (true) {
			// Read a line of text.
			// A line ends with a line feed ('\n').
			nrResponse = in.readLine();
			if (nrResponse != null) {
				System.out.printf("Received message with content: '%s'%n", nrResponse);
				break;
			}
		}

		// Close client socket
		socket.close();
		System.out.println("Connection closed");
	}

}
