package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;

public class KnockingServer extends Thread {

	private static Map<ClientInfo, List<Integer>> newComingClients = new WeakHashMap<>();
	private static List<Integer> keys = new ArrayList<>();

	public synchronized static void addClient(ClientInfo client, int port) {

		if (!newComingClients.containsKey(client)) {
			// if this client has knocked for the first time
			newComingClients.put(client, new ArrayList<>());
		}
		
		List<Integer> portList = newComingClients.get(client);
		portList.add(port);

		if (portList.size() < keys.size()) {
			if (!portList.equals(keys.subList(0, portList.size()))) {
				newComingClients.remove(client);
			}
		} else if (portList.equals(keys)) {
			
				System.out
						.println("\nClient " + client.getIP() + " - " + client.getPort() + " has successfully knocked");

				boolean check = false;
				int p = 0;
				
				while (!check) {
					p = new Random().nextInt(65535 - 1025 + 1) + 1025;
					// open a random TCP port
					TcpConnection t = new TcpConnection(p);
					t.start();
					// send an UDP message to client about this TCP port
					while (!t.checkIsReady()) {}
					check = t.getCheck();
				}
				sendUdpMessage(client, p);
				
			newComingClients.remove(client);
		} else {
			newComingClients.remove(client);
		}

	}

	private static void sendUdpMessage(ClientInfo client, int p) {
		// send an UDP message to client about the TCP port number
		try {
			DatagramSocket toClient = new DatagramSocket();
			byte[] sendData = new byte[1024];
			sendData = ByteBuffer.allocate(4).putInt(p).array();

			DatagramPacket packet = new DatagramPacket(sendData, sendData.length, client.getIP(), client.getPort());

			toClient.send(packet);
			toClient.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// check if there is at least one UDP port passed as argument
		if (args.length == 0) {
			System.err
					.println("There is no argument.\nPlease run the program again and pass UDP port numbers as arguments");
			System.exit(1);
		}

		// check if UDP ports are integers that is greater than 1024
		// save UDP sequence as list "keys"
		for (String s : args) {
			try {
				int port = 0;
				port = Integer.valueOf(s);
				if (port <= 1024) {
					System.err.println("Invalid parameter " + s + "\nShould be greater 1024");
					System.exit(1);
				}
				keys.add(new Integer(port));
			} catch (NumberFormatException e) {
				System.err.println("Invalid parameter " + s + "\nNot an integer");
				System.exit(1);
			}
		}

		// list of UDP ports that will be opened 
		Set<Integer> ports = new HashSet<>(keys);

		// Print sequence of UDP ports in correct order
		System.out.println("KEYS: " + keys + "\n");
		
		// open UDP ports
		for (Integer p : ports) {
			new UdpReceiveThread(p).start();
		}

	}
}
