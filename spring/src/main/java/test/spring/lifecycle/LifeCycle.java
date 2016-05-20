/**
 * 平安付
 * Copyright (c) 2013-2014 PingAnFu,Inc.All Rights Reserved.
 */
package test.spring.lifecycle;

/**
 * @author zhao.liangshu
 * @version 2014年9月17日 上午10:49:29
 */
public interface LifeCycle {
    
    void init();
    
    void start();
    
    void stop();

}
