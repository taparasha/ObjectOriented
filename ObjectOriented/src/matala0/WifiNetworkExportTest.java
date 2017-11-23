package matala0;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WifiNetworkExportTest {

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
	public void testBuildWifinetworkToExport() {
		
		WifiNetworkImport act = new WifiNetworkImport();
       		act.setMAC("test");
		act.setSSID("test");
		act.setAccuracyMeters(0);
		act.setRSSI(-3);
	
		
		
        	WifiNetworkExport ex = new WifiNetworkExport();
        	ex.setMAC("test");
		ex.setSSID("test");
		ex.setFreuncy(0);
		ex.setSignal(-3);
        
        WifiNetworkExport actual = WifiNetworkExport.buildWifinetworkToExport(act);
        
        String s = ""+actual.getMAC()+actual.getSSID()+actual.getFreuncy()+actual.getSignal();
        String t = ""+ex.getMAC()+ex.getSSID()+ex.getFreuncy()+ex.getSignal();;
        
		assertEquals(t, s);
	}

}
