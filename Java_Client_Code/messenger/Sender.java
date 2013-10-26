package messenger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/** This code is used to send the message in a new thread. 
 * @author Marek Kunz
 *
 */
public class Sender extends Thread{
	
	private String message;
	private String fromID;
	private String[] toID;
	private static final String SEND_URL = "http://localhost/testing/post.php";
	
	/** Creates a sender object with a message, from and to IDs.
	 * @param message - The message text.
	 * @param fromID - The from ID who sends the message.
	 * @param toID - The to ID
	 */
	public Sender(String message, String fromID, String toID){
		this.message = message;
		this.fromID = fromID;
		this.toID = new String[]{toID};
		
	}
	
	/** Creates a sender object with a message, from and to IDs.
	 * @param message - The message text.
	 * @param fromID - The from ID who sends the message.
	 * @param toID - The to ID; this time in array format. 
	 */
	public Sender(String message, String fromID, String[] toID){
		this.message =  message;
		this.fromID = fromID;
		this.toID = toID;
	}
	
	public void run(){
		try{
			//Package the message in XML. Then we send it off. 
			String xmlText = this.constructMessage();
			URL url = new URL(SEND_URL);
			HttpURLConnection connection = null;  
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type","text/xml");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(xmlText.getBytes().length));
			connection.setDoInput(true);
			connection.setUseCaches(false);
		    connection.setDoOutput(true);
		    //Write it to the server.
		    DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
		    wr.writeBytes(xmlText);
		    wr.flush ();
		    wr.close ();
		    
		    InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			//Get the response from the server. 
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			
			String responseText = (response.toString()).trim();
			//Check if there is an error; if yes then print it. 
			if(!responseText.equals("")){
				this.checkResponse(responseText);
			}   
		}
		catch(Exception e){
			System.err.println(e.toString());
		}
		
		
	}
	//Basically just check if the message was sent successfully. 
	private void checkResponse(String response) {
		XMLReader reader = new XMLReader(response);
		reader.deserialize();
		HashMap<String, ArrayList<String>> resp = reader.getHash();
		if(resp.containsKey("success")){
			String text = resp.get("success").get(0);
			//Check if there was an error with sending the message. 
			if(text.equals("false")){
				System.err.println("Some error occured in sending the message!");
			}
		}
		
	}

	// Just constructs the massage to be sent. 
	private String constructMessage() {
		XMLWriter writer = new XMLWriter(4);
		writer.openTag("message");
		writer.writeAttribute("from", fromID);
		writer.openTag("to");
		
		for(int x = 0; x < toID.length; x++){
			writer.writeAttribute("id", toID[x]);
		}
		writer.closeTag();
		writer.writeAttribute("text", message);
		writer.closeTag();
		return writer.getText();
	}
	
	
}
