package messenger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/** This class is used for receiving messages from the server. 
 * It always acquires lock when it needs to print the message. Also it sleeps  when
 * it needs to. 
 * @author Marek Kunz
 *
 */
public class Messenger extends Thread {
	
	
	private String myID;
	private boolean shouldRun; // This variable is used to stop this thread.
	
	/**
	 * @param myID - My identification is used to retrieve unread messages. 
	 */
	public Messenger (String myID){	
		this.myID = myID;
		this.shouldRun = true;
	}
	
	
	/*
	 * This class just loops getting new posts from the server. 
	 * */
	public void run (){
		while(this.shouldRun){
			StringBuffer response = null;
			
			try {
				//Get the message from the server
				String urlParameters = "id=" + this.myID;
				String urlString = "http://localhost/testing/getNewMessages.php";
				URL url = new URL(urlString);
				//Connect to the server
				HttpURLConnection connection = null;  
				connection = (HttpURLConnection)url.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoInput(true);
				connection.setUseCaches(false);
				connection.setDoOutput(true);

				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));

				DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
				wr.writeBytes(urlParameters);
				wr.flush ();
				wr.close ();
				InputStream is = connection.getInputStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is));
				String line;
				response = new StringBuffer();
				while ((line = rd.readLine()) != null) {
					response.append(line);
					response.append('\r');
				}
				rd.close();
							
				
			} catch(Exception e){
				System.out.println(e);
			}
			//If the response is not empty then we print. 
			if(!response.toString().trim().equals("")){
				
				try {
					//Acquire the look for printing.
					MessengerMain.lock.acquire();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
				this.printResponse(response.toString());
				//release the lock
				MessengerMain.lock.release();
			}
			
			
			try {
				this.sleep(2000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
	}
	
	//Print the response. The lock for printing should be acquired. 
	private void printResponse(String response) {
		XMLReader xml = new XMLReader(response);
		xml.deserialize();
		HashMap<String, ArrayList<String>> hash = xml.getHash();
		
		if(hash.containsKey("text")){
			ArrayList<String> text = hash.get("text");
			ArrayList<String> usr = hash.get("name");
			
			for(int x = 0; x< text.size(); x++){
				System.out.println(usr.get(x) + " says: " + text.get(x));
			}
		}
		
	}



	public void setMyID(String id){
		this.myID = id;
	}
	public String getMyID(){
		return this.myID;
	}
	
	/** Tells whether the thread should continue to run. This only works when the thread is running. 
	 * @param shouldRun - Set's the value if the thread should continue to run. 
	 */
	public void setShouldRun(boolean shouldRun){
		this.shouldRun = shouldRun;
	}
	
	

}
