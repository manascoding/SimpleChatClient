package messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/** This class is used for getting the other users from the server. 
 * @author Marek Kunz
 *	
 */
public class Getter {

	private static final String GETURL = "http://localhost/testing/getMembers.php";

	private String auth;

	public Getter(String auth){
		this.auth = auth;
	}

	/** Gets an XML with other registered people of the website. 
	 * @return - A String with an XML that has a list of other registered people
	 */
	public String getOthers(){
		URL url;
		try {
			url = new URL(GETURL + "?userID=" + this.auth);
			HttpURLConnection connection = null;  
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setUseCaches(false);
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			
			return response.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



}
