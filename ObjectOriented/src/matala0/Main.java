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

public class Main {

	private static final String SEPERATOR = ",";
	private static String BASE_PATH = "C:\\tmp";
	private static String CSV_FILE_NAME = "\\exportData.csv";
	private static String KML_FILE_NAME = "\\exportData.kml";

	public static void main(String[] args) {

		List<WifiNetworkImport> wifiNetworkImportList = new ArrayList<>();

		List<File> csvFiles = getFilesList();

		for (File file : csvFiles) {
			List<WifiNetworkImport> convertCsvToWifiNetworkImport = convertCsvToWifiNetworkImport(file);
			wifiNetworkImportList.addAll(convertCsvToWifiNetworkImport);
		}

		List<DataToExport> dataToExportList = buildDataToExportList(wifiNetworkImportList);


		for (DataToExport dataToExport : dataToExportList) {
			List<WifiNetworkExport> sortWifiNetworksBySignal = sortWifiNetworksBySignal(dataToExport.getWifiNetworks());
			dataToExport.setWifiNetworks(sortWifiNetworksBySignal);
		}

		String csvString = buildCSVData(dataToExportList);
		saveToCsvFile(csvString);
		saveToKlmFile(dataToExportList);

	}

	// building kml file
	
	//https://labs.micromata.de/projects/jak/quickstart.html                             jar FILES
	//https://developers.google.com/kml/documentation/kml_tut#placemarks                 KML FORMAT
	//http://freegeographytools.com/2007/putting-time-data-into-a-kml-file               TIMELINE
	
	
	private static void saveToKlmFile(List<DataToExport> dataToExportList) {
		Kml kml = new Kml();
		Folder kmlFolder = kml.createAndSetFolder();
		Document kmlDocument = kml.createAndSetDocument();
		List<StyleSelector> styleSelectorList = getStyleSelectorList();
		kmlDocument.setStyleSelector(styleSelectorList);
		for (DataToExport dataToExport : dataToExportList) {
			List<WifiNetworkExport> wifiNetworks = dataToExport.getWifiNetworks();
			
			Coordinate cord = new Coordinate(dataToExport.getLon(),dataToExport.getLat());
			List<Coordinate> coordinates = new ArrayList<>();
			coordinates.add(cord);
			
			/*TimeStamp ts = new TimeStamp();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String s = df.format(dataToExport.getTime());
			ts.setWhen(s);

			List<TimeStamp> TimeStampList = new ArrayList<>();
			TimeStampList.add(ts);*/
			
			String lineDescription = buildLineDescription(dataToExport);
			for (WifiNetworkExport wifiNetwork : wifiNetworks) {
				String description = buildDescription(wifiNetwork, lineDescription);
				String styleURL = getStyleURL(wifiNetwork);
				Placemark placemark = new Placemark();
				placemark.createAndSetTimeStamp().setWhen(dataToExport.getTime().toString());
				placemark.withName(wifiNetwork.getSSID()).withDescription(description).withStyleUrl(styleURL);
				placemark.createAndSetPoint().withCoordinates(coordinates);
				
				//placemark.createAndSetTimeStamp().withWhen(TimeStampList);
				
				kmlFolder.addToFeature(placemark);
			}
		}
		kmlDocument.addToFeature(kmlFolder);
		try {
			kml.marshal(new File(BASE_PATH + KML_FILE_NAME));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static List<StyleSelector> getStyleSelectorList() {
		List<StyleSelector> styleSelectorList = new ArrayList<>();
		Style redStyle = getStyleByColorAndHref("red", "http://maps.google.com/mapfiles/ms/icons/red-dot.png");
		Style yellowStyle = getStyleByColorAndHref("yellow", "http://maps.google.com/mapfiles/ms/icons/yellow-dot.png");
		Style greenStyle = getStyleByColorAndHref("green", "http://maps.google.com/mapfiles/ms/icons/green-dot.png");
		Style blueStyle = getStyleByColorAndHref("blue", "http://maps.google.com/mapfiles/ms/icons/blue-dot.png");
		Style orangeStyle = getStyleByColorAndHref("orange", "http://maps.google.com/mapfiles/ms/icons/orange-dot.png");
		styleSelectorList.add(redStyle);
		styleSelectorList.add(yellowStyle);
		styleSelectorList.add(greenStyle);
		styleSelectorList.add(blueStyle);
		styleSelectorList.add(orangeStyle);
		return styleSelectorList;
	}

	private static Style getStyleByColorAndHref(String color, String href) {
		Style redStyle = new Style();
		redStyle.setId(color);
		IconStyle iconStyle = new IconStyle();
		iconStyle.withScale(1.0);
		iconStyle.createAndSetIcon().withHref(href);
		redStyle.setIconStyle(iconStyle);
		return redStyle;
	}
	
	private static String getStyleURL(WifiNetworkExport wifiNetwork) {
		String styleURL = "";
		if(wifiNetwork.getSignal() > -67 ) styleURL = "#red";
		else if(wifiNetwork.getSignal() > -70 ) styleURL = "#orange";
		else if(wifiNetwork.getSignal() > -80 ) styleURL = "#blue";
		else if(wifiNetwork.getSignal() > -90 ) styleURL = "#green";
		else styleURL = "#yellow";
		return styleURL;
	}

	private static String buildLineDescription(DataToExport dataToExport) {
		StringBuilder ret = new StringBuilder();
		ret.append("Id: " + dataToExport.getId() + "\n");
		ret.append("Time: " + dataToExport.getTime() + "\n");
		ret.append("Alt: " + dataToExport.getAlt() + "\n\n");
		return ret.toString();
	}

	private static String buildDescription(WifiNetworkExport wifiNetwork, String lineDescription) {
		StringBuilder ret = new StringBuilder();
		ret.append(lineDescription);
		ret.append("Freuncy: " + wifiNetwork.getFreuncy() + "\n");
		ret.append("MAC: " + wifiNetwork.getMAC() + "\n");
		ret.append("Signal: " + wifiNetwork.getSignal() + "\n");
		return ret.toString();
	}

	private static void saveToCsvFile(String csvString) {
		try(  PrintWriter out = new PrintWriter(BASE_PATH + CSV_FILE_NAME)  ){
			out.println(csvString);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to save CSV file");
			e.printStackTrace();
		}

	}

	private static List<DataToExport> buildDataToExportList(List<WifiNetworkImport> wifiNetworkImportList) {
		List<DataToExport> dataToExportList = new ArrayList<>();
		int i = 1;
		for (WifiNetworkImport wifiNetworkImport : wifiNetworkImportList) {
			WifiNetworkExport wifiNetworkExport = buildWifinetworkToExport(wifiNetworkImport);
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


	private static DataToExport buildDataToExport(WifiNetworkImport wifiNetworkImport,
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


	private static WifiNetworkExport buildWifinetworkToExport(WifiNetworkImport wifiNetworkImport) {
		WifiNetworkExport wifiNetworkExport = new WifiNetworkExport();
		wifiNetworkExport.setSSID(wifiNetworkImport.getSSID());
		wifiNetworkExport.setSignal(wifiNetworkImport.getRSSI());
		wifiNetworkExport.setMAC(wifiNetworkImport.getMAC());
		wifiNetworkExport.setFreuncy(wifiNetworkImport.getAccuracyMeters());
		return wifiNetworkExport;
	}


	private static List<WifiNetworkImport> convertCsvToWifiNetworkImport(File file){
		List<WifiNetworkImport> wifiNetworkImportList = new ArrayList<>();
		BufferedReader br = null;
		int i = 1;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;

			br.readLine();
			br.readLine();


			while ((line = br.readLine()) != null) {
				System.out.println("Line: " + i++ + ") " + line);
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
				wifiNetworkImport.setAccuracyMeters(Integer.parseInt(entries[9]));
				wifiNetworkImport.setType(entries[10]);

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


	private static Date getDateFromString(String stringDate) {
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			date = formatter.parse(stringDate);
		} catch (ParseException e) {
			System.out.println("Failed to convert string to date for string: " + stringDate);
			e.printStackTrace();
		} 
		return date;
	}



	private static List<File> getFilesList() {
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

	//https://stackoverflow.com/questions/8432581/how-to-sort-a-listobject-alphabetically-using-object-name-field
	private static List<WifiNetworkExport> sortWifiNetworksBySignal(List<WifiNetworkExport> wifiNetworkExport){
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


	private static String buildCSVData(List<DataToExport> DataToExportList) {
		StringBuilder csvStringBuilder = buildHeadRow();
		csvStringBuilder.append("\n");
		for (DataToExport dataToExport : DataToExportList) {
			String rowFromDataToExport = getRowFromDataToExport(dataToExport);
			csvStringBuilder.append(rowFromDataToExport).append("\n");
		}
		String csvString = csvStringBuilder.toString();
		return csvString;
	}


	private static String getRowFromDataToExport(DataToExport dataToExport) {
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


	private static int getSizeUpToTen(int size) {
		if(size <= 10){
			return size;
		}
		return 10;
	}


	private static StringBuilder buildHeadRow() {
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
}
