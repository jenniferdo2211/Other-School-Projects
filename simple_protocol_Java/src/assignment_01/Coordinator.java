package assignment_01;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Coordinator {

	// list of all Players (no one excluded)
	private List<Player> allPlayers;

	private List<Integer> allTurns;

	// results of all duels
	// for HTTP Server/monitor
	private List<String> allDuels;

	// for synchronously editing allDuels
	private boolean editing;

	// is True if Coordinator is removing quited Player from allDuels
	private boolean removing;

	HttpServer httpServer;
	// response for HTTP server
	String response;
	boolean fileIsChanged;

	// ********************************************************************
	// CONSTRUCTORS

	// WHEN YOU ARE THE FIRST COORDINATOR
	public Coordinator(Player coor) {

		allPlayers = new ArrayList<>();
		allPlayers.add(coor);

		editing = false;
		removing = false;
		allDuels = new ArrayList<>();

		createHTTPServer();
		response = "TOURNAMENT\n\n";
		fileIsChanged = true;
	}

	// WHEN THE CURRENT COORDINATOR QUITED AND YOU ARE SELECTED TO BE NEW
	// COORDINATOR
	public Coordinator(Player p, List<Player> all, List<String> allDuels) {

		allPlayers = all;
		informAll("New Coordinator!\n" + p.toString());

		this.allDuels = allDuels;

		createHTTPServer();

		// remove all duels that do not contain any player in the list
		// in case when Coordinator quits, it did not finish deleting all duels related
		// to itself
		for (int i = 0; i < allDuels.size(); i++) {
			int numPlayers = 0;
			String duel = allDuels.get(i);
			for (int j = 0; j < allPlayers.size(); j++) {
				String player = allPlayers.get(j).toString();
				if (duel.contains(player)) {
					numPlayers++;
				}
			}
			if (numPlayers < 2)
				allDuels.remove(i);
		}

		fileIsChanged = true;
		System.out.println("YOU ARE NOW NEW COORDINATOR");
	}

	private void createHTTPServer() {
		try {
			httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
			httpServer.createContext("/", new MyHandler());
			httpServer.setExecutor(null); // creates a default executor
			httpServer.start();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	}

	class MyHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {

			if (fileIsChanged) {
				getResponseForHttpServer();
				fileIsChanged = false;
			}

			t.sendResponseHeaders(200, response.getBytes().length);
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.flush();
			os.close();
		}
	}

	private void getResponseForHttpServer() {
		response = "";

		String line;
		for (int i = 0; i < allDuels.size(); i++) {
			line = allDuels.get(i);
			response = line + "\n" + response;
		}

		response = "TOURNAMENT\n\n" + response;

	}

	// ********************************************************************

	// ADD AND REMOVE PLAYER

	// ADD A NEW PLAYER
	protected String addPlayer(Player newPlayer) {

		String message = getDataForNewPlayer();
		// message:
		// "
		// Returning allPlayers
		// IP1 Port1
		// IP2 Port2
		// IP3 Port3 ...
		// Returning allTurns
		// 010...
		// "
		// excluded the newPlayer from allPlayers

		allPlayers.add(newPlayer);
		return message;
	}

	private String getDataForNewPlayer() {

		createRandomTurns();
		String players = allPlayers.get(0).toString();
		String turns = String.valueOf(allTurns.get(0));

		if (allPlayers.size() > 1) {
			for (int i = 1; i < allPlayers.size(); i++) {
				players = players + "\n" + allPlayers.get(i).toString();
				turns = turns + String.valueOf(allTurns.get(i));
			}
		}
		String message = "Returning allPlayers\n" + players + "\nReturning allTurns\n" + turns;

		return message;
	}
	
	// create list of random turns and give it to players
		private void createRandomTurns() {
			allTurns = new ArrayList<>();
			for (int i = 0; i < allPlayers.size(); i++) {
				allTurns.add((int) (Math.random() * 10));
			}
		}

	// After new player gets all Data, inform all others about new player
	protected void informAllNewPlayer(Player newPlayer) {
		informAll("New Player JOINED!\n" + newPlayer.toString());
	}

	// ********************************************************************

	// REMOVE A PLAYER
	// So Coordinator will not send new player information about the quitted player
	protected void removePlayer(Player p) {
		allPlayers.remove(p);
	}

	// When the quitted player has already done all work
	// It is ready to quit
	// The Coordinator inform all others about quitted player
	protected void informAllQuitedPlayer(Player p) {
		informAll("A Player QUITED!\n" + p.toString());
		removeDuelsRelatedToAPlayer(p);
	}

	// remove all duels related to the quitted player
	protected synchronized void removeDuelsRelatedToAPlayer(Player player) {
		while (editing) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		editing = true;
		removing = true;

		String removedInformation = player.toString();
		String line;

		for (int i = 0; i < allDuels.size(); i++) {
			line = allDuels.get(i);
			if (line.contains(removedInformation))
				allDuels.remove(line);
		}

		editing = false;
		removing = false;
		fileIsChanged = true;
		notifyAll();
	}

	// --------------------------------------------------------------------
	// INFORM ALL PLAYERS
		private void informAll(String message) {
			for (Player p : allPlayers) {
				try {
					Socket socket = new Socket(p.getIP(), p.getPort());
					PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
					pw.println(message);
					socket.close();
				} catch (IOException e) {
					continue;
				}
			}
		}
	
	// ********************************************************************

	// when each duel is finished
	// add result to list allDuels (the list of Results of all duels)
	protected synchronized void addToAllDuels(String result) {
		while (editing) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		editing = true;

		allDuels.add(result);
		fileIsChanged = true;

		editing = false;
		notifyAll();
	}

	// ********************************************************************
	// WHEN THE CURRENT COORDINATOR WANTS TO QUIT
	// SELECT A NEW ONE
	
	protected synchronized void selectNewCoordinator() {
		while (editing || removing) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		editing = true;

		while (true) {
			try {
				Player newCoor = allPlayers.get(0);
				// inform the player that he is the new Coordinator
				// the dedicated port for receiving this message is (65000 - port)
				Socket coorSocket = new Socket(newCoor.getIP(), 65000 - newCoor.getPort());
				PrintWriter pw = new PrintWriter(coorSocket.getOutputStream(), true);

				pw.println("You are the new Coordinator");
				pw.print(getDataForNewCoordinator());

				pw.close();
				coorSocket.close();

				editing = false;
				this.httpServer.stop(1);
				return;

			} catch (IOException e) {
				// if cannot connect to the new selected Coordinator, we will sleep for 1 second
				// and then retry to connect
				// probably the selected new Coordinator is quitting and its Server has been
				// closed
				e.printStackTrace();
				System.err.println("Cannot connect to new selected Coordinator");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					System.err.println("InterruptedException - Thread cannot sleep");
				}
				continue;
			}
		}

	}

	// transfer information of allDuels to a String
	private String getDataForNewCoordinator() {

		String message = "";
		String line;

		for (int i = 0; i < allDuels.size(); i++) {
			line = allDuels.get(i);
			message = line + "\n" + message;
		}

		return message;
	}

}
