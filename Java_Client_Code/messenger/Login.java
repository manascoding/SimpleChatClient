package messenger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/** THis class is used for logging into the server. 
 * @author Marek Kunz
 *
 */
public class Login {


	private static final String USERNAME_ENTER = "Please enter your username: ";
	private static final String PASSWORD_ENTER = "Please enter your password: ";
	private static final String LOGGEDIN = "You have been logged in: ";
	private static final String MENU = "1) View People\n2) Start new Conversation\n3) Quit";


	/** Used for the actual logging in. 
	 * @param username - Username of the person logging in.
	 * @param password - Password of the person we are logging in.
	 * @return - String of the logging in message. 
	 * @throws Exception - Throws exception if it is not successful. A generic one that is fatal. 
	 */
	private static String acquireConnection(String username, String password) throws Exception{
		
		//Post the parameters to the server and get the result
		String urlParameters = "password=" + password + "&" + "username" + "=" + username;
		String urlString = "http://localhost/testing/php.php";
		URL url = new URL(urlString);
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
		StringBuffer response = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		
		//Varify that the result is correct and that the user is authenticated. 
		XMLReader text = new XMLReader(response.toString());
		text.deserialize();		
		HashMap<String, ArrayList<String>> hash= text.getHash();

		if(hash.containsKey("authorized") && hash.get("authorized").get(0).equals("true")){
			return hash.get("id").get(0);
		}

		return null;
	}

	/**
	 * Logs in a user. Takes his input. Also starts the Messenger class and the reader class. 
	 */
	public static String Login(){

		String auth = null;
		// Get the user name and password. 
		while(auth == null){
			System.out.println(USERNAME_ENTER);
			String username = (new Scanner(System.in)).nextLine();
			System.out.println(PASSWORD_ENTER);
			String password = (new Scanner(System.in)).nextLine();
			if(password != null && !password.equals("") && username != null && !username.equals("")){
				try {
					//Try to login
					auth = Login.acquireConnection(username, password);//username = csstudent471 password = password
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return auth;

	}
	//This function gets other registered members and prints them out.
	private static void printOthers(String auth){
		Getter get = new Getter(auth);
		String others = get.getOthers();
		
		XMLReader read = new XMLReader(others);
		read.deserialize();
		HashMap<String, ArrayList<String>> hash = read.getHash();
		
		if(hash.containsKey("memberid")){
			ArrayList<String> ids = hash.get("memberid");
			ArrayList<String> names = hash.get("membername");
			
			for(int x = 0; x < ids.size(); x++){
				System.out.println(ids.get(x) + ") " + names.get(x));
			}
			System.out.println("");
		}
	}

	/** This function is the menu function once the user is logged in. 
	 * @param auth - The authentication
	 * @return
	 */
	public static void Menu(String auth){
		try{
						
			System.out.println(LOGGEDIN);
			boolean cond = true;
			while(cond){
				System.out.println(MENU);
				Scanner scan = new Scanner(System.in);
				String selection = scan.nextLine();
				if(selection.trim().equals("1")){
					printOthers(auth);
				}
				else if(selection.trim().equals("2")){
					//Print the other registered number
					printOthers(auth);					
					System.out.println("Enter their number:");
					String number = scan.nextLine();															
					//Get the input from the user and establish the threads that will scan for new incoming messages and text from the user. 
					Messenger mes = new Messenger(auth);
					Reader red = new Reader(auth, new String[]{number.trim()});
					red.start();
					mes.start();
					red.join();
					mes.setShouldRun(false);
					mes.join();
				}
				else if(selection.trim().equals("3")){
					return;
				}
				else{
					System.out.println("Invalid Input");
				}
				
			}
			/*Get the list of people*/
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
