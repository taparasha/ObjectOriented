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

		List<WifiNetworkImport> wifiNetworkImportList = new ArrayList<>();

		List<File> csvFiles = KMLandCSVbuild.getFilesList();

		for (File file : csvFiles) {
			List<WifiNetworkImport> convertCsvToWifiNetworkImport = WifiNetworkImport.convertCsvToWifiNetworkImport(file);
			wifiNetworkImportList.addAll(convertCsvToWifiNetworkImport);
		}

		List<DataToExport> dataToExportList = DataToExport.buildDataToExportList(wifiNetworkImportList);
		List<MacImprove> MacImproveList = MacImprove.buildMacImproveList(wifiNetworkImportList);
		MacImprove.ReduceMacImproveList(MacImproveList);
         
		
		for (DataToExport dataToExport : dataToExportList) {
			List<WifiNetworkExport> sortWifiNetworksBySignal = sortWifiNetworksBySignal(dataToExport.getWifiNetworks());
			dataToExport.setWifiNetworks(sortWifiNetworksBySignal);
		}

		/**
		 * Sort By Coordinates/Time from Client
		 */

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

		//	List<WifiNetworkImport> matala2A = createListOfMac(wifiNetworkImportList);
		//	List<DataToExport> dataToExportList2A = DataToExport.buildDataToExportList(matala2A);
		//	String csvString2A = DataToExport.buildCSVData(dataToExportList2A);
		//	KMLandCSVbuild.saveToCsvFile(csvString2A);
		//	KMLandCSVbuild.saveTokmlFile(dataToExportList2A);
	}

	/**
	 * sortBySignal
	 * the function take the wifiNetworkExport and sort it by signal.
	 * at the writing, the program will write only the first 10
	 * 
	 * https://stackoverflow.com/questions/8432581/how-to-sort-a-listobject-alphabetically-using-object-name-field
	 */

	public static List<WifiNetworkExport> sortWifiNetworksBySignal(List<WifiNetworkExport> wifiNetworkExport){
		if (wifiNetworkExport.size() > 0) {
			Collections.sort(wifiNetworkExport, new Comparator<WifiNetworkExport>() {
				@Override
				public int compare(final WifiNetworkExport object1, final WifiNetworkExport object2) {
					return ((Integer)((object1.getSignal()) * (-1))).compareTo(((Integer)((object2.getSignal() * (-1)))));
				}
			});
		}
		return wifiNetworkExport;
	}

	/**
	 * מפה קוד של מטלה 2
	 * @param WifiNetworkImportList
	 * @return
	 */

}
