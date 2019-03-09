package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpReceiveThread extends Thread {

	int port;

	public UdpReceiveThread(Integer p) {
		port = p;
	}

	public void run() {
		try {
			///////////////////////////////
			System.out.println("Opening UDP port " + port);
			
			DatagramSocket server = new DatagramSocket(port);
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			while (true) {
				try {
					server.receive(receivePacket);
					ClientInfo client = new ClientInfo(receivePacket.getAddress(), receivePacket.getPort());
					
					// add this client to the list of newComingClients
					KnockingServer.addClient(client, port);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			System.err.println("Cannot open UDP port " + port);
			System.exit(1);
			e.printStackTrace();
		}

	}
}
