package NetworkClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Streamer {
	private static HttpURLConnection httpConn;
	 
//  Makes an HTTP request using GET method to the specified URL.
    public static HttpURLConnection sendGetRequest(String requestURL)
            throws IOException {
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
 
        httpConn.setDoInput(true); 		// true if we want to read server's response
        httpConn.setDoOutput(false); 	// false indicates this is a GET request
 
        return httpConn;
    }
 
//  POSTS JSONObject to server. Used to create exams
    public static HttpURLConnection sendPostRequest(String requestURL,JSONObject params) throws IOException {
    	URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoInput(true); 		// true indicates the server returns response
 
        /*
        StringBuffer requestParams = new StringBuffer();
 
        if (params != null && params.size() > 0) {
 
        	httpConn.setDoOutput(true); // true indicates POST request
 
            // creates the params string, encode them using URLEncoder
            Iterator<String> paramIterator = params.keySet().iterator();
            while (paramIterator.hasNext()) 
            {
                String key = paramIterator.next();
                String value = params.get(key);
                requestParams.append(URLEncoder.encode(key, "UTF-8"));
                requestParams.append("=").append(URLEncoder.encode(value, "UTF-8"));
                requestParams.append("&");
            }
 
            // sends POST data
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write(requestParams.toString());
            writer.flush();
        }
        */
        return httpConn;
    }
    
    
    
//  Makes an HTTP request using POST method to the specified URL.
    public static HttpURLConnection sendPostRequest(String requestURL,Map<String, String> params) throws IOException {
    	URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
 
        httpConn.setDoInput(true); 		// true indicates the server returns response
 
        StringBuffer requestParams = new StringBuffer();
 
        if (params != null && params.size() > 0) {
 
        	httpConn.setDoOutput(true); // true indicates POST request
 
            // creates the params string, encode them using URLEncoder
            Iterator<String> paramIterator = params.keySet().iterator();
            while (paramIterator.hasNext()) 
            {
                String key = paramIterator.next();
                String value = params.get(key);
                requestParams.append(URLEncoder.encode(key, "UTF-8"));
                requestParams.append("=").append(URLEncoder.encode(value, "UTF-8"));
                requestParams.append("&");
            }
 
            // sends POST data
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write(requestParams.toString());
            writer.flush();
        }
        return httpConn;
    }
 
//     Returns only one line from the server's response. This method should be used if the server returns only a single line of String.
    public static String readSingleLineRespone() throws IOException {
        InputStream inputStream = null;
        if (httpConn != null) 
        {
            inputStream = httpConn.getInputStream();
        }
        else 
        {
            throw new IOException("Connection is not established.");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
 
        String response = reader.readLine();
        reader.close();
 
        return response;
    }
 

//    Returns an array of lines from the server's response. This method should be used if the server returns multiple lines of String.
    public static String[] readMultipleLinesRespone() throws IOException {
        InputStream inputStream = null;
        if (httpConn != null) 
        {
            inputStream = httpConn.getInputStream();
        }
        else 
        {
            throw new IOException("Connection is not established.");
        }
 
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> response = new ArrayList<String>();
 
        String line = "";
        while ((line = reader.readLine()) != null) 
        {
            response.add(line);
        }
        reader.close();
 
        return (String[]) response.toArray(new String[0]);
    }
     
    //Closes the connection if opened
    public static void disconnect() {
        if (httpConn != null){
            httpConn.disconnect();
        }
    }
}
