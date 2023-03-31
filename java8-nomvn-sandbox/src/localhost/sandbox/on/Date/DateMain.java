package localhost.sandbox.on.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateMain {

	public static void main() {
	    
	    // general used DateFormat
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    
	    // current date
	    Date currentDate = new Date();
	    String currentDateStr = dateFormat.format(currentDate);
	    System.out.println("currentDateStr: " +  currentDateStr);
	    
        // current date plus 3 days
        Date plusThreeDaysDate = new Date();
        String plusThreeDaysDateStr = dateFormat.format(plusThreeDaysDate);
        System.out.println("plusThreeDaysDateStr: " +  plusThreeDaysDateStr);
	    
	    // current date minus 7 days
	    Date minusSevenDaysDate = new Date( System.currentTimeMillis() - ( 1000 * 3600 * 24 * 7 ) );
        String minusSevenDaysDateStr = dateFormat.format(minusSevenDaysDate);
        System.out.println("minusSevenDaysDateStr: " +  minusSevenDaysDateStr);
        
        // current date minus 30 days
        Date minusThirtyDaysDate = new Date( System.currentTimeMillis() - Long.valueOf(1000) * Long.valueOf(3600) * Long.valueOf(24*30) );
        String minusThirtyDaysDateStr = dateFormat.format(minusThirtyDaysDate);
        System.out.println("minusThirtyDaysDateStr: " +  minusThirtyDaysDateStr);
        
        // current date minus 90 days
        Date minusNinetyDaysDate = new Date( System.currentTimeMillis() - Long.valueOf(1000) * Long.valueOf(3600) * Long.valueOf(24*90) );
        String minusNinetyDaysDateStr = dateFormat.format(minusNinetyDaysDate);
        System.out.println("minusNinetyDaysDateStr: " +  minusNinetyDaysDateStr);

        // unix birthdate
        Date unixBirthDate = new Date(0);
        String unixBirthDateStr = dateFormat.format(unixBirthDate);
        System.out.println("unixBirthDateStr: " +  unixBirthDateStr);

        

	}
	
}	