package matala0;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		DataToExport ac = new DataToExport();
		
		List<DataToExport> acList = new ArrayList<>();
		acList.add(ac);	
		String actual = DataToExport.buildCSVData(acList);
		
		String expected = "55.5";
				
		assertEquals(expected, actual);
	}

}
