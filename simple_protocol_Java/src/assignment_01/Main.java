package assignment_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

	// example:
	// first player: localhost 1234 localhost 1234
	// second player: localhost 1276 localhost 1234

	public static void main(String[] args) {

		System.out.println("WELCOME TO THE GAME");
		System.out.println("---------------------------------\n");

		// CREATING A NEW PEER
		System.out.println("Enter IP - Port and Introducing IP - Introducing Port");
		System.out.println("Do not enter Port 8000 (HTTP Server)");
		System.out.println("For example: localhost 1234 localhost 1234\n");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Peer peer = null;
		ServerSocket server = null;
		int port;
		Socket introducingSocket = null;

		// Take the input and create a new player
		while (true) {
			try {
				String[] input = in.readLine().trim().split(" ");
				InetAddress IP = InetAddress.getByName(input[0]);
				port = Integer.parseInt(input[1]);
				InetAddress IIP = InetAddress.getByName(input[2]);
				int IPort = Integer.parseInt(input[3]);

				if (port == 8000 || IPort == 8000) {
					System.err.println("Enter another Port, not 8000");
					continue;
				}
				// try to create a server on this port
				// if OK, close it
				// we will open it again when we need
				try {
					server = new ServerSocket(port);
				} catch (IOException e) {
					System.err.println("Your IP and port has been used. Try another");
					continue;
				}
				server.close();

				// try to create connection to the Introducing
				// if OK, close the Socket
				// we will open again when we need
				if (!IP.equals(IIP) && port != IPort) {
					try {
						introducingSocket = new Socket(IIP, IPort);
					} catch (IOException e) {
						System.err.println("The Introducing IP and Port is incorrect. Try again");
						continue;
					}
					introducingSocket.close();
				}
				
				peer = new Peer(IP, port, IIP, IPort);

				System.out.println("Successfully created a PEER " + peer + "\n");
				break;

			} catch (UnknownHostException e) {
				System.err.println("Unknown host. Try again");
				continue;
			} catch (NumberFormatException e) {
				System.err.println("Number is not accepted. Try again");
				continue;
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("Input format is incorrect. Try again");
				continue;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		//--------------------------------------
		
		// FOR ACCEPTING CONNECTION FROM OTHERS
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// open server to listen to others
		new ReceiveMessageThread(peer, server).start();
		// --------------------
		
		// ----------------------------------------------
		// JOIN THE TOURNAMENT
		peer.join();

		// thread for checking if this Player is the selected new Coordinator or not
		// if you are already the Coordinator, do not need this thread
		if (!peer.isCoordinator()) {
			new NewCoordinatorThread(peer).start();
		}

		// wait for process to finish
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		// start play with all Duels
		peer.playAllDuels();

		// listen to the keyboard if "QUIT" is entered
		while (true) {
			String line;
			try {
				while ((line = in.readLine()) != null) {
					if (line.trim().equalsIgnoreCase("QUIT")) {
						// System.out.println("Attempt to close the program");
						peer.quit();
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
