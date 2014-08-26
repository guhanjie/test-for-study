package com.guhanjie.jdk.thread;

public class TestThread {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread());
		System.out.println(Thread.activeCount());
		
		Data data = new Data(0);
		TThread t1 = new TThread(data);
		TThread t2 = new TThread(data);
		TThread t3 = new TThread(data);
		t1.start();
		t2.start();
		t3.start();
	}

}

class Data {
	private int i;		//volatile
	public Data(int i) {
		this.i = i;
	}
	public void setData(int i) {
		this.i = i;
	}
	public int getData() {
		return i;
	}
}

class TThread extends Thread {
	public Data data;
	public TThread(Data data) {
		this.data = data;
	}
	public void run() {
		for(int i=0; i<3; i++) {
			int nn = data.getData();
			System.out.println("thread:"+Thread.currentThread().getName()+" data = "+nn);
			data.setData(++nn);
		}
	}
}