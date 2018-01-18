package test.java;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.matala0.DataToExport;
import main.java.matala0.WifiNetworkExport;
import main.java.matala0.WifiNetworkImport;

public class DataToExportTest {

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
	public void testBuildDataToExportList() {
		
		List<DataToExport> actual = new ArrayList<>();
		
		List<WifiNetworkImport> wList = new ArrayList<>();
		WifiNetworkImport w = new WifiNetworkImport();
		w.setAltitudeMeters(546.2);
		wList.add(w);
		
		actual = DataToExport.buildDataToExportList(wList);

		List<DataToExport> expected = new ArrayList<>();
		DataToExport ex = new DataToExport();
		ex.setAlt(546.2);
		expected.add(ex);
		
		String a =""+expected.get(0).getAlt();
		String b =""+actual.get(0).getAlt();
		
		assertEquals(a, b);
	}

	@Test
	public void testBuildDataToExport() {
		WifiNetworkImport ac = new WifiNetworkImport();
		ac.setAltitudeMeters(366.6);
		WifiNetworkExport wne = new WifiNetworkExport();
		wne.setSignal(0);
		
		DataToExport actual = new DataToExport();
		actual = DataToExport.buildDataToExport(ac,wne,0);
		
		DataToExport expected = new DataToExport();
		expected.setAlt(366.6);
		
		String a =""+expected.getAlt();
		String b =""+actual.getAlt();
		
		assertEquals(a, b);
	}

	@Test
	public void testBuildCSVData() {
		
		Date time = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(2017, 9, 28, 20, 10, 00);
		time=cal.getTime();
		
		DataToExport ac = new DataToExport();
		ac.setAlt(0.0);
		ac.setLat(0.0);
		ac.setLon(0.0);
		ac.setId(0);
		ac.setTime(time);
		
		WifiNetworkExport acExport = new WifiNetworkExport();
		acExport.setFreuncy(0);
		acExport.setMAC("test");
		acExport.setSignal(0);
		acExport.setSSID("test");
		
		List<WifiNetworkExport> acExportList = new ArrayList<>();
		acExportList.add(acExport);
		
		ac.setWifiNetworks(acExportList);
		
		List<DataToExport> acList = new ArrayList<>();
		acList.add(ac);	
		String actual = DataToExport.buildCSVData(acList);
		
		String expected = "Time,ID,Lat,Lon,Alt,#WiFi networks,SSID1,MAC1,Frequncy1,Signal1,SSID2,MAC2,Frequncy2,Signal2,SSID3,MAC3,Frequncy3,Signal3,SSID4,MAC4,Frequncy4,Signal4,SSID5,MAC5,Frequncy5,Signal5,SSID6,MAC6,Frequncy6,Signal6,SSID7,MAC7,Frequncy7,Signal7,SSID8,MAC8,Frequncy8,Signal8,SSID9,MAC9,Frequncy9,Signal9,SSID10,MAC10,Frequncy10,Signal10,\nSat Oct 28 20:10:00 IDT 2017,0,0.0,0.0,0.0,1,test,test,0,0,\n";
				
		assertEquals(expected, actual);
	}

}
