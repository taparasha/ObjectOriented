package matala0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @description
 * make list of abstract calld dataToExport with 6 parameters.
 * id, time, lat, lon, alt and list of the local wifiNetwork spots
 *
 */
public class DataToExport {

	public static String BASE_PATH1 = "C:\\objectoriented\\matala1";
	public static String BASE_PATH2 = "C:\\objectoriented\\algo1";
	public static String CSV_FILE_NAME = "\\exportData.csv";
	public static String KML_FILE_NAME = "\\exportData.kml";
	public static final String SEPERATOR = ",";


	private long id;
	private Date time;
	private double lat;
	private double lon;
	private double alt;
	private List<WifiNetworkExport> wifiNetworks = new ArrayList<>();

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getAlt() {
		return alt;
	}
	public void setAlt(double alt) {
		this.alt = alt;
	}
	public List<WifiNetworkExport> getWifiNetworks() {
		return wifiNetworks;
	}
	public void setWifiNetworks(List<WifiNetworkExport> wifiNetworks) {
		this.wifiNetworks = wifiNetworks;
	}

	/**
	 * 
	 * @param wifiNetworkImportList
	 * the function receive a list of WifiNetworkImports objects and added each object to DataToExport object at the list feild.
	 * @return list of DataToExport objects with the WifiNetworkImport dots in the list
	 */
	public static List<DataToExport> buildDataToExportList(List<WifiNetworkImport> wifiNetworkImportList) {
		List<DataToExport> dataToExportList = new ArrayList<>();
		int i = 1;
		for (WifiNetworkImport wifiNetworkImport : wifiNetworkImportList) {
			WifiNetworkExport wifiNetworkExport = WifiNetworkExport.buildWifinetworkToExport(wifiNetworkImport);
			boolean flag = true;
			for (DataToExport dataToExport : dataToExportList) {
				if (dataToExport.getTime().equals(wifiNetworkImport.getFirstSeen())){
					dataToExport.getWifiNetworks().add(wifiNetworkExport);
					flag = false;
					break;
				}
			}
			if (flag){
				DataToExport dataToExport = buildDataToExport(wifiNetworkImport, wifiNetworkExport,i++);
				dataToExportList.add(dataToExport);
			}
		}
		return dataToExportList;
	}

	/**
	 * @description make DataToExport object with 6 units. the sixth field is a list of WifiNetworkExport objects
	 */
	public static DataToExport buildDataToExport(WifiNetworkImport wifiNetworkImport,
			WifiNetworkExport wifiNetworkExport, int index) {
		DataToExport dataToExport = new DataToExport();
		dataToExport.setId(index);
		dataToExport.setAlt(wifiNetworkImport.getAltitudeMeters());
		dataToExport.setLat(wifiNetworkImport.getCurrentLatitude());
		dataToExport.setLon(wifiNetworkImport.getCurrentLongitude());
		dataToExport.setTime(wifiNetworkImport.getFirstSeen());
		dataToExport.getWifiNetworks().add(wifiNetworkExport);
		return dataToExport;
	}

	public static String getRowFromDataToExport(DataToExport dataToExport) {
		StringBuilder row = new StringBuilder();
		row.append(dataToExport.getTime()).append(SEPERATOR);
		row.append(dataToExport.getId()).append(SEPERATOR);
		row.append(dataToExport.getLat()).append(SEPERATOR);
		row.append(dataToExport.getLon()).append(SEPERATOR);
		row.append(dataToExport.getAlt()).append(SEPERATOR);
		List<WifiNetworkExport> wifiNetworksList = dataToExport.getWifiNetworks();
		int sizeUpToTen = getSizeUpToTen(wifiNetworksList.size());
		row.append("" + sizeUpToTen).append(SEPERATOR);
		for (int i = 0; i < sizeUpToTen; i++) {
			WifiNetworkExport wifiNetworkExport = wifiNetworksList.get(i);
			row.append(wifiNetworkExport.getSSID()).append(SEPERATOR);
			row.append(wifiNetworkExport.getMAC()).append(SEPERATOR);
			row.append(wifiNetworkExport.getFreuncy()).append(SEPERATOR);
			row.append(wifiNetworkExport.getSignal()).append(SEPERATOR);
		}
		return row.toString();
	}

	public static int getSizeUpToTen(int size) {
		if(size <= 10){
			return size;
		}
		return 10;
	}

