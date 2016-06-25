package com.guhanjie.jdk.timer;

import java.util.Date;
import java.util.Timer;  
import java.util.TimerTask;  

/**
 * TimerTest是用于测试java定时器中schedule和scheduleAtFixedRate方法的区别。
 * scheduleAtFixedRate与schedule方法的侧重点不同，
 * schedule方法侧重保存间隔时间的稳定，而scheduleAtFixedRate方法更加侧重于保持执行频率的稳定。
 * 原因如下：
 * 在schedule方法中会因为前一个任务的延迟而导致其后面的定时任务延时，当任务执行时间过长，可能会“丢失”一些任务的执行;
 * 而scheduleAtFixedRate方法则不会，如果第n个task执行时间过长
 * 导致systemCurrentTime >= scheduledExecutionTime(n+1)，
 * 则不会做任何等待他会立即调用启动第n+1个task，但具体执行时间
 * 所以scheduleAtFixedRate方法执行时间的计算方法不同于schedule，
 * 而是scheduledExecutionTime(n)=firstExecuteTime +n*periodTime，该计算方法永远保持不变。
 * 所以scheduleAtFixedRate更加侧重于保持执行频率的稳定。
 */
public class TimerTest {
	private Timer timer = new Timer();  

	public void lanuchTimer(){
		//scheduleAtFixedRate保持了任务执行的频率
		timer.scheduleAtFixedRate(new TimerTask(){ //schedule
	    	public void run() {
      			System.out.print("任务A");
      			//scheduledExecutionTime()，通常，此方法不与固定延迟执行的重复任务一起使用，因为其已安排执行时间允许随时间浮动，所以毫无意义。这里调用是为了验证此说法。
      			Date date = new Date(this.scheduledExecutionTime());
        		System.out.println("-本次执行该任务的时间为：" + date + "，当前时间为：" + new Date());
	    		try {
	    			Thread.sleep(1000);
	    		} catch(InterruptedException e) {
	    			Thread.currentThread().interrupt();
	    		}
        	}
		}, 1000, 1000);
		timer.scheduleAtFixedRate(new TimerTask(){ //schedule
	    	public void run() {  
      			System.out.print("任务B");
      			Date date = new Date(this.scheduledExecutionTime());
        		System.out.println("-本次执行该任务的时间为：" + date + "，当前时间为：" + new Date());
	    		try {
	    			Thread.sleep(1000*4);
	    		} catch(InterruptedException e) {
	    			Thread.currentThread().interrupt();
	    		}
        	}
		}, 1000*2, 4000);
	}  

	public static void main(String[] args) {
		TimerTest test = new TimerTest();
		System.out.println("Timer开始启动，时间为：" + new Date());
		test.lanuchTimer(); 
	}
}