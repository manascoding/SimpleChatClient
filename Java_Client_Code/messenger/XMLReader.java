package messenger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/** Please note that this is a really simple XML reader and only really works well
 *  with this project and how the XML is structured in the project. 
 * @author Marek Kunz
 *
 */
public class XMLReader {

	private HashMap<String, ArrayList<String>> hash = new HashMap<String, ArrayList<String>>();
	private Document doc;
	
	/** 
	 * @param text - Creates a XMLReader object given a String that contains XML. 
	 */
	public XMLReader(String text){
		//This just uses a DOcument object that is created from the text using DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			this.doc = db.parse(new ByteArrayInputStream(text.getBytes()));


		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
		
	
	/**
	 * Populates the HashMap with values from the XML that is stored in the String. 
	 */
	public void deserialize() {
		
		String attribute = null;
		String cell = null;
		Element rootElement = this.doc.getDocumentElement();
		
		NodeList nodes = rootElement.getChildNodes();

		for(int i=0; i<nodes.getLength(); i++){
		  Node node = nodes.item(i);
		  String a = node.getNodeName();
		  String c = node.getTextContent();
		  if(a != null && !a.equals("#text")){
			  attribute = a;
			  
		  }
		  if(c != null && !c.equals("")){
			  cell = c;
		  }
		  if (cell != null && attribute != null){
			  
			  if(this.hash.containsKey(attribute)){
				  this.hash.get(attribute).add(cell);
			  }
			  else{
				  ArrayList<String> in = new ArrayList<String>();
				  in.add(cell);
				  this.hash.put(attribute, in);
			  }
			  cell = null;
			  attribute =  null;
		 }		  
		 
		  
		}
		
	}
	
	/** Returns the current HashMap full of Strings as keys and ArrayLists with Strings as values. 
	 * @return - The HashMap containing Strings as keys and ArrayLists of values as values. 
	 */
	public HashMap<String, ArrayList<String>> getHash(){
		return this.hash;
	}	
	
	/**
	 * Prints the current content of the HashMap.
	 */
	public void printTest(){
		Iterator it = this.hash.entrySet().iterator();
		
		while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        
	    }
	}
}
