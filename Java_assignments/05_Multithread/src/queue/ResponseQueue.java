package queue;

import java.util.PriorityQueue;

import message.Response;
import participant.Requestor;

public class ResponseQueue extends PriorityQueue<Response>{

	boolean canPop;
	boolean canAdd;
	
	private static final long serialVersionUID = 1L;
	
	public ResponseQueue() {
		super();
		canPop = false;
		canAdd = true;
	}
	
	public synchronized Response popResponse(Requestor r) {
		while (canPop = false || super.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		canPop = false;
		Response res = super.peek();
		if (!res.checkRequestor(r)) {
			res = null;
		} else {
			res = super.poll();
		}
		canPop = true;
		notifyAll();
		return res;
	}
	
	public synchronized boolean addResponse(Response res) {
		canAdd = false;
		boolean b = super.add(res);
		canAdd = true;
		notifyAll();
		return b;
	}
	
}
