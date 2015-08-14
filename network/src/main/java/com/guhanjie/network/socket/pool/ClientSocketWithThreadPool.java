package com.guhanjie.network.socket.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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
public class ClientSocketWithThreadPool {
    
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    /**
     * 实现思路是：
     * 改造ThreadPoolExecutor中的内部类Worker，使创建worker时自动创建socket连接，提交的任务使用该worker的socket进行网络通信。
     */
    
    public static Future<String> submit(final String msg) {
        return threadPool.submit(new Callable<String>() {
            public String call() throws Exception {
                return Thread.currentThread().getName()+msg;
            }
        });
    }
    
    public static void main(String[] args) {
        List<Future<String>> taskResultList = new ArrayList<Future<String>>();
        for(int i=0; i<100; i++) {
            Future<String> future = ClientSocketWithThreadPool.submit("----"+i);
            taskResultList.add(future);
        }
        for(Future<String> f : taskResultList) {
            try {
                System.out.println(f.get());
            }
            catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
