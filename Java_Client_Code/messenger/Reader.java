package messenger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/** This class is used for reading in the user's input.
 * @author Marek Kunz
 *
 */
public class Reader extends Thread{

	private String auth;
	private String[] to;
	
	
	/** Creates a reader object given a authentication string. Reader object is used for reading in 
	 * input from the user. 
	 * @param auth - The authentication used by the program. This is really just the user id in this case.  
	 * @param to - Array of the ID to.
	 */
	public Reader (String auth, String[] to){
		this.auth = auth;
		this.to = to;
	}	
	
	public void run(){
		
		//Keeps getting users input. 
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringBuffer buffer = new StringBuffer();

		try{
			System.out.println("Receiving:");//We start with receiving new messages
			boolean empty = true;
			reader = new BufferedReader(new InputStreamReader(System.in));
			int charX;
			while (empty){
				buffer = new StringBuffer();
				// While user does not press enter then do nothing. 
				while((charX = reader.read()) != '\n');
				//Acquire look to get user input
				MessengerMain.lock.acquire();
				//Get the input from the user. 
				System.out.println("Type Now:");
				while((charX = reader.read())!= (int) '\n'){
					buffer.append((char) charX);					

				}
				//Extract the text only
				String text = (buffer.toString()).substring(0, buffer.toString().length() -1);
				
				if(!text.equals("-") && !text.equals("-q")){
					//then we send the line else nothing
					Sender send = new Sender(text, this.auth, this.to);
					send.start();
										
				}
				else if(text.equals("-q")){
					empty = false;
				}
				System.out.println("Receiving:");
				MessengerMain.lock.release();
				//Release and sleep for little time to give the other thread time to run.
				Thread.sleep(300);
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}

	}	
	

}
