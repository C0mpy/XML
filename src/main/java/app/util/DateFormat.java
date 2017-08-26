package app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.text.ParseException;

public class DateFormat {
	
	// check if passed string is a valid Date
	// im using strings for date in my app to avoid all the trouble that comes with saving Date objects in DB
	// and passing them later backend - frontend
	public static boolean isValid(String string) {
		Date date = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			date = format.parse(string);
			if(!string.equals(format.format(date)))
				date = null;
		}
		catch (ParseException ex) {
			ex.printStackTrace();
		}
		
		return date != null;
	}
 
}
