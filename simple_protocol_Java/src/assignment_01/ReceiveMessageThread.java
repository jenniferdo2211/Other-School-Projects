package assignment_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiveMessageThread extends Thread {

	Peer peer;
	private final Pattern pattern;
	ServerSocket server;

	public ReceiveMessageThread(Peer p, ServerSocket server) {
		peer = p;
		pattern = Pattern
				.compile("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
		this.server = server;
	}

	public void run() {
		Socket client = null;
		
		// wait for other sockets connecting
		while (true) {
			
			try {
				// for each connection

				client = server.accept();

				BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
				String line;

				while ((line = br.readLine()) != null) {

					switch (line) {
					case "JOIN":
						// when you are the Introducing Player of someone else
						// he joins and wants to ask about Coordinator connection

						Player coor = peer.getCoorConnection();
						pw.println(coor.toString());
						break;

					case "I am NEW":
						// when you are the Coordinator
						// new Player asked you to add him to the list

						line = br.readLine();
						Player p = transferStringToPlayer(line);
						String message = peer.addPlayer(p);
						pw.println(message);
						break;

					case "Please inform others about me":
						// when you are the Coordinator
						// new Player has got all data
						// he asked you to inform all other players
						
						line = br.readLine();
						Player p2 = transferStringToPlayer(line);
						peer.informAllNewPlayer(p2);
						break;
						
					case "New Player JOINED!":
						// when you are just a Player
						// and the Coordinator telling that we have a new
						line = br.readLine();
						
						// Coordinator inform that we have a new Player
						// Add him to the list
						Player newPlayer = transferStringToPlayer(line);
						peer.addNewPlayer(newPlayer);
						break;

					case "Want to play?":
						// when you are just a Normal Player
						// and an opponent wants to make a duel with you
						int turn = 0;

						// you receive information about the opponent and the turn (who starts first)
						// turn % 2 == 0 - you starts first
						// turn % 2 == 1 - the opponent start first
						line = br.readLine();
						Player opponent = transferStringToPlayer(line);
						
						while ((line = br.readLine()) != null) {
							turn = Integer.parseInt(line);
							System.out.println("NEW DUEL from " + opponent);
							break;
						}

						// you accept the duel and start to play
						peer.startTheDuel(br, pw, opponent, turn);

						// no matter how the duel is (whether you and the opponent agree about the
						// winner or not)
						// we will close and accept to new client
						// the opponent will start the duel again (not you)
						break;
						
					case "Result":
						//when you are the Coor
						// someone tell you to the result of the duel
						line = br.readLine();
						peer.addToAllDuels(line);
						break;
					
					case "Remove me":
						// when you are the coordinator
						// someone tells you to remove him from the list of allPlayers of the
						// Coordinator
						// so he will not be included in any duel any more
						line = br.readLine();
						Player removedPlayer = transferStringToPlayer(line);
						peer.removePlayer(removedPlayer);
						break;

					case "QUIT":
						// when you are the coordinator
						// someone tells you to remove him from the list of allPlayers of the
						// Coordinator
						line = br.readLine();
						Player quitedPlayer = transferStringToPlayer(line);
						peer.informAllQuitedPlayer(quitedPlayer);
						break;

					case "A Player QUITED!":
						// when the coordinator tell you that a player quitted
						line = br.readLine();
						Player quitedPlayer2 = transferStringToPlayer(line);
						peer.removeQuitedPlayer(quitedPlayer2);
						
						break;

					case "New Coordinator!":
						// when you are a normal player
						// there is a selected new player
						// you change the coorConnection
						line = br.readLine();
						Player newCoor = transferStringToPlayer(line);
						peer.setCoorConnection(newCoor);
						break;

					// we will not handle any other cases
					default:
						break;
					}

					break;
					// break and close all
					// wait for new client
				}

				br.close();
				pw.close();
				client.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private Player transferStringToPlayer(String line) {
		String[] inputs = line.trim().split(" ");
		Matcher matcher = pattern.matcher(inputs[0]);
		if (matcher.find()) {
			InetAddress IP;
			try {
				IP = InetAddress.getByName(matcher.group());
				int port = Integer.valueOf(inputs[1]);
				return new Player(IP, port);
			} catch (UnknownHostException e) {
				System.err.println("Unknown host");
			}
		}
		return null;
	}

}
