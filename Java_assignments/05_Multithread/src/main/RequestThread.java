package main;

import participant.Requestor;
import queue.RequestQueue;
import queue.ResponseQueue;

public class RequestThread extends Thread{
	final Requestor r;
	final RequestQueue requestQueue;
	final ResponseQueue responseQueue;
	
	public RequestThread(Requestor r, RequestQueue reqQueue, ResponseQueue responseQueue) {
		this.r = r;
		this.requestQueue = reqQueue;
		this.responseQueue = responseQueue;
	}
	
	public void run() {
		while (true) {
			requestQueue.addRequest(r.randomRequest());
			r.takeResponse(responseQueue.popResponse(r));
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
