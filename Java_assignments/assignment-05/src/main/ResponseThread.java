package main;

import message.Response;
import participant.Service;
import queue.RequestQueue;
import queue.ResponseQueue;

public class ResponseThread extends Thread{
	final Service s;
	final RequestQueue requestQueue;
	final ResponseQueue responseQueue;
	
	public ResponseThread(Service s, RequestQueue reqQueue, ResponseQueue responseQueue) {
		this.s = s;
		this.requestQueue = reqQueue;
		this.responseQueue = responseQueue;
	}
	
	public void run() {
		while (true) {
			
			Response res = s.process(requestQueue.popRequest());
			responseQueue.addResponse(res);
			
		}
	}
}
