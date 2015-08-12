package com.guhanjie.network.socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 这是一个Socket客户端的连接池（起到socket代理的功能）<br/>
 * 使用线程池技术维护多个SocketClient的请求，
 * 线程池内开启的每个线程维护一个有效socket连接（长连接），
 * 该socket连接可以负责多个用户的请求（顺序处理，以避免每个用户请求都要创建一次socket连接，浪费系统资源），
 * 如果某个线程空闲时间超过60s，则该线程自动回收，销毁socket连接，释放资源。
 * @author guhanjie
 *
 */
public class SocketClientWithThreadPool {
    
    private ExecutorService threadPool = Executors.newCachedThreadPool();
    
    public static Future<String> submit(String msg) {
        return null;
    }
}
