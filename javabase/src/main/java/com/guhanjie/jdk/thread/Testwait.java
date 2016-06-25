public class Testwait {

	public static void main(String[] args) throws InterruptedException {
		final Object obj = new Object();
		Thread1 t1 = new Thread1(obj);
		Thread2 t2 = new Thread2(obj);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private static class Thread1 extends Thread{
		private final Object obj;
		public Thread1(Object obj) {
			this.obj = obj;
		}
		public void run() {
			synchronized(obj) {
				try {
					System.out.println("Entering "+Thread.currentThread());
					System.out.println("starting waiting 1s");
					obj.wait(1000);
					System.out.println("wake up "+Thread.currentThread());
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static class Thread2 extends Thread {
		private final Object obj;
		public Thread2(Object obj) {
			this.obj = obj;
		}
		public void run() {
			synchronized(obj) {
				try {
					System.out.println("Entering "+Thread.currentThread());
					System.out.println("starting sleeping 10s");
					Thread.sleep(10000);
					System.out.println("wake up "+Thread.currentThread());
				}catch(Exception e) {

				}
			}
		}
	}
}
