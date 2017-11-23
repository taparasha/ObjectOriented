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
		
        WifiNetworkExport ex = new WifiNetworkExport();
        ex.setMAC("test");
        
        WifiNetworkExport actual = WifiNetworkExport.buildWifinetworkToExport(act);
        
        String s = ""+actual.getMAC();
        String t = ""+ex.getMAC();
        
		assertEquals(t, s);
	}

}
