package matala0;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WifiNetworkExportTest {

	
	@Test
	public void testBuildWifinetworkToExport() {
		
		WifiNetworkImport act = new WifiNetworkImport();
       	act.setMAC("test");
		act.setSSID("test");
		act.setAccuracyMeters(0);
		act.setRSSI(-3);
	
		
		
        	WifiNetworkExport ex = new WifiNetworkExport();
        	ex.setMAC("rdf,u.fu/");
		ex.setSSID("test");
		ex.setFreuncy(0);
		ex.setSignal(-3);
        
        WifiNetworkExport actual = WifiNetworkExport.buildWifinetworkToExport(act);
        
        String s = ""+actual.getMAC()+actual.getSSID()+actual.getFreuncy()+actual.getSignal();
        String t = ""+ex.getMAC()+ex.getSSID()+ex.getFreuncy()+ex.getSignal();;
        
		assertEquals(t, s);
	}

}
