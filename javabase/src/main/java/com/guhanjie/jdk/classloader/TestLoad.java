public class TestLoad {
	public static void main(String[] args) {
		int i = 0;
		while(true) {
			i++;
			if(i%60 == 0) {
				System.out.println("GUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIEGUHANJIE");
			}
			else {
				try {
					System.out.println("Sleeping...");
					Thread.sleep(1000);
				} catch(Exception e) {

				}
			}
		}
	}
	public static String getStr() {
		String str = "GUHANJIE";
		return str;
	}
}
