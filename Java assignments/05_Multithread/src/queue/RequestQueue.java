package queue;

import java.util.PriorityQueue;

import message.Request;

public class RequestQueue extends PriorityQueue<Request> {

	boolean canPop;
	boolean canAdd;

	private static final long serialVersionUID = 1L;

	public RequestQueue() {
			super();
			canPop = false;
			canAdd = true;
		}

	public synchronized Request popRequest() {
		while (canPop = false || super.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		canPop = false;
		Request res = super.poll();
		canPop = true;
		notifyAll();
		return res;
	}

	public synchronized boolean addRequest(Request res) {
		canAdd = false;
		boolean b = super.add(res);
		canAdd = true;
		notifyAll();
		return b;
	}

}
