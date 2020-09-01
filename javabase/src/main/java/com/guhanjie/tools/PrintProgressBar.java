import org.apache.commons.lang3.StringUtils;

public class PrintProgressBar {
	public static void main(String[] args) {
		printProgressBar1();
		printProgressBar2();
		printProgressBar3();
		printProgressBar4();
	}
	
	/**
	 * Checking Progress: [####################################################################################################]100%
	 */
	public static void printProgressBar1() {
        long start = System.currentTimeMillis();
        int percent = 0;
        for(long i=0; i < 1L<<32; i++) {
            if(i == ((1L<<32)-1)*percent/100) {
				System.out.print("\rChecking Progress: [");
				for(int n=0; n<percent; n++) {					
					System.out.print("#");
				}
				for(int n=percent; n<100; n++) {					
					System.out.print(" ");
				}
				System.out.printf("]%2d%%", percent);
                percent++;
			}
		}
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("32bits ip iteration elapse "+(end-start)+"ms");
	}
	
	/**
	 * Checking Progress(100%): [####################################################################################################]
	 */
    public static void printProgressBar2() {
        long start = System.currentTimeMillis();
        int percent = 0;
        for(long i=0; i < 1L<<32; i++) {
            if(i == ((1L<<32)-1)*percent/100) {
                System.out.printf("\rChecking Progress(%2d%%): [", percent);
                for (int n = 0; n < percent; n++)
                    System.out.printf("#");
                for (int n = 0; n < 100-percent; n++)
                    System.out.printf(" ");
                System.out.printf("]");
                percent++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("32bits ip iteration elapse "+(end-start)+"ms");
    }
	
	/**
	 * Checking Progress: |####################################################################################################| (100%)
	 */
    public static void printProgressBar3() {
        long start = System.currentTimeMillis();
        int percent = 0;
        for(long i=0; i < 1L<<32; i++) {
            if(i == ((1L<<32)-1)*percent/100) {
                System.out.printf("\rChecking Progress: |");
                for (int n = 0; n < percent; n++)
                    System.out.printf("#");
                for (int n = 0; n < 100-percent; n++)
                    System.out.printf(" ");
                System.out.printf("| (%2d%%)", percent);
                percent++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("32bits ip iteration elapse "+(end-start)+"ms");
    }

	/**
	 * Checking Progress: |█████████████████████████████████████████████████████████████████████████████████████████████████████| 100% Complete 
	 */
    public static void printProgressBar4() throws InterruptedException {
        long start = System.currentTimeMillis();
        int percent = 0;
        for(long i=0; i < 1L<<32; i++) {
            if(i == ((1L<<32)-1)*percent/100) {
                percent++;
                System.out.printf("\r%s |%s%s| \u001B[33m%2d%%\u001B[0m Complete\u001B[33m \u001B[0m",
                        "Checking Progress:",
                        StringUtils.repeat("█", percent),
                        StringUtils.repeat("-", 100-percent),
                        percent);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("32bits ip iteration elapse "+(end-start)+"ms");
    }
}
