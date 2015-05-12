public class TryCatchFinally {
	public static void main(String[] args) throws Exception {
            try {
                  throw new IllegalArgumentException("dddd");
            }catch (Exception e) {
                  throw new ArithmeticException("sss");
            }finally {
                  System.out.println("xxx");
            }
	}
}