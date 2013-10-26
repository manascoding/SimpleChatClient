package messenger;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class GetterTest {

	/**
	 * Just asserts that the value is not null.
	 */
	@Test
	public void test1() {
		Getter get = new Getter("1");
		String s = get.getOthers();
		assertNotNull(s);
	}
	
	/**
	 * Just asserts the two calls will result in the same answer. 
	 */
	@Test
	public void test2(){
		Getter get = new Getter("2");
		String s = get.getOthers();
		XMLReader xml = new XMLReader(s);
		xml.deserialize();
		HashMap<String, ArrayList<String>> bob = xml.getHash();
		assertNotNull(bob);
		
		Getter get2 = new Getter("2");
		String s2 = get2.getOthers();
		XMLReader xml2 = new XMLReader(s2);
		xml2.deserialize();
		HashMap<String, ArrayList<String>> bob2 = xml2.getHash();
		assertNotNull(bob2);
		
		assertEquals(bob.get("memberid"), bob2.get("memberid"));
	}
	
	/**
	 * Just asserts the two calls will result in different answers as the calls are different. 
	 */
	@Test
	public void test3(){
		Getter get = new Getter("2");
		String s = get.getOthers();
		XMLReader xml = new XMLReader(s);
		xml.deserialize();
		HashMap<String, ArrayList<String>> bob = xml.getHash();
		assertNotNull(bob);
		
		Getter get1 = new Getter("1");
		String s1 = get1.getOthers();
		XMLReader xml1 = new XMLReader(s1);
		xml1.deserialize();
		HashMap<String, ArrayList<String>> bob1 = xml1.getHash();
		assertNotNull(bob1);
		assertThat(bob.get("memberid").get(0), not(equalTo((bob1.get("memberid")).get(0))));
	}

}
