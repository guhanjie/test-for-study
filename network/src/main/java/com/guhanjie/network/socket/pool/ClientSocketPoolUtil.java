package com.guhanjie.network.socket.pool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientSocketPoolUtil {
    
    private ClientSocketPool pool = new ClientSocketPool("127.0.0.1", 4000);

    public String sendAndRecv(String sendMsg) {
        Socket socket = null;
        BufferedWriter out = null;
        BufferedReader in = null;
        try {
            socket = pool.borrowSocket();
            try {
                // 向服务器发送消息
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.write(sendMsg);
                out.flush();

                // 接收来自服务器的消息
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String str = in.readLine();
                return str;
            } catch (IOException e) {
                pool.invalidateSocket(socket);  
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to borrow socket from pool" + e.toString());
        } finally {
            try {
                out.close();                                   // 关闭输出流
            } catch(Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != socket) {
                    pool.returnSocket(socket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
