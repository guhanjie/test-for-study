/** 
 * Project Name:		test-for-study 
 * Package Name:	com.guhanjie.jdk.buffer 
 * File Name:			TestBufferSliceConcurrency.java 
 * Create Date:		2016年12月26日 上午10:15:35 
 * Copyright (c) guhanjie All Rights Reserved.
 */  
package com.guhanjie.jdk.buffer;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Class Name:		Main<br/>
 * Description:		[description]<br/>
 * Time:					2016年12月26日 上午10:15:35
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class TestBufferSliceConcurrency {
	public static void main(String[] args) throws InterruptedException {
		//allocate whole big buffer
		//ByteBuffer bb = ByteBuffer.allocate(2048);
		ByteBuffer bb = ByteBuffer.allocateDirect(2048);
		//slice 2 pieces from the whole buffer, 
		//and each one has its own status: position/limit/capactiy and offset on underlying buffer.
		bb.limit(1024);
		final ByteBuffer slice1 = bb.slice();
		bb.position(1024).limit(2048);
		final ByteBuffer slice2 = bb.slice();
		System.out.println("slice1: "+"position="+slice1.position()+",limit="+slice1.limit()+",capacity="+slice1.capacity());
		System.out.println("slice2: "+"position="+slice2.position()+",limit="+slice2.limit()+",capacity="+slice2.capacity());
		//create 2 threads to put data concurrently
		final CountDownLatch latch = new CountDownLatch(1);
		Thread t1 = new Thread() {
			public void run() {
				try {
					latch.await();
					int i = 0;
					for(; i<128; i++) {
						//sleep random to simulate misc environment
						Thread.sleep(new Random().nextInt(1000));
						System.out.println("Slice1-----putting position:"+slice1.position());
						byte b = (byte)(2*i);
						slice1.put(b);
					}
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				try {
					latch.await();
					int i = 0;
					for(; i<128; i++) {
						//sleep random to simulate misc environment
						Thread.sleep(new Random().nextInt(1000));
						System.out.println("Slice2-----putting position:"+slice2.position());
						byte b = (byte)(2*i+1);
						slice2.put(b);
					}
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t1.start();
		t2.start();
		latch.countDown();
		t1.join();
		t2.join();
		printBuffer(slice1);
		printBuffer(slice2);
	}
	
	public static void printBuffer(ByteBuffer bb) {
		bb.flip();
		while(bb.remaining()>0) {
			System.out.print(bb.get());
			System.out.print("-");
		}
		System.out.println();
	}
}

