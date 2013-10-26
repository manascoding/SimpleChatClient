package messenger;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

/**
 * @author Marek Kunz
 * Test assumes that the XMLWriter class works as expected and is complete. 
 */
public class XMLReaderTest {

	/**
	 * This test sees if the XMLReader works correctly for normal input. 
	 */
	@Test
	public void test() {
		XMLWriter writer = new XMLWriter(4);
		writer.openTag("response");
		writer.writeAttribute("some", "true");
		writer.closeTag();
		String text = writer.getText();
		assertEquals("<?xml version=\"1.0\"?>\n<response>\n    <some>true</some>\n</response>\n", text);
		XMLReader reader = new XMLReader(text);
		reader.deserialize();
		HashMap<String, ArrayList<String>> testHash = reader.getHash();
		assertEquals(testHash.get("some").get(0), "true");
		
	}
	
	
	/**
	 * This test tests that there can be two attributes with the same name. 
	 */
	@Test
	public void test2() {
		XMLWriter writer = new XMLWriter(4);
		writer.openTag("response");
		writer.writeAttribute("some", "true");
		writer.writeAttribute("some", "false");
		writer.closeTag();
		String text = writer.getText();
		assertEquals("<?xml version=\"1.0\"?>\n<response>\n    <some>true</some>\n    <some>false</some>\n</response>\n", text);
		XMLReader reader = new XMLReader(text);
		reader.deserialize();
		HashMap<String, ArrayList<String>> testHash = reader.getHash();
		assertEquals(testHash.get("some").get(1), "false");
		assertEquals(testHash.get("some").get(0), "true");
		
	}
	
	/**
	 * This test sees if the XMLReader works correctly for multilevel input. 
	 * OUTPUT NOTES: This test would be better suited if it worked little differently. However, it does not crash. More worked should be needed. 
	 */
	@Test
	public void test3() {
		XMLWriter writer = new XMLWriter(4);
		writer.openTag("response");
		writer.openTag("res");
		writer.writeAttribute("some", "true");
		writer.closeTag();
		writer.closeTag();
		String text = writer.getText();
		assertEquals("<?xml version=\"1.0\"?>\n<response>\n    <res>\n        <some>true</some>\n    </res>\n</response>\n", text);
		XMLReader reader = new XMLReader(text);
		reader.deserialize();
		HashMap<String, ArrayList<String>> testHash = reader.getHash();
		assertEquals(testHash.get("res").get(0).trim(), "true");
		
	}

}
