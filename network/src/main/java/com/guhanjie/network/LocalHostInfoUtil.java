import java.net.InetAddress;  
import java.net.UnknownHostException; 

public class Test {
	public static void main(String[] args) {

		System.out.println(getLocalHostIp());
	}

	public static String getLocalHostIp() {
		String ip = "";
		try   
        {  
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {  
              
        } 
        return ip;
	}
}