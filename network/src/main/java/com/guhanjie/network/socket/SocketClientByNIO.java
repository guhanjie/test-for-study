import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;  
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress; 
import java.nio.ByteBuffer;  
import java.nio.channels.SocketChannel;  

public class SocketClientByNIO {

    public static void main(String args[]) {
        SocketChannel socketChannel = null;
        try {
            //socketChannel.configureBlocking(false);  
            SocketAddress socketAddress = new InetSocketAddress("localhost", 4700);
            socketChannel = SocketChannel.open(socketAddress);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);  
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            String readline = sin.readLine();
            while (!readline.equals("bye")) {
                System.out.println("Ready to send: data = " + readline);
                socketChannel.write(ByteBuffer.wrap(readline.getBytes()));
                byteBuffer.clear();
                int readBytes = socketChannel.read(byteBuffer);
                byteBuffer.flip();
                if (readBytes > 0) {
                    System.out.println("Client: readBytes = " + readBytes);
                    String recvStr = new String(byteBuffer.array(),0,byteBuffer.limit());
                    System.out.println("Client: data = " + recvStr);
                }
                readline = sin.readLine();
            }  
        } catch (Exception e) {
            System.out.println("Error" + e); //出错，则打印出错信息
        } finally {
            try {  
                socketChannel.close();  
            } catch(Exception ex) {

            }  
        }
    }
}
