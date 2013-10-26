package messenger;

import java.util.*;

/** Very simple XML writer class. Only works well writing really simple XML like the ones used in this project. 
 * @author Marek Kunz
 *
 */
public class XMLWriter {
	
	private StringBuffer text;
	private Stack<String> openedTag;
	private int indent;
	private int currentIndent = 0;
	
	/** Creates a XMLWriter with a given indent number and a custom header. 
	 * @param indent - The number of spaces a tab is
	 * @param header - The custom header
	 */
	public XMLWriter(int indent, String header){
		this.text = new StringBuffer();
		this.openedTag = new Stack<String>();
		this.indent = indent;
		this.text.append(header);
		
	}
	
	/** Creates a XMLWriter with a given indent number. Uses <?xml version=\"1.0\"?> as the heading. 
	 * @param indent - The number of spaces a tab is
	 */
	public XMLWriter(int indent){
		this.text = new StringBuffer();
		this.openedTag = new Stack<String>();
		this.indent = indent;
		this.text.append("<?xml version=\"1.0\"?>\n");
		
	}
	
	
	/** Opens up a tag in the buffer.
	 * @param tag - the tag name to open
	 */
	public void openTag(String tag){
		this.writeIndent();
		this.openedTag.push(tag);		
		this.text.append("<" + tag +">\n");
		this.currentIndent += indent;
	}
	
	/**
	 *  Closes the latest tag.
	 */
	public void closeTag(){
		this.currentIndent -= indent;
		this.writeIndent();
		this.text.append("</" + this.openedTag.pop() + ">\n");
	}
	
	/** Writes text at the current spot and index level. 
	 * @param text - Text to write
	 */
	public void writeText(String text){
		this.writeIndent();
		this.text.append(text + "\n");
	}
	
	/** Writes a attribute with a given tag name, and given text.
	 * @param tag - The tag of the attribute to write
	 * @param text - The text to write
	 */
	public void writeAttribute(String tag, String text){
		this.writeIndent();
		this.text.append("<" + tag + ">" + text+ "</" + tag + ">\n");
	}
	
	/** Returns the text of the XML.
	 * @return - Text of XML
	 */
	public String getText(){
		return this.text.toString();
	}
	
	//Writes a indent level to the buffer. 
	private void writeIndent(){
		String current = "";
		for(int x = 0; x < currentIndent; x++){
			current += " ";
		}
		this.text.append(current);
	}
	
	

}
