package com.guhanjie.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This is a socket client tool which helps to send and recevice message base on TCP/IP.
 * @author guhanjie
 *
 */
public class SocketClientUtil {
    
    /**
     * 返回已建立Socket连接的socket对象
     * @param ip
     * @param port
     * @return
     */
    public static Socket connect(String ip, int port) {
        Socket client = null;
        try {
            client = new Socket(ip, port);            // 创建Socket，并建立连接
            return client;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 使用已建立连接的socket发送报文
     * @param connectedSocket
     * @param msg
     */
    public static void send(Socket connectedSocket, String msg) {
        BufferedWriter out = null;
        try {
            // 向服务器发送消息
            out = new BufferedWriter(new OutputStreamWriter(connectedSocket.getOutputStream()));
            out.write(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();                                   // 关闭输出流
                connectedSocket.close();           // 关闭Socket
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 使用已建立连接的socket接收报文（只接收一行）
     * @param connectedSocket
     * @param msg
     */
    public static String receive(Socket connectedSocket) {
        BufferedReader in = null;
        try {
            // 接收来自服务器的消息
            in = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
            String str = in.readLine();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();                                     // 关闭输入流
                connectedSocket.close();           // 关闭Socket
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    
    /**
     * 创建一个新的socket连接，并发送一行报文
     * @param ip
     * @param port
     * @param msg
     */
    public static void send(String ip, int port, String msg) {
        Socket client = null;
        PrintWriter out = null;
        try {
            // 创建Socket
            client = new Socket(ip, port);
            // 向服务器发送消息
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            out.println(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();                                    // 关闭输出流
                client.close();                                // 关闭Socket
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 创建一个新的socket连接，并接收一行报文（只接收一行）
     * @param ip
     * @param port
     * @param msg
     */
    public static String send(String ip, int port) {
        Socket client = null;
        BufferedReader in = null;
        try {
            // 创建Socket
            client = new Socket(ip, port);
            // 接收来自服务器的消息
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String str = in.readLine();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();                                   // 关闭输入流
            client.close();                                 // 关闭Socket
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
