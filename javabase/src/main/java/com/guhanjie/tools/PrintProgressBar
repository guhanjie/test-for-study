public class PrintProgressBar {
	public static void main(String[] args) {
		try{
			for (int i = 1; i <= 100; i++) {
				System.out.print("\r[");
				for(int j=0; j<i; j++) {					
					System.out.print("=");
				}
				for(int j=i; j<100; j++) {					
					System.out.print(" ");
				}
				System.out.printf("%3d%%]", i);
				Thread.sleep(100);
			}
		}catch(Exception e) {

		}
	}
}
