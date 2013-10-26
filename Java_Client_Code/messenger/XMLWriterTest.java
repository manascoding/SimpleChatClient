package messenger;

import static org.junit.Assert.*;

import org.junit.Test;

public class XMLWriterTest {

	/**
	 * This test writes a very simple XML. 
	 */
	@Test
	public void test() {
		XMLWriter writer = new XMLWriter(4);
		writer.openTag("response");
		writer.writeAttribute("some", "true");
		writer.closeTag();
		String text = writer.getText();
		assertEquals("<?xml version=\"1.0\"?>\n<response>\n    <some>true</some>\n</response>\n", text);
		
	}

}
