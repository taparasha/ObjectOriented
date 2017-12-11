package matala0;

import static org.junit.Assert.*;


import java.util.Calendar;
import java.util.Date;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WifiNetworkImportTest {

	

	
	@Test
	public void testGetDateFromString() {
		Date exepDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(2017, 9, 28, 20, 10, 00);
		exepDate=cal.getTime();
		
		String str = "2017-10-28 20:10:00";
		Date actDate = new Date();
		actDate = WifiNetworkImport.getDateFromString(str);
		
		
		assertEquals(exepDate.toString(), actDate.toString());
	
	}
}
