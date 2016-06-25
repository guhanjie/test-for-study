package com.guhanjie.network.socket.pool;

import org.apache.commons.pool.PoolableObjectFactory;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
public class NetConnectionPoolableObjectFactory implements PoolableObjectFactory {  
  
    private static Logger logger = LoggerFactory.getLogger(NetConnectionPoolableObjectFactory.class);  
      
    private static int count = 0;  
      
    public Object makeObject() throws Exception {  
        NetConnection myConn = new NetConnection(generateName());  
        logger.info(myConn.getName());  
        myConn.connect();  
        return myConn;  
    }  
      
    public void activateObject(Object obj) throws Exception {  
        NetConnection myConn = (NetConnection)obj;  
        //logger.info(myConn.getName() + " is activating...");  
    }  
  
    public void passivateObject(Object obj) throws Exception {  
        NetConnection myConn = (NetConnection)obj;  
        //logger.info(myConn.getName() + " is passivating...");  
    }  
      
    public boolean validateObject(Object obj) {  
        NetConnection myConn = (NetConnection)obj;  
        //logger.info("validating "+myConn.getName());  
        return myConn.isConnected();  
    }  
      
    public void destroyObject(Object obj) throws Exception {  
        NetConnection myConn = (NetConnection)obj;  
        //logger.info("destorying "+myConn.getName());  
        myConn.close();  
    }  
      
    private synchronized String generateName() {  
        return "conn_" + (++count);  
    }  
}  
