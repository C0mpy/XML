package app.util;

import com.marklogic.client.DatabaseClientFactory;

public class MarklogicProperties {
	
	// common data for both marklogic and mysql database
    public static String USER = "root";
    public static String PASS = "root";
    public static String HOST = "localhost";
    public static String DATABASE = "xml";
    public static int PORT = 8000;
	public static DatabaseClientFactory.Authentication AUTH = DatabaseClientFactory.Authentication.DIGEST;
    public static boolean PROXY = false;
    
}
