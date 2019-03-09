package assignment_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NewCoordinatorThread extends Thread {

	Peer peer;
	
	public NewCoordinatorThread(Peer p) {
		peer = p;
	}

	public void run() {
		Socket client = null;
		ServerSocket server = null;

		// new port for this peer
		// the dedicated port for receiving this message is (65000 - port)
		// port for listening to the Coordinator only
		// when the Coordinator quits, it will inform you that you are the new
		// Coordinator

		try {
			server = new ServerSocket(65000 - peer.getPlayer().getPort());
		} catch (IOException e1) {
			System.out.println("Cannot create a server");
		}

		try {
			client = server.accept();

			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String line;
			List<String> allDuels = new ArrayList<>();
			
			while ((line = br.readLine()) != null) {
				if (line.equals("You are the new Coordinator")) {
					while ((line = br.readLine()) != null && line != "\n") {
						allDuels.add(line);
					}
				}
				peer.setState(true); // you are selected to be the new Coordinator
				peer.setAsNewCoordinator(allDuels);
				// System.out.println("The Coor QUITED! You are new Coor!");
				break;
			}

			br.close();
			client.close();
			server.close();

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
