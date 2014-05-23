package com.guhanjie.jdk.exception;

public class InnerTryCatch {

	public void tryCatch() {
		try {
			throw new Exception("Here is a exception!");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("exception catched in INNER");
		}
	}
}
