package assignment_01;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {
	private InetAddress IP;
	private int port;
	private Pattern pattern = Pattern
			.compile("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");

	public Player(InetAddress IP, int port) {
		this.IP = IP;
		this.port = port;
	}

	public Player(Player p) {
		this(p.getIP(), p.getPort());
	}

	protected InetAddress getIP() {
		return IP;
	}
	
	protected int getPort() {
		return port;
	}

	public boolean equals(Object other) {
		if (other instanceof Player) {
			Player o = (Player) other;
			return IP.equals(o.getIP()) && port == o.getPort();
		} else
			return false;
	}

	public String toString() {
		Matcher matcher = pattern.matcher(IP.toString());
		if (matcher.find()) {
			try {
				IP = InetAddress.getByName(matcher.group());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return IP + " " + port ;
	}

}
