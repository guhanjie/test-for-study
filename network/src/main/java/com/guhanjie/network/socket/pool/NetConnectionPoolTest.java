package com.guhanjie.network.socket.pool;

import org.apache.commons.pool.ObjectPool;  
import org.apache.commons.pool.PoolableObjectFactory;  
import org.apache.commons.pool.impl.GenericObjectPool;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
public class NetConnectionPoolTest {  
      
    private static Logger logger = LoggerFactory.getLogger(NetConnectionPoolTest.class);  
      
    public static void main(String[] args) {  
        //test1();  
        //test2();  
        test3();  
    }  
      
    private static void test1() {  
        PoolableObjectFactory factory = new NetConnectionPoolableObjectFactory();  
        GenericObjectPool.Config config = new GenericObjectPool.Config();  
        config.lifo = false;  
        config.maxActive = 5;  
        config.maxIdle = 5;  
        config.minIdle = 1;  
        config.maxWait = 5 * 1000;  
          
        ObjectPool pool = new GenericObjectPool(factory, config);  
        for (int i = 0; i < 10; i++) {  
            Thread thread = new Thread(new MyTask(pool));  
            thread.start();  
        }  
        closePool(pool);  
    }  
      
    private static void test2() {  
        PoolableObjectFactory factory = new NetConnectionPoolableObjectFactory();  
        GenericObjectPool.Config config = new GenericObjectPool.Config();  
        config.lifo = false;  
        config.maxActive = 5;  
        config.maxIdle = 5;  
        config.minIdle = 1;  
        config.maxWait = 20 * 1000;  
  
        ObjectPool pool = new GenericObjectPool(factory, config);  
        for (int i = 0; i < 10; i++) {  
            Thread thread = new Thread(new MyTask(pool));  
            thread.start();  
        }  
        closePool(pool);  
    }  
  
    private static void test3() {  
        PoolableObjectFactory factory = new NetConnectionPoolableObjectFactory();  
        GenericObjectPool.Config config = new GenericObjectPool.Config();  
        config.lifo = false;  
        config.maxActive = 25;  
        config.maxIdle = 5;  
        config.minIdle = 0;  
        config.maxWait = -1;  
        config.minEvictableIdleTimeMillis = 5*1000;
        config.testWhileIdle = true;
        config.timeBetweenEvictionRunsMillis = 5*1000;
  
        ObjectPool pool = new GenericObjectPool(factory, config);  
        for (int i = 0; i < 1000; i++) {  
            Thread thread = new Thread(new MyTask(pool));  
            thread.start();  
        }  
  
        try {  
            Thread.sleep(600L * 1000L);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
        closePool(pool);  
    }  
  
    private static void closePool(ObjectPool pool) {  
        try {  
            pool.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    private static class MyTask implements Runnable {  
        private ObjectPool pool;  
          
        public MyTask(ObjectPool pool) {  
            this.pool = pool;  
        }  
          
        public void run() {  
            debug();
            NetConnection myConn = null;  
            try {  
                myConn = (NetConnection)pool.borrowObject();  
                try {  
                    myConn.print();  
                } catch(Exception ex) {  
                    pool.invalidateObject(myConn);  
                    myConn = null;  
                }  
                Thread.sleep(3L * 1000L);  
                debug();
            } catch(Exception ex) {  
                logger.error("Cannot borrow connection from pool.", ex);  
            } finally {  
                if (myConn != null) {  
                    try {  
                        pool.returnObject(myConn);  
                        debug();
                    } catch (Exception ex) {  
                        logger.error("Cannot return connection from pool.", ex);  
                    }  
                }  
            }  
        }  
        
        public void debug() {
            StringBuffer buf = new StringBuffer();
            buf.append("Active: ").append(pool.getNumActive()).append("\n");
            buf.append("Idle: ").append(pool.getNumIdle()).append("\n");
            System.out.println(buf.toString());
        }
    }  
}  