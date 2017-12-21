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

	public static void main(String[] args) {
/*
		/**
		 * this code take csv files and put the data into objects of WifiNetworkImport
		 
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
*/
		/**
		 * this code take csv files and put the data into objects of DataToExport
		 */
	
		List<DataToExport> DataToExportList = new ArrayList<>();
		List<File> csvFiles2 = DataToExport.getFilesListForDataToExport();

		for (File file : csvFiles2) {
			List<DataToExport> convertCsvToDataToExport = DataToExport.convertCsvToDataToExport(file);
			DataToExportList.addAll(convertCsvToDataToExport);
		}
	
/*	/**
	 * this code ask the user befor the build of the kml if he want to filter by radius or time
	 
		List<MacImprove> MacImproveList = MacImprove.buildMacImproveList(wifiNetworkImportList);
		List<MacImprove> d = new ArrayList<>();
		List<MacImprove> t = new ArrayList<>();
		
		d=MacImprove.ReduceMacImproveList(MacImproveList);
		t=MacImprove.Algo1(d);
		
		MacImprove.saveToCsvFile(t);
		*/
		List<DataToExport> input = new ArrayList<>();
		input=DataToExportList;
		
		List<DataToExport> algo2 = new ArrayList<>();
		algo2=DataToExportWithPI.Algo2(input,DataToExportList);
		
		String newCsv = DataToExport.buildCSVData(algo2);
		KMLandCSVbuild.saveToCsvFile(newCsv);
	/*
		/**
		 * Sort By Coordinates/Time from Client
		 

		Scanner in = new Scanner(System.in);

		System.out.println("Enter what category you want to sort by (1=coordinate, 2=time):\n");
		int category = in.nextInt();

		if (category==1){
			System.out.println("Enter Lat:\n");
			double lat = in.nextDouble();
			System.out.println("Enter Lon:\n");
			double lon = in.nextDouble();
			System.out.println("Enter Radius:\n");
			double radius = in.nextDouble();

			List<DataToExport> sortListByC = DataToExport.SortListByC(dataToExportList, lat, lon, radius);
			dataToExportList = sortListByC;
		}

		if(category==2){
			System.out.println("Enter Time in format as the follow:(YYYY-MM-DD HH-MM-SS):\n");
			System.out.println(dataToExportList.get(0).getTime().toString()+"\n");
			String time = in.next();

			List<DataToExport> sortListByT = DataToExport.SortListByT(dataToExportList, time);
			dataToExportList = sortListByT;
		}

		String csvString = DataToExport.buildCSVData(dataToExportList);
		KMLandCSVbuild.saveToCsvFile(csvString);
		KMLandCSVbuild.saveTokmlFile(dataToExportList);
*/
	}

	

}
