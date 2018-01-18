package main.java.matala0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * 
 * @description
 * the object has 11 unit from the csv file
 * the class convert the string to units at the object
 *
 */
public class WifiNetworkImport {
	

	public static String BASE_PATH2 = "C:\\objectoriented\\algo1";
	public static String CSV_FILE_NAME = "\\exportData.csv";
	public static String KML_FILE_NAME = "\\exportData.kml";
	public static final String SEPERATOR = ",";

	private String SSID;
	private String MAC;
	private String authMode;
	private Date firstSeen;
	private int channel;
	private int RSSI;
	private double currentLatitude;
	private double currentLongitude;
	private double altitudeMeters;
	private int accuracyMeters;
	private String type;

	public String getSSID() {
		return SSID;
	}
	public void setSSID(String sSID) {
		SSID = sSID;
	}
	public String getMAC() {
		return MAC;
	}
	public void setMAC(String mAC) {
		MAC = mAC;
	}
	public String getAuthMode() {
		return authMode;
	}
	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}
	public Date getFirstSeen() {
		return firstSeen;
	}
	public void setFirstSeen(Date firstSeen) {
		this.firstSeen = firstSeen;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getRSSI() {
		return RSSI;
	}
	public void setRSSI(int rSSI) {
		RSSI = rSSI;
	}
	public double getCurrentLatitude() {
		return currentLatitude;
	}
	public void setCurrentLatitude(double currentLatitude) {
		this.currentLatitude = currentLatitude;
	}
	public double getCurrentLongitude() {
		return currentLongitude;
	}
	public void setCurrentLongitude(double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}
	public double getAltitudeMeters() {
		return altitudeMeters;
	}
	public void setAltitudeMeters(double altitudeMeters) {
		this.altitudeMeters = altitudeMeters;
	}
	public int getAccuracyMeters() {
		return accuracyMeters;
	}
	public void setAccuracyMeters(int accuracyMeters) {
		this.accuracyMeters = accuracyMeters;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @the function convert every csv row to an WifiNetworkImport object
	 * @return list of WifiNetworkImport objects for each line
	 */
	public static List<WifiNetworkImport> convertCsvToWifiNetworkImport(File file){
		List<WifiNetworkImport> wifiNetworkImportList = new ArrayList<>();
		BufferedReader br = null;
		int i = 1;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;

			br.readLine();
			br.readLine();


			while ((line = br.readLine()) != null) {
		//		System.out.println("Line: " + i++ + ") " + line);
				WifiNetworkImport wifiNetworkImport = new WifiNetworkImport();
				String[] entries = line.split(",");
				wifiNetworkImport.setMAC(entries[0]);
				wifiNetworkImport.setSSID(entries[1]);
				wifiNetworkImport.setAuthMode(entries[2]);
				wifiNetworkImport.setFirstSeen(getDateFromString(entries[3]));
				wifiNetworkImport.setChannel(Integer.parseInt(entries[4]));
				wifiNetworkImport.setRSSI(Integer.parseInt(entries[5]));
				wifiNetworkImport.setCurrentLatitude(Double.parseDouble(entries[6]));
				wifiNetworkImport.setCurrentLongitude(Double.parseDouble(entries[7]));
				wifiNetworkImport.setAltitudeMeters(Double.parseDouble(entries[8]));
		//		wifiNetworkImport.setAccuracyMeters(Integer.parseInt(entries[9]));
		//		wifiNetworkImport.setType(entries[10]);

				if (wifiNetworkImport != null){
					wifiNetworkImportList.add(wifiNetworkImport);
				}
			}
		} catch (Exception e) {
			System.err.println("Failed to import file: " + file.getName() + ". On line: " + (i-1));
			e.printStackTrace();
		}finally {
			if (br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return wifiNetworkImportList;
	}

	
	/**
	 * 
	 * @param stringDate
	 * get string from csv file at the field date
	 * @return the same date as a Date object in Date format
	 */

	public static Date getDateFromString(String stringDate) {
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = formatter.parse(stringDate);
		} catch (ParseException e) {
			System.out.println("Failed to convert string to date for string: " + stringDate);
			e.printStackTrace();
		} 
		return date;
	}

	public static List<File> getFilesListForNetworkImport(String BASE_PATH) {
		Stream<Path> paths = null;
		List<File> csvFiles = new ArrayList<>();
		try {
			paths = Files.walk(Paths.get(BASE_PATH));
		} catch (IOException e) {
			System.err.println("Cano't find files in path");
			e.printStackTrace();
		}

		Stream<Path> filter = paths.filter(Files::isRegularFile);
		Stream<File> map = filter.map(Path::toFile);
		List<File> regularFiles = map.collect(Collectors.toList());

		for (File file : regularFiles) {
			String fileName = file.getName().toLowerCase();

			if (fileName.endsWith(".csv"))
			{
				csvFiles.add(file);
			}
		}
		return csvFiles;
	}

	
	
}
