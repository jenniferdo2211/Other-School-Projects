package assignment_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Peer {

	// each Player instance is a pair of IP and Port
	private Player player;
	private Player introducing;
	private Player coorConnection;

	// if you are the Coordinator of the tournament, then this value will be
	// initiated
	// otherwise, it will not be
	private Coordinator coordinator;

	// list of all players that you will make duels with (exclude yourself)
	private List<Player> allPlayers;

	// list of all turns for all duels (correspond to list of all Players)
	// if (turn % 2) == 0 -> start from you
	// if (turn % 2) == 1 -> start from opponent
	// this list gets from Coordinator
	private List<Integer> allTurns;

	// a list to store all winners of all duels
	private List<Player> allWinners;

	// pattern to recognize IP address
	private final Pattern pattern;

	// if state == true, it means when the Coordinator quits and he tells
	// you that you are the new Coordinator
	// if you want to quit, you need to select someone to be the new Coordinator
	// before quitting
	private boolean state;

	// when quitting is true, it means this player is waiting to quit
	// so it will not add newPlayer to its list when the Coordinator inform about
	// newPlayer
	private boolean quitting;

	// Number of duels this player has attended
	// and then compare with number of players in the list
	// if they are not equal, you are still playing with someone
	// probably a new player is playing with you
	// For checking if you already played with all players attended the tournament
	// after you
	private int duelCounter;

	public Peer(InetAddress IP, int port, InetAddress IIP, int IPort) {
		this.player = new Player(IP, port);
		this.introducing = new Player(IIP, IPort);
		allPlayers = new ArrayList<>();
		allTurns = new ArrayList<>();
		allWinners = new ArrayList<>();
		state = false;
		quitting = false;
		duelCounter = 0;

		pattern = Pattern
				.compile("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
	}

	// ********************************************************************
	// PHASE 1: JOIN AND GET DATA FROM COORDINATOR

	protected void join() {
		System.out.println("-------------------------------------");
		System.out.println("JOINING");

		// if player is the first player
		if (player.equals(introducing)) {
			System.out.println("First player!\n");
			coorConnection = player;
			coordinator = new Coordinator(player);

		} else {
			// if not the first player
			while (true) {
				try {
					Socket introducingSocket = new Socket(introducing.getIP(), introducing.getPort());
					// Connect to the Introducing Player
					// GET Coordinator from Introducing
					PrintWriter pw = new PrintWriter(introducingSocket.getOutputStream(), true);
					pw.println("JOIN");

					// receive the coordinator IP and Port from Coordinator
					BufferedReader br = new BufferedReader(new InputStreamReader(introducingSocket.getInputStream()));
					String line;
					while ((line = br.readLine()) != null) {
						coorConnection = transferStringToPlayer(line);
						break;
					}

					// when already got coorConnection from Introducing Player,
					// no need to connect to Introducing Player any more
					// disconnect
					br.close();
					pw.close();
					introducingSocket.close();

					// After getting the coorConnection, connect to Coordinator
					// Say I am NEW and get Data from Coordinator (allPlayers and allTurns)
					Socket coorSocket = new Socket(coorConnection.getIP(), coorConnection.getPort());
					// if it failed to connect to Coordinator, IOException is catched, try again

					tellCoordinatorAddNewPlayer(coorSocket);
					break;

				} catch (IOException e) {
					continue;
				}
			}
		}
	}

	// tell the coordinator that I am a new Player
	// Coordinator will give data to new Player
	// Data: List<Player> allPlayers && List<Integer> allTurns
	private void tellCoordinatorAddNewPlayer(Socket coorSocket) {

		try {

			PrintWriter pw = new PrintWriter(coorSocket.getOutputStream(), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(coorSocket.getInputStream()));

			// Send to Coordinator that I am NEW
			// Coordinator will add this player to the list and inform all other players
			// Then Coordinator will give data back to new Player
			pw.println("I am NEW\n" + player.toString());

			String line;
			// The Coordinator sends something like:
			// "
			// Returning allPlayers
			// IP1 Port1 IP2 Port2 IP3 Port3 ...
			// Returning allTurns
			// 010...
			// "
			while ((line = br.readLine()) != null) {
				if (line.equals("Returning allPlayers")) {
					while ((line = br.readLine()) != null) {
						if (line.equals("Returning allTurns")) {
							break;
						}
						Player newPlayer = transferStringToPlayer(line);
						allPlayers.add(newPlayer);
					}
				}

				while ((line = br.readLine()) != null) {
					line = line.trim();
					for (int i = 0; i < line.length(); i++) {
						allTurns.add(line.charAt(i) - '0');
					}
				}
			}

			tellCoordinator("Please inform others about me\n" + player.toString());

			System.out.println("All players\n " + allPlayers);
			System.out.println("All turns\n " + allTurns + "\n");

			br.close();
			pw.close();
			coorSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void tellCoordinator(String message) {
		try {
			Socket coorSocket = new Socket(coorConnection.getIP(), coorConnection.getPort());
			PrintWriter pw = new PrintWriter(coorSocket.getOutputStream(), true);

			pw.println(message);

			pw.close();
			coorSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ********************************************************************

	// PHASE 2: PLAY ALL DUELS WITH WHOM JOINS BEFORE

	// after getting allPlayers and allTurns
	// start the duel with all Players one by one
	protected void playAllDuels() {

		// if there are at least 2 players in the tournament
		// size of allPlayers will be > 0 (because the list excluded yourself)
		if (allPlayers.size() > 0) {
			System.out.println("--------------------------------------");
			System.out.println("START PLAYING");
			System.out.println(allPlayers.size() + " Players\n");

			// Run for each player, get Player and turn
			for (int i = 0; i < allPlayers.size(); i++) {
				Player opponent = allPlayers.get(i);
				int yourTurn = allTurns.get(i);

				try {

					// play until the state is true, which means two player has the same result
					int duelNo = duelCounter + 1;
					System.out.println("DUEL " + duelNo + " VS " + opponent);
					int oppoTurn;
					// boolean success is for checking if the duel is done successfully
					boolean success = false;
					int numOfFail = 0;
					while (!success && numOfFail < 3) {
						// because on your side, if yourTurn % 2 == 0 -> starts from you
						// but on your opponent side, if yourTurn % 2 == 0 -> start from his opponent
						// (you)
						// -> oppoTurn % 2 == 1

						Socket duelSocket = new Socket(opponent.getIP(), opponent.getPort());
						PrintWriter pw = new PrintWriter(duelSocket.getOutputStream(), true);
						BufferedReader br = new BufferedReader(new InputStreamReader(duelSocket.getInputStream()));

						if (yourTurn == 9) {
							oppoTurn = yourTurn - 1;
						} else {
							oppoTurn = yourTurn + 1;
						}

						// ----------------------------- here
						pw.println("Want to play?\n" + player.toString() + "\n" + oppoTurn);
						success = startTheDuel(br, pw, opponent, yourTurn);

						if (!success)
							numOfFail++;

						br.close();
						pw.close();
						duelSocket.close();
					}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (numOfFail == 3) {
						System.err.println("\nSomething wrong with duels\n");
						removeFromAllPlayers();
						informAllBeforeQuitting();
					}

					// tell the Coordinator to add the information about this duel
					tellCoordinator("Result\n" + player.toString() + " - " + opponent.toString() + " : "
							+ allWinners.get(allWinners.size() - 1).toString());
					System.out.println();

					// after finishing each duel

				} catch (IOException e) {
					// if any exception happens, just continue with next one
					continue;
				}
			}
		}

	}

	// get the input stream, output stream, opponent and the turn (who starts first)
	protected boolean startTheDuel(BufferedReader br, PrintWriter pw, Player opponent, int turn) {
		int oppoNum = 0;
		int result = 0;
		Player winner;

		// adding a random number to my number
		// so myNum will be hided
		final int myNum = (int) (Math.random() * 10);
		final int randomAdding = (int) (Math.random() * 10);

		System.out.println("Who first? " + ((turn % 2 == 0) ? "You" : "Opponent"));
		pw.println(myNum + randomAdding);
		String line;

		while (true) {

			try {
				// if the opponent did not send anything, check again
				while ((line = br.readLine()) == null) {
					continue;
				}

				// read the number from the opponent
				oppoNum = Integer.parseInt(line);

				// sleep for a moment
				// then send the randomAdding and receive opponent's randomAdding
				// then subtract to get the opponent number

				pw.println("Sending random number");

				while ((line = br.readLine()) != null) {
					if (line.equals("Sending random number"))
						break;
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Cannot sleep");
				}

				pw.println(randomAdding);

				if ((line = br.readLine()) != null) {
					int oppoRandom = Integer.parseInt(line);
					System.out.println("Your number: " + myNum);
					System.out.println("Opponent number: " + (int) (oppoNum - oppoRandom));
					result = (myNum + (oppoNum - oppoRandom) + turn);
				}
				
				if ((result % 2) == 0) {
					winner = opponent;
				} else {
					winner = player;
				}

				// send the winner to the opponent
				// check if both get the same result
				pw.println(winner);

				while ((line = br.readLine()) != null) {
					Player p = transferStringToPlayer(line);
					if (p.equals(winner)) {
						if (winner.equals(player))
							System.out.println("Winner: YOU");
						else
							System.out.println("Winner: OPPONENT");

						allWinners.add(winner);
						duelCounter++;
						return true;
					} else {
						System.out.println("\nSomething wrong, play again\n");
						return false;
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// ********************************************************************

	// PHASE 3: QUIT
	protected void quit() {
		quitting = true;
		removeFromAllPlayers();

		// sleep until the player has already played with all player
		while (duelCounter < allPlayers.size()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				System.err.println("InterruptedException - Thread cannot sleep");
			}
		}

		informAllBeforeQuitting();
	}

	private void removeFromAllPlayers() {

		if (allPlayers.size() == 0) {
			// when you are the last player in the game
			// System.out.println("You are the last player in the game");
			printResult();
			System.out.println("Closing the program...");
			System.exit(0);
		} else {

			// when there is at least one more player up to this moment

			// firstly, remove this quitted player from the list of allPlayers of
			// Coordinator
			// then when a new player comes, the list of allPlayers given to new player will
			// not contain quitted player
			// so new player will not make a duel with this player

			// but you are still included in the list of some players
			// some of those are playing with all players including you
			// you have not inform the Coordinator to inform all that you quitted yet
			// inform only when you finish all the duels

			// when you are the Coordinator

			if (isCoordinator()) {
				// Removing when you are the Coordinator
				coordinator.removePlayer(player);

			} else {
				tellCoordinator("Remove me\n" + player.toString());

			}
		}
	}

	private void informAllBeforeQuitting() {

		// check again the number of Players
		// in case that the program takes time to remove the player
		// the number of players may be changed all the time
		if (allPlayers.size() == 0) {
			// when you are the last player in the game
			printResult();
			System.out.println("Closing the program...");
			System.exit(0);
		}

		// now allPlayers.size() > 0
		// there is at least one more players in the tournament
		// when you already played will all players
		// now you can start quitting
		if (isCoordinator() || state == true) {
			// QUIT when you are the old Coordinator
			coordinator.informAllQuitedPlayer(player);
			coordinator.selectNewCoordinator();
		} else {

			// Normal player is sending QUIT
			tellCoordinator("QUIT\n" + player.toString());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("Cannot sleep");
			}

		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			System.err.println("InterruptedException - Thread cannot sleep");
		}

		printResult();
		System.out.println("Closing the program...");
		System.exit(0);

	}

	protected void setAsNewCoordinator(List<String> allDuels) {
		coorConnection = player;
		List<Player> all = new ArrayList<>();
		all.add(player);
		all.addAll(allPlayers);
		coordinator = new Coordinator(player, all, allDuels);
		state = false;
	}

	protected void printResult() {
		int winDuels = 0;
		if (allWinners.size() == 0)
			return;

		for (Player winner : allWinners) {
			if (winner.equals(player))
				winDuels++;
		}

		System.out.println("\n****************");
		System.out.println("NUMBER OF WINNING DUELS: " + winDuels + "/" + duelCounter);
		System.out.println("\n");
	}

	// ********************************************************************

	// transfer a line "IP Port" into an instance of Player
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

	// ********************************************************************

	// IN CASE THIS PEER IS THE COORDINATOR
	// METHODS FOR CALLING ON coordinator

	protected String addPlayer(Player p) {
		return coordinator.addPlayer(p);
	}

	protected void informAllNewPlayer(Player p2) {
		coordinator.informAllNewPlayer(p2);
	}

	protected void addToAllDuels(String line) {
		coordinator.addToAllDuels(line);
	}

	protected void removePlayer(Player removedPlayer) {
		coordinator.removePlayer(removedPlayer);
	}

	protected void informAllQuitedPlayer(Player quited) {
		coordinator.informAllQuitedPlayer(quited);
	}

	// ********************************************************************

	// METHODS FOR CALLING ON THIS PEER

	// when you are selected to be new Coordinator
	// state == true
	protected void setState(boolean state) {
		this.state = state;
	}

	protected Player getCoorConnection() {
		return coorConnection;
	}

	protected void setCoorConnection(Player p) {
		coorConnection = p;
	}

	protected Player getPlayer() {
		return player;
	}

	protected Coordinator getCoordinator() {
		return coordinator;
	}

	protected void addNewPlayer(Player p) {
		// when you are quitting, you do not want to add more player into allPlayers
		// the list allPlayers excludes yourself
		if (!quitting && !player.equals(p)) {
			allPlayers.add(p);
			System.out.println("New Player JOINED! " + p + "\n");
		}
	}

	protected void addWinner(Player winner) {
		allWinners.add(winner);
	}

	protected void removeQuitedPlayer(Player quited) {
		allPlayers.remove(quited);
		System.out.println("\n" + quited + " QUITED\n");
	}

	protected boolean isCoordinator() {
		return player.equals(coorConnection);
	}

	public String toString() {
		return player.toString();
	}

}
