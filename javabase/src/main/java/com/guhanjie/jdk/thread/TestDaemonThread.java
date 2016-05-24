public class TestDaemonThread {

	public static void main(String[] args) throws Exception{
		Thread t = new Thread() {
			public void run() {
				System.out.println("Hello world");
			}
		};
		t.setDaemon(true);
		t.start();
		Thread.sleep(1000);
		System.out.println(t.isAlive()+"   "+t.getState().toString());
	}
}