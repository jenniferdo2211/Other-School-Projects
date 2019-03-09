package server;

import java.net.InetAddress;

public class ClientInfo {
	private InetAddress IP;
	private int port;
	
	public ClientInfo(InetAddress IP, int port) {
		this.IP = IP;
		this.port = port;
	}
	
	public InetAddress getIP() {
		return IP;
	}
	
	public int getPort() {
		return port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IP == null) ? 0 : IP.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientInfo other = (ClientInfo) obj;
		if (IP == null) {
			if (other.IP != null)
				return false;
		} else if (!IP.equals(other.IP))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
	
	
}
