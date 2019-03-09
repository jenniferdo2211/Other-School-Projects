package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpConnection extends Thread {

	private int port;
	private boolean check;
	private boolean checkIsReady;
	
	public TcpConnection(int port) {
		this.port = port;
		checkIsReady = false;
	}

	public void run() {
		try {
			System.out.println("Listening on TCP port: " + port);
			ServerSocket server = new ServerSocket(port);
			check = true;
			checkIsReady = true;
			
			while (true) {
				Socket client = server.accept();
				System.out.println("TCP Port accepted the client");
				BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter pr = new PrintWriter(client.getOutputStream(), true);
				
				// simple request - response
				// uppercase all letters
				String line;
				while ((line = br.readLine()) != null) {
					System.out.println("Receive a request on TCP Port: " + line);
					line = line.toUpperCase();
					break;
				}
				
				System.out.println("Responding: " + line);
				pr.println(line);
				pr.flush();
				
				br.close();
				pr.close();
				break;
			}
			server.close();
		} catch (IOException e) {
			System.err.println("Cannot open TCP port " + port);
			check = false;
			checkIsReady = true;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	public boolean checkIsReady() {
		return checkIsReady;
	}
	
	public boolean getCheck() {
		return check;
	}

}
