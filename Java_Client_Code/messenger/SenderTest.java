package messenger;

import static org.junit.Assert.*;

import org.junit.Test;

public class SenderTest {

		
	/**
	 *  This tests if we get something from the server. 
	 */
	@Test
	public void test(){
		Sender send = new Sender("Hello World!2", "2", "1");
		send.start();
		try {
			send.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
