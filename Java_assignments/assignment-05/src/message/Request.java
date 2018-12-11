package message;

import java.util.Date;

import participant.Requestor;

public class Request implements Comparable<Request>{
	private static int ID = 0;
	
	private String action;
	private int x;
	private int y;
	
	private int idRequest;
	private Priority priority;
	private Date deadline;
	private Requestor requestor;
	
	public Request(Requestor r, String action, int x, int y) {
		requestor = r;
		this.action = action;
		this.x = x;
		this.y = y;		
		
		idRequest = ID++;
		priority = createRandomPriority();
		deadline = new Date();
	}
	
	private Priority createRandomPriority() {
		int rand = (int) (Math.random() * 30); 
		if (rand % 3 == 0)
			return Priority.LOW;
		else if (rand % 3 == 1)
			return Priority.NORMAL;
		else 
			return Priority.HIGH;
	}
	
	public int compareTo(Request other) {
		int diff = priority.compareTo(other.priority);
		if (diff == 0) {
			diff = deadline.compareTo(other.deadline);
		}
		
		if (diff == 0) {
			diff = idRequest - other.idRequest;
		}
		
		return diff;
	}
	
	public String toString() {
		return requestor.toString() + " : " + x + " " + action + " " + y + " = ";
	}
	
	public int getIdRequest() {
		return idRequest;
	}

	public Priority getPriority() {
		return priority;
	}
	
	public Requestor getRequestor() {
		return requestor;
	}
	
	public String getAction() {
		return action;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
