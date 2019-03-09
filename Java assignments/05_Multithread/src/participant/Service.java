package participant;

import message.Request;
import message.Response;

public class Service {
	private static int ID = 0;
	int idService;
	
	public Service() {
		idService = ID++;
	}
	
	public Response process(Request req) {
		int result;
		
		switch(req.getAction()) {
		case "+":
			result = req.getX() + req.getY();
			return new Response(req, result);
		case "-":
			result = req.getX() - req.getY();
			return new Response(req, result);
		case "*":
			result = req.getX() * req.getY();
			return new Response(req, result);
		case "/":
			result = req.getX() / req.getY();
			return new Response(req, result);	
		}
		
		return null;	
	}
}