	public static String buildCSVData(List<DataToExport> DataToExportList) {
		StringBuilder csvStringBuilder = buildHeadRow();
		csvStringBuilder.append("\n");
		for (DataToExport dataToExport : DataToExportList) {
			String rowFromDataToExport = getRowFromDataToExport(dataToExport);
			csvStringBuilder.append(rowFromDataToExport).append("\n");
		}
		String csvString = csvStringBuilder.toString();
		return csvString;
	}

	public static StringBuilder buildHeadRow() {
		StringBuilder headRow = new StringBuilder();
		headRow.append("Time").append(SEPERATOR);
		headRow.append("ID").append(SEPERATOR);
		headRow.append("Lat").append(SEPERATOR);
		headRow.append("Lon").append(SEPERATOR);
		headRow.append("Alt").append(SEPERATOR);
		headRow.append("#WiFi networks").append(SEPERATOR);
		for (int i = 1; i < 11; i++) {
			headRow.append("SSID" + i).append(SEPERATOR);
			headRow.append("MAC" + i).append(SEPERATOR);
			headRow.append("Frequncy" + i).append(SEPERATOR);
			headRow.append("Signal" + i).append(SEPERATOR);
		}
		return headRow;
	}

	//https://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
	/**
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * get 2 points
	 * @return the distance between
	 */
	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		double earthRadius = 6371000; //meters
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lon2-lon1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
				Math.sin(dLng/2) * Math.sin(dLng/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double dist = (float) (earthRadius * c);

		return dist;
	}
	/**
	 * 
	 * @param dataToExportList
	 * @param lat
	 * @param lon
	 * @param radius
	 * @return DataToExport list sorted by coordinates
	 */
	public static List<DataToExport> SortListByC(List<DataToExport> dataToExportList, double lat, double lon, double radius){
		List<DataToExport> sortList = new ArrayList<>();
		for(DataToExport a : dataToExportList){
			if(distance(a.lat, a.lon, lat, lon)/1000<=radius){
				sortList.add(a);
			}
		}
		return sortList;
	}
	/**
	 * 
	 * @param dataToExportList
	 * @param time
	 * @return DataToExport list sorted by time
	 */

	public static List<DataToExport> SortListByT(List<DataToExport> dataToExportList, String time){

		List<DataToExport> sortList = new ArrayList<>();
		for(DataToExport a : dataToExportList){
			if(a.getTime().toString().equals(time)){
				sortList.add(a);
			}
		}
		return sortList;
	}

	public static List<DataToExport> convertCsvToDataToExportfrominputfile(File file){
		List<DataToExport> dataToExportList = new ArrayList<>();
		int i = 1;

		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {
		//		System.out.println("Line: " + i++ + ") " + line);

				DataToExport dataToExport = new DataToExport();
				String[] entries = line.split(",");
        						
				for(int q=6; q<=entries.length - 3; q+=4){
					WifiNetworkExport temp = new WifiNetworkExport();
					temp.setMAC(entries[q+1]);
					temp.setSignal(Integer.parseInt(entries[q+3]));

					dataToExport.getWifiNetworks().add(temp);
				}

				if (dataToExport != null){
					dataToExportList.add(dataToExport);
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

		return dataToExportList;
	}

	public static List<DataToExport> convertCsvToDataToExportfromcombofile(File file){
		List<DataToExport> dataToExportList = new ArrayList<>();
		int i = 1;

		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			br.readLine();
			br.readLine();
			br.readLine();

			while ((line = br.readLine()) != null) {
		//		System.out.println("Line: " + i++ + ") " + line);

				DataToExport dataToExport = new DataToExport();
				String[] entries = line.split(",");
                 
				//dataToExport.setTime(WifiNetworkImport.getDateFromString(entries[0]));
        		dataToExport.setLat(Double.parseDouble(entries[2]));
				dataToExport.setLon(Double.parseDouble(entries[3]));
				dataToExport.setAlt(Double.parseDouble(entries[4]));
				
				for(int q=6; q<=entries.length - 3; q+=4){
					WifiNetworkExport temp = new WifiNetworkExport();
					temp.setMAC(entries[q+1]);
					temp.setSignal(Integer.parseInt(entries[q+3]));

					dataToExport.getWifiNetworks().add(temp);
				}

				if (dataToExport != null){
					dataToExportList.add(dataToExport);
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

		return dataToExportList;
	}

	
	public static List<File> getFilesListForDataToExport(String path) {
		Stream<Path> paths = null;
		List<File> csvFiles = new ArrayList<>();
		try {
			paths = Files.walk(Paths.get(path));
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

	/**
	 * this function let the user option to sort the csv and kml markplaces by time or by radius
	 **/


	public static void SortByUser(List<DataToExport> dataToExportList){
		try (Scanner in = new Scanner(System.in)) {	
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
