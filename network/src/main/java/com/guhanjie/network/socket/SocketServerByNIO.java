import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.ClosedChannelException;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.ServerSocketChannel;  
import java.nio.channels.SocketChannel;  
import java.util.Iterator;

public class SocketServerByNIO {

    public static void main(String args[]) {
        ServerSocketChannel serverChannel = null;
        try { 
            //��ȡһ��ServerSocketͨ��
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(4700));
            //��ȡͨ��������
            Selector selector = Selector.open();
            //��ͨ����������ͨ���󶨣���Ϊ��ͨ��ע��SelectionKey.OP_ACCEPT�¼���
            //ֻ�е����¼�����ʱ��Selector.select()�᷵�أ�����һֱ������
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("�������������ɹ�");     
            //������ȡ���ݻ�����
            ByteBuffer buffer = ByteBuffer.allocate(1024);      
            //ʹ����ѯ����selector
            while(true){
                //����ע����¼�����ʱ���������أ�����������
                selector.select();            
                //��ȡselector�еĵ�������ѡ����Ϊע����¼�
                Iterator<SelectionKey> ite=selector.selectedKeys().iterator();            
                while(ite.hasNext()){
                    SelectionKey key = ite.next();
                    //ɾ����ѡkey����ֹ�ظ�����
                    ite.remove();
                    //�ͻ������������¼�
                    if(key.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        //��ÿͻ�������ͨ��
                        SocketChannel channel = server.accept();
                        channel.configureBlocking(false);
                        //����ͻ������ӳɹ���Ϊ�ͻ���ͨ��ע��SelectionKey.OP_READ�¼���
                        channel.register(selector, SelectionKey.OP_READ);                    
                        System.out.println("�ͻ������������¼�");
                    }else if(key.isReadable()){//�пɶ������¼�
                        //���buffer
                        buffer.clear();
                        //��ȡ�ͻ��˴������ݿɶ�ȡ��Ϣͨ����
                        SocketChannel channel = (SocketChannel)key.channel();
                        int read = channel.read(buffer);
                        buffer.flip();
                        String message=new String(buffer.array(),0,buffer.limit());                     
                        System.out.println("receive message from client, size:" + read + " msg: " + message);
                        if(message.equals("bye")) {
                            return;
                        }
                        ByteBuffer outbuffer = ByteBuffer.wrap(("from server:"+message).getBytes());
                        channel.write(outbuffer);
                    }
                }
            }    
        } catch(Exception e) {

        } finally {            
            try {  
                serverChannel.close();  
            } catch(Exception ex) {

            }  
        }
    }
}
