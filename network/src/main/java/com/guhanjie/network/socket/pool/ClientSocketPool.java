package com.guhanjie.network.socket.pool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolUtils;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用Apache Commons Pool管理已建立连接的（客户端）Socket的生命周期
 * 该Socket池可以实现已建立连接的socket租借
 * @author guhanjie
 *
 */
public class ClientSocketPool {
    
    private ObjectPool<Socket> pool;
    
    //Here are configurations
    private String ip;
    private int port;
    private GenericObjectPool.Config config = new GenericObjectPool.Config();   
    
    public ClientSocketPool(String ip, int port) {
        this.ip = ip;
        this.port = port;
        //默认配置
        config.maxActive = 100;    //池内最大可用数
        config.maxIdle = 2;    //池内最大可空闲数
        config.timeBetweenEvictionRunsMillis = 3*60*1000L;  //池内逐出空闲对象的任务器执行间隔时间（毫秒）
        config.minEvictableIdleTimeMillis =3*60*1000L; //池内对象被逐出的最小空闲时间（毫秒）
        config.numTestsPerEvictionRun = config.maxActive; //每次逐出空闲对象的任务器扫描测试对象的数量
        config.testWhileIdle = true;     //池内空闲对象是否检查
        config.testOnBorrow = true;    //对象从池内借出是否需检查其有效性
    }
        
    public ClientSocketPool(String ip, int port, int maxActive, int maxIdle, boolean testOnBorrow, boolean testWhileIdle, long timeBetweenEvictionRunsMillis, long minEvictableIdleTimeMillis) {
        this(ip, port);
        config.lifo = false;
        config.maxActive = maxActive;
        config.maxIdle = maxIdle;
        config.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        config.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        config.numTestsPerEvictionRun = maxActive; 
        config.testWhileIdle = testWhileIdle;
        config.testOnBorrow = testOnBorrow;
    }

    public ClientSocketPool(String ip, int port, GenericObjectPool.Config config) {
        this.ip = ip;
        this.port = port;
        this.config = config;
    }
    
    public void init() {
        PoolableObjectFactory factory = new PooledSocketFactory(ip, port);          
        pool = PoolUtils.synchronizedPool(new GenericObjectPool(factory, config));  
    }
    
    public void destory() {
        try {  
            pool.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }    
    
    /**
     * 获取实际对象池
     * @return
     */
    public ObjectPool<Socket> getActualPool() {
        return pool;
    }
    
    /**
     * 借出Socket
     * @return
     * @throws Exception
     */
    public Socket borrowSocket() throws Exception {
        return pool.borrowObject();
    }
    
    /**
     * 归还Socket
     * @param socket
     * @throws Exception
     */
    public void returnSocket(Socket socket) throws Exception {
        pool.returnObject(socket);
    }
    
    /**
     * 该Socket已坏，使之置为非法，而清除
     * @param socket
     * @throws Exception
     */
    public void invalidateSocket(Socket socket) throws Exception {
        pool.invalidateObject(socket);
    }
    
    private static class PooledSocketFactory extends BasePoolableObjectFactory<Socket> {  
        
        private static Logger logger = LoggerFactory.getLogger(PooledSocketFactory.class);  
          
        private String ip;
        private int port;  
        
        public PooledSocketFactory(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }
          
        public Socket makeObject() throws Exception {  
            logger.info("starting to connect an new socket to [][]...", ip, port);  
            Socket clientSocket = null;
            try {
                // 创建Socket，并建立连接
                clientSocket = new Socket(ip, port);           
                return clientSocket;
            } catch (IOException e) {
                logger.error("Failed to connect an new socket to [][]...", ip, port);  
                e.printStackTrace();
            }
            return null;
        }  
          
        public void destroyObject(Socket socket) throws Exception {  
            logger.info("disconnect client socket...");  
            try {
                socket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 检测对象是否"有效"
         */
        public boolean validateObject(Socket socket) {  
            return socket.isConnected();
        }

        /**
         * "激活"对象,当Pool中决定移除一个对象交付给调用者时额外的"激活"操作,
         * 比如可以在activateObject方法中"重置"参数列表让调用者使用时感觉像一个"新创建"的对象一样;
         * 如果object是一个线程,可以在"激活"操作中重置"线程中断标记",或者让线程从阻塞中唤醒等;
         * 如果 object是一个socket,那么可以在"激活操作"中刷新通道,或者对socket进行链接重建(假如socket意外关闭)等.
         */
        public void activateObject(Socket socket) throws Exception {  
            logger.info("activate client socket...");  
            if(!socket.isConnected()) {
                socket.connect(new InetSocketAddress(ip, port)); //socket重连
            }
            try {
                socket.getOutputStream().flush();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }  
      
        /**
         * "钝化"对象,当调用者"归还对象"时,Pool将会"钝化对象";
         * 钝化的言外之意,就是此"对象"暂且需要"休息"一下.
         * 如果object是一个 socket,那么可以在passivateObject中清除buffer,将socket阻塞;
         * 如果object是一个线程,可以在"钝化"操作中将线程sleep或者将线程中的某个对象wait.
         * 需要注意的时,activateObject和passivateObject两个方法需要对应,避免死锁或者"对象"状态的混乱.
         */
        public void passivateObject(Socket socket) throws Exception { 
            logger.info("passivate client socket...");  
            try {
                socket.getOutputStream().flush();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }  
    }  

}