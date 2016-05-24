class ThreadCommunication {
	public boolean flag;
	public int n;
	public synchronized int get() {
		if(!flag) {
			try {
				wait();
			} catch(Exception e) {
				System.out.println("InterruptedException caught");
			}
		}
		System.out.println("Got: " + n);
	    flag = false;
	    notifyAll();
	    return n;
	}

	public synchronized void put(int i) {
		if(flag) {
			try {
				wait();
			} catch(Exception e) {
				System.out.println("InterruptedException caught");
			}
		}
		this.n = i;
		System.out.println("Put: " + n);
	    flag = true;
	    notifyAll();
	}
	public static void main(String[] args) {
		ThreadCommunication tc = new ThreadCommunication();
		new Producer(tc);
		new Consumer(tc);
		System.out.println("Press Control-C to stop.");
	}
	private static class Producer implements Runnable {
		public ThreadCommunication tc;
		public Producer(ThreadCommunication tc) {
			this.tc = tc;
			new Thread(this, "Producer").start();
		}
		public void run() {
			int i = 0;
			while(true) {
				tc.put(i++);
			}
		}
	}
	private static class Consumer implements Runnable {
		public ThreadCommunication tc;
		public Consumer(ThreadCommunication tc) {
			this.tc = tc;
			new Thread(this, "Consumer").start();
		}
		public void run() {
			while(true) {
				tc.get();
			}
		}
	}
}