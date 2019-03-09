package message;

import participant.Requestor;

public class Response implements Comparable<Response>{
	private Request request;
	private int result;
	
	private int idResponse;
	private Priority priority;
	private Requestor requestor;
	
	public Response(Request req, int result) {
		request = req;
		this.result = result;
		idResponse = req.getIdRequest();
		priority = req.getPriority();
		requestor = req.getRequestor();
	}

	@Override
	public int compareTo(Response o) {
		int diff = priority.compareTo(o.priority);
		if (diff == 0)
			diff = idResponse - o.idResponse;
		return diff;
	}
	
	public boolean checkRequestor(Requestor r) {
		if (r.equals(requestor))
			return true;
		else 
			return false;
	}
	
	public int getResult() {
		return result;
	}
	
	public Request getRequest() {
		return request;
	}
	
	public String toString() {
		return request.toString() + result + " (" + requestor.toString() + ")";
	}
}
