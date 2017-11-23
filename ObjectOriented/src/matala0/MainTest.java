package matala0;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSortWifiNetworksBySignal() {
		
		List<WifiNetworkExport> actual = new ArrayList<>();
		WifiNetworkExport big = new WifiNetworkExport();
		WifiNetworkExport mid = new WifiNetworkExport();
		WifiNetworkExport small = new WifiNetworkExport();
		
		big.setSignal(-60);
		mid.setSignal(-40);
		small.setSignal(-20);
		
		actual.add(small);
		actual.add(big);
		actual.add(mid);
		 
		Main.sortWifiNetworksBySignal(actual);
		
		List<WifiNetworkExport> expected = new ArrayList<>();

		expected.add(small);
		expected.add(mid);
		expected.add(big);
		
		String a = ""+actual.get(0)+actual.get(1)+actual.get(2)+"";
		String b = ""+expected.get(0)+expected.get(1)+expected.get(2)+"";
		
		assertEquals(a, b);	
	}

	@Test
	public void testBuildLineDescription() {
		DataToExport act = new DataToExport();
		Date time = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(2017, 9, 28, 20, 10, 00);
		time=cal.getTime();
		
		act.setId(123);
		act.setTime(time);
		act.setAlt(10);
		
		String expected = "Id: 123\nTime: Sat Oct 28 20:10:00 IDT 2017\nAlt: 10.0\n\n";
		String actual = Main.buildLineDescription(act);
		
		assertEquals(expected, actual);
		
	}

	@Test
	public void testBuildDescription() {
		fail("Not yet implemented");
	}


}
