package messenger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

/** The main entry point of the program. Contains only the main class and a synchronized print method. 
 * @author Marek Kunz
 *
 */
public class MessengerMain {

	/** This is the main part of the program. 
	 * @param args - No Command Line arguments 
	 */
	private static final String MENU = "n) New Connection \nq) Quit";
	public static final Semaphore lock = new Semaphore(1, true);
	
	
	//The outer menu. Just takes input fromt he user to login. 
	public static void main(String[] args) {
		
		try{
		
		boolean running = true;
		while(running){
			//Get the input from the user. 
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			StringBuffer buffer = new StringBuffer();
			System.out.println(MENU);
			int charX;
			while((charX = reader.read())!= (int) '\n'){
				buffer.append((char) charX);	
			}
			String command = (buffer.toString()).trim();
			
			
			if(command.length() != 1){
				System.out.println("Invalid Command");
				break;
			}
			else {
				 switch (command.toCharArray()[0]){
				 case 'n': 
					 //Login
					 String auth = Login.Login();
					 Login.Menu(auth);
					 break;
				 case 'q':
					 running = false;
					 break;
					 
				 }
			}			
		}
		
		}
		catch(Exception e){
			System.out.println(e.toString());
		}	

	}
		

}
