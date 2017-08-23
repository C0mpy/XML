package app.util;

import com.marklogic.client.DatabaseClientFactory;

public class MarklogicProperties {
	
	// common data for both marklogic and mysql database
    public static String user = "root";
    public static String password = "root";
    public static String host = "localhost";
    public static String database = "xml";
    public static int marklogicPort = 8000;
	public static DatabaseClientFactory.Authentication auth = DatabaseClientFactory.Authentication.DIGEST;
    public static boolean proxy = false;
    
}
