package com.guhanjie.network.socket.pool;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
public class NetConnection {  
      
    private static Logger logger = LoggerFactory.getLogger(NetConnection.class);  
      
    private String name;  
    private boolean connected;  
  
    public NetConnection(String name) {  
        this.name = name;  
    }  
  
    public void connect() {  
        //logger.info("name: "+name + " is connectting...");  
        this.connected = true;  
    }  
  
    public void close() {  
        //logger.info("name: "+name + " is closing...");  
        this.connected = false;  
    }  
  
    public boolean isConnected() {  
        return this.connected;  
    }  
      
    public String getName() {  
        return this.name;  
    }  
      
    public void print() {  
        //logger.info(this.name);  
    }  
}  