package main;

import participant.Requestor;
import participant.Service;
import queue.RequestQueue;
import queue.ResponseQueue;

public class Main {
	public static void main(String[] args) {
		Requestor req1 = new Requestor();
		Requestor req2 = new Requestor();
		Requestor req3 = new Requestor();
		Service ser1 = new Service();
		
		final RequestQueue requestQueue = new RequestQueue();
		final ResponseQueue responseQueue = new ResponseQueue();
		
		new RequestThread(req1, requestQueue, responseQueue).start();
		new RequestThread(req2, requestQueue, responseQueue).start();
		new RequestThread(req3, requestQueue, responseQueue).start();
		
		new ResponseThread(ser1, requestQueue, responseQueue).start();
		
	}
	
	
}
