package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KnockingClient extends Thread {

	private String[] arguments;
	private DatagramSocket toServer;
	private InetAddress IPserver;
	
	public KnockingClient(String[] arguments) {
		this.arguments = arguments;
		
		// check the number of arguments, it should be at least 2 (the IP of server and UDP ports)
		if (arguments.length < 2) {
			System.err.println("Parameters are not enough.\nPlease pass in IP server and sequence of UDP ports");
			System.exit(1);
		}

		// check if the IP is OK
		String address = arguments[0].trim();
		try {
			Pattern pattern = Pattern
					.compile("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
			Matcher matcher = pattern.matcher(address);

			if (matcher.find()) {
				IPserver = InetAddress.getByName(matcher.group());
			} else {
				IPserver = InetAddress.getByName(address);
			}
		} catch (UnknownHostException e) {
			System.err.println("The IP has incorrect format\nPlease enter a new one");
			System.exit(1);
		}

		// open an UDP socket at a random port
		//int rand = new Random().nextInt(65535 - 1025 + 1) + 1025;
		try {
			toServer = new DatagramSocket();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void run() {
		int port = 0;
		
		// knocking all UDP ports one by one
		for (int i = 1; i < arguments.length; i++) {
			try {
				port = Integer.valueOf(arguments[i]);
				if (port <= 1024) {
					System.err.println("Invalid parameter " + i + "\nShould be greater 1024");
					System.exit(1);
				}
			} catch (NumberFormatException e) {
				System.err.println("Invalid parameter " + i + "\nNot an integer");
				System.exit(1);
			}

			try {
				System.out.println("Knocking UDP port: " + port);
				byte[] sendData = new byte[1024];
				int n = new Random().nextInt();
				sendData = ByteBuffer.allocate(4).putInt(n).array();
				DatagramPacket packet = new DatagramPacket(sendData, sendData.length, IPserver, port);

				toServer.send(packet);
				
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// receive an UDP packet about the TCP port if successfully knocked 
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		
		// set timeout
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			    terminate();			    
			  }
			}, (int)10000);
		
		// wait for UDP packet from server about the TCP port
		try {
			toServer.receive(receivePacket);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int tcpPort = ByteBuffer.wrap(receiveData).getInt();

		// get the TCP port and send a TCP request
		if (tcpPort > 1024) {
			System.out.println("\nSuccessfully knocked ports\nConnecting to TCP port " + tcpPort);
			String randString = randomString();
			tcpConnectionWithServer(IPserver, tcpPort, randString);
		} else {
			System.err.print("Error with TCP port. Should be greater than 1024");
			System.err.println("Error with transmission");
			System.exit(1);
		}
	
	}

	private void tcpConnectionWithServer(InetAddress IPserver, int tcpPort, String randString) {
		// Connect to TCP port
		// send request and wait for response
		try {
			Socket socket = new Socket(IPserver, tcpPort);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);

			System.out.println("TCP Request: " + randString);
			pr.println(randString);
			pr.flush();

			String line;
			while ((line = br.readLine()) != null) {
				System.out.println("Response from Server: " + line);
				break;
			}

			pr.close();
			br.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nClose this client");
		System.exit(0);
	}

	private String randomString() {
		// create random string for request
		final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String lower = upper.toLowerCase();
		final String digits = "0123456789";
		final String alphanum = upper + lower + digits;
		final int alphaLength = alphanum.length();

		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		int length = rand.nextInt(20) + 1;
		while (length-- > 0) {
			sb.append(alphanum.charAt(rand.nextInt(alphaLength)));
		}

		return sb.toString();
	}

	private void terminate() {
		// terminate when time out
		System.err.println("\nOH NOOOOOOOOOOO");
		System.err.println("Time out -> Terminate");
		System.err.println("Knocked port sequence is not correct or UDP packet has been lost!");
		System.out.println("\nPLEASE TRY THE PROGRAM AGAIN");
		toServer.close();
		System.exit(1);
	}
	
	public static void main(String[] args) throws SocketTimeoutException {
		// start the program
		new KnockingClient(args).start();;
	}
	
}
