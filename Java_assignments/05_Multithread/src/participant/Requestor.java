package participant;

import message.Request;
import message.Response;

public class Requestor {
	private static int ID = 0;
	
	int idRequestor;
	
	public Requestor() {
		idRequestor = ID++;
	}
	
	public void takeResponse(Response res) {
		if (res != null)
			System.out.println(res.toString());
	}
	
	public Request randomRequest() {
		int x = (int)(Math.random() * 10 + 1);
		int y = (int)(Math.random() * 10 + 1);
		int rand = (int)(Math.random() * 40);
		if (rand % 4 == 0) {
			return new Request(this, "+", x, y);
		} else if (rand % 4 == 1) {
			return new Request(this, "-", x, y);
		} else if (rand % 4 == 2) {
			return new Request(this, "*", x, y);
		} else {
			return new Request(this, "/", x, y);
		}
	}
	
	public String toString() {
		return "Requestor " + idRequestor;
	}
}
