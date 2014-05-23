package com.guhanjie.jdk.exception;

import org.junit.Test;

public class InnerTryCatchTest {

	@Test
	public void testTryCatch() {
		try {
			new InnerTryCatch().tryCatch();
		}catch(Exception e) {
			System.out.println("exception catched in OUT");
		}
	}

}
