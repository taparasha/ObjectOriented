package matala0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.StyleSelector;
import de.micromata.opengis.kml.v_2_2_0.TimeSpan;
import de.micromata.opengis.kml.v_2_2_0.TimeStamp;

/**
 *
 * @author OriMol, TaParasha, YoavHenig
 * @category main
 */

public class Main {
	public static String BASE_PATH3 = "C:\\objectoriented\\algo2input";
	public static String BASE_PATH4 = "C:\\objectoriented\\algo2comb";
	public static void main(String[] args) {

		/**
		 * this code take csv files and put the data into objects of WifiNetworkImport (for matala 1)
		**/
		
		List<WifiNetworkImport> wifiNetworkImportList = new ArrayList<>();
		List<File> csvFiles = WifiNetworkImport.getFilesListForNetworkImport();

		for (File file : csvFiles) {
			List<WifiNetworkImport> convertCsvToWifiNetworkImport = WifiNetworkImport.convertCsvToWifiNetworkImport(file);
			wifiNetworkImportList.addAll(convertCsvToWifiNetworkImport);
		}

		List<DataToExport> dataToExportList = DataToExport.buildDataToExportList(wifiNetworkImportList);         
		
		for (DataToExport dataToExport : dataToExportList) {
			List<WifiNetworkExport> sortWifiNetworksBySignal = WifiNetworkExport.sortWifiNetworksBySignal(dataToExport.getWifiNetworks());
			dataToExport.setWifiNetworks(sortWifiNetworksBySignal);
		}
		
		List<DataToExport> a= new ArrayList<>();
		a=DataToExport.SortByUser(dataToExportList);
		
		String csvString = DataToExport.buildCSVData(dataToExportList);		
		KMLandCSVbuild.saveToCsvFile(csvString);
		KMLandCSVbuild.saveTokmlFile(dataToExportList);


		/**
		 * this code take csv files and put the data into objects of DataToExport (for matala 2)
		 **/
	
		List<DataToExport> comb = new ArrayList<>();
		List<DataToExport> input = new ArrayList<>();
		
		List<File> csvFiles3 = DataToExport.getFilesListForDataToExport(BASE_PATH3);
//		List<File> csvFiles4 = DataToExport.getFilesListForDataToExport(BASE_PATH4);

		for (File file : csvFiles3) {
			List<DataToExport> convertCsvToDataToExport = DataToExport.convertCsvToDataToExport(file);
			input.addAll(convertCsvToDataToExport);
			
		//	for (DataToExport z: input){
		//		System.out.println("mac: "+z.getAlt());
		//	}
		}
		
//		for (File file : csvFiles4) {
	//		List<DataToExport> convertCsvToDataToExport = DataToExport.convertCsvToDataToExport(file);
		//	comb.addAll(convertCsvToDataToExport);
		//}

		
		List<MacImprove> MacImproveList = MacImprove.buildMacImproveList(wifiNetworkImportList);
		List<MacImprove> d = new ArrayList<>();
		List<MacImprove> r = new ArrayList<>();
		
		d=MacImprove.ReduceMacImproveList(MacImproveList);
	    r=MacImprove.Algo1(d);
		
		MacImprove.saveToCsvFile(r);
		
		comb=dataToExportList;
		
		List<DataToExport> algo2 = new ArrayList<>();
		algo2=DataToExportWithPI.Algo2(comb,input);
		
		String newCsv = DataToExport.buildCSVData(algo2);
		KMLandCSVbuild.saveToCsvFile2(newCsv);

	}
}
